package interpreter;

import parser.Node;

import java.util.ArrayList;
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
        switch (node.identifier) {
            case "plus":
                return (int) traverse(node.descendants.get(0), block)
                        + (int) traverse(node.descendants.get(1), block);
            case "minus":
                return (int) traverse(node.descendants.get(0), block)
                        - (int) traverse(node.descendants.get(1), block);
            case "multiply":
                return (int) traverse(node.descendants.get(0), block)
                        * (int) traverse(node.descendants.get(1), block);
            case "divide":
                return (int) traverse(node.descendants.get(0), block)
                        / (int) traverse(node.descendants.get(1), block);
            case "more":
                return (double) traverse(node.descendants.get(0), block)
                        > (double) traverse(node.descendants.get(1), block);
            case "more or equal":
                return (double) traverse(node.descendants.get(0), block)
                        >= (double) traverse(node.descendants.get(1), block);
            case "equal":
                return (double) traverse(node.descendants.get(0), block)
                        == (double) traverse(node.descendants.get(1), block);
            case "less":
                return (double) traverse(node.descendants.get(0), block)
                        < (double) traverse(node.descendants.get(1), block);
            case "less or equal":
                return (double) traverse(node.descendants.get(0), block)
                        <= (double) traverse(node.descendants.get(1), block);
            case "not equal":
                return (double) traverse(node.descendants.get(0), block)
                        != (double) traverse(node.descendants.get(1), block);
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
            case "root":
                for (Node child :
                        node.descendants) {
                    traverse(child, block);
                }
                return null;
            case "function-call":
                FunctionValue func = block.getFunctionValue(node.descendants.get(0).identifier);
                Block funcBlock = func.block;
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
                    block.createVariable(node.descendants.get(0).identifier, traverse(node.descendants.get(2), block), (String) node.descendants.get(1).value);
                }
                return null;
            case "assignment":
                block.assignVariable(node.descendants.get(0).identifier, traverse(node.descendants.get(1), block));
                return null;
            case "print":
                System.out.println(traverse(node.descendants.get(0), block));
                return null;
            default:
                if (block.isVariable(node.identifier))
                    return block.getVariableValue(node.identifier);
                else {
                    return node.value;
                }
        }
    }
}

