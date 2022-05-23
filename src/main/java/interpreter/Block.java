package interpreter;

import parser.Node;
import java.util.HashMap;
import java.util.Map;

public class Block {
    Block prev;
    HashMap<String, VariableValue> variableIds = new HashMap<>();
    HashMap<String, FunctionValue> functionIds = new HashMap<>();

    public Block(Block prev) {
        this.prev = prev;
    }

    public Block() {
        this.prev = null;
    }

    public void cloneBlock (Block copy) {
        this.prev = copy.prev;
        for (Map.Entry<String, VariableValue> entry : copy.variableIds.entrySet()) {
            this.variableIds.put(entry.getKey(), new VariableValue(entry.getValue()));
        }
        for (Map.Entry<String, FunctionValue> entry : copy.functionIds.entrySet()) {
            this.functionIds .put(entry.getKey(), new FunctionValue(entry.getValue()));
        }
    }

    public boolean isVariable(String id) {
        if (this.variableIds.containsKey(id))
            return true;
        else if (this.prev != null)
            return this.prev.isVariable(id);
        else
            return false;
    }

    public boolean assignVariable(String identifier, Object value) {
        if (this.variableIds.containsKey(identifier)) {
            this.variableIds.put(identifier, new VariableValue(value, this.variableIds.get(identifier).type));
            return true;
        } else if (this.prev != null)
            return this.prev.assignVariable(identifier, value);
        else
            return false;
    }

    public Object getVariableValue(String identifier) {
        if (this.variableIds.containsKey(identifier))
            return this.variableIds.get(identifier).value;
        else if (this.prev != null)
            return this.prev.getVariableValue(identifier);
        else
            return null;
    }

    public String getVariableType(String identifier) {
        if (this.variableIds.containsKey(identifier))
            return this.variableIds.get(identifier).type;
        else if (this.prev != null)
            return this.prev.getVariableType(identifier);
        else
            return null;
    }


    public boolean createFunction(String identifier, Node body, String type, Block block) {
        try {
            this.functionIds.put(identifier, new FunctionValue(body, type, block));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public FunctionValue getFunctionValue(String identifier) {
        if (this.functionIds.containsKey(identifier))
            return this.functionIds.get(identifier);
        else if (this.prev != null)
            return this.prev.getFunctionValue(identifier);
        else
            return null;
    }

    public boolean createVariable(String identifier, String type) {
        try {
            this.variableIds.put(identifier, new VariableValue(null, type));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean createVariable(String identifier, Object value, String type) {
        try {
            this.variableIds.put(identifier, new VariableValue(value, type));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

