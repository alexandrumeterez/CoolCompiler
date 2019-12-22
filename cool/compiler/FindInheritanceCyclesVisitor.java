package cool.compiler;

import cool.structures.SymbolTable;

import java.util.ArrayList;

public class FindInheritanceCyclesVisitor implements ASTVisitor<Void> {
    ArrayList<ArrayList<String>> cycles = new ArrayList<>();

    public FindInheritanceCyclesVisitor(ArrayList<ArrayList<String>> cycles) {
        this.cycles = cycles;
    }

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
        for (var cycle : cycles) {
            var classType = classDef.class_type;
            if (cycle.contains(classType.token.getText())) {
                SymbolTable.error(classDef.ctx, classType.token,
                        "Inheritance cycle for class "
                                + classType.token.getText());
            }
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
