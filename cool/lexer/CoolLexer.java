// Generated from cool/lexer/CoolLexer.g4 by ANTLR 4.7.2

    package cool.lexer;	

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CoolLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ERROR=1, LBRACE=2, SEMI=3, COLON=4, AT=5, COMMA=6, ASSIGN=7, LPAREN=8, 
		RPAREN=9, RBRACE=10, PLUS=11, MINUS=12, MULT=13, DIV=14, EQUALS=15, LT=16, 
		LE=17, TILDE=18, DOT=19, RESULTS=20, BOOL_CONSTANT=21, CLASS=22, ELSE=23, 
		FI=24, IF=25, IN=26, INHERITS=27, LET=28, LOOP=29, POOL=30, THEN=31, WHILE=32, 
		CASE=33, ESAC=34, OF=35, NEW=36, ISVOID=37, NOT=38, SELFID=39, SELF_TYPEID=40, 
		INT_CONSTANT=41, OBJECTID=42, TYPEID=43, STR_CONSTANT=44, LINE_COMMENT=45, 
		UNMATCHED=46, COMMENT=47, WS=48, INVALID_CHAR=49;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"NEW_LINE", "LBRACE", "SEMI", "COLON", "AT", "COMMA", "ASSIGN", "LPAREN", 
			"RPAREN", "RBRACE", "PLUS", "MINUS", "MULT", "DIV", "EQUALS", "LT", "LE", 
			"TILDE", "DOT", "RESULTS", "TRUE", "FALSE", "BOOL_CONSTANT", "CLASS", 
			"ELSE", "FI", "IF", "IN", "INHERITS", "LET", "LOOP", "POOL", "THEN", 
			"WHILE", "CASE", "ESAC", "OF", "NEW", "ISVOID", "NOT", "SELFID", "SELF_TYPEID", 
			"INT_CONSTANT", "OBJECTID", "TYPEID", "ESCAPE", "ESCAPED_ALLOWED", "ALLOWED", 
			"NULL_CHAR", "STR_CONSTANT", "LINE_COMMENT", "UNMATCHED", "COMMENT", 
			"WS", "INVALID_CHAR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'{'", "';'", "':'", "'@'", "','", "'<-'", "'('", "')'", 
			"'}'", "'+'", "'-'", "'*'", "'/'", "'='", "'<'", "'<='", "'~'", "'.'", 
			"'=>'", null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "'self'", "'SELF_TYPE'", null, 
			null, null, null, null, "'*)'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ERROR", "LBRACE", "SEMI", "COLON", "AT", "COMMA", "ASSIGN", "LPAREN", 
			"RPAREN", "RBRACE", "PLUS", "MINUS", "MULT", "DIV", "EQUALS", "LT", "LE", 
			"TILDE", "DOT", "RESULTS", "BOOL_CONSTANT", "CLASS", "ELSE", "FI", "IF", 
			"IN", "INHERITS", "LET", "LOOP", "POOL", "THEN", "WHILE", "CASE", "ESAC", 
			"OF", "NEW", "ISVOID", "NOT", "SELFID", "SELF_TYPEID", "INT_CONSTANT", 
			"OBJECTID", "TYPEID", "STR_CONSTANT", "LINE_COMMENT", "UNMATCHED", "COMMENT", 
			"WS", "INVALID_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	    

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


	public CoolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CoolLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 40:
			SELFID_action((RuleContext)_localctx, actionIndex);
			break;
		case 41:
			SELF_TYPEID_action((RuleContext)_localctx, actionIndex);
			break;
		case 49:
			STR_CONSTANT_action((RuleContext)_localctx, actionIndex);
			break;
		case 51:
			UNMATCHED_action((RuleContext)_localctx, actionIndex);
			break;
		case 52:
			COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 54:
			INVALID_CHAR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void SELFID_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 setType(OBJECTID); 
			break;
		}
	}
	private void SELF_TYPEID_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 setType(TYPEID); 
			break;
		}
	}
	private void STR_CONSTANT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 startString(); 
			break;
		case 3:
			 raiseError("String contains null character"); 
			break;
		case 4:
			 continueString(); 
			break;
		case 5:
			 if (n_chars >= 1024) raiseError("String constant too long"); 
			break;
		case 6:
			 raiseError("Unterminated string constant"); 
			break;
		case 7:
			 raiseError("EOF in string constant"); 
			break;
		}
	}
	private void UNMATCHED_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 8:
			 raiseError("Unmatched *)"); 
			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 9:
			skip();
			break;
		case 10:
			 raiseError("EOF in comment"); 
			break;
		}
	}
	private void INVALID_CHAR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 11:
			 raiseError("Invalid character: " + getText().charAt(0)); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\63\u017f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\3\2\5\2s\n\2\3\2\3\2\3\3\3\3"+
		"\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22"+
		"\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\5\30\u00ad\n\30\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35"+
		"\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37"+
		"\3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3"+
		"$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3"+
		"(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3"+
		"+\3+\3,\6,\u0115\n,\r,\16,\u0116\3-\3-\3-\7-\u011c\n-\f-\16-\u011f\13"+
		"-\5-\u0121\n-\3.\3.\3.\7.\u0126\n.\f.\16.\u0129\13.\5.\u012b\n.\3/\3/"+
		"\3\60\3\60\3\60\5\60\u0132\n\60\3\61\3\61\5\61\u0136\n\61\3\62\3\62\3"+
		"\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\7\63\u0142\n\63\f\63\16\63\u0145"+
		"\13\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\5\63\u014e\n\63\3\64\3\64\3"+
		"\64\3\64\7\64\u0154\n\64\f\64\16\64\u0157\13\64\3\64\3\64\5\64\u015b\n"+
		"\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\7\66\u0169"+
		"\n\66\f\66\16\66\u016c\13\66\3\66\3\66\3\66\3\66\3\66\3\66\5\66\u0174"+
		"\n\66\3\67\6\67\u0177\n\67\r\67\16\67\u0178\3\67\3\67\38\38\38\5\u0143"+
		"\u0155\u016a\29\3\2\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\2-\2/\27\61\30\63\31\65\32"+
		"\67\339\34;\35=\36?\37A C!E\"G#I$K%M&O\'Q(S)U*W+Y,[-]\2_\2a\2c\2e.g/i"+
		"\60k\61m\62o\63\3\2\30\4\2TTtt\4\2WWww\4\2GGgg\4\2CCcc\4\2NNnn\4\2UUu"+
		"u\4\2EEee\4\2HHhh\4\2KKkk\4\2PPpp\4\2JJjj\4\2VVvv\4\2QQqq\4\2RRrr\4\2"+
		"YYyy\4\2XXxx\4\2FFff\3\2\62;\3\2c|\6\2\62;C\\aac|\3\2C\\\5\2\13\f\16\17"+
		"\"\"\2\u018a\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3"+
		"\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2"+
		"\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2"+
		"Y\3\2\2\2\2[\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3"+
		"\2\2\2\2o\3\2\2\2\3r\3\2\2\2\5v\3\2\2\2\7x\3\2\2\2\tz\3\2\2\2\13|\3\2"+
		"\2\2\r~\3\2\2\2\17\u0080\3\2\2\2\21\u0083\3\2\2\2\23\u0085\3\2\2\2\25"+
		"\u0087\3\2\2\2\27\u0089\3\2\2\2\31\u008b\3\2\2\2\33\u008d\3\2\2\2\35\u008f"+
		"\3\2\2\2\37\u0091\3\2\2\2!\u0093\3\2\2\2#\u0095\3\2\2\2%\u0098\3\2\2\2"+
		"\'\u009a\3\2\2\2)\u009c\3\2\2\2+\u009f\3\2\2\2-\u00a4\3\2\2\2/\u00ac\3"+
		"\2\2\2\61\u00ae\3\2\2\2\63\u00b4\3\2\2\2\65\u00b9\3\2\2\2\67\u00bc\3\2"+
		"\2\29\u00bf\3\2\2\2;\u00c2\3\2\2\2=\u00cb\3\2\2\2?\u00cf\3\2\2\2A\u00d4"+
		"\3\2\2\2C\u00d9\3\2\2\2E\u00de\3\2\2\2G\u00e4\3\2\2\2I\u00e9\3\2\2\2K"+
		"\u00ee\3\2\2\2M\u00f1\3\2\2\2O\u00f5\3\2\2\2Q\u00fc\3\2\2\2S\u0100\3\2"+
		"\2\2U\u0107\3\2\2\2W\u0114\3\2\2\2Y\u0120\3\2\2\2[\u012a\3\2\2\2]\u012c"+
		"\3\2\2\2_\u012e\3\2\2\2a\u0135\3\2\2\2c\u0137\3\2\2\2e\u0139\3\2\2\2g"+
		"\u014f\3\2\2\2i\u015e\3\2\2\2k\u0163\3\2\2\2m\u0176\3\2\2\2o\u017c\3\2"+
		"\2\2qs\7\17\2\2rq\3\2\2\2rs\3\2\2\2st\3\2\2\2tu\7\f\2\2u\4\3\2\2\2vw\7"+
		"}\2\2w\6\3\2\2\2xy\7=\2\2y\b\3\2\2\2z{\7<\2\2{\n\3\2\2\2|}\7B\2\2}\f\3"+
		"\2\2\2~\177\7.\2\2\177\16\3\2\2\2\u0080\u0081\7>\2\2\u0081\u0082\7/\2"+
		"\2\u0082\20\3\2\2\2\u0083\u0084\7*\2\2\u0084\22\3\2\2\2\u0085\u0086\7"+
		"+\2\2\u0086\24\3\2\2\2\u0087\u0088\7\177\2\2\u0088\26\3\2\2\2\u0089\u008a"+
		"\7-\2\2\u008a\30\3\2\2\2\u008b\u008c\7/\2\2\u008c\32\3\2\2\2\u008d\u008e"+
		"\7,\2\2\u008e\34\3\2\2\2\u008f\u0090\7\61\2\2\u0090\36\3\2\2\2\u0091\u0092"+
		"\7?\2\2\u0092 \3\2\2\2\u0093\u0094\7>\2\2\u0094\"\3\2\2\2\u0095\u0096"+
		"\7>\2\2\u0096\u0097\7?\2\2\u0097$\3\2\2\2\u0098\u0099\7\u0080\2\2\u0099"+
		"&\3\2\2\2\u009a\u009b\7\60\2\2\u009b(\3\2\2\2\u009c\u009d\7?\2\2\u009d"+
		"\u009e\7@\2\2\u009e*\3\2\2\2\u009f\u00a0\7v\2\2\u00a0\u00a1\t\2\2\2\u00a1"+
		"\u00a2\t\3\2\2\u00a2\u00a3\t\4\2\2\u00a3,\3\2\2\2\u00a4\u00a5\7h\2\2\u00a5"+
		"\u00a6\t\5\2\2\u00a6\u00a7\t\6\2\2\u00a7\u00a8\t\7\2\2\u00a8\u00a9\t\4"+
		"\2\2\u00a9.\3\2\2\2\u00aa\u00ad\5+\26\2\u00ab\u00ad\5-\27\2\u00ac\u00aa"+
		"\3\2\2\2\u00ac\u00ab\3\2\2\2\u00ad\60\3\2\2\2\u00ae\u00af\t\b\2\2\u00af"+
		"\u00b0\t\6\2\2\u00b0\u00b1\t\5\2\2\u00b1\u00b2\t\7\2\2\u00b2\u00b3\t\7"+
		"\2\2\u00b3\62\3\2\2\2\u00b4\u00b5\t\4\2\2\u00b5\u00b6\t\6\2\2\u00b6\u00b7"+
		"\t\7\2\2\u00b7\u00b8\t\4\2\2\u00b8\64\3\2\2\2\u00b9\u00ba\t\t\2\2\u00ba"+
		"\u00bb\t\n\2\2\u00bb\66\3\2\2\2\u00bc\u00bd\t\n\2\2\u00bd\u00be\t\t\2"+
		"\2\u00be8\3\2\2\2\u00bf\u00c0\t\n\2\2\u00c0\u00c1\t\13\2\2\u00c1:\3\2"+
		"\2\2\u00c2\u00c3\t\n\2\2\u00c3\u00c4\t\13\2\2\u00c4\u00c5\t\f\2\2\u00c5"+
		"\u00c6\t\4\2\2\u00c6\u00c7\t\2\2\2\u00c7\u00c8\t\n\2\2\u00c8\u00c9\t\r"+
		"\2\2\u00c9\u00ca\t\7\2\2\u00ca<\3\2\2\2\u00cb\u00cc\t\6\2\2\u00cc\u00cd"+
		"\t\4\2\2\u00cd\u00ce\t\r\2\2\u00ce>\3\2\2\2\u00cf\u00d0\t\6\2\2\u00d0"+
		"\u00d1\t\16\2\2\u00d1\u00d2\t\16\2\2\u00d2\u00d3\t\17\2\2\u00d3@\3\2\2"+
		"\2\u00d4\u00d5\t\17\2\2\u00d5\u00d6\t\16\2\2\u00d6\u00d7\t\16\2\2\u00d7"+
		"\u00d8\t\6\2\2\u00d8B\3\2\2\2\u00d9\u00da\t\r\2\2\u00da\u00db\t\f\2\2"+
		"\u00db\u00dc\t\4\2\2\u00dc\u00dd\t\13\2\2\u00ddD\3\2\2\2\u00de\u00df\t"+
		"\20\2\2\u00df\u00e0\t\f\2\2\u00e0\u00e1\t\n\2\2\u00e1\u00e2\t\6\2\2\u00e2"+
		"\u00e3\t\4\2\2\u00e3F\3\2\2\2\u00e4\u00e5\t\b\2\2\u00e5\u00e6\t\5\2\2"+
		"\u00e6\u00e7\t\7\2\2\u00e7\u00e8\t\4\2\2\u00e8H\3\2\2\2\u00e9\u00ea\t"+
		"\4\2\2\u00ea\u00eb\t\7\2\2\u00eb\u00ec\t\5\2\2\u00ec\u00ed\t\b\2\2\u00ed"+
		"J\3\2\2\2\u00ee\u00ef\t\16\2\2\u00ef\u00f0\t\t\2\2\u00f0L\3\2\2\2\u00f1"+
		"\u00f2\t\13\2\2\u00f2\u00f3\t\4\2\2\u00f3\u00f4\t\20\2\2\u00f4N\3\2\2"+
		"\2\u00f5\u00f6\t\n\2\2\u00f6\u00f7\t\7\2\2\u00f7\u00f8\t\21\2\2\u00f8"+
		"\u00f9\t\16\2\2\u00f9\u00fa\t\n\2\2\u00fa\u00fb\t\22\2\2\u00fbP\3\2\2"+
		"\2\u00fc\u00fd\t\13\2\2\u00fd\u00fe\t\16\2\2\u00fe\u00ff\t\r\2\2\u00ff"+
		"R\3\2\2\2\u0100\u0101\7u\2\2\u0101\u0102\7g\2\2\u0102\u0103\7n\2\2\u0103"+
		"\u0104\7h\2\2\u0104\u0105\3\2\2\2\u0105\u0106\b*\2\2\u0106T\3\2\2\2\u0107"+
		"\u0108\7U\2\2\u0108\u0109\7G\2\2\u0109\u010a\7N\2\2\u010a\u010b\7H\2\2"+
		"\u010b\u010c\7a\2\2\u010c\u010d\7V\2\2\u010d\u010e\7[\2\2\u010e\u010f"+
		"\7R\2\2\u010f\u0110\7G\2\2\u0110\u0111\3\2\2\2\u0111\u0112\b+\3\2\u0112"+
		"V\3\2\2\2\u0113\u0115\t\23\2\2\u0114\u0113\3\2\2\2\u0115\u0116\3\2\2\2"+
		"\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117X\3\2\2\2\u0118\u0121\5"+
		"S*\2\u0119\u011d\t\24\2\2\u011a\u011c\t\25\2\2\u011b\u011a\3\2\2\2\u011c"+
		"\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u0121\3\2"+
		"\2\2\u011f\u011d\3\2\2\2\u0120\u0118\3\2\2\2\u0120\u0119\3\2\2\2\u0121"+
		"Z\3\2\2\2\u0122\u012b\5U+\2\u0123\u0127\t\26\2\2\u0124\u0126\t\25\2\2"+
		"\u0125\u0124\3\2\2\2\u0126\u0129\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128"+
		"\3\2\2\2\u0128\u012b\3\2\2\2\u0129\u0127\3\2\2\2\u012a\u0122\3\2\2\2\u012a"+
		"\u0123\3\2\2\2\u012b\\\3\2\2\2\u012c\u012d\7^\2\2\u012d^\3\2\2\2\u012e"+
		"\u0131\5]/\2\u012f\u0132\5\3\2\2\u0130\u0132\13\2\2\2\u0131\u012f\3\2"+
		"\2\2\u0131\u0130\3\2\2\2\u0132`\3\2\2\2\u0133\u0136\5_\60\2\u0134\u0136"+
		"\13\2\2\2\u0135\u0133\3\2\2\2\u0135\u0134\3\2\2\2\u0136b\3\2\2\2\u0137"+
		"\u0138\7\2\2\2\u0138d\3\2\2\2\u0139\u013a\7$\2\2\u013a\u0143\b\63\4\2"+
		"\u013b\u013c\5c\62\2\u013c\u013d\b\63\5\2\u013d\u0142\3\2\2\2\u013e\u013f"+
		"\5a\61\2\u013f\u0140\b\63\6\2\u0140\u0142\3\2\2\2\u0141\u013b\3\2\2\2"+
		"\u0141\u013e\3\2\2\2\u0142\u0145\3\2\2\2\u0143\u0144\3\2\2\2\u0143\u0141"+
		"\3\2\2\2\u0144\u014d\3\2\2\2\u0145\u0143\3\2\2\2\u0146\u0147\7$\2\2\u0147"+
		"\u014e\b\63\7\2\u0148\u0149\5\3\2\2\u0149\u014a\b\63\b\2\u014a\u014e\3"+
		"\2\2\2\u014b\u014c\7\2\2\3\u014c\u014e\b\63\t\2\u014d\u0146\3\2\2\2\u014d"+
		"\u0148\3\2\2\2\u014d\u014b\3\2\2\2\u014ef\3\2\2\2\u014f\u0150\7/\2\2\u0150"+
		"\u0151\7/\2\2\u0151\u0155\3\2\2\2\u0152\u0154\13\2\2\2\u0153\u0152\3\2"+
		"\2\2\u0154\u0157\3\2\2\2\u0155\u0156\3\2\2\2\u0155\u0153\3\2\2\2\u0156"+
		"\u015a\3\2\2\2\u0157\u0155\3\2\2\2\u0158\u015b\5\3\2\2\u0159\u015b\7\2"+
		"\2\3\u015a\u0158\3\2\2\2\u015a\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c"+
		"\u015d\b\64\n\2\u015dh\3\2\2\2\u015e\u015f\7,\2\2\u015f\u0160\7+\2\2\u0160"+
		"\u0161\3\2\2\2\u0161\u0162\b\65\13\2\u0162j\3\2\2\2\u0163\u0164\7*\2\2"+
		"\u0164\u0165\7,\2\2\u0165\u016a\3\2\2\2\u0166\u0169\5k\66\2\u0167\u0169"+
		"\13\2\2\2\u0168\u0166\3\2\2\2\u0168\u0167\3\2\2\2\u0169\u016c\3\2\2\2"+
		"\u016a\u016b\3\2\2\2\u016a\u0168\3\2\2\2\u016b\u0173\3\2\2\2\u016c\u016a"+
		"\3\2\2\2\u016d\u016e\7,\2\2\u016e\u016f\7+\2\2\u016f\u0170\3\2\2\2\u0170"+
		"\u0174\b\66\f\2\u0171\u0172\7\2\2\3\u0172\u0174\b\66\r\2\u0173\u016d\3"+
		"\2\2\2\u0173\u0171\3\2\2\2\u0174l\3\2\2\2\u0175\u0177\t\27\2\2\u0176\u0175"+
		"\3\2\2\2\u0177\u0178\3\2\2\2\u0178\u0176\3\2\2\2\u0178\u0179\3\2\2\2\u0179"+
		"\u017a\3\2\2\2\u017a\u017b\b\67\n\2\u017bn\3\2\2\2\u017c\u017d\13\2\2"+
		"\2\u017d\u017e\b8\16\2\u017ep\3\2\2\2\25\2r\u00ac\u0116\u011d\u0120\u0127"+
		"\u012a\u0131\u0135\u0141\u0143\u014d\u0155\u015a\u0168\u016a\u0173\u0178"+
		"\17\3*\2\3+\3\3\63\4\3\63\5\3\63\6\3\63\7\3\63\b\3\63\t\b\2\2\3\65\n\3"+
		"\66\13\3\66\f\38\r";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}