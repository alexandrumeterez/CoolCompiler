package cool.compiler;

import cool.structures.ClassSymbol;
import cool.structures.Scope;
import cool.structures.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class ClassesDefinitionPassVisitor implements ASTVisitor<Void> {
    Scope currentScope = SymbolTable.globals;
    public static ArrayList<String> classesList = new ArrayList<>();

    public ClassesDefinitionPassVisitor() {
        classesList.add("IO");
        classesList.add("String");
        classesList.add("Bool");
        classesList.add("Int");
        classesList.add("Object");
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
        var classType = classDef.class_type;
        var classSymbol = new ClassSymbol(currentScope, classType.token.getText());
        boolean isValid = true;

        if (classType.token.getText().equals("SELF_TYPE")) {
            isValid = false;
            SymbolTable.error(classDef.ctx, classType.token, "Class has illegal name " + classType.token.getText());
            return null;
        }

        if (!currentScope.add(classSymbol)) {
//        if (classesList.contains(classType.token.getText()) || classType.token.getText().equals("Int") || classType.token.getText().equals("Bool") || classType.token.getText().equals("String") || classType.token.getText().equals("Object") || classType.token.getText().equals("IO")) {
            isValid = false;
            SymbolTable.error(classDef.ctx, classType.token, "Class " + classType.token.getText() + " is redefined");
        }
        if (isValid) {
            classesList.add(classType.token.getText());
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
        currentScope = SymbolTable.globals;
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
