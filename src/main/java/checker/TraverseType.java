package checker;

import parser.Node;

import java.util.ArrayList;
import java.util.List;

public class TraverseType {
    String type;
    TypeBlock func;
    boolean isReturn = false;

    public TraverseType (String type) {
        this.type = type;
    }

    public TraverseType (String type, TypeBlock func) {
        this.type = type;
        this.func = func;
    }
}
