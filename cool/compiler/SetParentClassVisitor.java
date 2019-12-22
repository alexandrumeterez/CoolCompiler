package cool.compiler;

import cool.structures.BasicClasses;
import cool.structures.SymbolTable;

public class SetParentClassVisitor implements ASTVisitor<Void> {
    @Override
    public Void visit(TypeId id) {
        return null;
    }

    @Override
    public Void visit(ObjectId objectId) {
        return null;
    }

    @Override
    public Void visit(If iff) {
        return null;
    }

    @Override
    public Void visit(Formal formal) {
        return null;
    }

    @Override
    public Void visit(FuncDef funcDef) {
        return null;
    }

    @Override
    public Void visit(ClassDef classDef) {
        var baseClass = classDef.inherits_type;
        var classType = classDef.class_type;
        var classSymbol = SymbolTable.globals.lookupClassSymbol(classType.token.getText());
        if (classSymbol == null) {
            return null;
        }
        classSymbol.setParent(SymbolTable.globals);
        if (baseClass == null) {
            classSymbol.setParentClassSymbol(BasicClasses.OBJECT);
            return null;
        } else {
            var parentClassSymbol = SymbolTable.globals.lookupClassSymbol(baseClass.token.getText());
            if (parentClassSymbol == null) {
                return null;
            }
            classSymbol.setParentClassSymbol(parentClassSymbol);
        }

        return null;
    }

    @Override
    public Void visit(AssignExpr assignExpr) {
        return null;
    }

    @Override
    public Void visit(Dispatch dispatch) {
        return null;
    }

    @Override
    public Void visit(Call call) {
        return null;
    }

    @Override
    public Void visit(While while1) {
        return null;
    }

    @Override
    public Void visit(Block block) {
        return null;
    }

    @Override
    public Void visit(Let let) {
        return null;
    }

    @Override
    public Void visit(Case case1) {
        return null;
    }

    @Override
    public Void visit(NewType newType) {
        return null;
    }

    @Override
    public Void visit(IsVoidExpr isVoidExpr) {
        return null;
    }

    @Override
    public Void visit(Mult mult) {
        return null;
    }

    @Override
    public Void visit(Div div) {
        return null;
    }

    @Override
    public Void visit(Plus plus) {
        return null;
    }

    @Override
    public Void visit(Minus minus) {
        return null;
    }

    @Override
    public Void visit(TildeExpr tildeExpr) {
        return null;
    }

    @Override
    public Void visit(Relational relational) {
        return null;
    }

    @Override
    public Void visit(Equal equal) {
        return null;
    }

    @Override
    public Void visit(NotExpr notExpr) {
        return null;
    }

    @Override
    public Void visit(Paren paren) {
        return null;
    }

    @Override
    public Void visit(Simple simple) {
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
        for (var classDefinition : program.class_list) {
            classDefinition.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(VarDef varDef) {
        return null;
    }

    @Override
    public Void visit(Self self) {
        return null;
    }

    @Override
    public Void visit(LetLocal letLocal) {
        return null;
    }

    @Override
    public Void visit(CaseBranch caseBranch) {
        return null;
    }
}
