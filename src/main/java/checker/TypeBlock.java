package checker;

import java.util.ArrayList;
import java.util.HashMap;

public class TypeBlock {
    String name;
    TypeBlock prev;
    ArrayList<TypeBlock> descendants;
    HashMap<String, String> variables;
    HashMap<String, ArrayList<String>> functions;

    public TypeBlock () {
        this.name = "";
        this.prev = null;
        this.descendants = new ArrayList<>();
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    private TypeBlock (TypeBlock prev) {
        this.name = "";
        this.prev = prev;
        this.descendants = new ArrayList<>();
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    private TypeBlock (TypeBlock prev, String name) {
        this.name = name;
        this.prev = prev;
        this.descendants = new ArrayList<>();
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    public TypeBlock addDescendant () {
        TypeBlock block = new TypeBlock(this, name);
        descendants.add(block);
        return block;
    }

    public TypeBlock addDescendant (String name) {
        TypeBlock block = new TypeBlock(this, name);
        descendants.add(block);
        return block;
    }
}
