package cool.structures;

import java.io.File;

import cool.parser.CoolParser;
import org.antlr.v4.runtime.*;

import cool.compiler.Compiler;
import cool.compiler.ASTNode;

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

        // Set methods and formals of OBJECT
        var abortMethodSymbol = new MethodSymbol(BasicClasses.OBJECT, "abort");
        abortMethodSymbol.setType(BasicClasses.OBJECT);
        abortMethodSymbol.setReturn_type("Object");
        var typeNameMethodSymbol = new MethodSymbol(BasicClasses.OBJECT, "type_name");
        typeNameMethodSymbol.setType(BasicClasses.STRING);
        typeNameMethodSymbol.setReturn_type("String");
        var copyMethodSymbol = new MethodSymbol(BasicClasses.OBJECT, "copy");
        copyMethodSymbol.setType(BasicClasses.SELF_TYPE);
        copyMethodSymbol.setReturn_type("SELF_TYPE");
        BasicClasses.OBJECT.add(abortMethodSymbol);
        BasicClasses.OBJECT.add(typeNameMethodSymbol);
        BasicClasses.OBJECT.add(copyMethodSymbol);

        // Set methods and formals of STRING
        var lengthMethodSymbol = new MethodSymbol(BasicClasses.STRING, "length");
        lengthMethodSymbol.setType(BasicClasses.INT);
        lengthMethodSymbol.setReturn_type("Int");
        var concatMethodSymbol = new MethodSymbol(BasicClasses.STRING, "concat");
        concatMethodSymbol.setType(BasicClasses.STRING);
        concatMethodSymbol.setReturn_type("String");
        concatMethodSymbol.getParams().add("String");
        var concatFormal1 = new AttributeSymbol("s");
        concatFormal1.setType(BasicClasses.STRING);
        concatMethodSymbol.add(concatFormal1);

        var substrMethodSymbol = new MethodSymbol(BasicClasses.STRING, "substr");
        substrMethodSymbol.setType(BasicClasses.STRING);
        substrMethodSymbol.setReturn_type("String");
        substrMethodSymbol.getParams().add("Int");
        substrMethodSymbol.getParams().add("Int");
        var substrFormal1 = new AttributeSymbol("i");
        substrFormal1.setType(BasicClasses.INT);
        var substrFormal2 = new AttributeSymbol("l");
        substrFormal2.setType(BasicClasses.INT);
        substrMethodSymbol.add(substrFormal1);
        substrMethodSymbol.add(substrFormal2);

        BasicClasses.STRING.add(lengthMethodSymbol);
        BasicClasses.STRING.add(concatMethodSymbol);
        BasicClasses.STRING.add(substrMethodSymbol);

        // Set methods and formals of IO
        var outStringMethodSymbol = new MethodSymbol(BasicClasses.IO, "out_string");
        outStringMethodSymbol.setType(BasicClasses.SELF_TYPE);
        outStringMethodSymbol.setReturn_type("SELF_TYPE");
        outStringMethodSymbol.getParams().add("String");
        var outStringFormal1 = new AttributeSymbol("x");
        outStringFormal1.setType(BasicClasses.STRING);
        outStringMethodSymbol.add(outStringFormal1);

        var outIntMethodSymbol = new MethodSymbol(BasicClasses.IO, "out_int");
        outIntMethodSymbol.setType(BasicClasses.SELF_TYPE);
        outIntMethodSymbol.setReturn_type("SELF_TYPE");
        outIntMethodSymbol.getParams().add("Int");
        var outIntFormal1 = new AttributeSymbol("x");
        outIntFormal1.setType(BasicClasses.INT);
        outIntMethodSymbol.add(outIntFormal1);

        var inStringMethodSymbol = new MethodSymbol(BasicClasses.IO, "in_string");
        inStringMethodSymbol.setType(BasicClasses.STRING);
        inStringMethodSymbol.setReturn_type("String");
        var inIntMethodSymbol = new MethodSymbol(BasicClasses.IO, "in_int");
        inIntMethodSymbol.setType(BasicClasses.INT);
        inIntMethodSymbol.setReturn_type("Int");
        BasicClasses.IO.add(outStringMethodSymbol);
        BasicClasses.IO.add(outIntMethodSymbol);
        BasicClasses.IO.add(inStringMethodSymbol);
        BasicClasses.IO.add(inIntMethodSymbol);

        BasicClasses.OBJECT.setParentClassSymbol(null);
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
