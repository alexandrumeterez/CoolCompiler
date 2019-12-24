package cool.compiler;

import cool.structures.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ResolutionPassVisitor implements ASTVisitor<Symbol> {

    @Override
    public Symbol visit(TypeId id) {
        id.setSymbol(SymbolTable.globals.lookupClassSymbol(id.token.getText()));
        return id.getSymbol();
    }

    @Override
    public Symbol visit(ObjectId objectId) {
        objectId.setSymbol(objectId.getScope().lookupAttributeSymbol(objectId.token.getText()));
        if (objectId.getSymbol() == null) {
            return null;
        }
        return ((AttributeSymbol) objectId.getSymbol()).getType();
    }

    @Override
    public Symbol visit(If iff) {
        var type = iff.cond.accept(this);
        if (type != BasicClasses.BOOL) {
            SymbolTable.error(iff.ctx, iff.cond.token,
                    "If condition has type " + type.getName()
                            + " instead of Bool");
        }
        var thenT = iff.thenBranch.accept(this);
        if (iff.elseBranch == null) {
            return thenT;
        }
        var elseT = iff.elseBranch.accept(this);

        if (thenT != elseT) {
            // find meething point on path to Object
            var parent = ((ClassSymbol) elseT).getParentClassSymbol();
            while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
                if (thenT == parent) {
                    return thenT;
                }
                parent = parent.getParentClassSymbol();
            }

            parent = ((ClassSymbol) thenT).getParentClassSymbol();
            while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
                if (elseT == parent) {
                    return elseT;
                }
                parent = parent.getParentClassSymbol();
            }

            return BasicClasses.OBJECT;
        }

        return thenT;
    }

    @Override
    public Symbol visit(Formal formal) {
        var name = formal.name;
        var type = formal.type;
        var methodScope = (MethodSymbol) formal.getScope();
        var classScope = (ClassSymbol) methodScope.getParent();

        if (type.token.getText().equals("SELF_TYPE")) {
            SymbolTable.error(formal.ctx, type.token, "Method " + methodScope +
                    " of class " + classScope + " has formal parameter " + name.token.getText() +
                    " with illegal type " + type.token.getText());
        } else if (SymbolTable.globals.lookupClassSymbol(type.token.getText()) == null) {
            SymbolTable.error(formal.ctx, type.token, "Method " + methodScope + " of class " +
                    classScope + " has formal parameter " + name.token.getText() +
                    " with undefined type " + type.token.getText());
        }
        return null;
    }

    @Override
    public Symbol visit(FuncDef funcDef) {
        var type = funcDef.return_type;
        var name = funcDef.name;
        // class that contains this function
        var classScope = (ClassSymbol) funcDef.getScope().getParent();
        if (SymbolTable.globals.lookupClassSymbol(type.token.getText()) == null) {
            SymbolTable.error(funcDef.ctx, funcDef.token, "Class " +
                    classScope + " has method " + name.token.getText() +
                    " with undefined return type " + type.token.getText());
            return null;
        }

        // get parent class of class that has this function
        var parentClassSymbol = (ClassSymbol) classScope.getParentClassSymbol();
        if (parentClassSymbol != null) {
            //get method with same name in the parent class(if exists)
            var parentMethodSymbol = parentClassSymbol.lookupMethodSymbol(name.token.getText());
            if (parentMethodSymbol != null) {
                var parentMethodASTNode = (FuncDef) parentMethodSymbol.getFunctionNode();
                // check number of formal params
                if (funcDef.params.size() != parentMethodASTNode.params.size()) {
                    SymbolTable.error(funcDef.ctx, funcDef.token, "Class " + classScope + " overrides method " +
                            name.token.getText() +
                            " with different number of formal parameters");
                }

                // check parameter types

                for (int i = 0; i < Math.min(funcDef.params.size(), parentMethodASTNode.params.size()); i++) {
                    var formal = funcDef.params.get(i);
                    var parentFormal = parentMethodASTNode.params.get(i);
                    if (!formal.type.token.getText().equals(parentFormal.type.token.getText())) {
                        SymbolTable.error(funcDef.ctx, formal.type.token, "Class " +
                                classScope + " overrides method " + name.token.getText() +
                                " but changes type of formal parameter " +
                                formal.name.token.getText() + " from " +
                                parentFormal.type.token.getText() + " to " +
                                formal.type.token.getText());
                    }
                }

                // check return type
                if (!type.token.getText().equals(parentMethodASTNode.return_type.token.getText())) {
                    SymbolTable.error(funcDef.ctx, type.token, "Class " +
                            classScope + " overrides method " +
                            name.token.getText() +
                            " but changes return type from " +
                            parentMethodASTNode.return_type.token.getText() + " to " +
                            type.token.getText());
                }
            }
        }

        for (var formal : funcDef.params) {
            formal.accept(this);
        }
        // get method return type
        var methodReturnType = ((MethodSymbol) funcDef.getSymbol()).getType();
        if (methodReturnType == null) {
            return null;
        }

        if (funcDef.func_body != null) {
            var bodyType = funcDef.func_body.accept(this);
            if (bodyType == null) {
                return methodReturnType;
            }


            // check for SELF_TYPE in the function definition
            if(bodyType.getName().equals("SELF_TYPE") && methodReturnType.getName().equals("SELF_TYPE")) {
                return methodReturnType;
            }
            if (bodyType.getName().equals("SELF_TYPE")) {
                bodyType = (ClassSymbol)funcDef.getScope().getParent();
            }

            var parent = ((ClassSymbol) bodyType).getParentClassSymbol();
            while (parent != null) {
                if (parent.getName().equals(BasicClasses.OBJECT.getName())) {
                    if (methodReturnType == parent) {
                        return methodReturnType;
                    }
                    break;
                }
                if (methodReturnType == parent) {
                    return methodReturnType;
                }
                parent = parent.getParentClassSymbol();
            }
            if (methodReturnType != bodyType) {
                SymbolTable.error(funcDef.ctx, funcDef.func_body.token, "Type " +
                        bodyType +
                        " of the body of method " +
                        name.token.getText() +
                        " is incompatible with declared return type " +
                        methodReturnType);
            }
        }
        return methodReturnType;
    }

    @Override
    public Symbol visit(ClassDef classDef) {
        for (var member : classDef.features) {
            member.accept(this);
        }
        return null;
    }

    @Override
    public Symbol visit(AssignExpr assignExpr) {
        var left = assignExpr.name.accept(this);
        var right = assignExpr.e.accept(this);
        // check if the left is SELF_TYPE
//        if (left.getName().equals("SELF_TYPE")) {
//            // if left is self_type, then set left as the type of the class it's in
//            var currentScope = assignExpr.getScope();
//            while (!(currentScope instanceof ClassSymbol)) {
//                currentScope = currentScope.getParent();
//            }
//            left = SymbolTable.globals.lookupClassSymbol(((ClassSymbol) currentScope).getName());
//        }


        // if there is no assignment, then return the left type
        if (right == null) {
            return left;
        }
        if(left.getName().equals(right.getName())) {
            return right;
        }

//        // check if the right is SELF_TYPE
//        if (right.getName().equals("SELF_TYPE")) {
//            // if right is self_type, then set right as the type of the class it's in
//            var currentScope = assignExpr.getScope();
//            while (!(currentScope instanceof ClassSymbol)) {
//                currentScope = currentScope.getParent();
//            }
//            right = SymbolTable.globals.lookupClassSymbol(((ClassSymbol) currentScope).getName());
//        }

        // search on the type tree to see if the right expression
        // matches the type with the left
        var parent = ((ClassSymbol) right).getParentClassSymbol();
//        System.out.println(assignExpr.e.token.getLine() + ":" + assignExpr.e.token.getCharPositionInLine() + " " + right + " " + parent);

        while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
//            System.out.println("WHILE " + assignExpr.e.token.getLine() + ":" + assignExpr.e.token.getCharPositionInLine() + " " + right + " " + parent);
            if (left == parent) {
                return left;
            }

            parent = parent.getParentClassSymbol();
        }
        // if the types dont match, then report error
        if (left != right) {
            SymbolTable.error(assignExpr.ctx, assignExpr.e.token, "Type "
                    + right
                    + " of assigned expression is incompatible with declared type "
                    + left + " of identifier "
                    + assignExpr.name.token.getText());
        }

        return right;
    }

    @Override
    public Symbol visit(Dispatch dispatch) {
        var caller = dispatch.object;
        var callerType = (ClassSymbol) caller.accept(this);
        var deepestCallerType = callerType;
//        System.out.println(callerType);
        // check if callerType is self type
        if (callerType.getName().equals("SELF_TYPE")) {
            // set caller type as the type of the class the expression is in
            var currentScope = dispatch.getScope();
            while (!(currentScope instanceof ClassSymbol)) {
                currentScope = currentScope.getParent();
            }
            callerType = SymbolTable.globals.lookupClassSymbol(((ClassSymbol) currentScope).getName());
        }

        var atClass = dispatch.class_name;
        ClassSymbol atClassType = null;
        if (atClass != null) {
            atClassType = (ClassSymbol) atClass.accept(this);
            if (atClass.token.getText().equals("SELF_TYPE")) {
                SymbolTable.error(atClass.ctx, atClass.token, "Type of static dispatch cannot be SELF_TYPE");
                return null;
            }

            if (atClassType == null) {
                SymbolTable.error(dispatch.ctx, atClass.token, "Type " +
                        atClass.token.getText() +
                        " of static dispatch is undefined");
                return null;
            }

            if (callerType != null) {
                var parent = ((ClassSymbol) callerType).getParentClassSymbol();
                while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
                    if (atClassType == parent) {
                        callerType = atClassType;
                        break;
                    }
                    parent = parent.getParentClassSymbol();
                }
                if (atClassType != callerType) {
                    SymbolTable.error(dispatch.ctx, dispatch.class_name.token, "Type " +
                            atClassType + " of static dispatch is not a superclass of type " +
                            callerType);
                    return null;
                }
            }
        }


        // check if the called function is ok
        if (callerType == null) {
            return null;
        }
        // check if method exists in caller type class
        var callSymbol = (MethodSymbol) callerType.lookupMethodSymbol(dispatch.call.name.token.getText());
        if(atClass != null) {
            callSymbol = (MethodSymbol) atClassType.lookupMethodSymbol(dispatch.call.name.token.getText());
        }
        var call = dispatch.call;

//        System.out.println(callSymbol.getType());

        if (callSymbol == null) {
            SymbolTable.error(call.ctx, call.name.token, "Undefined method " +
                    call.name.token.getText() + " in class " + callerType);
            return null;
        }

        // check
        var methodFormals = callSymbol.getAttributeSymbols();
        var callArguments = call.args;
        if (methodFormals.size() != callArguments.size()) {
            SymbolTable.error(call.ctx, call.name.token, "Method " +
                    call.name.token.getText() + " of class " +
                    callerType + " is applied to wrong number of arguments");
        }
        // check if types of call match types of method arguments
        var methodFormalsList = new ArrayList<>(methodFormals.entrySet());
        for (int i = 0; i < Math.min(methodFormals.size(), callArguments.size()); i++) {
            var methodFormal = methodFormalsList.get(i).getValue();
            var callArgument = callArguments.get(i);
            var callArgumentType = callArgument.accept(this);
            var methodFormalType = methodFormal.getType();

            if (callArgumentType != null) {

                var parent = ((ClassSymbol) callArgumentType).getParentClassSymbol();
                while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
                    if (methodFormalType == parent) {
                        callArgumentType = methodFormalType;
                        break;
                    }

                    parent = parent.getParentClassSymbol();
                }
                if (methodFormalType != callArgumentType) {
                    SymbolTable.error(call.ctx, callArgument.token, "In call to method " +
                            call.name.token.getText() + " of class " +
                            callerType +
                            ", actual type " + callArgumentType +
                            " of formal parameter " + methodFormal + " is incompatible with declared type " +
                            methodFormalType);
                }

            }
        }

        // if the return type is self type
//        System.out.println(callSymbol.getType());
        if (callSymbol.getType().getName().equals("SELF_TYPE")) {
            // check the caller type
            // if caller type is self_type, then return the type of the class where the dispatch appears
            // TODO: REDO THISinherits
            if (callerType.getName().equals("SELF_TYPE")) {
                var currentClass = dispatch.getScope();
                while (!(currentClass instanceof ClassSymbol)) {
                    currentClass = currentClass.getParent();
                }
                return SymbolTable.globals.lookupClassSymbol(((ClassSymbol) currentClass).getName());
            }
            // if caller type is not self_type, then return the caller type
            return deepestCallerType;
        }
        return callSymbol.getType();
    }

    @Override
    public Symbol visit(Call call) {
        var parentClassSymbol = call.getParentClassSymbol();
        var callSymbol = (MethodSymbol) call.getSymbol();
        // check if method exists in parent class
        if (callSymbol == null) {
            SymbolTable.error(call.ctx, call.name.token, "Undefined method " +
                    call.name.token.getText() + " in class " + parentClassSymbol.getName());
            return null;
        }
        // check if the method is called with the correct number of arguments
        var methodFormals = callSymbol.getAttributeSymbols();
        var callArguments = call.args;
        if (methodFormals.size() != callArguments.size()) {
            SymbolTable.error(call.ctx, call.name.token, "Method " +
                    call.name.token.getText() + " of class " +
                    parentClassSymbol + " is applied to wrong number of arguments");
        }

        // check if types of call match types of method arguments
        var methodFormalsList = new ArrayList<>(methodFormals.entrySet());
        for (int i = 0; i < Math.min(methodFormals.size(), callArguments.size()); i++) {
            var methodFormal = methodFormalsList.get(i).getValue();
            var callArgument = callArguments.get(i);
            var callArgumentType = callArgument.accept(this);
            var methodFormalType = methodFormal.getType();

            if (callArgumentType != null) {

                var parent = ((ClassSymbol) callArgumentType).getParentClassSymbol();
                while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
                    if (methodFormalType == parent) {
                        callArgumentType = methodFormalType;
                        break;
                    }

                    parent = parent.getParentClassSymbol();
                }
                if (methodFormalType != callArgumentType) {
                    SymbolTable.error(call.ctx, callArgument.token, "In call to method " +
                            call.name.token.getText() + " of class " +
                            parentClassSymbol +
                            ", actual type " + callArgumentType +
                            " of formal parameter " + methodFormal + " is incompatible with declared type " +
                            methodFormalType);
                }

            }
        }


        return callSymbol.getType();
    }

    @Override
    public Symbol visit(While while1) {
        var type = while1.cond.accept(this);
        if (type != BasicClasses.BOOL) {
            SymbolTable.error(while1.ctx, while1.cond.token,
                    "While condition has type " + type.getName()
                            + " instead of Bool");
        }
        return BasicClasses.OBJECT;
    }

    @Override
    public Symbol visit(Block block) {
        ClassSymbol ret = null;
        for (var e : block.block_expressions) {
            ret = (ClassSymbol) e.accept(this);
        }
        return ret;
    }

    @Override
    public Symbol visit(Let let) {
        for (var v : let.variables) {
            v.accept(this);
        }

        return let.let_block_expr.accept(this);
    }

    @Override
    public Symbol visit(Case case1) {
        case1.cond.accept(this);
        ClassSymbol returnType = null;
        for (var c : case1.caseBranches) {
            var branchType = (ClassSymbol) c.accept(this);
            var returnParent = returnType == null ? null : ((ClassSymbol) returnType).getParentClassSymbol();
            var branchParent = branchType == null ? null : ((ClassSymbol) branchType).getParentClassSymbol();
            //System.out.println(returnType + " " + branchType);
            //System.out.println();
            if (returnType == null) {
                returnType = branchType;
                continue;
            }
            if (branchType == null || branchType == returnType) {
                continue;
            }
            while (branchParent != null) {
                if (branchParent.getName().equals(BasicClasses.OBJECT.getName())) {
                    break;
                }
                if (returnType == branchParent) {
                    returnType = branchParent;
                    returnParent = null;
                    break;
                }
                branchParent = (ClassSymbol) branchParent.getParentClassSymbol();
            }
            while (returnParent != null) {
                if (returnParent.getName().equals(BasicClasses.OBJECT.getName())) {
                    returnType = BasicClasses.OBJECT;
                    break;
                }
                if (branchType == returnParent) {
                    returnType = returnParent;
                    break;
                }
                returnParent = (ClassSymbol) returnParent.getParentClassSymbol();
            }
        }
        return returnType;
    }

    @Override
    public Symbol visit(NewType newType) {
        var type = newType.type;

        if (SymbolTable.globals.lookupClassSymbol(type.token.getText()) == null) {
            SymbolTable.error(newType.ctx, type.token,
                    "new is used with undefined type " + type.token.getText());
        }
        return newType.type.accept(this);
    }

    @Override
    public Symbol visit(IsVoidExpr isVoidExpr) {
        isVoidExpr.e.accept(this);
        return BasicClasses.BOOL;
    }

    @Override
    public Symbol visit(Mult mult) {
        var leftType = mult.left.accept(this);
        var rightType = mult.right.accept(this);
        if (leftType != BasicClasses.INT) {
            SymbolTable.error(mult.ctx, mult.left.token,
                    "Operand of " + mult.token.getText()
                            + " has type " + leftType
                            + " instead of Int");
        }
        if (rightType != BasicClasses.INT) {
            SymbolTable.error(mult.ctx, mult.right.token,
                    "Operand of " + mult.token.getText()
                            + " has type " + rightType.getName()
                            + " instead of Int");
        }
        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Div division) {
        var leftType = division.left.accept(this);
        var rightType = division.right.accept(this);
        if (leftType != BasicClasses.INT) {
            SymbolTable.error(division.ctx, division.left.token,
                    "Operand of " + division.token.getText() + " has type "
                            + leftType.getName() + " instead of Int");
        }
        if (rightType != BasicClasses.INT) {
            SymbolTable.error(division.ctx, division.right.token,
                    "Operand of " + division.token.getText() + " has type "
                            + rightType.getName() + " instead of Int");
        }
        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Plus addition) {
        var leftType = addition.left.accept(this);
        var rightType = addition.right.accept(this);
        if (leftType == null || rightType == null) {
            return BasicClasses.INT;
        }
        if (leftType != BasicClasses.INT) {
            SymbolTable.error(addition.ctx, addition.left.token,
                    "Operand of " + addition.token.getText() + " has type "
                            + leftType.getName() + " instead of Int");
        }
        if (rightType != BasicClasses.INT) {
            SymbolTable.error(addition.ctx, addition.right.token,
                    "Operand of " + addition.token.getText() + " has type "
                            + rightType.getName() + " instead of Int");
        }
        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Minus subtraction) {
        var leftType = subtraction.left.accept(this);
        var rightType = subtraction.right.accept(this);
        if (leftType != BasicClasses.INT) {
            SymbolTable.error(subtraction.ctx, subtraction.left.token,
                    "Operand of " + subtraction.token.getText() + " has type "
                            + leftType + " instead of Int");
        }
        if (rightType != BasicClasses.INT) {
            SymbolTable.error(subtraction.ctx, subtraction.right.token,
                    "Operand of " + subtraction.token.getText() + " has type "
                            + rightType + " instead of Int");
        }
        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(TildeExpr tildeExpr) {
        var type = tildeExpr.e.accept(this);
        if (type != BasicClasses.INT) {
            SymbolTable.error(tildeExpr.ctx, tildeExpr.e.token,
                    "Operand of " + tildeExpr.token.getText() + " has type "
                            + type + " instead of Int");
        }
        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Relational relational) {
        var leftType = relational.left.accept(this);
        var rightType = relational.right.accept(this);
        if (leftType != BasicClasses.INT) {
            SymbolTable.error(relational.ctx, relational.left.token,
                    "Operand of " + relational.operation + " has type "
                            + leftType.getName() + " instead of Int");
        }
        if (rightType != BasicClasses.INT) {
            SymbolTable.error(relational.ctx, relational.right.token,
                    "Operand of " + relational.operation + " has type "
                            + rightType.getName() + " instead of Int");
        }
        return BasicClasses.BOOL;
    }

    @Override
    public Symbol visit(Equal equal) {
        var leftType = equal.left.accept(this);
        var rightType = equal.right.accept(this);
        if (leftType == BasicClasses.INT || leftType == BasicClasses.BOOL
                || leftType == BasicClasses.STRING) {
            if (leftType != rightType) {
                SymbolTable.error(equal.ctx, equal.token, "Cannot compare "
                        + leftType.getName() + " with " + rightType.getName());
            }
        } else if (rightType == BasicClasses.INT || rightType == BasicClasses.BOOL
                || rightType == BasicClasses.STRING) {
            if (rightType != leftType) {
                SymbolTable.error(equal.ctx, equal.token, "Cannot compare "
                        + leftType.getName() + " with " + rightType.getName());
            }
        }
        return BasicClasses.BOOL;
    }

    @Override
    public Symbol visit(NotExpr notExpr) {
        var type = notExpr.e.accept(this);
        if (type != BasicClasses.BOOL) {
            SymbolTable.error(notExpr.ctx, notExpr.e.token,
                    "Operand of " + notExpr.token.getText() + " has type "
                            + type.getName() + " instead of Bool");
        }
        return BasicClasses.BOOL;
    }

    @Override
    public Symbol visit(Paren paren) {
        return paren.e.accept(this);
    }

    @Override
    public Symbol visit(Simple simple) {
        if (!simple.token.getText().equals("self")) {
            if (simple.getScope().lookupAttributeSymbol(simple.token.getText()) == null) {
                SymbolTable.error(simple.ctx, simple.token,
                        "Undefined identifier " + simple.token.getText());
            }
        }

        simple.setSymbol(simple.getScope().lookupAttributeSymbol(simple.token.getText()));
        if (simple.getSymbol() == null) {
            return null;
        }
        return ((AttributeSymbol) simple.getSymbol()).getType();
    }

    @Override
    public Symbol visit(Int int1) {
        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Str str) {
        return BasicClasses.STRING;
    }

    @Override
    public Symbol visit(Bool bool) {
        return BasicClasses.BOOL;
    }

    @Override
    public Symbol visit(Program program) {
        for (var c : program.class_list) {
            c.accept(this);
        }
        return null;
    }

    @Override
    public Symbol visit(VarDef varDef) {
        var name = varDef.name;
        var type = varDef.type;
        var scopeClass = (ClassSymbol) varDef.getScope();
        if (scopeClass == null) {
            return null;
        }
        var parentClass = scopeClass.getParentClassSymbol();
        // leave it alone if it doesnt inherit anything
        if (parentClass == null) {
            return null;
        }
        // check if attribute is redefined by searching in parent class
        if (parentClass.lookupAttributeSymbol(name.token.getText()) != null) {
            SymbolTable.error(varDef.ctx, varDef.token, "Class " + scopeClass + " redefines inherited attribute " + name.token.getText());
        }

        if (SymbolTable.globals.lookupClassSymbol(type.token.getText()) == null) {
            SymbolTable.error(varDef.ctx, type.token, "Class " + scopeClass + " has attribute " + name.token.getText() + " with undefined type " + type.token.getText());
        }

        var lvalueType = ((AttributeSymbol) varDef.getSymbol()).getType();
        if (lvalueType == null) {
            return null;
        }

        if (varDef.init != null) {
            var rvalueType = varDef.init.accept(this);
            if (rvalueType == null) {
                return null;
            }

            var parent = ((ClassSymbol) rvalueType).getParentClassSymbol();
            while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
                if (lvalueType == parent) {
                    return lvalueType;
                }
                parent = parent.getParentClassSymbol();
            }
            if (lvalueType != rvalueType) {
                SymbolTable.error(varDef.ctx, varDef.init.token, "Type " + rvalueType +
                        " of initialization expression of attribute " +
                        name.token.getText() +
                        " is incompatible with declared type " +
                        lvalueType.getName());
            }
        }

        return lvalueType;
    }

    @Override
    public Symbol visit(Self self) {
        return self.accept(this);
    }

    @Override
    public Symbol visit(LetLocal letLocal) {
        var name = letLocal.name;
        var type = letLocal.type;
        if (!(type.token.getText().equals("SELF_TYPE")) && SymbolTable.globals.lookupClassSymbol(type.token.getText()) == null) {
            SymbolTable.error(letLocal.ctx, type.token,
                    "Let variable " + name.token.getText()
                            + " has undefined type " + type.token.getText());
        }
        var nameType = ((AttributeSymbol) letLocal.getSymbol()).getType();
        if (nameType == null) {
            return null;
        }
        if (letLocal.e != null) {
            var expressionType = letLocal.e.accept(this);
            if (expressionType == null) {
                return nameType;
            }
            var parent = ((ClassSymbol) expressionType).getParentClassSymbol();
            while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
                if (nameType == parent) {
                    return nameType;
                }
                parent = parent.getParentClassSymbol();
            }
            if (nameType != expressionType) {
                SymbolTable.error(letLocal.ctx, letLocal.e.token, "Type "
                        + expressionType
                        + " of initialization expression of identifier "
                        + name.token.getText()
                        + " is incompatible with declared type "
                        + nameType);
            }
        }
        return nameType;
    }

    @Override
    public Symbol visit(CaseBranch caseBranch) {
        var name = caseBranch.name;
        var type = caseBranch.type;
        if (type.token.getText().equals("SELF_TYPE")) {
            SymbolTable.error(caseBranch.ctx, type.token,
                    "Case variable " + name.token.getText()
                            + " has illegal type " + type.token.getText());
            return null;
        } else if (SymbolTable.globals
                .lookupClassSymbol(type.token.getText()) == null) {
            SymbolTable.error(caseBranch.ctx, type.token,
                    "Case variable " + name.token.getText()
                            + " has undefined type " + type.token.getText());
        }
        return caseBranch.expression.accept(this);
    }
}
