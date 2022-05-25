package checker;

import parser.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionType {
    String name;
    String returnType;
    HashMap<String, Node> parameters;
    ArrayList<String> parametersId;
    Node body;

    public FunctionType (Node body, String returnType, String name) {
        this.name = name;
        this.body = body;
        this.returnType = returnType;
        this.parameters = new HashMap<>();
        this.parametersId = new ArrayList<>();
    }

    public void addParameter (String identifier, Node type) {
        this.parameters.put(identifier, type);
        this.parametersId.add(identifier);
    }
}
