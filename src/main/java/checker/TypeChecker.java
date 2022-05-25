package checker;

import interpreter.Block;
import interpreter.FunctionValue;
import interpreter.ReturnObject;
import parser.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TypeChecker {
    HashMap<String, String> customTypes;
    ArrayList<String> types = new ArrayList<>();
    ArrayList<String> numericTypes = new ArrayList<>();


    public TypeChecker () {
        types.add("type-integer");
        types.add("type-double");
        types.add("type-void");
        types.add("type-auto");
        types.add("type-boolean");
        types.add("type-func");
        types.add("type-numeric"); // Type only for checker
        numericTypes.add("type-integer");
        numericTypes.add("type-double");
        numericTypes.add("type-auto");
    }

    private void processCustomTypes (Node root) {
        List<Node> descendants = new ArrayList<>();
        customTypes = new HashMap<>();
        for (int i = 0; i < root.descendants.size(); i++) {
            Node current = root.descendants.get(i);
            if (Objects.equals(current.identifier, "type-declaration")) {
                customTypes.put(current.descendants.get(0).identifier, current.descendants.get(1).identifier);
            } else if (Objects.equals(current.identifier, "function-declaration")) {
                descendants.add(current);
            }
        }
        root.descendants = descendants;
    }

    public TypeBlock createStructure (Node root) {
        TypeBlock globalBlock = new TypeBlock();
        addFunctions(root, globalBlock);
        traverseStructure(root, globalBlock);
        return globalBlock;
    }

    public void traverseStructure (Node node, TypeBlock block) {
        switch (node.identifier) {
            case "function-declaration":
            case "function-expression":
                TypeBlock funcBlock = block.getFunctionBlock(node.descendants.get(0).identifier);
                addFunctions(node.descendants.get(3), funcBlock);
                traverseStructure(node.descendants.get(3), funcBlock);
                break;
            case "while":
            case "for":
            case "if":
                TypeBlock bodyBlock = block.addDescendant();
                addFunctions(node.descendants.get(node.descendants.size() - 1), bodyBlock);
                break;
            case "body":
                for (Node child : node.descendants) {
                    traverseStructure(child, block);
                }
                break;
            case "variable-declaration":
                block.addVariable(node.descendants.get(0).identifier, node.descendants.get(1));
                break;
            default:
                break;
        }
    }

    public TraverseType mainTraverse (Node node, TypeBlock block, String type) throws Exception {
        TraverseType leftType, rightType;
        TraverseType typed;
        FunctionType func;
        if (!types.contains(type)) {
            throw new Exception("Unknown type");
        }
        switch (node.identifier) {
            case "function-declaration":
            case "function-expression":
                return null;
            case "plus":
            case "minus":
            case "multiply":
            case "divide":
                leftType = mainTraverse(node.descendants.get(0), block, type);
                rightType = mainTraverse(node.descendants.get(1), block, type);
                if (!numericTypes.contains(leftType.type) || numericTypes.contains(rightType.type)) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(leftType.type, rightType.type)) {
                    if (!Objects.equals(leftType.type, type)) {
                        throw new Exception("Incompatible types");
                    }
                    return new TraverseType (leftType.type);
                }
                if (!numericTypes.contains(type)) {
                    throw new Exception("Incompatible types");
                }
                return new TraverseType("type-numeric");
            case "more":
            case "more or equal":
            case "less":
            case "less or equal":
                leftType = mainTraverse(node.descendants.get(0), block, null);
                rightType = mainTraverse(node.descendants.get(1), block, null);
                if (!numericTypes.contains(leftType.type) || numericTypes.contains(rightType.type)) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(leftType.type, rightType.type)) {
                    if (!Objects.equals(leftType.type, type)) {
                        throw new Exception("Incompatible types");
                    }
                    return new TraverseType("type-boolean");
                }
                if (!numericTypes.contains(type)) {
                    throw new Exception("Incompatible types");
                }
                return new TraverseType("type-boolean");
            case "not equal":
            case "equal":
                leftType = mainTraverse(node.descendants.get(0), block, null);
                rightType = mainTraverse(node.descendants.get(1), block, null);
                if (Objects.equals(leftType.type, rightType.type)) {
                    if (Objects.equals(leftType.type, "type-string")) {
                        return new TraverseType("type-boolean");
                    } else if (numericTypes.contains(leftType.type)) {
                        return new TraverseType("type-boolean");
                    }
                    throw new Exception("Incompatible types");
                }
                if (!numericTypes.contains(leftType.type) || numericTypes.contains(rightType.type)) {
                    throw new Exception("Incompatible types");
                }
                return new TraverseType("type-boolean");
            case "or":
            case "and":
            case "xor":
                mainTraverse(node.descendants.get(0), block, "type-boolean");
                mainTraverse(node.descendants.get(1), block, "type-boolean");
                return new TraverseType("type-boolean");
            case "body":
                for (Node child :
                        node.descendants) {
                    mainTraverse(child, block, null);
                }
                return null;
            case "while":
            case "for":
            case "if":
                TypeBlock bodyBlock = block.getCurrentBlock();
                block.currentBlock++;
                mainTraverse(node.descendants.get(node.descendants.size() - 1), bodyBlock, null);
            case "return":
                func = block.getNearestFunction();
                typed = mainTraverse(node.descendants.get(1), block, func.returnType)
                func.body.descendants.get(2).identifier = typed.type;
                TraverseType returned;
                if (!Objects.equals(func.body.descendants.get(2).identifier, "type-func")) {
                    returned = new TraverseType(func.body.descendants.get(2).identifier);
                } else {
                    returned = new TraverseType(func.body.descendants.get(2).identifier, typed.func);
                }
                return returned;
            case "function-call":
                func = block.getNearestFunction();
                TypeBlock funcBlock = block.getCurrentFunctionBlock(node.descendants.get(0).identifier);
                if (func.parametersId.size() != node.descendants.get(1).descendants.size()) {
                    throw new Exception("Wrong number of arguments");
                }
                for (int i = 0; i < func.parametersId.size(); i++) {
                    node.descendants.get(1).descendants.get(i).descendants.get(1).identifier
                            = mainTraverse(node.descendants.get(1).descendants.get(i), block, func.parametersId.get(i)).type;
                }
                typed = mainTraverse(func.body.descendants.get(3), funcBlock, func.returnType);
                return typed;
            case "variable-declaration":
                if (node.descendants.size() == 3) {
                    typed = mainTraverse(node.descendants.get(2), block,
                            block.variables.get(node.descendants.get(0).identifier).identifier);
                    block.variables.get(node.descendants.get(0).identifier).identifier = typed.type;
                    if (Objects.equals(typed.type, "type-func")) {
                        addFunction(block, typed.func);
                    }
                }
                return null;
            case "assignment":
                leftType = mainTraverse(node.descendants.get(0), block, null);
                rightType = mainTraverse(node.descendants.get(0), block, leftType.type);
                if (Objects.equals(leftType.type, "type-auto")) {
                    if (node.descendants.get(0).descendants.size() == 0) {
                        node.descendants.get(0).identifier = rightType.type;
                    } else {
                        node.descendants.get(0).descendants.get(0).identifier = rightType.type;
                    }
                    if (Objects.equals(rightType.type, "type-func")) {

                    }
                }
                if (Objects.equals(leftType.type, "type-numeric")) {
                    if (!numericTypes.contains(rightType.type)) {
                        throw new Exception("Incompatible types");
                    }
                    if (node.descendants.get(0).descendants.size() == 0) {
                        node.descendants.get(0).identifier = rightType.type;
                    } else {
                        node.descendants.get(0).descendants.get(0).identifier = rightType.type;
                    }
                }
                return null;
            case "modifiable":
                return mainTraverse(node.descendants.get(0), block, type);
            default:
                if (block.getVariableValue(node.identifier) != null) {
                    String nodeType = block.getVariableValue(node.identifier).identifier;
                    if (Objects.equals(type, "type-auto")) {
                        return nodeType;
                    } else if (numericTypes.contains(nodeType) && Objects.equals(type, "type-numeric")) {
                        return nodeType;
                    } else if (Objects.equals(nodeType, type)) {
                        return nodeType;
                    } else {
                        throw new Exception("Incompatible types");
                    }
                }
                else if (block.getFunctionValue(node.identifier) != null) {
                    if (Objects.equals(type, "type-auto") || Objects.equals(type, "type-func")) {
                        return "type-func";
                    }
                    throw new Exception("Incompatible types");
                }
                if (node.value instanceof Integer) {
                    return "type-integer";
                } else if (node.value instanceof Double) {
                    return "type-double";
                } else if (node.value instanceof String) {
                    return "type-string";
                } else if (node.value instanceof Boolean) {
                    return "type-boolean";
                }
                throw new Exception("Undefined type problem");
        }
    }

    public void addFunction (TypeBlock block, FunctionType func) {
        block.addDescendant(func.name);
        block.addFunction(func.name, func);
    }

    public void addFunctions (Node node, TypeBlock block) {
        for (Node child : node.descendants) {
            if (Objects.equals(child.identifier, "function-declaration")
                    || Objects.equals(child.identifier, "function-expression")) {
                block.addDescendant(child.descendants.get(0).identifier);
                FunctionType func = new FunctionType(child, child.descendants.get(2).identifier, child.descendants.get(0).identifier);
                block.addFunction(child.descendants.get(0).identifier, func);
                for (Node parameter : child.descendants.get(1).descendants) {
                    func.addParameter(parameter.descendants.get(0).identifier, parameter.descendants.get(1));
                }
            }
        }
    }
}
