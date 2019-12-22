package cool.structures;

public class AttributeSymbol extends Symbol {
    protected ClassSymbol type;

    public AttributeSymbol(String name) {
        super(name);
    }

    public void setType(ClassSymbol type) {
        this.type = type;
    }

    public ClassSymbol getType() {
        return type;
    }
}
