package cool.compiler;

import cool.structures.*;
import org.w3c.dom.Attr;

public class DefinitionPassVisitor implements ASTVisitor<Void> {
    Scope currentScope = SymbolTable.globals;

    @Override
    public Void visit(TypeId id) {
        id.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ObjectId objectId) {
        objectId.setScope(currentScope);
        objectId.setSymbol(objectId.getScope().lookupAttributeSymbol(objectId.token.getText()));

        return null;
    }

    @Override
    public Void visit(If iff) {
        iff.cond.accept(this);
        iff.thenBranch.accept(this);
        if (iff.elseBranch != null) {
            iff.elseBranch.accept(this);
        }
        iff.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(Formal formal) {
        var attributeSymbol = new AttributeSymbol(formal.name.token.getText());
        attributeSymbol.setType(SymbolTable.globals.lookupClassSymbol(formal.type.token.getText()));
        attributeSymbol.setOffset(offset);
        attributeSymbol.setLocation("fp");
        var currentMethodScope = (MethodSymbol) currentScope;
        var currentMethodClassScope = (ClassSymbol) currentMethodScope.getParent();
        if (formal.name.token.getText().equals("self")) {
            SymbolTable.error(formal.ctx, formal.token, "Method " + currentMethodScope + " of class " + currentMethodClassScope
                    + " has formal parameter with illegal name " + formal.name.token.getText());
        }
        boolean notExists = currentScope.add(attributeSymbol);
        if (!notExists) {
            SymbolTable.error(formal.ctx, formal.token,
                    "Method " + currentMethodScope + " of class " + currentMethodClassScope +
                            " redefines formal parameter " + formal.name.token.getText());
        }
        formal.setScope(currentScope);
        formal.setSymbol(attributeSymbol);
        return null;
    }

    int offset = 12;
    @Override
    public Void visit(FuncDef funcDef) {
        offset = 12;
        var name = funcDef.name;
        var methodSymbol = new MethodSymbol(currentScope, name.token.getText());

        methodSymbol.setType(SymbolTable.globals.lookupClassSymbol(funcDef.return_type.token.getText()));
        //check if method is redefined
        boolean notExists = currentScope.add(methodSymbol);
        if (!notExists) {
            SymbolTable.error(funcDef.ctx, funcDef.token, "Class " + currentScope + " redefines method " + name.token.getText());
        }

        // enter method scope

        currentScope = methodSymbol;
        for(var v : funcDef.params) {
            methodSymbol.getParams().add(v.type.token.getText());
        }
        methodSymbol.setReturn_type(funcDef.return_type.token.getText());
        funcDef.setSymbol(methodSymbol);
        // set method scope
        funcDef.setScope(currentScope);
        // visit parameters and body
        for (var formal : funcDef.params) {
            formal.accept(this);
            offset += 4;
        }
        funcDef.func_body.accept(this);
        currentScope = currentScope.getParent();


        return null;
    }

    @Override
    public Void visit(ClassDef classDef) {
        var classSymbol = SymbolTable.globals.lookupClassSymbol(classDef.class_type.token.getText());
        // check if class is good
        if (classSymbol == null) {
            return null;
        }

        // set current scope as current class
        currentScope = classSymbol;

        // set self types: the type itself and its children
        var currentType = classSymbol;
        classSymbol.getSelfTypesList().add(currentType.getName());
        // TODO: Modify this to work with multiple inheritances
        while (BuildClassGraphPassVisitor.parentToChild.containsKey(currentType.getName())) {
            var childTypeName = BuildClassGraphPassVisitor.parentToChild.get(currentType.getName());

            currentType = SymbolTable.globals.lookupClassSymbol(childTypeName);
            if (currentType == null) {
                break;
            }
            classSymbol.getSelfTypesList().add(currentType.getName());

        }


        classDef.setSymbol(classSymbol);
        classDef.setScope(currentScope);
        for (var member : classDef.features) {
            member.accept(this);
        }
        AttributeSymbol selfAttribute = new AttributeSymbol("self");
        selfAttribute.setType(SymbolTable.globals.lookupClassSymbol("SELF_TYPE"));
        classSymbol.add(selfAttribute);
        currentScope = currentScope.getParent();

        return null;
    }

    @Override
    public Void visit(AssignExpr assignExpr) {
        assignExpr.setScope(currentScope);
        if (assignExpr.name.token.getText().equals("self")) {
            SymbolTable.error(assignExpr.ctx, assignExpr.name.token, "Cannot assign to self");
        }
        assignExpr.name.accept(this);
        assignExpr.e.accept(this);
        return null;
    }

    @Override
    public Void visit(Dispatch dispatch) {
        if (dispatch.object != null) {
            dispatch.object.accept(this);
        }
        dispatch.call.accept(this);
        dispatch.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(Call call) {
        for (var a : call.args) {
            a.accept(this);
        }

        call.setScope(currentScope);
        call.setParentClassSymbolFromCurrentScope(currentScope);

        // set for special classes
        var functionName = call.name.token.getText();
        switch (functionName) {
            case "abort":
            case "type_name":
            case "copy":
                call.setSymbol(BasicClasses.OBJECT.lookupMethodSymbol(functionName));
                break;
            case "length":
            case "concat":
            case "substr":
                call.setSymbol(BasicClasses.STRING.lookupMethodSymbol(functionName));
                break;
            case "out_int":
            case "out_string":
            case "in_string":
            case "in_int":
                call.setSymbol(BasicClasses.IO.lookupMethodSymbol(functionName));
                break;
            default:
                call.setSymbol(call.getParentClassSymbol().lookupMethodSymbol(call.name.token.getText()));
                break;
        }

        return null;
    }

    @Override
    public Void visit(While while1) {
        while1.cond.accept(this);
        while1.whileBody.accept(this);
        return null;
    }

    @Override
    public Void visit(Block block) {
        for (var expression : block.block_expressions) {
            expression.accept(this);
        }
        return null;
    }

    int letOffset = -4;
    @Override
    public Void visit(Let let) {
        letOffset = -4;
        var letScope = new MethodSymbol(currentScope, "let");
        currentScope = letScope;
        for (var v : let.variables) {
            v.accept(this);
            letOffset -= 4;
        }
        let.let_block_expr.accept(this);
        currentScope = letScope.getParent();

        return null;
    }

    @Override
    public Void visit(Case case1) {
        case1.cond.accept(this);
        for (var c : case1.caseBranches) {
            c.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(NewType newType) {
        newType.type.accept(this);
        return null;
    }

    @Override
    public Void visit(IsVoidExpr isVoidExpr) {
        isVoidExpr.e.accept(this);
        return null;
    }

    @Override
    public Void visit(Mult mult) {
        mult.left.accept(this);
        mult.right.accept(this);
        return null;
    }

    @Override
    public Void visit(Div div) {
        div.left.accept(this);
        div.right.accept(this);
        return null;
    }

    @Override
    public Void visit(Plus plus) {
        plus.left.accept(this);
        plus.right.accept(this);
        return null;
    }

    @Override
    public Void visit(Minus minus) {
        minus.left.accept(this);
        minus.right.accept(this);
        return null;
    }

    @Override
    public Void visit(TildeExpr tildeExpr) {
        tildeExpr.e.accept(this);
        return null;
    }

    @Override
    public Void visit(Relational relational) {
        relational.left.accept(this);
        relational.right.accept(this);
        return null;
    }

    @Override
    public Void visit(Equal equal) {
        equal.left.accept(this);
        equal.right.accept(this);
        return null;
    }

    @Override
    public Void visit(NotExpr notExpr) {
        notExpr.e.accept(this);
        return null;
    }

    @Override
    public Void visit(Paren paren) {
        paren.e.accept(this);
        return null;
    }

    @Override
    public Void visit(Int int1) {
        return null;
    }

    @Override
    public Void visit(Str str) {
        return null;
    }

    @Override
    public Void visit(Bool bool) {
        return null;
    }

    @Override
    public Void visit(Program program) {
        currentScope = SymbolTable.globals;
        for (var classNode : program.class_list) {
            classNode.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(VarDef varDef) {
        var attributeSymbol = new AttributeSymbol(varDef.name.token.getText());
        attributeSymbol.setType(SymbolTable.globals.lookupClassSymbol(varDef.type.token.getText()));

        if (varDef.name.token.getText().equals("self")) {
            SymbolTable.error(varDef.ctx, varDef.token, "Class " + currentScope + " has attribute with illegal name " + varDef.name.token.getText());
        }
        boolean notExists = currentScope.add(attributeSymbol);
        if (!notExists) {
            SymbolTable.error(varDef.ctx, varDef.token, "Class " + currentScope + " redefines attribute " + varDef.name.token.getText());
            return null;
        }

        varDef.setScope(currentScope);
        varDef.setSymbol(attributeSymbol);
        if (varDef.init != null) {
            varDef.init.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Self self) {
        self.accept(this);
        return null;
    }

    @Override
    public Void visit(LetLocal letLocal) {
        var letSymbol = new MethodSymbol(currentScope, "let_" + letLocal.name.token.getText());
        var letLocalSymbol = new AttributeSymbol(letLocal.name.token.getText());
        letLocalSymbol.setType(SymbolTable.globals.lookupClassSymbol(letLocal.type.token.getText()));
        letLocalSymbol.setOffset(letOffset);
        letLocalSymbol.setLocation("fp");
        if (letLocal.name.token.getText().equals("self")) {
            SymbolTable.error(letLocal.ctx, letLocal.token, "Let variable has illegal name " + letLocal.name.token.getText());
        }
        letLocal.setScope(letSymbol);
        if (letLocal.e != null) {
            letLocal.e.accept(this);
        }
        currentScope = letSymbol;
        currentScope.add(letLocalSymbol);
        letLocal.setSymbol(letLocalSymbol);

        return null;
    }

    @Override
    public Void visit(CaseBranch caseBranch) {
        var caseBranchScope = new MethodSymbol(currentScope, "caseBranch");
        var caseBranchAttributeSymbol = new AttributeSymbol(caseBranch.name.token.getText());
        caseBranchAttributeSymbol.setType(SymbolTable.globals.lookupClassSymbol(caseBranch.type.token.getText()));
        caseBranchAttributeSymbol.setLocation("fp");
        caseBranchAttributeSymbol.setOffset(-4);
        currentScope = caseBranchScope;
        currentScope.add(caseBranchAttributeSymbol);
        if (caseBranch.name.token.getText().equals("self")) {
            SymbolTable.error(caseBranch.ctx, caseBranch.token, "Case variable has illegal name " + caseBranch.name.token.getText());
        }
        caseBranch.setScope(currentScope);
        caseBranch.setSymbol(caseBranchAttributeSymbol);
        caseBranch.expression.accept(this);
        currentScope = currentScope.getParent();

        return null;
    }
}
