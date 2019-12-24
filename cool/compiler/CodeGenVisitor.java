package cool.compiler;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public class CodeGenVisitor implements ASTVisitor<ST> {
    static STGroupFile templates = new STGroupFile("cool/template.stg");
    ST mainSection;
    ST dataSection;
    ST textSection;

    @Override
    public ST visit(TypeId id) {
        return null;
    }

    @Override
    public ST visit(ObjectId objectId) {
        return null;
    }

    @Override
    public ST visit(If iff) {
        return null;
    }

    @Override
    public ST visit(Formal formal) {
        return null;
    }

    @Override
    public ST visit(FuncDef funcDef) {
        return null;
    }

    @Override
    public ST visit(ClassDef classDef) {

        return null;
    }

    @Override
    public ST visit(AssignExpr assignExpr) {
        return null;
    }

    @Override
    public ST visit(Dispatch dispatch) {
        return null;
    }

    @Override
    public ST visit(Call call) {
        return null;
    }

    @Override
    public ST visit(While while1) {
        return null;
    }

    @Override
    public ST visit(Block block) {
        return null;
    }

    @Override
    public ST visit(Let let) {
        return null;
    }

    @Override
    public ST visit(Case case1) {
        return null;
    }

    @Override
    public ST visit(NewType newType) {
        return null;
    }

    @Override
    public ST visit(IsVoidExpr isVoidExpr) {
        return null;
    }

    @Override
    public ST visit(Mult mult) {
        return null;
    }

    @Override
    public ST visit(Div div) {
        return null;
    }

    @Override
    public ST visit(Plus plus) {
        return null;
    }

    @Override
    public ST visit(Minus minus) {
        return null;
    }

    @Override
    public ST visit(TildeExpr tildeExpr) {
        return null;
    }

    @Override
    public ST visit(Relational relational) {
        return null;
    }

    @Override
    public ST visit(Equal equal) {
        return null;
    }

    @Override
    public ST visit(NotExpr notExpr) {
        return null;
    }

    @Override
    public ST visit(Paren paren) {
        return null;
    }

    @Override
    public ST visit(Simple simple) {
        return null;
    }

    @Override
    public ST visit(Int int1) {
        return null;
    }

    @Override
    public ST visit(Str str) {
        return null;
    }

    @Override
    public ST visit(Bool bool) {
        return null;
    }

    @Override
    public ST visit(Program program) {
        dataSection = templates.getInstanceOf("sequence");
        textSection = templates.getInstanceOf("sequence");
        mainSection = templates.getInstanceOf("sequence");

        for (var e : program.class_list) {
            mainSection.add("e", e.accept(this));
        }
        var programST = templates.getInstanceOf("program");
        programST.add("data", dataSection);
        programST.add("text", textSection);
        return programST;
    }

    @Override
    public ST visit(VarDef varDef) {
        return null;
    }

    @Override
    public ST visit(Self self) {
        return null;
    }

    @Override
    public ST visit(LetLocal letLocal) {
        return null;
    }

    @Override
    public ST visit(CaseBranch caseBranch) {
        return null;
    }
}
