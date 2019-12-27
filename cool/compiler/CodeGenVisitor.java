package cool.compiler;

import cool.structures.AttributeSymbol;
import cool.structures.ClassSymbol;
import cool.structures.MethodSymbol;
import cool.structures.SymbolTable;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.ListIterator;

public class CodeGenVisitor implements ASTVisitor<ST> {

    ST programST = CodeGenConstGenVisitor.programST;
    STGroupFile templates = CodeGenConstGenVisitor.templates;
    ArrayList<ST> functionsCode = new ArrayList<>();
    static int dispatchIndex = 0;
    int numberLocalVariables = 0;

    private ST getSequence(ST pieceOfCode) {
        return templates.getInstanceOf("sequence").add("e", pieceOfCode);
    }

    public CodeGenVisitor() {
    }

    @Override
    public ST visit(TypeId id) {
        ST constDeclr = templates.getInstanceOf("const_load");
        var type = id.token.getText();
        switch (type) {
            case "String":
                constDeclr.add("tag", "str_const0");
                break;
            case "Int":
                constDeclr.add("tag", "int_const0");
                break;
            case "Bool":
                constDeclr.add("tag", "bool_const0");
                break;
            default:
                constDeclr.add("tag", "0");
                break;
        }
        return constDeclr;
    }

    @Override
    public ST visit(ObjectId objectId) {
        ST objectIdST;
        if (objectId.token.getText().equals("self")) {
            objectIdST = templates.getInstanceOf("self");
        } else {
            objectIdST = templates.getInstanceOf("id");
            // TODO: check this
            var symbol = (AttributeSymbol) objectId.getSymbol();
            objectIdST.add("offset", symbol.getOffset());
            objectIdST.add("location", symbol.getLocation());
        }
        return objectIdST;
    }

    static int end_index = 0;
    static int else_index = 0;

    @Override
    public ST visit(If iff) {
        ST ifST = templates.getInstanceOf("if");
        ifST.add("cond", iff.cond.accept(this));
        ifST.add("else_label", "else" + else_index);
        else_index++;
        ifST.add("end_label", "end" + end_index);
        end_index++;
        ifST.add("thenBranch", iff.thenBranch.accept(this));
        ifST.add("elseBranch", iff.elseBranch.accept(this));
        return ifST;
    }

    @Override
    public ST visit(Formal formal) {
        return null;
    }

    @Override
    public ST visit(FuncDef funcDef) {
        numberLocalVariables = 0;
        ST defineMethod = templates.getInstanceOf("define_method");
        MethodSymbol methodSymbol = (MethodSymbol) funcDef.getSymbol();
        ClassSymbol classSymbol = (ClassSymbol) methodSymbol.getParent();
        defineMethod.add("name", methodSymbol.getName());
        defineMethod.add("class", classSymbol.getName());

//        System.out.println(funcDef.token);
//        for(var v : funcDef.params)
//            System.out.println(v.getSymbol().getOffset());
//        System.out.println();

        if (methodSymbol.getAttributeSymbols().size() > 0) {
            defineMethod.add("param_inc", templates.getInstanceOf("stack_inc").add("val", 4 * methodSymbol.getAttributeSymbols().size()));
        } else {
            defineMethod.add("param_inc", "");
        }

        ST methodBody = templates.getInstanceOf("sequence");
        methodBody.add("e", funcDef.func_body.accept(this));

        defineMethod.add("body", methodBody);

        //TODO: the local variables in the function
        if (numberLocalVariables > 0) {
            defineMethod.add("var_dec", templates.getInstanceOf("stack_dec").add("val", 4 * numberLocalVariables));
            defineMethod.add("var_inc", templates.getInstanceOf("stack_inc").add("val", 4 * numberLocalVariables));
        } else {
            defineMethod.add("var_dec", "");
            defineMethod.add("var_inc", "");
        }


        return defineMethod;
    }

    @Override
    public ST visit(ClassDef classDef) {
        ST class_init = templates.getInstanceOf("obj_init");
        var classSymbol = (ClassSymbol) classDef.getSymbol();
        // set class name
        class_init.add("name", classSymbol.getName());
        // set parent class
        class_init.add("parent", "jal     " + classSymbol.getParentClassSymbol().getName() + "_init");
        var classObject = CodeGenConstGenVisitor.classNameToClassObjectMap.get(classSymbol.getName());
        for (var v : classDef.features) {
            var featureCode = v.accept(this);
            if (v instanceof VarDef && featureCode != null) {
                class_init.add("e", getSequence(featureCode));
            } else if (v instanceof FuncDef && featureCode != null) {
                functionsCode.add(getSequence(featureCode));
            }
        }

        return class_init;
    }

    @Override
    public ST visit(AssignExpr assignExpr) {
        ST assignST = templates.getInstanceOf("assign");
//        System.out.println(assignExpr.name.getSymbol().getOffset());
        assignST.add("expr", assignExpr.e.accept(this));
        assignST.add("offset", assignExpr.name.getSymbol().getOffset());
        assignST.add("location", assignExpr.name.getSymbol().getLocation());

        return assignST;
    }

    @Override
    public ST visit(Dispatch dispatch) {
        ST dispatchST;
        if (dispatch.class_name != null)
            dispatchST = templates.getInstanceOf("dispatch_call");
        else
            dispatchST = templates.getInstanceOf("method_call");
        var methodSymbol = (MethodSymbol) dispatch.call.getSymbol();
        var li = dispatch.call.args.listIterator(dispatch.call.args.size());

        while (li.hasPrevious()) {
            ST param = templates.getInstanceOf("load_param");
            param.add("param", li.previous().accept(this));
            dispatchST.add("param", param);
        }

        dispatchST.add("obj", dispatch.object.accept(this));
        dispatchST.add("label", "dispatch" + dispatchIndex);
        if (dispatch.class_name != null)
            dispatchST.add("class", dispatch.class_name.token.getText());

        dispatchST.add("m_offset", methodSymbol.getOffset());

        dispatchST.add("line", dispatch.call.name.token.getLine());
        dispatchST.add("file", "str_const" + CodeGenConstGenVisitor.strConstFileNameIndex);
        dispatchIndex++;

        return dispatchST;
    }

    @Override
    public ST visit(Call call) {
        ST dispatchST = templates.getInstanceOf("method_call");
        var methodSymbol = (MethodSymbol) call.getSymbol();
        var li = call.args.listIterator(call.args.size());

        while (li.hasPrevious()) {
            ST param = templates.getInstanceOf("load_param");
            param.add("param", li.previous().accept(this));
            dispatchST.add("param", param);
        }

        dispatchST.add("obj", templates.getInstanceOf("self"));
        dispatchST.add("label", "dispatch" + dispatchIndex);

        dispatchST.add("m_offset", methodSymbol.getOffset());

        dispatchST.add("line", call.name.token.getLine());
        dispatchST.add("file", "str_const" + CodeGenConstGenVisitor.strConstFileNameIndex);
        dispatchIndex++;

        return dispatchST;
    }

    @Override
    public ST visit(While while1) {
        return null;
    }

    @Override
    public ST visit(Block block) {
        ST blockST = templates.getInstanceOf("sequence");
        for (var v : block.block_expressions) {
            blockST.add("e", v.accept(this));
        }
        return blockST;
    }

    @Override
    public ST visit(Let let) {
        ST letST = templates.getInstanceOf("let");
        for (var v : let.variables) {
            numberLocalVariables++;
            letST.add("var", v.accept(this));
        }
        letST.add("body", let.let_block_expr.accept(this));
        return letST;
    }

    @Override
    public ST visit(Case case1) {
        return null;
    }

    @Override
    public ST visit(NewType newType) {
        ST newTypeST;
        if (newType.type.token.getText().equals("SELF_TYPE")) {
            newTypeST = templates.getInstanceOf("selftype");
        } else {
            newTypeST = templates.getInstanceOf("new").add("class", newType.type.token.getText());
        }
        return newTypeST;
    }

    @Override
    public ST visit(IsVoidExpr isVoidExpr) {
        return null;
    }

    @Override
    public ST visit(Mult mult) {
        return null;
    }

    @Override
    public ST visit(Div div) {
        return null;
    }

    @Override
    public ST visit(Plus plus) {
        return null;
    }

    @Override
    public ST visit(Minus minus) {
        return null;
    }

    @Override
    public ST visit(TildeExpr tildeExpr) {
        return null;
    }

    @Override
    public ST visit(Relational relational) {
        return null;
    }

    @Override
    public ST visit(Equal equal) {
        return null;
    }

    @Override
    public ST visit(NotExpr notExpr) {
        return null;
    }

    @Override
    public ST visit(Paren paren) {
        return null;
    }

    @Override
    public ST visit(Int int1) {
        var intConst = CodeGenConstGenVisitor.intValueToIntConstMap.get(Integer.parseInt(int1.token.getText()));
        var constLoad = templates.getInstanceOf("const_load");
        constLoad.add("tag", intConst);
        return constLoad;
    }

    @Override
    public ST visit(Str str) {
        var strConst = CodeGenConstGenVisitor.strValueToStrConstMap.get(str.token.getText().substring(1, str.token.getText().length() - 1));
        var constLoad = templates.getInstanceOf("const_load");
        constLoad.add("tag", strConst);
        return constLoad;
    }

    @Override
    public ST visit(Bool bool) {
        var constLoad = templates.getInstanceOf("const_load");
        if (bool.token.getText().equals("true")) {
            constLoad.add("tag", "bool_const1");
        } else {
            constLoad.add("tag", "bool_const0");
        }
        return constLoad;
    }

    @Override
    public ST visit(Program program) {

        for (var e : program.class_list) {
            var classCode = e.accept(this);
            CodeGenConstGenVisitor.textSection.add("e", getSequence(classCode));
        }
        CodeGenConstGenVisitor.textSection.add("e", functionsCode);
        programST.add("data", CodeGenConstGenVisitor.dataSection);
        programST.add("text", CodeGenConstGenVisitor.textSection);
        return programST;
    }

    @Override
    public ST visit(VarDef varDef) {
        // if the variable isnt initialized, then return null
        if (varDef.init == null) {
            return null;
        }
        // get the constant from the vardef init
        ST constLoad = varDef.init.accept(this);
        // get the offset
        ST attribInit = templates.getInstanceOf("attrib_init");
        AttributeSymbol attributeSymbol = (AttributeSymbol) varDef.getSymbol();
        attribInit.add("offset", attributeSymbol.getOffset());
        // if it is, then fill the template
        ST loadConstAndInitAttribute = templates.getInstanceOf("sequence");
        loadConstAndInitAttribute.add("e", constLoad);
        loadConstAndInitAttribute.add("e", attribInit);
        return loadConstAndInitAttribute;
    }

    @Override
    public ST visit(Self self) {
        return null;
    }

    @Override
    public ST visit(LetLocal letLocal) {
        ST letLocalST = templates.getInstanceOf("assign");
        letLocalST.add("offset", letLocal.getSymbol().getOffset());
        letLocalST.add("location", letLocal.getSymbol().getLocation());
        if (letLocal.e != null) {
            letLocalST.add("expr", letLocal.e.accept(this));
        } else {
            letLocalST.add("expr", letLocal.type.accept(this));
        }
//        System.out.println("letlocal " + letLocal.token);
//        System.out.println(((AttributeSymbol)letLocal.getSymbol()).getType());
        return letLocalST;
    }

    @Override
    public ST visit(CaseBranch caseBranch) {
        return null;
    }
}
