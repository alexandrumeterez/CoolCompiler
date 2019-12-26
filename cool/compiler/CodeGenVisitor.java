package cool.compiler;

import cool.structures.AttributeSymbol;
import cool.structures.ClassSymbol;
import cool.structures.MethodSymbol;
import cool.structures.SymbolTable;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;

public class CodeGenVisitor implements ASTVisitor<ST> {

    ST programST = CodeGenConstGenVisitor.programST;
    STGroupFile templates = CodeGenConstGenVisitor.templates;
    ArrayList<ST> functionsCode = new ArrayList<>();
    static int dispatchIndex = 0;

    private ST getSequence(ST pieceOfCode) {
        return templates.getInstanceOf("sequence").add("e", pieceOfCode);
    }

    public CodeGenVisitor() {
    }

    @Override
    public ST visit(TypeId id) {
        return null;
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
        }
        return objectIdST;
    }

    @Override
    public ST visit(If iff) {
        return null;
    }

    @Override
    public ST visit(Formal formal) {
        return null;
    }

    @Override
    public ST visit(FuncDef funcDef) {
        ST defineMethod = templates.getInstanceOf("define_method");
        MethodSymbol methodSymbol = (MethodSymbol) funcDef.getSymbol();
        ClassSymbol classSymbol = (ClassSymbol) methodSymbol.getParent();

        defineMethod.add("name", methodSymbol.getName());
        defineMethod.add("class", classSymbol.getName());

        if (methodSymbol.getAttributeSymbols().size() > 0) {
            defineMethod.add("param_inc", templates.getInstanceOf("stack_inc").add("val", 4 * methodSymbol.getAttributeSymbols().size()));
        } else {
            defineMethod.add("param_inc", "");
        }

        ST methodBody = templates.getInstanceOf("sequence");
        methodBody.add("e", funcDef.func_body.accept(this));

        defineMethod.add("body", methodBody);

        //TODO: the local variables in the function

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
        return null;
    }

    @Override
    public ST visit(Dispatch dispatch) {
        ST dispatchST;
        if(dispatch.class_name != null)
            dispatchST = templates.getInstanceOf("dispatch_call");
        else
            dispatchST = templates.getInstanceOf("method_call");
        var methodSymbol = (MethodSymbol) dispatch.call.getSymbol();
        for (var p : dispatch.call.args) {
            ST param = templates.getInstanceOf("load_param");
            param.add("param", p.accept(this));
            dispatchST.add("param", param);
        }

        dispatchST.add("obj", dispatch.object.accept(this));
        dispatchST.add("label", "dispatch" + dispatchIndex);
        if(dispatch.class_name != null)
            dispatchST.add("class", dispatch.class_name.token.getText());
        dispatchIndex++;
        System.out.println(dispatch.call.getSymbol());
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
        for (var p : call.args) {
            ST param = templates.getInstanceOf("load_param");
            param.add("param", p.accept(this));
            dispatchST.add("param", param);
        }

        dispatchST.add("obj", templates.getInstanceOf("self"));
        dispatchST.add("label", "dispatch" + dispatchIndex);
        dispatchIndex++;

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
        for(var v : block.block_expressions) {
            blockST.add("e", v.accept(this));
        }
        return blockST;
    }

    @Override
    public ST visit(Let let) {
        return null;
    }

    @Override
    public ST visit(Case case1) {
        return null;
    }

    @Override
    public ST visit(NewType newType) {
        return null;
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
        return null;
    }

    @Override
    public ST visit(CaseBranch caseBranch) {
        return null;
    }
}
