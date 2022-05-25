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
        numericTypes.add("type-numeric"); // Type only for checker
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
        changeCustomTypes(root);
    }

    private void changeCustomTypes (Node root) {
        for (Node node : root.descendants) {
            if (this.customTypes.containsKey(node.identifier)) {
                node.identifier = this.customTypes.get(node.identifier);
            }
            changeCustomTypes(node);
        }
    }

    public void startTraverse (Node root) {
        TypeBlock global = new TypeBlock(root);
        for (Node node : root.descendants) {
            global.addFunction(node, node.descendants.get(0).identifier);
        }
        traverse(global.functions.get("main").body.descendants.get(3), global.functions.get("main"), );
    }

    public TraverseType traverse (Node node, TypeBlock block, String type) throws Exception {
        TraverseType left, right;
        TraverseType result = null;
        TraverseType returned;
        for (Node child : node.descendants) {
            if (Objects.equals(child.identifier, "function-declaration")) {
                block.addFunction(child, child.descendants.get(0).identifier);
            }
        }
        switch (node.identifier) {
            case "function-declaration":
                return null;
            case "minus":
            case "multiply":
            case "divide":
                left = traverse(node.descendants.get(0), block, "type-numeric");
                right = traverse(node.descendants.get(1), block, "type-numeric");
                if (!numericTypes.contains(type)) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    return new TraverseType(type);
                }
                return new TraverseType("type-numeric");
            case "plus":
                left = traverse(node.descendants.get(0), block, "type-numeric");
                right = traverse(node.descendants.get(1), block, "type-numeric");
                if (Objects.equals(left.type, right.type)
                        && (Objects.equals(type, right.type) || Objects.equals(type, "type-auto")) && Objects.equals(left.type, "type-string")) {
                    return new TraverseType("type-string");
                }
                if (!numericTypes.contains(type)) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    return new TraverseType(type);
                }
                return new TraverseType("type-numeric");
            case "more":
            case "more or equal":
            case "less":
            case "less or equal":
                left = traverse(node.descendants.get(0), block, "type-numeric");
                right = traverse(node.descendants.get(1), block, "type-numeric");
                if (!Objects.equals(type, "type-boolean") && !Objects.equals(type, "type-auto")) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    return new TraverseType("type-boolean");
                }
                return new TraverseType("type-boolean");
            case "not equal":
            case "equal":
                left = traverse(node.descendants.get(0), block, "type-numeric");
                right = traverse(node.descendants.get(1), block, "type-numeric");
                if (Objects.equals(left.type, right.type)
                        && (Objects.equals(type, "type-boolean") || Objects.equals(type, "type-auto")) && Objects.equals(left.type, "type-string")) {
                    return new TraverseType("type-boolean");
                }
                if (!Objects.equals(type, "type-boolean") && !Objects.equals(type, "type-auto")) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    return new TraverseType("type-boolean");
                }
                return new TraverseType("type-boolean");
            case "or":
            case "and":
            case "xor":
                left = traverse(node.descendants.get(0), block, "type-boolean");
                right = traverse(node.descendants.get(1), block, "type-boolean");
                if (!(Objects.equals(left.type, "type-boolean") && Objects.equals(right.type, "type-boolean"))) {
                    throw new Exception("Incompatible types");
                }
                return new TraverseType("type-boolean");
            case "body":
                for (Node child :
                        node.descendants) {
                    result = traverse(child, block, null);
                    if (result.isReturn) {
                        return result;
                    }
                }
                return null;
            case "while":
            case "for":
            case "if":
                TypeBlock bodyBlock = block.addDescendant(node);
                for (Node child : node.descendants.get(node.descendants.size() - 1).descendants) {
                    result = traverse(child, bodyBlock, null);
                    if (result.isReturn) {
                        return result;
                    }
                }
                return null;
            case "return":
                result = traverse(node.descendants.get(0), block, block.returnType());
                if (Objects.equals(result.type, "type-func")) {
                    returned = new TraverseType(result.type, result.func);
                } else {
                    returned = new TraverseType(result.type);
                }
                returned.isReturn = true;
                return returned;
            case "function-call":
                if (!block.isFunction(node.descendants.get(0).identifier)) {
                    throw new Exception("Is not a function");
                }
                TypeBlock funcBlock = block.getFunction(node.descendants.get(0).identifier);
                result = traverse(funcBlock.body.descendants.get(3), funcBlock, null);
                ArrayList<String> arguments = new ArrayList<>();
                for (Node child : funcBlock.body.descendants.get(1).descendants) {
                    arguments.add(traverse(child.descendants.get(0), block, child.descendants.get(1).identifier).type);
                }
                for (int i = 0; i < arguments.size(); i++) {
                    if (Objects.equals(funcBlock.body.descendants.get(1).descendants.get(i).descendants.get(1).identifier,
                            "type-auto")) {
                        funcBlock.body.descendants.get(1).descendants.get(i).descendants.get(1).identifier = arguments.get(i);
                    } else if (Objects.equals(funcBlock.body.descendants.get(1).descendants.get(i).descendants.get(1).identifier,
                            "type-numeric")) {
                        if (!numericTypes.contains(arguments.get(i))) {
                            throw new Exception("Incompatible types");
                        }
                        funcBlock.body.descendants.get(1).descendants.get(i).descendants.get(1).identifier = arguments.get(i);
                    } else if (!Objects.equals(funcBlock.body.descendants.get(1).descendants.get(i).descendants.get(1).identifier,
                            arguments.get(i))) {
                        throw new Exception("Incompatible types");
                    }
                }
                if (Objects.equals(funcBlock.body.descendants.get(2).identifier, "type-auto")) {
                    funcBlock.body.descendants.get(2).identifier = result.type;
                    return result;
                }
                if (!Objects.equals(funcBlock.body.descendants.get(2).identifier, result.type)) {
                    throw new Exception("Incompatible types");
                }
                return result;
            //case "array-declaration":
            //case "array-access":

            case "variable-declaration":
                TypeBlock varBlock = block.addVariable(node, node.descendants.get(0).identifier,
                        node.descendants.get(1).identifier);
                if (node.descendants.size() == 3)
                    result = traverse(node.descendants.get(2), block, varBlock.type);
                if (Objects.equals(varBlock.type, "type-auto")) {
                    if (result != null && Objects.equals(result.type, "type-func")) {
                        block.addVariable(result.func.body, node.descendants.get(0).identifier, "type-func");
                    }
                    assert result != null;
                    block.addVariable(result.func.body, node.descendants.get(0).identifier, result.type);
                }
                return null;
            case "function-expression":
                block.addVariable(node, node.descendants.get(0).identifier,
                        node.descendants.get(1).identifier);
                return null;
            case "assignment":
                TypeBlock var = block.getVariable(node.descendants.get(0).identifier);
                traverse(node.descendants.get(1), block, var.type);
                return null;
            case "modifiable":
                return traverse(node.descendants.get(0), block, type);
            default:
                TypeBlock vared = block.getVariable(node.identifier);
                if (vared != null) {
                    if (Objects.equals(type, "type-auto")) {
                        if (Objects.equals(vared.type, "type-func")) {
                            return new TraverseType(vared.type, vared);
                        }
                        return new TraverseType(vared.type);
                    }
                    if (Objects.equals(type, "type-numeric")) {
                        if (numericTypes.contains(vared.type)) {
                            return new TraverseType(vared.type);
                        } else {
                            throw new Exception("Incompatible types");
                        }
                    }
                    if (Objects.equals(type, vared.type)) {
                        if (Objects.equals(vared.type, "type-func")) {
                            return new TraverseType(vared.type, vared);
                        }
                        return new TraverseType(vared.type);
                    }
                    throw new Exception("Incompatible types");
                }
                TypeBlock funced = block.getFunction(node.identifier);
                if (funced != null) {
                    if (Objects.equals(type, "type-func")) {
                        return new TraverseType(funced.type, funced);
                    }
                    if (Objects.equals(type, "type-auto")) {
                        return new TraverseType(funced.type, funced);
                    }
                    throw new Exception("Incompatible types");
                }
                if (node.value instanceof Integer) {
                    if (Objects.equals(type, "type-auto")
                            || Objects.equals(type, "type-numeric") || Objects.equals(type, "type-integer")) {
                        return new TraverseType("type-integer");
                    }
                    throw new Exception("Incompatible types");
                } else if (node.value instanceof Double) {
                    if (Objects.equals(type, "type-auto")
                            || Objects.equals(type, "type-numeric") || Objects.equals(type, "type-double")) {
                        return new TraverseType("type-double");
                    }
                    throw new Exception("Incompatible types");
                } else if (node.value instanceof String) {
                    if (Objects.equals(type, "type-auto")
                            || Objects.equals(type, "type-string")) {
                        return new TraverseType("type-string");
                    }
                    throw new Exception("Incompatible types");
                } else if (node.value instanceof Boolean) {
                    if (Objects.equals(type, "type-auto")
                            || Objects.equals(type, "type-boolean")) {
                        return new TraverseType("type-boolean");
                    }
                    throw new Exception("Incompatible types");
                }
                throw new Exception("Unknown error");
        }
    }
}
