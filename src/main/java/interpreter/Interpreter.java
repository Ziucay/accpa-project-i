package interpreter;

import parser.Node;

import java.util.Objects;

public class Interpreter {
    public Interpreter() {
    }

    public void traverseTree(Node root, String startFunction) {
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
            if (Objects.equals(child.descendants.get(0).identifier, startFunction)) {
                FunctionValue func = global.getFunctionValue(startFunction);
                traverse(func.body, func.block);
                break;
            }
        }
    }

    public Object traverse(Node node, Block block) {
        Object left, right, result;
        switch (node.identifier) {
            case "plus":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left + (int) right;
                } else {
                    return (double) left + (double) right;
                }
            case "minus":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left - (int) right;
                } else {
                    return (double) left - (double) right;
                }
            case "multiply":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left * (int) right;
                } else {
                    return (double) left * (double) right;
                }
            case "divide":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left / (int) right;
                } else {
                    return (double) left / (double) right;
                }
            case "more":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left > (int) right;
                } else {
                    return (double) left > (double) right;
                }
            case "more or equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left >= (int) right;
                } else {
                    return (double) left >= (double) right;
                }
            case "equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left == (int) right;
                } else {
                    return (double) left == (double) right;
                }
            case "less":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left < (int) right;
                } else {
                    return (double) left < (double) right;
                }
            case "less or equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left <= (int) right;
                } else {
                    return (double) left <= (double) right;
                }
            case "not equal":
                left = traverse(node.descendants.get(0), block);
                right = traverse(node.descendants.get(1), block);
                if (left instanceof Integer) {
                    return (int) left != (int) right;
                } else {
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
                String variable = node.descendants.get(0).descendants.get(0).identifier;
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
                    }
                    else {
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
                int parameterCounter = 1;
                for (String parameter :
                        func.parameters) {
                    funcBlock.assignVariable(parameter, traverse(node.descendants.get(parameterCounter), block));
                    parameterCounter++;
                }
                return traverse(func.body, funcBlock);
            case "variable-declaration":
                if (node.descendants.size() == 2) {
                    block.createVariable(node.descendants.get(0).identifier, (String) node.descendants.get(1).value);
                } else if (node.descendants.size() == 3) {
                    result = traverse(node.descendants.get(2), block);
                    block.createVariable(node.descendants.get(0).identifier, result, (String) node.descendants.get(1).value);
                }
                return null;
            case "assignment":
                block.assignVariable(node.descendants.get(0).identifier, traverse(node.descendants.get(1), block));
                return null;
            case "print":
                System.out.println(traverse(node.descendants.get(0), block));
                return null;
            case "modifiable":
                return traverse(node.descendants.get(0), block);
            default:
                if (block.isVariable(node.identifier)) {
                    return block.getVariableValue(node.identifier);
                }
                else {
                    return node.value;
                }
        }
    }
}

