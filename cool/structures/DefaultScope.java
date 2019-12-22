package cool.structures;

import java.util.*;

public class DefaultScope implements Scope {
    public Map<String, ClassSymbol> classSymbols = new LinkedHashMap<>();
    protected Map<String, MethodSymbol> methodSymbols = new LinkedHashMap<>();
    protected Map<String, AttributeSymbol> attributeSymbols = new LinkedHashMap<>();
    protected Scope parent;

    public DefaultScope(Scope parent) {
        this.parent = parent;
    }

    @Override
    public boolean add(ClassSymbol sym) {
        if (classSymbols.containsKey(sym.getName())) {
            return false;
        }
        classSymbols.put(sym.getName(), sym);
        return true;
    }

    @Override
    public boolean add(MethodSymbol sym) {
        if (methodSymbols.containsKey(sym.getName())) {
            return false;
        }
        methodSymbols.put(sym.getName(), sym);
        return true;
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
        var sym = classSymbols.get(str);
        if (sym != null) {
            return sym;
        }

        if (parent != null) {
            return parent.lookupClassSymbol(str);
        }
        return null;
    }

    @Override
    public MethodSymbol lookupMethodSymbol(String str) {
        var sym = methodSymbols.get(str);
        if (sym != null) {
            return sym;
        }

        if (parent != null) {
            return parent.lookupMethodSymbol(str);
        }
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
