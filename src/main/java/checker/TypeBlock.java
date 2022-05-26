package checker;

import parser.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TypeBlock {
    TypeBlock prev;
    Node body;
    String type = null;
    int currentBlock = 0;
    ArrayList<TypeBlock> descendants;
    HashMap<String, TypeBlock> variables;
    HashMap<String, TypeBlock> functions;

    public TypeBlock(Node body) {
        this.prev = null;
        this.descendants = new ArrayList<>();
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.body = body;
    }

    public TypeBlock(TypeBlock prev, Node body) {
        this.prev = prev;
        this.descendants = new ArrayList<>();
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.body = body;
    }

    public TypeBlock addDescendant(Node body) {
        TypeBlock block = new TypeBlock(this, body);
        this.descendants.add(block);
        return block;
    }

    public TypeBlock addVariable(Node body, String identifier, String type) {
        TypeBlock block = new TypeBlock(this, body);
        this.variables.put(identifier, block);
        block.type = type;
        if (Objects.equals(type, "type-func")) {
            body.descendants.get(2).identifier = type;
        }
        body.descendants.get(1).identifier = type;
        return block;
    }

    public TypeBlock addFunction(Node body, String identifier) {
        TypeBlock block = new TypeBlock(this, body);
        this.functions.put(identifier, block);
        block.type = "type-func";
        return block;
    }

    public String returnType() {
        if (Objects.equals(this.type, "type-func")) {
            return this.body.descendants.get(2).identifier;
        }
        return null;
    }

    public String variableType() {
        return this.body.descendants.get(1).identifier;
    }

    public String returnType(String identifier) {
        if (this.functions.containsKey(identifier)) {
            return this.functions.get(identifier).body.descendants.get(2).identifier;
        } else if (this.variables.containsKey(identifier)) {
            if (Objects.equals(this.variables.get(identifier).type, "type-func")) {
                return this.variables.get(identifier).body.descendants.get(2).identifier;
            }
            return null;
        } else if (prev != null) {
            return prev.returnType(identifier);
        } else {
            return null;
        }
    }

    public void changeReturnType(String type) {
        if (Objects.equals(this.type, "type-func")) {
            this.body.descendants.get(2).identifier = type;
        }
    }

    public boolean isFunction (String identifier) {
        if (this.functions.containsKey(identifier)) {
            return true;
        } else if (this.variables.containsKey(identifier)) {
            if (Objects.equals(this.variables.get(identifier).type, "type-func")) {
                return true;
            }
            return false;
        } else if (prev != null) {
            return prev.isFunction(identifier);
        } else {
            return false;
        }
    }

    public TypeBlock getFunction (String identifier) {
        if (this.functions.containsKey(identifier)) {
            return this.functions.get(identifier);
        } else if (this.variables.containsKey(identifier)) {
            if (Objects.equals(this.variables.get(identifier).type, "type-func")) {
                return this.variables.get(identifier);
            }
            return null;
        } else if (prev != null) {
            return prev.getFunction(identifier);
        } else {
            return null;
        }
    }

    public TypeBlock getVariable (String identifier) {
        if (this.variables.containsKey(identifier)) {
            return this.variables.get(identifier);
        } else if (prev != null) {
            return prev.getVariable(identifier);
        } else {
            return null;
        }
    }
}
