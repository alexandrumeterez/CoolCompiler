package cool.compiler;

import cool.structures.*;
import org.antlr.v4.runtime.Token;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ResolutionPassVisitor implements ASTVisitor<Symbol> {
    ClassSymbol getLUB(ClassSymbol type1, ClassSymbol type2) {
        var parent = ((ClassSymbol) type1).getParentClassSymbol();
        while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
            if (type2 == parent) {
                return type2;
            }
            parent = parent.getParentClassSymbol();
        }
        return null;
    }

    @Override
    public Symbol visit(TypeId id) {
        id.setSymbol(SymbolTable.globals.lookupClassSymbol(id.token.getText()));
        return id.getSymbol();
    }

    @Override
    public Symbol visit(ObjectId objectId) {
        if (!objectId.token.getText().equals("self")) {
            if (objectId.getScope().lookupAttributeSymbol(objectId.token.getText()) == null) {
                SymbolTable.error(objectId.ctx, objectId.token,
                        "Undefined identifier " + objectId.token.getText());
            }
        }
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

        if (thenT.getName().equals("SELF_TYPE")) {
            var currentScope = iff.getScope();
            while (!(currentScope instanceof ClassSymbol)) {
                currentScope = currentScope.getParent();
            }
            thenT = (ClassSymbol) currentScope;
        }
        if (elseT.getName().equals("SELF_TYPE")) {
            var currentScope = iff.getScope();
            while (!(currentScope instanceof ClassSymbol)) {
                currentScope = currentScope.getParent();
            }
            elseT = (ClassSymbol) currentScope;
        }


        if (thenT != elseT) {
            // find meething point on path to Object
            var LUB = getLUB((ClassSymbol) thenT, (ClassSymbol) elseT);
            if (LUB != null) {
                return LUB;
            }
            LUB = getLUB((ClassSymbol) elseT, (ClassSymbol) thenT);
            if (LUB != null) {
                return LUB;
            }


            return BasicClasses.OBJECT;
        }

        return thenT;
    }

    @Override
    public Symbol visit(Formal formal) {
        var methodScope = (MethodSymbol) formal.getScope();
        var classScope = (ClassSymbol) methodScope.getParent();

        if (formal.type.token.getText().equals("SELF_TYPE")) {
            SymbolTable.error(formal.ctx, formal.type.token, "Method " + methodScope +
                    " of class " + classScope + " has formal parameter " + formal.name.token.getText() +
                    " with illegal type " + formal.type.token.getText());
        } else if (SymbolTable.globals.lookupClassSymbol(formal.type.token.getText()) == null) {
            SymbolTable.error(formal.ctx, formal.type.token, "Method " + methodScope + " of class " +
                    classScope + " has formal parameter " + formal.name.token.getText() +
                    " with undefined type " + formal.type.token.getText());
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
                // check number of formal params
                if (funcDef.params.size() != parentMethodSymbol.getParams().size()) {
                    SymbolTable.error(funcDef.ctx, funcDef.token, "Class " + classScope + " overrides method " +
                            name.token.getText() +
                            " with different number of formal parameters");
                }

                // check parameter types

                for (int i = 0; i < Math.min(funcDef.params.size(), parentMethodSymbol.getParams().size()); i++) {
                    if (!funcDef.params.get(i).type.token.getText().equals(parentMethodSymbol.getParams().get(i))) {
                        SymbolTable.error(funcDef.ctx, funcDef.params.get(i).type.token, "Class " +
                                classScope + " overrides method " + name.token.getText() +
                                " but changes type of formal parameter " +
                                funcDef.params.get(i).name.token.getText() + " from " +
                                parentMethodSymbol.getParams().get(i) + " to " +
                                funcDef.params.get(i).type.token.getText());
                    }
                }

                // check return type
                if (!type.token.getText().equals(parentMethodSymbol.getReturn_type())) {
                    SymbolTable.error(funcDef.ctx, type.token, "Class " +
                            classScope + " overrides method " +
                            name.token.getText() +
                            " but changes return type from " +
                            parentMethodSymbol.getReturn_type() + " to " +
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
            if (bodyType.getName().equals("SELF_TYPE") && methodReturnType.getName().equals("SELF_TYPE")) {
                return methodReturnType;
            }
            if (bodyType.getName().equals("SELF_TYPE")) {
                bodyType = (ClassSymbol) funcDef.getScope().getParent();
            }
            var parent = ((ClassSymbol) bodyType).getParentClassSymbol();
            while (parent != null) {
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


        // if there is no assignment, then return the left type
        if (right == null) {
            return left;
        }
        if (left.getName().equals(right.getName())) {
            return right;
        }

        // search on the type tree to see if the right expression
        // matches the type with the left
        var parent = ((ClassSymbol) right).getParentClassSymbol();

        while (parent != null && !parent.getName().equals(BasicClasses.OBJECT.getName())) {
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
        if (atClass != null) {
            callSymbol = (MethodSymbol) atClassType.lookupMethodSymbol(dispatch.call.name.token.getText());
        }
        dispatch.call.setSymbol(callSymbol);

        var call = dispatch.call;


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


                while (parent != null) {
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
            call.setSymbol(call.getParentClassSymbol().lookupMethodSymbol(call.name.token.getText()));
        }
        callSymbol = (MethodSymbol) call.getSymbol();
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
                if (!callArgumentType.getName().equals(methodFormalType.getName())) {
                    while (parent != null) {
                        if (methodFormalType == parent) {
                            callArgumentType = methodFormalType;
                            break;
                        }

                        parent = parent.getParentClassSymbol();
                    }
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
            ClassSymbol returnParent;
            if (returnType == null) {
                returnParent = null;
            } else {
                returnParent = returnType.getParentClassSymbol();
            }
            ClassSymbol branchParent;
            if (branchType == null) {
                branchParent = null;
            } else {
                branchParent = branchType.getParentClassSymbol();
            }

            if (returnType == null) {
                returnType = branchType;
                continue;
            }
            if (branchType == null || branchType == returnType) {
                continue;
            }

            boolean matched = false;
            while (branchParent != null) {
                if (branchParent.getName().equals(BasicClasses.OBJECT.getName())) {
                    break;
                }
                if (returnType == branchParent) {
                    matched = true;
                    break;
                }
                branchParent = (ClassSymbol) branchParent.getParentClassSymbol();
            }

            if (matched) {
                continue;
            }

            while (returnParent != null) {
                if (branchType == returnParent || returnParent == BasicClasses.OBJECT) {
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

        if (SymbolTable.globals.lookupClassSymbol(newType.type.token.getText()) == null) {
            SymbolTable.error(newType.ctx, newType.type.token,
                    "new is used with undefined type " + newType.type.token.getText());
        }
        return newType.type.accept(this);
    }

    @Override
    public Symbol visit(IsVoidExpr isVoidExpr) {
        isVoidExpr.e.accept(this);
        return BasicClasses.BOOL;
    }

    void checkBadOperandType(ASTNode op, ASTNode left, ASTNode right, String operation, Symbol leftType, Symbol rightType) {
        if (leftType != BasicClasses.INT) {
            SymbolTable.error(op.ctx, left.token,
                    "Operand of " + operation + " has type " + leftType
                            + " instead of Int");
        }
        if (rightType != BasicClasses.INT) {
            SymbolTable.error(op.ctx, right.token,
                    "Operand of " + operation + " has type " + rightType
                            + " instead of Int");
        }
    }

    @Override
    public Symbol visit(Mult mult) {
        var leftType = mult.left.accept(this);
        var rightType = mult.right.accept(this);
        checkBadOperandType(mult, mult.left, mult.right, mult.token.getText(), leftType, rightType);
        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Div division) {
        var leftType = division.left.accept(this);
        var rightType = division.right.accept(this);
        checkBadOperandType(division, division.left, division.right, division.token.getText(), leftType, rightType);

        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Plus addition) {
        var leftType = addition.left.accept(this);
        var rightType = addition.right.accept(this);
        if (leftType == null || rightType == null) {
            return BasicClasses.INT;
        }
        checkBadOperandType(addition, addition.left, addition.right, addition.token.getText(), leftType, rightType);

        return BasicClasses.INT;
    }

    @Override
    public Symbol visit(Minus subtraction) {
        var leftType = subtraction.left.accept(this);
        var rightType = subtraction.right.accept(this);
        checkBadOperandType(subtraction, subtraction.left, subtraction.right, subtraction.token.getText(), leftType, rightType);

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
        checkBadOperandType(relational, relational.left, relational.right, relational.operation, leftType, rightType);

        return BasicClasses.BOOL;
    }

    boolean isBasicType(Symbol type) {
        return type.getName().equals("Int") || type.getName().equals("Bool") || type.getName().equals("String");
    }

    @Override
    public Symbol visit(Equal equal) {
        var leftType = equal.left.accept(this);
        var rightType = equal.right.accept(this);

        if (isBasicType(leftType) || isBasicType(rightType)) {
            if (leftType != rightType) {
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

            var LUB = getLUB((ClassSymbol) rvalueType, (ClassSymbol) lvalueType);
            if (LUB != null) {
                return LUB;
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
        if (!(letLocal.type.token.getText().equals("SELF_TYPE")) && SymbolTable.globals.lookupClassSymbol(letLocal.type.token.getText()) == null) {
            SymbolTable.error(letLocal.ctx, letLocal.type.token,
                    "Let variable " + letLocal.name.token.getText()
                            + " has undefined type " + letLocal.type.token.getText());
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
            var LUB = getLUB((ClassSymbol) expressionType, (ClassSymbol) nameType);
            if (LUB != null) {
                return LUB;
            }

            if (nameType != expressionType) {
                SymbolTable.error(letLocal.ctx, letLocal.e.token, "Type "
                        + expressionType
                        + " of initialization expression of identifier "
                        + letLocal.name.token.getText()
                        + " is incompatible with declared type "
                        + nameType);
            }
        }
        return nameType;
    }

    @Override
    public Symbol visit(CaseBranch caseBranch) {
        if (caseBranch.type.token.getText().equals("SELF_TYPE")) {
            SymbolTable.error(caseBranch.ctx, caseBranch.type.token,
                    "Case variable " + caseBranch.name.token.getText()
                            + " has illegal type " + caseBranch.type.token.getText());
            return null;
        }
        if (SymbolTable.globals
                .lookupClassSymbol(caseBranch.type.token.getText()) == null) {
            SymbolTable.error(caseBranch.ctx, caseBranch.type.token,
                    "Case variable " + caseBranch.name.token.getText()
                            + " has undefined type " + caseBranch.type.token.getText());
        }
        return caseBranch.expression.accept(this);
    }
}
