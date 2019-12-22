package cool.structures;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClassSymbol extends Symbol implements Scope {

    protected Map<String, ClassSymbol> classSymbols = new LinkedHashMap<>();
    protected Map<String, MethodSymbol> methodSymbols = new LinkedHashMap<>();
    protected Map<String, AttributeSymbol> attributeSymbols = new LinkedHashMap<>();
    protected Scope parent;

    public ClassSymbol getParentClassSymbol() {
        return parentClassSymbol;
    }

    public void setParentClassSymbol(ClassSymbol parentClassSymbol) {
        this.parentClassSymbol = parentClassSymbol;
    }

    protected ClassSymbol parentClassSymbol;

    public ClassSymbol(String name) {
        super(name);
    }

    public ClassSymbol(Scope parent, String name) {
        super(name);
        this.parent = parent;
    }


    @Override
    public boolean add(ClassSymbol sym) {
        return false;
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
        // Ne asiguram ca simbolul nu exista in domeniul de viziblitate curent.
        if (attributeSymbols.containsKey(sym.getName())) {
            return false;
        }

        attributeSymbols.put(sym.getName(), sym);
        return true;
    }

    @Override
    public ClassSymbol lookupClassSymbol(String str) {
        return SymbolTable.globals.lookupClassSymbol(str);
    }

    @Override
    public MethodSymbol lookupMethodSymbol(String str) {
        var sym = methodSymbols.get(str);

        if (sym != null) {
            return sym;
        }

        // Daca nu gasim simbolul in domeniul de vizibilitate curent,
        // il cautam in domeniul de deasupra.

        if (parentClassSymbol != null) {
            return parentClassSymbol.lookupMethodSymbol(str);
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

        // Daca nu gasim simbolul in domeniul de vizibilitate curent,
        // il cautam in domeniul de deasupra.

        if (parentClassSymbol != null) {
            return parentClassSymbol.lookupAttributeSymbol(str);
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

    public void setParent(Scope parent) {
        this.parent = parent;
    }
}