package interpreter;

import parser.Node;
import java.util.ArrayList;

public class FunctionValue {
    public Node body;
    ArrayList<String> parameters;
    String type;
    Block block;

    public FunctionValue (Node body, String type, Block block) {
        this.body = body;
        this.type = type;
        this.parameters = new ArrayList<>();
        this.block = block;
    }

    public void addParameterIdentifier (String identifier) {
        this.parameters.add(identifier);
    }
}

