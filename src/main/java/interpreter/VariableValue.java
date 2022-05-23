package interpreter;

public class VariableValue {
    public Object value;
    public String type;

    public VariableValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public VariableValue(VariableValue copy) {
        this.value = copy.value;
        this.type = copy.type;
    }
}