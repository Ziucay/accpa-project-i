package semanticAnalysis;

import parser.Node;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {

    private Node root;
    private String startFunction;

    public SemanticAnalyzer(Node root, String startFunction)
    {
        this.root = root;
        this.startFunction = startFunction;
    }

    public String run()
    {
        return null;
    }

    public void checkFunctionNaming()
    {
        List<String> functionNames = new ArrayList<>();
        for (Node node: root.descendants) {
            if (node.identifier == "function-declaration")
            {
                functionNames.add(node.descendants.get(0).identifier);
            }
        }

    }

}
