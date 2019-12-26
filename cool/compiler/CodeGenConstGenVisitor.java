package cool.compiler;

import cool.structures.*;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.util.*;

class ClassProtObj {
    LinkedList<AttributeSymbol> attributes = new LinkedList<>();
    Map<String, AttributeSymbol> attributeNameToAttributeSymbol = new LinkedHashMap<>();
}

class ClassDispatchTable {
    LinkedList<MethodSymbol> methods = new LinkedList<>();
    LinkedList<String> methodsNames = new LinkedList<>();
}

class ClassObject {
    ClassProtObj protObjList = new ClassProtObj();
    ClassDispatchTable dispatchTable = new ClassDispatchTable();
};


public class CodeGenConstGenVisitor implements ASTVisitor<ST> {
    private ST getSequence(ST pieceOfCode) {
        return templates.getInstanceOf("sequence").add("e", pieceOfCode);
    }

    private ST getSequence(String pieceOfCode) {
        return templates.getInstanceOf("sequence").add("e", pieceOfCode);
    }

    private ClassObject createClassObject(String name) {
        ClassObject classObject = new ClassObject();

        // find inheritnace chain of current class
        ArrayList<String> inheritanceChain = new ArrayList<>();
        var current = SymbolTable.globals.lookupClassSymbol(name);
        while (current != null) {
            inheritanceChain.add(current.getName());
            current = current.getParentClassSymbol();
        }
        Collections.reverse(inheritanceChain);

        // go from Object down the inheritance chain to current class and add all attributes and methods
        int currentOffset = 12;
        int currentMethodOffset = 0;
        for (var c : inheritanceChain) {
            ClassSymbol classSymbol = SymbolTable.globals.lookupClassSymbol(c);
            // add all variables
            // set offset for symbols
            for (Map.Entry<String, AttributeSymbol> entry : classSymbol.getAttributeSymbols().entrySet()) {
                AttributeSymbol attributeSymbol = entry.getValue();
                // append symbols to the current classobject
                if (attributeSymbol.getName().equals("self")) {
                    continue;
                }
                classObject.protObjList.attributeNameToAttributeSymbol.put(attributeSymbol.getName(), attributeSymbol);

                attributeSymbol.setOffset(currentOffset);
                classObject.protObjList.attributes.add(attributeSymbol);
                currentOffset += 4;
            }
            // add all methods
            for (Map.Entry<String, MethodSymbol> entry : classSymbol.getMethodSymbols().entrySet()) {
                MethodSymbol methodSymbol = entry.getValue();
                methodSymbol.setOffset(currentMethodOffset);
                currentMethodOffset += 4;
                // append symbols to the current classobject
                classObject.dispatchTable.methods.add(methodSymbol);
                classObject.dispatchTable.methodsNames.add(classSymbol.getName() + "." + methodSymbol.getName());
            }
        }

        return classObject;
    }

    private void createClassNameToClassObjectMap() {
        for (Map.Entry<String, Integer> entry : classNameToIndexMap.entrySet()) {
            String className = entry.getKey();

            ClassObject classObject = createClassObject(className);
            classNameToClassObjectMap.put(className, classObject);
        }
    }

    private ST createStringConstant(int index, int word_dim, String int_const_name, String text, int tag) {
        ST str_constant = templates.getInstanceOf("str_const");
        str_constant.add("index", index);
        str_constant.add("word_dim", word_dim);
        str_constant.add("int_const_name", int_const_name);
        str_constant.add("tag", tag);
        str_constant.add("text", text);
        return str_constant;
    }

    private ST createAndAddIntConstant(int index, int value, int tag) {
        ST int_constant = templates.getInstanceOf("int_const");
        int_constant.add("index", index);
        int_constant.add("val", value);
        int_constant.add("tag", tag);
        intValueToIntConstMap.put(value, "int_const" + index);
        return int_constant;
    }

    private ST createBoolConstant(int tag) {
        ST bool_constant = templates.getInstanceOf("bool_const");
        bool_constant.add("tag", tag);
        return bool_constant;
    }

    private ST createClassNameTab() {
        ST classNameTab = templates.getInstanceOf("classnametab");
        for (Map.Entry<String, String> entry : classNameToStrConstMap.entrySet()) {
            if (entry.getKey().equals(""))
                continue;
            ;
            classNameTab.add("tag", ".word " + entry.getValue());
        }
        return classNameTab;
    }

    private ST createClassObjTab() {
        ST classObjTab = templates.getInstanceOf("classobjtab");
        for (String v : classNameToIndexMap.keySet()) {
            if (v.equals(""))
                continue;
            classObjTab.add("tag", ".word " + v + "_protObj");
            classObjTab.add("tag", ".word " + v + "_init");
        }
        return classObjTab;
    }

    static STGroupFile templates = new STGroupFile("cool/template.stg");
    static ST programST = templates.getInstanceOf("program");

    static ST mainSection;
    static ST dataSection;
    static ST textSection;

    static int strConstIndex = 1; //starts at 6 because first 5 are default classes
    static int intConstIndex = 1; //TODO: why

    static HashMap<String, ClassObject> classNameToClassObjectMap = new LinkedHashMap<>();
    static HashMap<String, Integer> classNameToIndexMap = new LinkedHashMap<>();
    static HashMap<Integer, String> classIndexToClassNameMap = new LinkedHashMap<>();
    static HashMap<String, String> classNameToStrConstMap = new LinkedHashMap<>();
    static HashMap<String, String> strConstToClassNameMap = new LinkedHashMap<>();
    static HashMap<String, String> strValueToStrConstMap = new LinkedHashMap<>();
    static HashMap<Integer, String> intValueToIntConstMap = new LinkedHashMap<>();

    static int strConstFileNameIndex;

    static ArrayList<ST> intConstantsList = new ArrayList<>();
    static ArrayList<ST> stringConstantsList = new ArrayList<>();
    static ArrayList<ST> protObjList = new ArrayList<>();
    static ArrayList<ST> dispTabList = new ArrayList<>();

    public CodeGenConstGenVisitor() {
        // Build class name to index mapping
        int classTagIndex = 0;
        HashSet<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push("Object");
        while (!stack.empty()) {
            var current = stack.pop();

            if (!visited.contains(current)) {
                classNameToIndexMap.put(current, classTagIndex);
                classIndexToClassNameMap.put(classTagIndex, current);
                classTagIndex++;
                visited.add(current);
            }
            if (Compiler.reverseGraph.containsKey(current)) {
                var children = Compiler.reverseGraph.get(current);
                for (var c : children) {
                    if (!visited.contains(c)) {
                        stack.push(c);
                    }
                }
            }
        }

        // Now each class has its own class tag
        // Now I build the int const mapping and str const mapping

        // Build int const and str const for the default classes: object, io, int, bool, string
        ST str_const0 = createStringConstant(0, 5, "int_const0", "", classNameToIndexMap.get("String"));
        stringConstantsList.add(str_const0);
        classNameToStrConstMap.put("", "str_const0");
        strConstToClassNameMap.put("str_const0", "");
        strValueToStrConstMap.put("", "str_const0");
        ST int_const0 = createAndAddIntConstant(0, 0, classNameToIndexMap.get("Int"));
        intConstantsList.add(int_const0);

//        System.out.println(classNameToIndexMap);
        // compute all int consts and str consts
        for (Map.Entry<String, Integer> entry : classNameToIndexMap.entrySet()) {
            String className = entry.getKey();
            int tag = entry.getValue();

            // computer wordDim for the str const
            int length = className.length() + 1;
            int wordDim = length / 4;
            if (length % 4 != 0) {
                wordDim = length / 4 + 1;
            }
            wordDim += 4;

            // if the int const with the correct value doesnt exist, then create it
            if (!intValueToIntConstMap.containsKey(className.length())) {
                ST int_const = createAndAddIntConstant(intConstIndex, className.length(), classNameToIndexMap.get("Int"));
                intConstantsList.add(int_const);
                intConstIndex++;
            }
            // create the str const
            ST str_const = createStringConstant(strConstIndex, wordDim, intValueToIntConstMap.get(className.length()), className, classNameToIndexMap.get("String"));
            stringConstantsList.add(str_const);
            classNameToStrConstMap.put(className, "str_const" + strConstIndex);
            strConstToClassNameMap.put("str_const" + strConstIndex, className);
            strValueToStrConstMap.put(className, "str_const" + strConstIndex);
            strConstIndex++;
        }
//        System.out.println(Compiler.reverseGraph);



        createClassNameToClassObjectMap();

        // build the protobj list
        for (Map.Entry<String, Integer> entry : classNameToIndexMap.entrySet()) {
            var className = entry.getKey();
            var classIndex = entry.getValue();

            ClassObject classObject = classNameToClassObjectMap.get(className);
            int nAttributes = classObject.protObjList.attributes.size();
            int wordDim = nAttributes + 3;
            ST protObjST = templates.getInstanceOf("protObj");
            protObjST.add("index", classIndex);
            protObjST.add("name", className);

            // add attributes
            for (var attribute : classObject.protObjList.attributes) {
                switch (attribute.getType().getName()) {
                    case "Int":
                        protObjST.add("e", ".word int_const0");
                        break;
                    case "String":
                        protObjST.add("e", ".word str_const0");
                        break;
                    case "Bool":
                        protObjST.add("e", ".word bool_const0");
                        break;
                    default:
                        protObjST.add("e", ".word 0");
                        break;
                }
            }

            // add special inits for int string and bool
            switch (className) {
                case "Int":
                    wordDim = 4;
                    protObjST.add("e", ".word 0");
                    break;
                case "String":
                    wordDim = 5;
                    protObjST.add("e", ".asciiz \"\"");
                    protObjST.add("e", ".align 2");
                    break;
                case "Bool":
                    wordDim = 4;
                    protObjST.add("e", ".word 0");
                    break;
            }

            protObjST.add("dim", wordDim);
            protObjList.add(protObjST);
        }

        // build the dispatch table list
        for (Map.Entry<String, Integer> entry : classNameToIndexMap.entrySet()) {
            var className = entry.getKey();
            var classIndex = entry.getValue();

            ClassObject classObject = classNameToClassObjectMap.get(className);
            var methodsNames = classObject.dispatchTable.methodsNames;

            ST dispTab = templates.getInstanceOf("dispTab");
            dispTab.add("name", className);
            for (var v : methodsNames) {
                dispTab.add("e", getSequence(".word " + v));
            }

            dispTabList.add(dispTab);
        }
    }

    @Override
    public ST visit(TypeId id) {
        return null;
    }

    @Override
    public ST visit(ObjectId objectId) {
        return null;
    }

    @Override
    public ST visit(If iff) {
        iff.cond.accept(this);
        iff.thenBranch.accept(this);
        iff.elseBranch.accept(this);
        return null;
    }

    @Override
    public ST visit(Formal formal) {
        return null;
    }

    @Override
    public ST visit(FuncDef funcDef) {
        funcDef.func_body.accept(this);
        return null;
    }

    @Override
    public ST visit(ClassDef classDef) {
        File f = new File(Compiler.fileNames.get(classDef.ctx));
        // add strconst with file name
        var text = f.getName();
        if(!strValueToStrConstMap.containsKey(text)) {
            int length = text.length() + 1;
            int wordDim = length / 4;
            if (length % 4 != 0) {
                wordDim = length / 4 + 1;
            }
            wordDim += 4;

            // if the int const with the correct value doesnt exist, then create it
            if (!intValueToIntConstMap.containsKey(text.length())) {
                ST int_const = createAndAddIntConstant(intConstIndex, text.length(), classNameToIndexMap.get("Int"));
                intConstantsList.add(int_const);
                intConstIndex++;
            }
            // create the str const
            ST str_const = createStringConstant(strConstIndex, wordDim, intValueToIntConstMap.get(text.length()), text, classNameToIndexMap.get("String"));
            stringConstantsList.add(str_const);
            classNameToStrConstMap.put(text, "str_const" + strConstIndex);
            strConstToClassNameMap.put("str_const" + strConstIndex, text);
            strValueToStrConstMap.put(text, "str_const" + strConstIndex);
            strConstFileNameIndex = strConstIndex;
            strConstIndex++;
        }
        for (var v : classDef.features) {
            v.accept(this);
        }
        return null;
    }

    @Override
    public ST visit(AssignExpr assignExpr) {
        assignExpr.e.accept(this);
        return null;
    }

    @Override
    public ST visit(Dispatch dispatch) {
        dispatch.call.accept(this);
        return null;
    }

    @Override
    public ST visit(Call call) {
        for (var v : call.args) {
            v.accept(this);
        }
        return null;
    }

    @Override
    public ST visit(While while1) {
        while1.cond.accept(this);
        while1.whileBody.accept(this);
        return null;
    }

    @Override
    public ST visit(Block block) {
        for (var e : block.block_expressions) {
            e.accept(this);
        }
        return null;
    }

    @Override
    public ST visit(Let let) {
        for (var v : let.variables) {
            v.accept(this);
        }
        let.let_block_expr.accept(this);
        return null;
    }

    @Override
    public ST visit(Case case1) {
        case1.cond.accept(this);
        for (var v : case1.caseBranches) {
            v.accept(this);
        }
        return null;
    }

    @Override
    public ST visit(NewType newType) {
        return null;
    }

    @Override
    public ST visit(IsVoidExpr isVoidExpr) {
        isVoidExpr.e.accept(this);
        return null;
    }

    @Override
    public ST visit(Mult mult) {
        mult.left.accept(this);
        mult.right.accept(this);
        return null;
    }

    @Override
    public ST visit(Div div) {
        div.left.accept(this);
        div.right.accept(this);
        return null;
    }

    @Override
    public ST visit(Plus plus) {
        plus.left.accept(this);
        plus.right.accept(this);
        return null;
    }

    @Override
    public ST visit(Minus minus) {
        minus.left.accept(this);
        minus.right.accept(this);
        return null;
    }

    @Override
    public ST visit(TildeExpr tildeExpr) {
        tildeExpr.e.accept(this);
        return null;
    }

    @Override
    public ST visit(Relational relational) {
        relational.left.accept(this);
        relational.right.accept(this);
        return null;
    }

    @Override
    public ST visit(Equal equal) {
        equal.left.accept(this);
        equal.right.accept(this);
        return null;
    }

    @Override
    public ST visit(NotExpr notExpr) {
        notExpr.e.accept(this);
        return null;
    }

    @Override
    public ST visit(Paren paren) {
        paren.e.accept(this);
        return null;
    }

    @Override
    public ST visit(Int int1) {
        var integer1 = Integer.parseInt(int1.token.getText());
        if (!intValueToIntConstMap.containsKey(integer1)) {
            ST int_const = createAndAddIntConstant(intConstIndex, integer1, classNameToIndexMap.get("Int"));
            intConstantsList.add(int_const);
            intConstIndex++;
        }
        return null;
    }

    @Override
    public ST visit(Str str) {
        var text = str.token.getText().substring(1, str.token.getText().length() - 1);
        if (!strValueToStrConstMap.containsKey(text)) {
            // computer wordDim for the str const
            int length = text.length() + 1;
            int wordDim = length / 4;
            if (length % 4 != 0) {
                wordDim = length / 4 + 1;
            }
            wordDim += 4;

            // if the int const with the correct value doesnt exist, then create it
            if (!intValueToIntConstMap.containsKey(text.length())) {
                ST int_const = createAndAddIntConstant(intConstIndex, text.length(), classNameToIndexMap.get("Int"));
                intConstantsList.add(int_const);
                intConstIndex++;
            }
            // create the str const
            ST str_const = createStringConstant(strConstIndex, wordDim, intValueToIntConstMap.get(text.length()), text, classNameToIndexMap.get("String"));
            stringConstantsList.add(str_const);
            strValueToStrConstMap.put(text, "str_const" + strConstIndex);
            strConstIndex++;
        }
        return null;
    }

    @Override
    public ST visit(Bool bool) {
        return null;
    }

    @Override
    public ST visit(Program program) {
        dataSection = templates.getInstanceOf("sequence");
        textSection = templates.getInstanceOf("sequence");
        mainSection = templates.getInstanceOf("sequence");
        for (var e : program.class_list) {
            mainSection.add("e", e.accept(this));
        }

        programST.add("int", classNameToIndexMap.get("Int"));
        programST.add("string", classNameToIndexMap.get("String"));
        programST.add("bool", classNameToIndexMap.get("Bool"));
//
        dataSection.add("e", stringConstantsList);
        dataSection.add("e", intConstantsList);
        dataSection.add("e", createBoolConstant(classNameToIndexMap.get("Bool")));

        dataSection.add("e", createClassNameTab());
        dataSection.add("e", createClassObjTab());

        dataSection.add("e", protObjList);
        dataSection.add("e", dispTabList);


        return programST;
    }

    @Override
    public ST visit(VarDef varDef) {
        if (varDef.init != null) {
            varDef.init.accept(this);
        }
        return null;
    }

    @Override
    public ST visit(Self self) {
        return null;
    }

    @Override
    public ST visit(LetLocal letLocal) {
        letLocal.e.accept(this);
        return null;
    }

    @Override
    public ST visit(CaseBranch caseBranch) {
        caseBranch.expression.accept(this);
        return null;
    }
}
