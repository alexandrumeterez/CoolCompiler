parser grammar CoolParser;

options {
    tokenVocab = CoolLexer;
}

@header{
    package cool.parser;
}
program
	:	(class_list+=classdef SEMI)+
	;

classdef
	:	CLASS class_type=TYPEID (INHERITS inherits_type=TYPEID)? LBRACE (features+=feature SEMI)* RBRACE #classDef
	;

feature
	:	name=OBJECTID LPAREN (params+=formal (COMMA params+=formal)*)? RPAREN COLON return_type=(TYPEID | SELF_TYPEID) LBRACE func_body=expr RBRACE #funcDef
	|	name=OBJECTID COLON type=(TYPEID | SELF_TYPEID) (ASSIGN init=expr)?	#varDef
	;
formal
	:	name=OBJECTID COLON type=TYPEID
	;

functioncall
    :   func_name=OBJECTID LPAREN (args+=expr (COMMA args+=expr)*)? RPAREN
    ;
expr
	:	functionCall=functioncall #call
	|	object=expr (AT class_name=TYPEID)? DOT functionCall=functioncall #dispatch
	|	LPAREN e=expr RPAREN #paren
	|	TILDE e=expr #tildeExpr
	|	ISVOID e=expr #isVoidExpr
	|	left=expr op=(MULT | DIV) right=expr #multDiv
	|	left=expr op=(PLUS | MINUS) right=expr #plusMinus
	|	left=expr op=(LE | LT ) right=expr #relational
	|	left=expr op=EQUALS right=expr #equal
	|	NOT e=expr #notExpr
	|	name=OBJECTID ASSIGN e=expr #assignExpr
	|	IF cond=expr THEN thenBranch=expr ELSE elseBranch=expr FI #if
	|	WHILE cond=expr LOOP whileBody=expr POOL #while
	|	LBRACE (block_expr+=expr SEMI)+ RBRACE #block
	|	LET variables+=letlocal (COMMA variables+=letlocal)* IN let_block_expr=expr #let
	|	CASE cond=expr OF (caseBranches+=casebranch)* ESAC #case
	|	NEW type=TYPEID #newType
	|	name=OBJECTID #objectId
	|	SELFID #self
	|	INT_CONSTANT #int
	|	BOOL_CONSTANT #bool
	|	STR_CONSTANT #str
	;
letlocal
	:	var_name=OBJECTID COLON var_type=TYPEID (ASSIGN var_assignment=expr)? #localLetVar
	;

casebranch
    : name=OBJECTID COLON type=TYPEID RESULTS expression=expr
        SEMI # caseBranchDefinition
    ;
