lexer grammar CoolLexer;

tokens { 
	ERROR
} 

@header{
    package cool.lexer;	
}

@members{    

	int n_chars;
	private void startString() {
		n_chars = 0;
	}

	private void continueString() {
		n_chars++;
	}

    private void raiseError(String msg) {
        setText(msg);
        setType(ERROR);
    }
}

fragment NEW_LINE : '\r'? '\n';

/* Lexer rules */

/* Other operators */
LBRACE : '{';
SEMI : ';';
COLON : ':';
AT : '@';
COMMA : ',';
ASSIGN : '<-';
LPAREN : '(';
RPAREN : ')';
RBRACE : '}';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';
EQUALS : '=';
LT : '<';
LE : '<=';
TILDE : '~';
DOT : '.';
RESULTS : '=>';


/* Keywords */
// Case sensitive
fragment TRUE
	:	't'('r'|'R')('u'|'U')('e'|'E');
fragment FALSE
	:	'f'('a'|'A')('l'|'L')('s'|'S')('e'|'E');
BOOL_CONSTANT
	:	TRUE | FALSE
	;

// Case insensitive
CLASS
	: 	('c'|'C') ('l'|'L') ('a'|'A') ('s'|'S') ('s'|'S') ;
ELSE		
	: 	('e'|'E') ('l'|'L') ('s'|'S') ('e'|'E') ;
FI			
	:	('f'|'F') ('i'|'I') ;
IF			
	: 	('i'|'I') ('f'|'F') ;
IN			
	: 	('i'|'I') ('n'|'N') ;
INHERITS	
	: 	('i'|'I') ('n'|'N') ('h'|'H') ('e'|'E') ('r'|'R') ('i'|'I') ('t'|'T') ('s'|'S') ;
LET			
	: 	('l'|'L') ('e'|'E') ('t'|'T') ;
LOOP		
	: 	('l'|'L') ('o'|'O') ('o'|'O') ('p'|'P') ;
POOL		
	: 	('p'|'P') ('o'|'O') ('o'|'O') ('l'|'L') ;
THEN		
	: 	('t'|'T') ('h'|'H') ('e'|'E') ('n'|'N') ;
WHILE		
	: 	('w'|'W') ('h'|'H') ('i'|'I') ('l'|'L') ('e'|'E') ;
CASE		
	: 	('c'|'C') ('a'|'A') ('s'|'S') ('e'|'E') ;
ESAC		
	: 	('e'|'E') ('s'|'S') ('a'|'A') ('c'|'C') ;
OF			
	: 	('o'|'O') ('f'|'F') ;
NEW			
	: 	('n'|'N') ('e'|'E') ('w'|'W') ;
ISVOID		
	: 	('i'|'I') ('s'|'S') ('v'|'V') ('o'|'O') ('i'|'I') ('d'|'D') ;
NOT			
	: 	('n'|'N') ('o'|'O') ('t'|'T') ;


/* Integers, Identifiers and Special Notation */

SELFID : 'self' { setType(OBJECTID); };
SELF_TYPEID : 'SELF_TYPE' { setType(TYPEID); };

INT_CONSTANT
	:	[0-9]+
	;
OBJECTID
    : SELFID
	|	[a-z][a-zA-Z0-9_]*
	;
TYPEID
	:   SELF_TYPEID
	|	[A-Z][a-zA-Z0-9_]*
	;


/* Strings */
fragment ESCAPE : '\\';
fragment ESCAPED_ALLOWED : ESCAPE(NEW_LINE | .);
fragment ALLOWED : ESCAPED_ALLOWED | .;
fragment NULL_CHAR : '\u0000';
STR_CONSTANT : 
	'"' { startString(); }
	(
	  NULL_CHAR { raiseError("String contains null character"); }
	| ALLOWED { continueString(); }
	)
	*?
	(
	'"' { if (n_chars >= 1024) raiseError("String constant too long"); }
	| NEW_LINE { raiseError("Unterminated string constant"); }
	| EOF { raiseError("EOF in string constant"); }
	);


LINE_COMMENT
    : '--' .*? (NEW_LINE | EOF) -> skip
    ;

UNMATCHED:
	'*)' { raiseError("Unmatched *)"); }
	;

COMMENT
    : 
	'(*' (COMMENT | .)
	*?
	(
	'*)' {skip();}
	| EOF { raiseError("EOF in comment"); }
	);

/* White Space.*/
WS
    :   [ \n\f\r\t]+ -> skip
    ; 

INVALID_CHAR: 
	. { raiseError("Invalid character: " + getText().charAt(0)); }
	;
	