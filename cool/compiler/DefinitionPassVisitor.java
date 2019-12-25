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
        return null;
    }

    @Override
    public Void visit(If iff) {
        iff.cond.accept(this);
        iff.thenBranch.accept(this);
        if (iff.elseBranch != null) {
            iff.elseBranch.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Formal formal) {
        var name = formal.name;
        var attributeSymbol = new AttributeSymbol(name.token.getText());
        attributeSymbol.setType(SymbolTable.globals.lookupClassSymbol(formal.type.token.getText()));
        var currentMethodScope = (MethodSymbol) currentScope;
        var currentMethodClassScope = (ClassSymbol) currentMethodScope.getParent();
        if (name.token.getText().equals("self")) {
            SymbolTable.error(formal.ctx, formal.token, "Method " + currentMethodScope + " of class " + currentMethodClassScope
                    + " has formal parameter with illegal name " + name.token.getText());
        }
        if (!currentScope.add(attributeSymbol)) {
            SymbolTable.error(formal.ctx, formal.token,
                    "Method " + currentMethodScope + " of class " + currentMethodClassScope +
                            " redefines formal parameter " + name.token.getText());
        }
        formal.setScope(currentScope);
        formal.setSymbol(attributeSymbol);
        return null;
    }

    @Override
    public Void visit(FuncDef funcDef) {
        var name = funcDef.name;
        var methodSymbol = new MethodSymbol(currentScope, name.token.getText());

        methodSymbol.setType(SymbolTable.globals.lookupClassSymbol(funcDef.return_type.token.getText()));
        //check if method is redefined
        if (!currentScope.add(methodSymbol)) {
            SymbolTable.error(funcDef.ctx, funcDef.token, "Class " + currentScope + " redefines method " + name.token.getText());
//            return null;
        }

        // enter method scope

        currentScope = methodSymbol;
        methodSymbol.setFunctionNode(funcDef);
        funcDef.setSymbol(methodSymbol);
        // set method scope
        funcDef.setScope(currentScope);
        // visit parameters and body
        for (var formal : funcDef.params) {
            formal.accept(this);
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
        var self = new AttributeSymbol("self");
        self.setType(SymbolTable.globals.lookupClassSymbol("SELF_TYPE"));
        classSymbol.add(self);
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
        call.setSymbol(call.getParentClassSymbol().lookupMethodSymbol(call.name.token.getText()));
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

    @Override
    public Void visit(Let let) {
        var letScope = new MethodSymbol(currentScope, "let");
        currentScope = letScope;
        for (var v : let.variables) {
            v.accept(this);
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
        var name = varDef.name;
        var attributeSymbol = new AttributeSymbol(name.token.getText());
//        if(varDef.type.token.getText().equals("SELF_TYPE")) {
//            attributeSymbol.setType((ClassSymbol) currentScope);
//        } else {
        attributeSymbol.setType(SymbolTable.globals.lookupClassSymbol(varDef.type.token.getText()));
//        }

        if (name.token.getText().equals("self")) {
            SymbolTable.error(varDef.ctx, varDef.token, "Class " + currentScope + " has attribute with illegal name " + name.token.getText());
        }
        if (!currentScope.add(attributeSymbol)) {
            SymbolTable.error(varDef.ctx, varDef.token, "Class " + currentScope + " redefines attribute " + name.token.getText());
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

        var name = letLocal.name;
        var letSymbol = new MethodSymbol(currentScope, "let_" + name.token.getText());
        var letLocalSymbol = new AttributeSymbol(name.token.getText());
        letLocalSymbol.setType(SymbolTable.globals.lookupClassSymbol(letLocal.type.token.getText()));
        if (name.token.getText().equals("self")) {
            SymbolTable.error(letLocal.ctx, letLocal.token, "Let variable has illegal name " + name.token.getText());
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
        var name = caseBranch.name;
        var caseBranchScope = new MethodSymbol(currentScope, "caseBranch");
        var caseBranchAttributeSymbol = new AttributeSymbol(name.token.getText());
        caseBranchAttributeSymbol.setType(SymbolTable.globals.lookupClassSymbol(caseBranch.type.token.getText()));
        currentScope = caseBranchScope;
        currentScope.add(caseBranchAttributeSymbol);
        if (name.token.getText().equals("self")) {
            SymbolTable.error(caseBranch.ctx, caseBranch.token, "Case variable has illegal name " + name.token.getText());
        }
        caseBranch.setScope(currentScope);
        caseBranch.setSymbol(caseBranchAttributeSymbol);
        caseBranch.expression.accept(this);
        currentScope = currentScope.getParent();

        return null;
    }
}
