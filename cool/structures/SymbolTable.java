package cool.structures;

import java.io.File;

import cool.parser.CoolParser;
import org.antlr.v4.runtime.*;

import cool.compiler.Compiler;

public class SymbolTable {
    public static Scope globals;

    private static boolean semanticErrors;

    public static void defineBasicClasses() {
        globals = new DefaultScope(null);
        semanticErrors = false;

        BasicClasses.OBJECT.setParent(globals);
        BasicClasses.BOOL.setParent(BasicClasses.OBJECT);
        BasicClasses.INT.setParent(BasicClasses.OBJECT);
        BasicClasses.STRING.setParent(BasicClasses.OBJECT);
        BasicClasses.IO.setParent(BasicClasses.OBJECT);

        BasicClasses.OBJECT.add(new MethodSymbol(BasicClasses.OBJECT, "abort"));
        BasicClasses.OBJECT.add(new MethodSymbol(BasicClasses.OBJECT, "type_name"));
        BasicClasses.OBJECT.add(new MethodSymbol(BasicClasses.OBJECT, "copy"));

        BasicClasses.STRING.add(new MethodSymbol(BasicClasses.STRING, "length"));
        BasicClasses.STRING.add(new MethodSymbol(BasicClasses.STRING, "concat"));
        BasicClasses.STRING.add(new MethodSymbol(BasicClasses.STRING, "substr"));

        BasicClasses.IO.add(new MethodSymbol(BasicClasses.IO, "out_string"));
        BasicClasses.IO.add(new MethodSymbol(BasicClasses.IO, "out_int"));
        BasicClasses.IO.add(new MethodSymbol(BasicClasses.IO, "in_string"));
        BasicClasses.IO.add(new MethodSymbol(BasicClasses.IO, "in_int"));

        BasicClasses.INT.setParentClassSymbol(BasicClasses.OBJECT);
        BasicClasses.BOOL.setParentClassSymbol(BasicClasses.OBJECT);
        BasicClasses.STRING.setParentClassSymbol(BasicClasses.OBJECT);
        BasicClasses.IO.setParentClassSymbol(BasicClasses.OBJECT);

        globals.add(BasicClasses.OBJECT);
        globals.add(BasicClasses.INT);
        globals.add(BasicClasses.BOOL);
        globals.add(BasicClasses.STRING);
        globals.add(BasicClasses.IO);
        globals.add(BasicClasses.SELF_TYPE);
    }

    /**
     * Displays a semantic error message.
     *
     * @param ctx  Used to determine the enclosing class context of this error,
     *             which knows the file name in which the class was defined.
     * @param info Used for line and column information.
     * @param str  The error message.
     */
    public static void error(ParserRuleContext ctx, Token info, String str) {
        while (!(ctx.getParent() instanceof CoolParser.ProgramContext))
            ctx = ctx.getParent();

        String message = "\"" + new File(Compiler.fileNames.get(ctx)).getName()
                + "\", line " + info.getLine()
                + ":" + (info.getCharPositionInLine() + 1)
                + ", Semantic error: " + str;

        System.err.println(message);

        semanticErrors = true;
    }

    public static void error(String str) {
        String message = "Semantic error: " + str;

        System.err.println(message);

        semanticErrors = true;
    }

    public static boolean hasSemanticErrors() {
        return semanticErrors;
    }
}
