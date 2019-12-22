package cool.compiler;

public interface ASTVisitor<T> {
	T visit(TypeId id);
	T visit(ObjectId objectId);
	T visit(If iff);
	T visit(Formal formal);
	T visit(FuncDef funcDef);
	T visit(ClassDef classDef);
	T visit(AssignExpr assignExpr);
	T visit(Dispatch dispatch);
	T visit(Call call);
	T visit(While while1);
	T visit(Block block);
	T visit(Let let);
	T visit(Case case1);

	T visit(NewType newType);
	T visit(IsVoidExpr isVoidExpr);
	T visit(Mult mult);
	T visit(Div div);
	T visit(Plus plus);
	T visit(Minus minus);
	T visit(TildeExpr tildeExpr);
	T visit(Relational relational);
	T visit(Equal equal);
	T visit(NotExpr notExpr);
	T visit(Paren paren);
	T visit(Simple simple);
	T visit(Int int1);
	T visit(Str str);
	T visit(Bool bool);
	T visit(Program program);
	T visit(VarDef varDef);
	T visit(Self self);
	T visit(LetLocal letLocal);
	T visit(CaseBranch caseBranch);
}
