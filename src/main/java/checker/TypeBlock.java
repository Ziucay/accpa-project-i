package checker;

import parser.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TypeBlock {
    String name;
    TypeBlock prev;
    int currentBlock = 0;
    int functionNumber = 0;
    ArrayList<TypeBlock> descendants;
    HashMap<String, Node> variables;
    HashMap<String, Node> functions;

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
        descendants.add(functionNumber, block);
        this.functionNumber++;
        return block;
    }

    public void addFunction (String identifier, FunctionType func) {
        this.functions.put(identifier, func);
    }

    public TypeBlock getFunctionBlock (String identifier) {
        for (TypeBlock descendant : this.descendants) {
            if (Objects.equals(descendant.name, identifier)) {
                return descendant;
            }
        }
        return null;
    }

    public TypeBlock getCurrentFunctionBlock (String identifier) {
        if (prev.getFunctionBlock(identifier) != null) {
            return prev.getFunctionBlock(identifier);
        } else if (prev.prev != null) {
            return prev.getCurrentFunctionBlock(identifier);
        } else {
            return null;
        }
    }

    public Node getFunctionBody (String identifier) {
        if (this.functions.containsKey(identifier)) {
            return this.functions.get(identifier).body;
        } else if (prev != null) {
            return prev.getFunctionBody(identifier);
        } else {
            return null;
        }
    }

    public void addVariable (String identifier, Node type) {
        this.variables.put(identifier, type);
    }

    public TypeBlock getCurrentBlock () {
        return this.descendants.get(this.currentBlock + this.functionNumber);
    }

    public FunctionType getNearestFunction () {
        if (this.prev == null) {
            return null;
        }
        return getNearestFunction(this.name);
    }

    public FunctionType getNearestFunction (String name) {
        if (prev.functions.containsKey(name)) {
            return prev.functions.get(name);
        } else if (prev.prev != null) {
            return prev.getNearestFunction(name);
        } else {
            return null;
        }
    }

    public Node getVariableValue (String identifier) {
        if (this.variables.containsKey(identifier)) {
            return this.variables.get(identifier);
        } else if (prev != null) {
            return prev.getVariableValue(identifier);
        } else {
            return null;
        }
    }

    public FunctionType getFunctionValue (String identifier) {
        if (this.functions.containsKey(identifier)) {
            return this.functions.get(identifier);
        } else if (prev != null) {
            return prev.getFunctionValue(identifier);
        } else {
            return null;
        }
    }
}
