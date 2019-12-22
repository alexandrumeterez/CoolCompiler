package cool.structures;

public interface Scope {
    public boolean add(ClassSymbol sym);

    public boolean add(MethodSymbol sym);

    public boolean add(AttributeSymbol sym);

    public ClassSymbol lookupClassSymbol(String str);

    public MethodSymbol lookupMethodSymbol(String str);

    public AttributeSymbol lookupAttributeSymbol(String str);

    public Scope getParent();
}
