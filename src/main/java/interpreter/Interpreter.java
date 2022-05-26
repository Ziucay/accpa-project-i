package interpreter;

import parser.Node;

import java.util.Objects;

public class Interpreter {
    public Interpreter() {
    }

    public void traverseTree(Node root) {
        Block global = new Block();
        for (Node child :
                root.descendants) {
            Block block = new Block(global);
            global.createFunction(child.descendants.get(0).identifier, child.descendants.get(3), child.descendants.get(2).identifier, block);
            FunctionValue func = global.getFunctionValue(child.descendants.get(0).identifier);
            if (child.descendants.get(1).descendants != null)
                for (Node parameter :
                        child.descendants.get(1).descendants) {
                    if (parameter == null)
                        break;
                    block.createVariable(parameter.descendants.get(0).identifier, parameter.descendants.get(1).identifier);
                    func.addParameterIdentifier(parameter.descendants.get(0).identifier);
                }
        }
        for (Node child :
                root.descendants) {
            if (Objects.equals(child.descendants.get(0).identifier, "main")) {
                FunctionValue func = global.getFunctionValue("main");
                traverse(func.body, func.block);
                break;
            }
        }
    }

    public Object traverse(Node node, Block block) {
        Object left, right, result;
        for (Node child :
                node.descendants) {
            if (Objects.equals(child.identifier, "function-declaration")) {
                Block blocked = new Block(block);
                block.createFunction(child.descendants.get(0).identifier, child.descendants.get(3), child.descendants.get(2).identifier, blocked);
                FunctionValue func = block.getFunctionValue(child.descendants.get(0).identifier);
                if (child.descendants.get(1).descendants != null)
                    for (Node parameter :
                            child.descendants.get(1).descendants) {
                        if (parameter == null)
                            break;
                        blocked.createVariable(parameter.descendants.get(0).identifier, parameter.descendants.get(1).identifier);
                        func.addParameterIdentifier(parameter.descendants.get(0).identifier);
                    }
            }
        }
        switch (node.identifier) {
            case "function-declaration":
                return null;
            case "function-expression":
                Block blocked = new Block(block);
                block.createFunction(node.descendants.get(0).identifier, node.descendants.get(3), node.descendants.get(2).identifier, blocked);
                FunctionValue funced = block.getFunctionValue(node.descendants.get(0).identifier);
                if (node.descendants.get(1).descendants != null)
                    for (Node parameter :
                            node.descendants.get(1).descendants) {
                        if (parameter == null)
                            break;
                        blocked.createVariable(parameter.descendants.get(0).identifier, parameter.descendants.get(1).identifier);
                        funced.addParameterIdentifier(parameter.descendants.get(0).identifier);
                    }
                return null;
            case "plus":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                }
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left + (int) right;
                    }
                    return (int) left + (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left + (int) right;
                    }
                    return (double) left + (double) right;
                }
            case "minus":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left - (int) right;
                    }
                    return (int) left - (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left - (int) right;
                    }
                    return (double) left - (double) right;
                }
            case "multiply":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left * (int) right;
                    }
                    return (int) left * (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left * (int) right;
                    }
                    return (double) left * (double) right;
                }
            case "divide":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left / (int) right;
                    }
                    return (int) left / (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left / (int) right;
                    }
                    return (double) left / (double) right;
                }
            case "more":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left > (int) right;
                    }
                    return (int) left > (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left > (int) right;
                    }
                    return (double) left > (double) right;
                }
            case "more or equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left >= (int) right;
                    }
                    return (int) left >= (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left >= (int) right;
                    }
                    return (double) left >= (double) right;
                }
            case "equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof String && right instanceof String) {
                    return ((String) left).equals((String) right);
                }
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left == (int) right;
                    }
                    return (int) left == (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left == (int) right;
                    }
                    return (double) left == (double) right;
                }
            case "less":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left < (int) right;
                    }
                    return (int) left < (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left < (int) right;
                    }
                    return (double) left < (double) right;
                }
            case "less or equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left <= (int) right;
                    }
                    return (int) left <= (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left <= (int) right;
                    }
                    return (double) left <= (double) right;
                }
            case "not equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof String && right instanceof String) {
                    return !((String) left).equals((String) right);
                }
                if (left instanceof Integer) {
                    if (right instanceof Integer) {
                        return (int) left != (int) right;
                    }
                    return (int) left != (double) right;
                } else {
                    if (right instanceof Integer) {
                        return (double) left != (int) right;
                    }
                    return (double) left != (double) right;
                }
            case "or":
                return (boolean) traverse(node.descendants.get(0), block)
                        || (boolean) traverse(node.descendants.get(1), block);
            case "and":
                return (boolean) traverse(node.descendants.get(0), block)
                        && (boolean) traverse(node.descendants.get(1), block);
            case "xor":
                return (boolean) traverse(node.descendants.get(0), block)
                        ^ (boolean) traverse(node.descendants.get(1), block);
            case "body":
                for (Node child :
                        node.descendants) {
                    result = traverse(child, block);
                    if (result instanceof ReturnObject) {
                        return result;
                    }
                }
                return null;
            case "while":
                Block whileBlock = new Block(block);
                while ((boolean) traverse(node.descendants.get(0), whileBlock)) {
                    result = traverse(node.descendants.get(1), whileBlock);
                    if (result instanceof ReturnObject) {
                        return result;
                    }
                }
                return null;
            case "for":
                Block forBlock = new Block(block);
                String variable = node.descendants.get(0).identifier;
                int leftRange = (int) traverse(node.descendants.get(1).descendants.get(0), forBlock);
                int rightRange = (int) traverse(node.descendants.get(1).descendants.get(1), forBlock);
                block.assignVariable(variable, leftRange);
                int forVariable = (int) block.getVariableValue(variable);
                if (leftRange > rightRange) {
                    while (forVariable > rightRange) {
                        result = traverse(node.descendants.get(2), forBlock);
                        if (result instanceof ReturnObject) {
                            return result;
                        }
                        forVariable--;
                        block.assignVariable(variable, forVariable);
                    }
                } else {
                    while (forVariable < rightRange) {
                        result = traverse(node.descendants.get(2), forBlock);
                        if (result instanceof ReturnObject) {
                            return result;
                        }
                        forVariable++;
                        block.assignVariable(variable, forVariable);
                    }
                }
                return null;
            case "if":
                Block ifBlock = new Block(block);
                boolean conditionCheck = (boolean) traverse(node.descendants.get(0), ifBlock);
                if (node.descendants.size() == 2) {
                    if (conditionCheck) {
                        result = traverse(node.descendants.get(1), ifBlock);
                        if (result instanceof ReturnObject) {
                            return result;
                        }
                    }
                } else {
                    if (conditionCheck) {
                        result = traverse(node.descendants.get(1), ifBlock);
                    } else {
                        result = traverse(node.descendants.get(2), ifBlock);
                    }
                    if (result instanceof ReturnObject) {
                        return result;
                    }
                }
                return null;
            case "return":
                result = traverse(node.descendants.get(0), block);
                return new ReturnObject(result);
            case "function-call":
                FunctionValue func = new FunctionValue(block.getFunctionValue(node.descendants.get(0).identifier));
                Block funcBlock = new Block();
                funcBlock.cloneBlock(func.block);
                int parameterCounter = 0;
                for (String parameter :
                        func.parameters) {
                    funcBlock.assignVariable(parameter, traverse(node.descendants.get(1).descendants.get(parameterCounter), block));
                    parameterCounter++;
                }
                Object resulted = traverse(func.body, funcBlock);
                if (resulted == null) {
                    return null;
                } else {
                    return ((ReturnObject) resulted).returnValue;
                }
            case "variable-declaration":
                if (node.descendants.size() == 2) {
                    block.createVariable(node.descendants.get(0).identifier, (String) node.descendants.get(1).value);
                } else if (node.descendants.size() == 3) {
                    result = traverse(node.descendants.get(2), block);
                    switch (node.descendants.get(1).identifier) {
                        case "type-integer":
                            result = (int) result;
                            break;
                        case "type-double":
                            result = (double) result;
                            break;
                        case "type-string":
                            result = (String) result;
                            break;
                        case "type-boolean":
                            result = (boolean) result;
                            break;
                    }
                    block.createVariable(node.descendants.get(0).identifier, result, node.descendants.get(1).identifier);
                }
                return null;
            case "assignment":
                result = traverse(node.descendants.get(1), block);
                switch (block.getVariableType(node.descendants.get(0).identifier)) {
                    case "type-integer":
                        result = (int) result;
                        break;
                    case "type-double":
                        result = (double) result;
                        break;
                    case "type-string":
                        result = (String) result;
                        break;
                    case "type-boolean":
                        result = (boolean) result;
                        break;
                }
                block.assignVariable(node.descendants.get(0).identifier, result);
                return null;
            case "print":
                System.out.println(traverse(node.descendants.get(0), block));
                return null;
            case "argument":
            case "modifiable":
                return traverse(node.descendants.get(0), block);
            default:
                if (block.isVariable(node.identifier)) {
                    return block.getVariableValue(node.identifier);
                } else if (block.getFunctionValue(node.identifier) != null) {
                    return block.getFunctionValue(node.identifier);
                } else {
                    return node.value;
                }
        }
    }
}

