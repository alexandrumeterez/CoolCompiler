package cool.structures;

import cool.compiler.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MethodSymbol extends Symbol implements Scope {
    public Map<String, AttributeSymbol> getAttributeSymbols() {
        return attributeSymbols;
    }

    protected Map<String, AttributeSymbol> attributeSymbols = new LinkedHashMap<>();
    protected Scope parent;

    public LinkedList<String> getParams() {
        return params;
    }

    public void setParams(LinkedList<String> params) {
        this.params = params;
    }

    LinkedList<String> params = new LinkedList<>();

    public String getReturn_type() {
        return return_type;
    }

    public void setReturn_type(String return_type) {
        this.return_type = return_type;
    }

    String return_type;

    public ClassSymbol getType() {
        return type;
    }

    public void setType(ClassSymbol type) {
        this.type = type;
    }

    protected ClassSymbol type;

    public MethodSymbol(String name) {
        super(name);
    }

    public MethodSymbol(Scope parent, String name) {
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
