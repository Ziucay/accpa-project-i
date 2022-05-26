package checker;

import parser.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TypeChecker {
    HashMap<String, String> customTypes;
    ArrayList<String> types = new ArrayList<>();
    ArrayList<String> numericTypes = new ArrayList<>();


    public TypeChecker() {
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

    public void check(Node root) throws Exception {
        processCustomTypes(root);
        startTraverse(root);
        traverseNumeric(root);
    }

    private void processCustomTypes(Node root) {
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

    private void changeCustomTypes(Node root) {
        for (Node node : root.descendants) {
            if (node == null) {
                return;
            }
            if (this.customTypes.containsKey(node.identifier)) {
                node.identifier = this.customTypes.get(node.identifier);
            }
            changeCustomTypes(node);
        }
    }

    public void startTraverse(Node root) throws Exception {
        TypeBlock global = new TypeBlock(root);
        for (Node node : root.descendants) {
            TypeBlock func = global.addFunction(node, node.descendants.get(0).identifier);
            func.type = "type-func";
            for (Node param : node.descendants.get(1).descendants) {
                if (param == null) break;
                func.addVariable(param, param.descendants.get(0).identifier, param.descendants.get(1).identifier);
            }
        }
        if (!Objects.equals(global.functions.get("main").returnType(), "type-auto")
                && !Objects.equals(global.functions.get("main").returnType(), "type-void")) {
            throw new Exception("Incorrect main function type");
        }
        if (Objects.equals(global.functions.get("main").returnType(), "type-auto")) {
            global.functions.get("main").changeReturnType("type-void");
        }
        traverse(global.functions.get("main").body.descendants.get(3), global.functions.get("main"), null, null);
    }

    public TraverseType traverse(Node node, TypeBlock block, String type, String CallReturn) throws Exception {
        TraverseType left, right;
        TraverseType result = null;
        TraverseType returned;
        for (Node child : node.descendants) {
            if (Objects.equals(child.identifier, "function-declaration")) {
                TypeBlock fun = block.addFunction(child, child.descendants.get(0).identifier);
                fun.type = "type-func";
                for (Node param : child.descendants.get(1).descendants) {
                    if (param == null) break;
                    fun.addVariable(param, param.descendants.get(0).identifier, param.descendants.get(1).identifier);
                }
            }
        }
        switch (node.identifier) {
            case "function-declaration":
                return null;
            case "minus":
            case "multiply":
            case "divide":
                left = traverse(node.descendants.get(0), block, "type-numeric", CallReturn);
                right = traverse(node.descendants.get(1), block, "type-numeric", CallReturn);
                if (!numericTypes.contains(type) && type != null) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    if (Objects.equals(left.type, "type-auto")) {
                        return new TraverseType("type-numeric");
                    }
                    return new TraverseType(left.type);
                }
                return new TraverseType("type-numeric");
            case "plus":
                left = traverse(node.descendants.get(0), block, "type-numeric", CallReturn);
                right = traverse(node.descendants.get(1), block, "type-numeric", CallReturn);
                if (Objects.equals(left.type, right.type)
                        && (Objects.equals(type, right.type) || Objects.equals(type, "type-auto")) && Objects.equals(left.type, "type-string")) {
                    return new TraverseType("type-string");
                }
                if (!numericTypes.contains(type) && type != null) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    if (Objects.equals(left.type, "type-auto")) {
                        return new TraverseType("type-numeric");
                    }
                    return new TraverseType(left.type);
                }
                return new TraverseType("type-numeric");
            case "more":
            case "more or equal":
            case "less":
            case "less or equal":
                left = traverse(node.descendants.get(0), block, "type-numeric", CallReturn);
                right = traverse(node.descendants.get(1), block, "type-numeric", CallReturn);
                if (!Objects.equals(type, "type-boolean") && !Objects.equals(type, "type-auto") && type != null) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    return new TraverseType("type-boolean");
                }
                return new TraverseType("type-boolean");
            case "not equal":
            case "equal":
                left = traverse(node.descendants.get(0), block, "type-numeric", CallReturn);
                right = traverse(node.descendants.get(1), block, "type-numeric", CallReturn);
                if (Objects.equals(left.type, right.type)
                        && (Objects.equals(type, "type-boolean") || Objects.equals(type, "type-auto")) && Objects.equals(left.type, "type-string")) {
                    return new TraverseType("type-boolean");
                }
                if (!Objects.equals(type, "type-boolean") && !Objects.equals(type, "type-auto") && type != null) {
                    throw new Exception("Incompatible types");
                }
                if (Objects.equals(left.type, right.type)) {
                    return new TraverseType("type-boolean");
                }
                return new TraverseType("type-boolean");
            case "or":
            case "and":
            case "xor":
                left = traverse(node.descendants.get(0), block, "type-boolean", CallReturn);
                right = traverse(node.descendants.get(1), block, "type-boolean", CallReturn);
                if (!(Objects.equals(left.type, "type-boolean") && Objects.equals(right.type, "type-boolean"))) {
                    throw new Exception("Incompatible types");
                }
                return new TraverseType("type-boolean");
            case "body":
                TraverseType lastReturn = null;
                for (Node child :
                        node.descendants) {
                    if (Objects.equals(child.identifier, "function-call")
                            && (Objects.equals(child.descendants.get(0).identifier, CallReturn))
                            || (child.descendants.get(0).descendants.size() != 0
                            && Objects.equals(child.descendants.get(0).descendants.get(0).identifier, CallReturn))) {
                        return lastReturn;
                    }
                    result = traverse(child, block, null, CallReturn);
                    if (result != null && result.isReturn) {
                        if (Objects.equals(type, "type-auto")) {
                            lastReturn = result;
                        } else if (Objects.equals(type, "type-numeric")) {
                            if (numericTypes.contains(result.type)) {
                                throw new Exception("Incompatible return type");
                            }
                            lastReturn = result;
                        } else if (Objects.equals(type, result.type)) {
                            lastReturn = result;
                        } else {
                            throw new Exception("Incompatible return type");
                        }
                    }
                }
                if (type != null && !Objects.equals(type, "type-void") && lastReturn == null && !Objects.equals(type, "type-auto")) {
                    throw new Exception("Incompatible return type");
                }
                return lastReturn;
            case "while":
            case "for":
            case "if":
                TypeBlock bodyBlock = block.addDescendant(node);
                result = traverse(node.descendants.get(node.descendants.size() - 1), bodyBlock, null, CallReturn);
                if (result != null && result.isReturn) {
                    return result;
                }
                return null;
            case "return":
                result = traverse(node.descendants.get(0), block, block.returnType(), CallReturn);
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
                ArrayList<String> arguments = new ArrayList<>();
                int k = 0;
                for (Node child : funcBlock.body.descendants.get(1).descendants) {
                    if (child != null) {
                        arguments.add(traverse(node.descendants.get(1).descendants.get(k), block, child.descendants.get(1).identifier, CallReturn).type);
                        k++;
                    }
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
                result = traverse(funcBlock.body.descendants.get(3), funcBlock, funcBlock.returnType(), funcBlock.body.descendants.get(0).identifier);
                if (result == null) {
                    if (Objects.equals(funcBlock.returnType(), "type-auto")) {
                        funcBlock.changeReturnType("type-void");
                    }
                    return null;
                }

                if (Objects.equals(funcBlock.body.descendants.get(2).identifier, "type-auto")) {
                    funcBlock.body.descendants.get(2).identifier = result.type;
                    return result;
                }
                if (!Objects.equals(funcBlock.body.descendants.get(2).identifier, result.type)) {
                    throw new Exception("Incompatible types");
                }
                return result;
            case "variable-declaration":
                TypeBlock varBlock = block.addVariable(node, node.descendants.get(0).identifier,
                        node.descendants.get(1).identifier);
                if (node.descendants.size() == 3)
                    result = traverse(node.descendants.get(2), block, varBlock.type, CallReturn);
                if (Objects.equals(varBlock.type, "type-auto")) {
                    if (result != null && Objects.equals(result.type, "type-func")) {
                        block.addVariable(result.func.body, node.descendants.get(0).identifier, "type-func");
                    }
                    assert result != null;
                    block.addVariable(block.getVariable(node.descendants.get(0).identifier).body, node.descendants.get(0).identifier, result.type);
                }
                return null;
            case "function-expression":
                block.addVariable(node, node.descendants.get(0).identifier,
                        node.descendants.get(1).identifier);
                return null;
            case "assignment":
                TypeBlock var = block.getVariable(node.descendants.get(0).identifier);
                traverse(node.descendants.get(1), block, var.type, CallReturn);
                return null;
            case "modifiable":
            case "argument":
            case "print":
                return traverse(node.descendants.get(0), block, type, CallReturn);
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
                    if (type == null) {
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

    public void traverseNumeric(Node root) {
        if (Objects.equals(root.identifier, "type-numeric")) {
            root.identifier = "type-double";
        }
        for (Node node : root.descendants) {
            if (node == null) {
                return;
            }
            traverseNumeric(node);
        }
    }
}
