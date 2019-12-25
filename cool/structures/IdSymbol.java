package cool.structures;

import java.util.LinkedHashMap;
import java.util.Map;

public class IdSymbol extends Symbol implements Scope {

    protected Map<String, AttributeSymbol> attributeSymbols = new LinkedHashMap<>();
    protected Scope parent;

    public ClassSymbol getType() {
        return type;
    }

    public void setType(ClassSymbol type) {
        this.type = type;
    }

    protected ClassSymbol type;
    public IdSymbol(String name) {
        super(name);
    }

    public IdSymbol(Scope parent, String name) {
        super(name);
        this.parent = parent;
    }

    @Override
    public boolean add(ClassSymbol sym) {
        return false;
    }

    @Override
    public boolean add(MethodSymbol sym) {
        return false;
    }

    @Override
    public boolean add(AttributeSymbol sym) {
        if (attributeSymbols.containsKey(sym.getName())) {
            return false;
        }

        attributeSymbols.put(sym.getName(), sym);
        return true;
    }

    @Override
    public ClassSymbol lookupClassSymbol(String str) {
        return null;
    }

    @Override
    public MethodSymbol lookupMethodSymbol(String str) {
        return null;
    }

    @Override
    public AttributeSymbol lookupAttributeSymbol(String str) {
        var sym = attributeSymbols.get(str);
        if (sym != null) {
            return sym;
        }

        if (parent != null) {
            return parent.lookupAttributeSymbol(str);
        }
        return null;
    }

    @Override
    public Scope getParent() {
        return parent;
    }
}