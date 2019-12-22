package cool.compiler;

import cool.structures.ClassSymbol;
import cool.structures.Scope;
import cool.structures.Symbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.*;

public abstract class ASTNode {
    Token token;
    ParserRuleContext ctx;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    Symbol symbol;

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    Scope scope;

    ASTNode(ParserRuleContext ctx, Token token) {
        this.ctx = ctx;
        this.token = token;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }
}

abstract class Expression extends ASTNode {
    Expression(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }
}

abstract class Feature extends ASTNode {
    Feature(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }
}

class FuncDef extends Feature {
    ObjectId name;
    LinkedList<Formal> params;
    TypeId return_type;
    Expression func_body;

    FuncDef(ParserRuleContext ctx, ObjectId name, LinkedList<Formal> params, TypeId return_type, Expression func_body, Token token) {
        super(ctx, token);
        this.name = name;
        this.params = params;
        this.func_body = func_body;
        this.return_type = return_type;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class VarDef extends Feature {
    ObjectId name;
    TypeId type;
    Expression init;

    VarDef(ParserRuleContext ctx, ObjectId name, TypeId type, Expression init, Token token) {
        super(ctx, token);
        this.name = name;
        this.type = type;
        this.init = init;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class TypeId extends Expression {
    TypeId(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class ObjectId extends Expression {
    ObjectId(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

//Argumentul din definita unei functii
class Formal extends ASTNode {
    ObjectId name;
    TypeId type;

    Formal(ParserRuleContext ctx, TypeId type, ObjectId name, Token token) {
        super(ctx, token);
        this.type = type;
        this.name = name;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}


class ClassDef extends ASTNode {
    TypeId class_type;
    TypeId inherits_type;
    LinkedList<Feature> features;

    ClassDef(ParserRuleContext ctx, TypeId class_type, TypeId inherits_type, LinkedList<Feature> features, Token token) {
        super(ctx, token);
        this.class_type = class_type;
        this.inherits_type = inherits_type;
        this.features = features;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class AssignExpr extends Expression {
    ObjectId name;
    Expression e;

    AssignExpr(ParserRuleContext ctx, ObjectId name, Expression e, Token token) {
        super(ctx, token);
        this.name = name;
        this.e = e;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Self extends Expression {
    Self(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Dispatch extends Expression {
    Expression object;
    TypeId class_name;
    Call call;

    Dispatch(ParserRuleContext ctx, Expression object, TypeId class_name, Call call, Token token) {
        super(ctx, token);
        this.object = object;
        this.class_name = class_name;
        this.call = call;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Call extends Expression {
    ObjectId name;
    LinkedList<Expression> args;

    public ClassSymbol getParentClassSymbol() {
        return parentClassSymbol;
    }

    public void setParentClassSymbolFromCurrentScope(Scope currentScope) {
        while (!(currentScope instanceof ClassSymbol)) {
            currentScope = currentScope.getParent();
        }
        this.parentClassSymbol = (ClassSymbol) currentScope;
    }

    ClassSymbol parentClassSymbol;

    Call(ParserRuleContext ctx, ObjectId name, LinkedList<Expression> args, Token token) {
        super(ctx, token);
        this.name = name;
        this.args = args;
    }


    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class If extends Expression {
    Expression cond;
    Expression thenBranch;
    Expression elseBranch;

    If(ParserRuleContext ctx, Expression cond, Expression thenBranch, Expression elseBranch, Token token) {
        super(ctx, token);
        this.cond = cond;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class While extends Expression {
    Expression cond;
    Expression whileBody;

    While(ParserRuleContext ctx, Expression cond, Expression whileBody, Token token) {
        super(ctx, token);
        this.cond = cond;
        this.whileBody = whileBody;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Block extends Expression {
    LinkedList<Expression> block_expressions;

    Block(ParserRuleContext ctx, LinkedList<Expression> block_expressions, Token token) {
        super(ctx, token);
        this.block_expressions = block_expressions;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class LetLocal extends ASTNode {
    ObjectId name;
    TypeId type;
    Expression e;

    LetLocal(ParserRuleContext ctx, ObjectId name, TypeId type, Expression e, Token token) {
        super(ctx, token);
        this.name = name;
        this.type = type;
        this.e = e;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Let extends Expression {
    LinkedList<LetLocal> variables;
    Expression let_block_expr;

    Let(ParserRuleContext ctx, LinkedList<LetLocal> variables, Expression let_block_expr, Token token) {
        super(ctx, token);
        this.variables = variables;
        this.let_block_expr = let_block_expr;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class CaseBranch extends ASTNode {
    ObjectId name;
    TypeId type;
    Expression expression;


    CaseBranch(ParserRuleContext ctx, ObjectId name, TypeId type, Expression expression, Token token) {
        super(ctx, token);
        this.name = name;
        this.type = type;
        this.expression = expression;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

}

class Case extends Expression {
    Expression cond;
    LinkedList<CaseBranch> caseBranches;

    Case(ParserRuleContext ctx, Expression cond, LinkedList<CaseBranch> caseBranches, Token token) {
        super(ctx, token);
        this.cond = cond;
        this.caseBranches = caseBranches;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class NewType extends Expression {
    TypeId type;

    NewType(ParserRuleContext ctx, TypeId type, Token token) {
        super(ctx, token);
        this.type = type;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class IsVoidExpr extends Expression {
    Expression e;

    IsVoidExpr(ParserRuleContext ctx, Expression e, Token token) {
        super(ctx, token);
        this.e = e;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Mult extends Expression {
    Expression left;
    Expression right;

    Mult(ParserRuleContext ctx, Expression left, Expression right, Token token) {
        super(ctx, token);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Div extends Expression {
    Expression left;
    Expression right;

    Div(ParserRuleContext ctx, Expression left, Expression right, Token token) {
        super(ctx, token);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Plus extends Expression {
    Expression left;
    Expression right;

    Plus(ParserRuleContext ctx, Expression left, Expression right, Token token) {
        super(ctx, token);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Minus extends Expression {
    Expression left;
    Expression right;

    Minus(ParserRuleContext ctx, Expression left, Expression right, Token token) {
        super(ctx, token);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class TildeExpr extends Expression {
    Expression e;

    TildeExpr(ParserRuleContext ctx, Expression e, Token token) {
        super(ctx, token);
        this.e = e;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Relational extends Expression {
    Expression left;
    Expression right;
    String operation;

    Relational(ParserRuleContext ctx, String operation, Expression left, Expression right, Token token) {
        super(ctx, token);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Equal extends Expression {
    Expression left;
    Expression right;

    Equal(ParserRuleContext ctx, Expression left, Expression right, Token token) {
        super(ctx, token);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class NotExpr extends Expression {
    Expression e;

    NotExpr(ParserRuleContext ctx, Expression e, Token token) {
        super(ctx, token);
        this.e = e;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Paren extends Expression {
    Expression e;

    Paren(ParserRuleContext ctx, Expression e, Token token) {
        super(ctx, token);
        this.e = e;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Simple extends Expression {
    ObjectId name;

    Simple(ParserRuleContext ctx, ObjectId name, Token token) {
        super(ctx, token);
        this.name = name;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Int extends Expression {
    Int(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Str extends Expression {
    Str(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Bool extends Expression {
    Bool(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Program extends ASTNode {
    LinkedList<ClassDef> class_list;

    Program(ParserRuleContext ctx, LinkedList<ClassDef> class_list, Token token) {
        super(ctx, token);
        this.class_list = class_list;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
