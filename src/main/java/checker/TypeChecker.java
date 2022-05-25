package checker;

import parser.Node;

import java.util.HashMap;

public class TypeChecker {
    HashMap<String, String> customTypes;

    public TypeChecker () {}

    public void createStructure (Node root) {
        TypeBlock globalBlock = new TypeBlock();
    }
}
