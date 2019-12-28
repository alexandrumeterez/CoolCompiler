package cool.compiler;

import cool.parser.CoolParser;
import cool.parser.CoolParserBaseVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import cool.lexer.*;

import java.io.*;
import java.io.IOException;
import java.util.*;

import cool.structures.*;

public class Compiler {
    // Annotates class nodes with the names of files where they are defined.
    public static ParseTreeProperty<String> fileNames = new ParseTreeProperty<>();
    public static HashMap<String, ArrayList<String>> reverseGraph = new LinkedHashMap<>();
    private static ArrayList<ArrayList<String>> findCycles(HashMap<String, String> graph, ArrayList<String> classesList) {
        ArrayList<ArrayList<String>> cycles = new ArrayList<>();

        for (var c : classesList) {
            if (!graph.containsKey(c)) {
                continue;
            } else {
                // check if c is already in a cycle
                boolean isValid = true;
                for (var cycle : cycles) {
                    if (cycle.contains(c)) {
                        isValid = false;
                        break;
                    }
                }
                if (!isValid) {
                    continue;
                }

                // if c is valid, then check for cycle
                ArrayList<String> currentCycle = new ArrayList<>();
                ArrayList<String> visited = new ArrayList<>();
                Stack<String> stack = new Stack<>();

                stack.add(c);
                String current;
                while (!stack.empty()) {
                    current = stack.pop();
                    visited.add(current);

                    var next = graph.getOrDefault(current, null);
                    if (next != null && !visited.contains(next)) {
                        stack.push(next);
                    } else if (visited.contains(next)) {

                        //found cycle head = next
                        var cycleHead = next;
                        currentCycle.add(cycleHead);
                        current = graph.get(cycleHead);
                        while (!current.equals(cycleHead)) {
                            currentCycle.add(current);
                            current = graph.get(current);
                        }
                        cycles.add(currentCycle);
                        break;
                    }
                }

            }
        }

        return cycles;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("No file(s) given");
            return;
        }

        CoolLexer lexer = null;
        CommonTokenStream tokenStream = null;
        CoolParser parser = null;
        ParserRuleContext globalTree = null;

        // True if any lexical or syntax errors occur.
        boolean lexicalSyntaxErrors = false;

        // Parse each input file and build one big parse tree out of
        // individual parse trees.
        for (var fileName : args) {
            var input = CharStreams.fromFileName(fileName);

            // Lexer
            if (lexer == null)
                lexer = new CoolLexer(input);
            else
                lexer.setInputStream(input);

            // Token stream
            if (tokenStream == null)
                tokenStream = new CommonTokenStream(lexer);
            else
                tokenStream.setTokenSource(lexer);

            /*
            // Test lexer only.
            tokenStream.fill();
            List<Token> tokens = tokenStream.getTokens();
            tokens.stream().forEach(token -> {
                var text = token.getText();
                var name = CoolLexer.VOCABULARY.getSymbolicName(token.getType());

                System.out.println(text + " : " + name);
                //System.out.println(token);
            });
            */

            // Parser
            if (parser == null)
                parser = new CoolParser(tokenStream);
            else
                parser.setTokenStream(tokenStream);

            // Customized error listener, for including file names in error
            // messages.
            var errorListener = new BaseErrorListener() {
                public boolean errors = false;

                @Override
                public void syntaxError(Recognizer<?, ?> recognizer,
                                        Object offendingSymbol,
                                        int line, int charPositionInLine,
                                        String msg,
                                        RecognitionException e) {
                    String newMsg = "\"" + new File(fileName).getName() + "\", line " +
                            line + ":" + (charPositionInLine) + ", ";

                    Token token = (Token) offendingSymbol;
                    if (token.getType() == CoolLexer.ERROR)
                        newMsg += "Lexical error: " + token.getText();
                    else
                        newMsg += "Syntax error: " + msg;

                    System.err.println(newMsg);
                    errors = true;
                }
            };

            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            // Actual parsing
            var tree = parser.program();
            if (globalTree == null)
                globalTree = tree;
            else
                // Add the current parse tree's children to the global tree.
                for (int i = 0; i < tree.getChildCount(); i++)
                    globalTree.addAnyChild(tree.getChild(i));

            // Annotate class nodes with file names, to be used later
            // in semantic error messages.
            for (int i = 0; i < tree.getChildCount(); i++) {
                var child = tree.getChild(i);
                // The only ParserRuleContext children of the program node
                // are class nodes.
                if (child instanceof ParserRuleContext)
                    fileNames.put(child, fileName);
            }

            // Record any lexical or syntax errors.
            lexicalSyntaxErrors |= errorListener.errors;


        }

        // Stop before semantic analysis phase, in case errors occurred.
        if (lexicalSyntaxErrors) {
            System.err.println("Compilation halted");
            return;
        }

        // TODO Print tree
        var astConstructionVisitor = new CoolParserBaseVisitor<ASTNode>() {
            @Override
            public ASTNode visitProgram(CoolParser.ProgramContext ctx) {
                LinkedList<ClassDef> class_list = new LinkedList<>();
                for (var child : ctx.children) {
                    ClassDef c = (ClassDef) visit(child);
                    if (c != null) {
                        class_list.add(c);
                    }
                }
                return new Program(ctx, class_list, ctx.start);
            }

            @Override
            public ASTNode visitSelf(CoolParser.SelfContext ctx) {
                return new Self(ctx, ctx.SELFID().getSymbol());
            }

            @Override
            public ASTNode visitClassDef(CoolParser.ClassDefContext ctx) {
                LinkedList<Feature> features = new LinkedList<>();
                for (var feature : ctx.features) {
                    if (feature != null)
                        features.add((Feature) visit(feature));
                }
                if (ctx.inherits_type != null)
                    return new ClassDef(ctx, new TypeId(ctx, ctx.class_type), new TypeId(ctx, ctx.inherits_type), features, ctx.start);
                else
                    return new ClassDef(ctx, new TypeId(ctx, ctx.class_type), null, features, ctx.start);
            }

            @Override
            public ASTNode visitFuncDef(CoolParser.FuncDefContext ctx) {
                LinkedList<Formal> formals = new LinkedList<>();
                for (var formal : ctx.params)
                    formals.add((Formal) visit(formal));

                return new FuncDef(ctx, new ObjectId(ctx, ctx.name), formals, new TypeId(ctx, ctx.return_type), (Expression) visit(ctx.func_body), ctx.start);
            }

            @Override
            public ASTNode visitVarDef(CoolParser.VarDefContext ctx) {
                if (ctx.init != null)
                    return new VarDef(ctx, new ObjectId(ctx, ctx.name), new TypeId(ctx, ctx.type), (Expression) visit(ctx.init), ctx.start);
                else
                    return new VarDef(ctx, new ObjectId(ctx, ctx.name), new TypeId(ctx, ctx.type), null, ctx.start);
            }

            @Override
            public ASTNode visitFormal(CoolParser.FormalContext ctx) {
                return new Formal(ctx, new TypeId(ctx, ctx.type), new ObjectId(ctx, ctx.name), ctx.start);
            }

            @Override
            public ASTNode visitNewType(CoolParser.NewTypeContext ctx) {
                return new NewType(ctx, new TypeId(ctx, ctx.type), ctx.start);
            }

            @Override
            public ASTNode visitPlusMinus(CoolParser.PlusMinusContext ctx) {
                if (ctx.op.getText().equals("+"))
                    return new Plus(ctx, (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.op);
                else if (ctx.op.getText().equals("-"))
                    return new Minus(ctx, (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.op);
                else
                    return null;
            }

            @Override
            public ASTNode visitFunctioncall(CoolParser.FunctioncallContext ctx) {
                LinkedList<Expression> args = new LinkedList<>();
                for (var arg : ctx.args) {
                    if (arg != null)
                        args.add((Expression) visit(arg));
                    else
                        args.add(null);
                }
                return new Call(ctx, new ObjectId(ctx, ctx.func_name), args, ctx.start);


            }

            @Override
            public ASTNode visitDispatch(CoolParser.DispatchContext ctx) {
                if (ctx.class_name != null)
                    return new Dispatch(ctx, (Expression) visit(ctx.object), new TypeId(ctx, ctx.class_name), (Call) visit(ctx.functionCall), ctx.start);
                else
                    return new Dispatch(ctx, (Expression) visit(ctx.object), null, (Call) visit(ctx.functionCall), ctx.start);

            }

            @Override
            public ASTNode visitBool(CoolParser.BoolContext ctx) {
                return new Bool(ctx, ctx.BOOL_CONSTANT().getSymbol());
            }

            @Override
            public ASTNode visitObjectId(CoolParser.ObjectIdContext ctx) {
                return new ObjectId(ctx, ctx.start);
            }

            @Override
            public ASTNode visitWhile(CoolParser.WhileContext ctx) {
                return new While(ctx, (Expression) visit(ctx.cond), (Expression) visit(ctx.whileBody), ctx.start);
            }

            @Override
            public ASTNode visitInt(CoolParser.IntContext ctx) {
                return new Int(ctx, ctx.INT_CONSTANT().getSymbol());
            }


            @Override
            public ASTNode visitCall(CoolParser.CallContext ctx) {
                LinkedList<Expression> args = new LinkedList<>();
                for (var arg : ctx.functionCall.args) {
                    if (arg != null)
                        args.add((Expression) visit(arg));
                    else
                        args.add(null);
                }
                return new Call(ctx, new ObjectId(ctx, ctx.functionCall.func_name), args, ctx.start);
            }

            @Override
            public ASTNode visitStr(CoolParser.StrContext ctx) {
                return new Str(ctx, ctx.STR_CONSTANT().getSymbol());
            }

            @Override
            public ASTNode visitParen(CoolParser.ParenContext ctx) {
                return new Paren(ctx, (Expression) visit(ctx.e), ctx.start);
            }

            @Override
            public ASTNode visitNotExpr(CoolParser.NotExprContext ctx) {
                return new NotExpr(ctx, (Expression) visit(ctx.e), ctx.start);
            }

            @Override
            public ASTNode visitMultDiv(CoolParser.MultDivContext ctx) {
                if (ctx.op.getText().equals("*"))
                    return new Mult(ctx, (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.op);
                else if (ctx.op.getText().equals("/"))
                    return new Div(ctx, (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.op);
                else
                    return null;
            }

            @Override
            public ASTNode visitIsVoidExpr(CoolParser.IsVoidExprContext ctx) {
                return new IsVoidExpr(ctx, (Expression) visit(ctx.e), ctx.start);
            }

            @Override
            public ASTNode visitBlock(CoolParser.BlockContext ctx) {
                LinkedList<Expression> block_expressions = new LinkedList<>();
                for (var e : ctx.block_expr)
                    block_expressions.add((Expression) visit(e));
                return new Block(ctx, block_expressions, ctx.start);
            }

            @Override
            public ASTNode visitLocalLetVar(CoolParser.LocalLetVarContext ctx) {
                if (ctx.var_assignment != null)
                    return new LetLocal(ctx, new ObjectId(ctx, ctx.var_name), new TypeId(ctx, ctx.var_type), (Expression) visit(ctx.var_assignment), ctx.start);
                else
                    return new LetLocal(ctx, new ObjectId(ctx, ctx.var_name), new TypeId(ctx, ctx.var_type), null, ctx.start);
            }

            @Override
            public ASTNode visitLet(CoolParser.LetContext ctx) {
                LinkedList<LetLocal> variables = new LinkedList<>();
                for (var v : ctx.variables) {
                    ;
                    variables.add((LetLocal) visit(v));
                }
                return new Let(ctx, variables, (Expression) visit(ctx.let_block_expr), ctx.start);
            }

            @Override
            public ASTNode visitRelational(CoolParser.RelationalContext ctx) {
                return new Relational(ctx, ctx.op.getText(), (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.start);
            }

            @Override
            public ASTNode visitEqual(CoolParser.EqualContext ctx) {
                return new Equal(ctx, (Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.op);
            }

            @Override
            public ASTNode visitAssignExpr(CoolParser.AssignExprContext ctx) {
                return new AssignExpr(ctx, new ObjectId(ctx, ctx.name), (Expression) visit(ctx.e), ctx.start);
            }

            @Override
            public ASTNode visitIf(CoolParser.IfContext ctx) {
                return new If(ctx, (Expression) visit(ctx.cond), (Expression) visit(ctx.thenBranch), (Expression) visit(ctx.elseBranch), ctx.start);
            }

            @Override
            public ASTNode visitCase(CoolParser.CaseContext ctx) {
                LinkedList<CaseBranch> caseBranches = new LinkedList<>();
                for (var v : ctx.caseBranches) {
                    caseBranches.add((CaseBranch) visit(v));
                }
                return new Case(ctx, (Expression) visit(ctx.cond), caseBranches, ctx.start);
            }

            @Override
            public ASTNode visitCaseBranchDefinition(CoolParser.CaseBranchDefinitionContext ctx) {
                return new CaseBranch(ctx, new ObjectId(ctx, ctx.name), new TypeId(ctx, ctx.type), (Expression) visit(ctx.expression), ctx.start);
            }

            @Override
            public ASTNode visitTildeExpr(CoolParser.TildeExprContext ctx) {
                return new TildeExpr(ctx, (Expression) visit(ctx.e), ctx.start);
            }

        };
        // FROM HERE CONTINUE
        var ast = astConstructionVisitor.visit(globalTree);

        var printVisitor = new ASTVisitor<Void>() {
            int indent = 0;

            void printIndent(String str) {
                for (int i = 0; i < indent; i++)
                    System.out.print("  ");
                System.out.println(str);
            }

            @Override
            public Void visit(TypeId id) {
                printIndent(id.token.getText());
                return null;
            }

            @Override
            public Void visit(ObjectId objectId) {
                printIndent(objectId.token.getText());
                return null;
            }

            @Override
            public Void visit(If iff) {
                printIndent("if");
                indent++;
                iff.cond.accept(this);
                iff.thenBranch.accept(this);
                iff.elseBranch.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Formal formal) {
                printIndent("formal");
                indent++;
                formal.name.accept(this);
                formal.type.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(FuncDef funcDef) {
                printIndent("method");
                indent++;
                funcDef.name.accept(this);
                for (var p : funcDef.params)
                    p.accept(this);
                funcDef.return_type.accept(this);
                funcDef.func_body.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(VarDef varDef) {
                printIndent("attribute");
                indent++;
                varDef.name.accept(this);
                varDef.type.accept(this);
                if (varDef.init != null)
                    varDef.init.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(ClassDef classDef) {
                printIndent("class");
                indent++;
                if (classDef.class_type != null)
                    // printIndent(classDef.class_type.token.getText());
                    classDef.class_type.accept(this);
                if (classDef.inherits_type != null)
                    // printIndent(classDef.inherits_type.token.getText());
                    classDef.inherits_type.accept(this);
                for (var f : classDef.features) {
                    f.accept(this);
                }
                indent--;
                return null;
            }

            @Override
            public Void visit(AssignExpr assignExpr) {
                printIndent("<-");
                indent++;
                assignExpr.name.accept(this);
                assignExpr.e.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Dispatch dispatch) {
                printIndent(".");
                indent++;
                dispatch.object.accept(this);
                if (dispatch.class_name != null)
                    dispatch.class_name.accept(this);
                if (dispatch.call != null)
                    dispatch.call.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Call call) {
                printIndent("implicit dispatch");
                indent++;
                call.name.accept(this);
                for (var arg : call.args) {
                    if (arg != null)
                        arg.accept(this);
                }
                indent--;
                return null;
            }

            @Override
            public Void visit(While while1) {
                printIndent("while");
                indent++;
                while1.cond.accept(this);
                while1.whileBody.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Block block) {
                printIndent("block");
                indent++;
                for (var e : block.block_expressions) {
                    e.accept(this);
                }
                indent--;
                return null;
            }

            @Override
            public Void visit(Let let) {
                printIndent("let");
                indent++;
                for (var v : let.variables) {
                    v.accept(this);
                }
                let.let_block_expr.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Case case1) {
                printIndent("case");
                indent++;
                case1.cond.accept(this);

                for (var caseBranch : case1.caseBranches) {
                    caseBranch.accept(this);
                }

                indent--;
                return null;
            }

            @Override
            public Void visit(NewType newType) {
                printIndent("new");
                indent++;
                newType.type.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(IsVoidExpr isVoidExpr) {
                printIndent("isvoid");
                indent++;
                isVoidExpr.e.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Mult mult) {
                printIndent("*");
                indent++;
                mult.left.accept(this);
                mult.right.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Div div) {
                printIndent("/");
                indent++;
                div.left.accept(this);
                div.right.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Plus plus) {
                printIndent("+");
                indent++;
                plus.left.accept(this);
                plus.right.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Minus minus) {
                printIndent("-");
                indent++;
                minus.left.accept(this);
                minus.right.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(TildeExpr tildeExpr) {
                printIndent("~");
                indent++;
                tildeExpr.e.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Relational relational) {
                printIndent(relational.operation);
                indent++;
                relational.left.accept(this);
                relational.right.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Equal equal) {
                printIndent("=");
                indent++;
                equal.left.accept(this);
                equal.right.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(NotExpr notExpr) {
                printIndent("not");
                indent++;
                notExpr.e.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(Paren paren) {
                paren.e.accept(this);
                return null;
            }

            @Override
            public Void visit(Int int1) {
                printIndent(int1.token.getText());
                return null;
            }

            @Override
            public Void visit(Str str) {
                String text = str.token.getText();
                StringBuilder result = new StringBuilder();
                for (int i = 1; i < text.length() - 1; i++) {
                    if (text.charAt(i) == '\\') {
                        i++;
                        if (text.charAt(i) == '\n' || text.charAt(i) == 'n')
                            result.append('\n');
                        else if (text.charAt(i) == 't')
                            result.append('\t');
                        else if (text.charAt(i) == 'b')
                            result.append('\b');
                        else if (text.charAt(i) == 'f')
                            result.append('\f');
                        else
                            result.append(text.charAt(i));
                    } else {
                        result.append(text.charAt(i));
                    }
                }
                printIndent(result.toString());
                return null;
            }

            @Override
            public Void visit(Bool bool) {
                printIndent(bool.token.getText());
                return null;
            }

            @Override
            public Void visit(Program program) {
                printIndent("program");
                indent++;
                for (var c : program.class_list) {
                    c.accept(this);
                }
                indent--;
                return null;
            }

            @Override
            public Void visit(Self self) {
                printIndent(self.token.getText());
                return null;
            }

            @Override
            public Void visit(LetLocal letLocal) {
                printIndent("local");
                indent++;
                letLocal.name.accept(this);
                letLocal.type.accept(this);
                if (letLocal.e != null)
                    letLocal.e.accept(this);
                indent--;
                return null;
            }

            @Override
            public Void visit(CaseBranch caseBranch) {
                printIndent("case branch");
                indent++;
                caseBranch.name.accept(this);
                caseBranch.type.accept(this);
                caseBranch.expression.accept(this);
                indent--;
                return null;
            }


        };
//        ast.accept(printVisitor);
        // Populate global scope.
        SymbolTable.defineBasicClasses();
        var classesDefinitionPassVisitor = new ClassesDefinitionPassVisitor();
        var buildClassGraphPassVisitor = new BuildClassGraphPassVisitor();
        ast.accept(classesDefinitionPassVisitor);
        ast.accept(buildClassGraphPassVisitor);
        ArrayList<ArrayList<String>> cycles = Compiler.findCycles(BuildClassGraphPassVisitor.classGraph, ClassesDefinitionPassVisitor.classesList);
        var findInheritanceCyclesVisitor = new FindInheritanceCyclesVisitor(cycles);
        var setParentClassVisitor = new SetParentClassVisitor();
        ast.accept(setParentClassVisitor);
        //##########################
        //DEBUG
        var DEBUG = false;
        if (DEBUG) {
//            DefaultScope s = (DefaultScope) SymbolTable.globals;
//
//            for (var c : s.classSymbols.keySet()) {
//                System.out.println("Class name: " + c);
//                System.out.println("Parent class: " + s.lookupClassSymbol(c).getParentClassSymbol());
//                System.out.println("Parent: " + s.lookupClassSymbol(c).getParent());
//            }
            System.out.println(ClassesDefinitionPassVisitor.classesList);
            //##########################
        }

        if (!cycles.isEmpty()) {
            ast.accept(findInheritanceCyclesVisitor);
            System.err.println("Compilation halted");
            return;
        }
        var definitionPassVisitor = new DefinitionPassVisitor();
        ast.accept(definitionPassVisitor);
        var resolutionPassVisitor = new ResolutionPassVisitor();
        ast.accept(resolutionPassVisitor);
        if (SymbolTable.hasSemanticErrors()) {
            System.err.println("Compilation halted");
            return;
        }

        BuildClassGraphPassVisitor.classGraph.put("Bool", "Object");
        BuildClassGraphPassVisitor.classGraph.put("String", "Object");
        BuildClassGraphPassVisitor.classGraph.put("Int", "Object");
        BuildClassGraphPassVisitor.classGraph.put("IO", "Object");

        var classGraph = BuildClassGraphPassVisitor.classGraph;

        // form reverse graph -- parent to children
        for (Map.Entry<String, String> entry : classGraph.entrySet()) {
            String child = entry.getKey(); // child
            String parent = entry.getValue(); // parent

            if (!reverseGraph.containsKey(parent)) {
                ArrayList<String> array = new ArrayList<>();
                array.add(child);
                reverseGraph.put(parent, array);
            } else {
                ArrayList<String> array = (ArrayList<String>) reverseGraph.get(parent);
                array.add(child);
                reverseGraph.put(parent, array);
            }
        }

//        System.out.println(classGraph);
//        System.out.println(reverseGraph);
        var codeGenConstGenVisitor = new CodeGenConstGenVisitor();
        ast.accept(codeGenConstGenVisitor);
        var codeGenVisitor = new CodeGenVisitor();
        var out = ast.accept(codeGenVisitor);
//        System.out.println(out.render());
    }

}


