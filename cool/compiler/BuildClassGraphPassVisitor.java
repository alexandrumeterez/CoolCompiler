package cool.compiler;

import cool.structures.BasicClasses;
import cool.structures.ClassSymbol;
import cool.structures.Scope;
import cool.structures.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BuildClassGraphPassVisitor implements ASTVisitor<Void> {
    Scope currentScope = SymbolTable.globals;
    ArrayList<String> classesList = ClassesDefinitionPassVisitor.classesList;
    public static HashMap<String, ClassDef> stringToClassDef = new LinkedHashMap<>();
    public static HashMap<String, String> classGraph = new LinkedHashMap<>(); //key is child, value is parent
    public static HashMap<String, String> parentToChild = new LinkedHashMap<>(); //key is parent, value is child

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

        stringToClassDef.put(classType.token.getText(), classDef);

        if (baseClass == null) {
            classGraph.put(classType.token.getText(), BasicClasses.OBJECT.getName());
            parentToChild.put(BasicClasses.OBJECT.getName(), classType.token.getText());

            return null;
        }

        if (baseClass.token.getText().equals("Int") || baseClass.token.getText().equals("String") || baseClass.token.getText().equals("Bool") || baseClass.token.getText().equals("SELF_TYPE")) {
            SymbolTable.error(classDef.ctx, baseClass.token, "Class " + classType.token.getText() + " has illegal parent " + baseClass.token.getText());
        } else if (!classesList.contains(baseClass.token.getText())) {
            SymbolTable.error(classDef.ctx, baseClass.token, "Class " + classType.token.getText() + " has undefined parent " + baseClass.token.getText());
        } else {
//            SymbolTable.globals.add(new ClassSymbol(SymbolTable.globals, baseClass.token.getText()));
            classGraph.put(classType.token.getText(), baseClass.token.getText());
            parentToChild.put(baseClass.token.getText(), classType.token.getText());
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
