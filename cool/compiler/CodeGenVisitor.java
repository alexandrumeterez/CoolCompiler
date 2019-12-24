package cool.compiler;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.*;

public class CodeGenVisitor implements ASTVisitor<ST> {
    private ST createStringConstant(int index, int word_dim, String int_const_name, String text, int tag) {
        ST str_constant = templates.getInstanceOf("str_const");
        str_constant.add("index", index);
        str_constant.add("word_dim", word_dim);
        str_constant.add("int_const_name", int_const_name);
        str_constant.add("tag", tag);
        return str_constant;
    }

    private ST createAndAddIntConstant(int index, int value, int tag) {
        ST int_constant = templates.getInstanceOf("int_const");
        int_constant.add("index", index);
        int_constant.add("val", value);
        int_constant.add("tag", tag);
        intConstValueToIntConstName.put(value, "int_const" + index);
        return int_constant;
    }

    private ST createBoolConstant(int tag) {
        ST bool_constant = templates.getInstanceOf("bool_const");
        bool_constant.add("tag", tag);
        return bool_constant;
    }

    private ST createClassNameTab() {
        ST classNameTab = templates.getInstanceOf("classnametab");
        for(Map.Entry<String, String> entry : classNameToStrConstMap.entrySet()) {
            if(entry.getKey().equals(""))
                continue;;
            classNameTab.add("tag", entry.getValue());
        }
        return classNameTab;
    }

    private ST createClassObjTab() {
        ST classObjTab = templates.getInstanceOf("classobjtab");
        for(String v : classNameToStrConstMap.keySet()) {
            if(v.equals(""))
                continue;
            classObjTab.add("tag", v + "_protObj");
            classObjTab.add("tag", v + "_init");
        }
        return classObjTab;
    }

    static STGroupFile templates = new STGroupFile("cool/template.stg");
    ST programST = templates.getInstanceOf("program");

    ST mainSection;
    ST dataSection;
    ST textSection;

    int intTag = 2;
    int stringTag = 3;
    int boolTag = 4;

    static int strConstIndex = 6; //starts at 6 because first 5 are default classes
    static int intConstIndex = 6; //TODO: why

    static HashMap<String, Integer> classNameToIndexMap = new LinkedHashMap<>();
    static HashMap<Integer, String> classIndexToClassNameMap = new LinkedHashMap<>();
    static HashMap<String, String> classNameToStrConstMap = new LinkedHashMap<>();
    static HashMap<String, String> strConstToClassNameMap = new LinkedHashMap<>();
    static HashMap<Integer, String> intConstValueToIntConstName = new LinkedHashMap<>();

    static ArrayList<ST> intConstantsList = new ArrayList<>();
    static ArrayList<ST> stringConstantsList = new ArrayList<>();

    public CodeGenVisitor() {
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
        ST int_const0 = createAndAddIntConstant(0, 0, classNameToIndexMap.get("Int"));
        intConstantsList.add(int_const0);


        ST str_const1 = createStringConstant(1, 6, "int_const1", "Object", classNameToIndexMap.get("String"));
        stringConstantsList.add(str_const1);
        classNameToStrConstMap.put("Object", "str_const1");
        strConstToClassNameMap.put("str_const1", "Object");
        ST int_const1 = createAndAddIntConstant(1, 6, classNameToIndexMap.get("Int"));
        intConstantsList.add(int_const1);

        ST str_const2 = createStringConstant(2, 5, "int_const2", "IO", classNameToIndexMap.get("String"));
        stringConstantsList.add(str_const2);
        classNameToStrConstMap.put("IO", "str_const2");
        strConstToClassNameMap.put("str_const2", "IO");
        ST int_const2 = createAndAddIntConstant(2, 2, classNameToIndexMap.get("Int"));
        intConstantsList.add(int_const2);

        ST str_const3 = createStringConstant(3, 5, "int_const3", "Int", classNameToIndexMap.get("String"));
        stringConstantsList.add(str_const3);
        classNameToStrConstMap.put("Int", "str_const3");
        strConstToClassNameMap.put("str_const3", "Int");
        ST int_const3 = createAndAddIntConstant(3, 3, classNameToIndexMap.get("Int"));
        intConstantsList.add(int_const3);

        ST str_const4 = createStringConstant(4, 6, "int_const1", "String", classNameToIndexMap.get("String"));
        stringConstantsList.add(str_const4);
        classNameToStrConstMap.put("String", "str_const4");
        strConstToClassNameMap.put("str_const4", "String");

        ST str_const5 = createStringConstant(5, 6, "int_const4", "Bool", classNameToIndexMap.get("String"));
        stringConstantsList.add(str_const5);
        classNameToStrConstMap.put("Bool", "str_const5");
        strConstToClassNameMap.put("str_const5", "Bool");
        ST int_const4 = createAndAddIntConstant(4, 4, classNameToIndexMap.get("Int"));
        intConstantsList.add(int_const4);

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
        return null;
    }

    @Override
    public ST visit(Formal formal) {
        return null;
    }

    @Override
    public ST visit(FuncDef funcDef) {
        return null;
    }

    @Override
    public ST visit(ClassDef classDef) {

        return null;
    }

    @Override
    public ST visit(AssignExpr assignExpr) {
        return null;
    }

    @Override
    public ST visit(Dispatch dispatch) {
        return null;
    }

    @Override
    public ST visit(Call call) {
        return null;
    }

    @Override
    public ST visit(While while1) {
        return null;
    }

    @Override
    public ST visit(Block block) {
        return null;
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
    public ST visit(Simple simple) {
        return null;
    }

    @Override
    public ST visit(Int int1) {
        return null;
    }

    @Override
    public ST visit(Str str) {
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

        // TODO: Modify this to actual values
        System.out.println(classNameToIndexMap);
        programST.add("int", classNameToIndexMap.get("Int"));
        programST.add("string", classNameToIndexMap.get("String"));
        programST.add("bool", classNameToIndexMap.get("Bool"));

        dataSection.add("e", stringConstantsList);
        dataSection.add("e", intConstantsList);
        dataSection.add("e", createBoolConstant(classNameToIndexMap.get("Bool")));
        dataSection.add("e", createClassNameTab());
        dataSection.add("e", createClassObjTab());

        programST.add("data", dataSection);
//        programST.add("text", textSection);
        return programST;
    }

    @Override
    public ST visit(VarDef varDef) {
        return null;
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
