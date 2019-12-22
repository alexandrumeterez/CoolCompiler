package cool.structures;

public class BasicClasses {
    public static ClassSymbol OBJECT = new ClassSymbol("Object");
    public static ClassSymbol INT = new ClassSymbol(OBJECT, "Int");
    public static ClassSymbol STRING = new ClassSymbol(OBJECT, "String");
    public static ClassSymbol BOOL = new ClassSymbol(OBJECT, "Bool");
    public static ClassSymbol IO = new ClassSymbol(OBJECT, "IO");
    public static ClassSymbol SELF_TYPE = new ClassSymbol(OBJECT, "SELF_TYPE");
}
