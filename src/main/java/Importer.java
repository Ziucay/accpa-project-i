import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Importer {

    public List<Token> tokens;

    public List<String> extractSourcesFromTokens()
    {
        List<String> paths = new ArrayList<>();
        for (Iterator<Token> it = tokens.iterator(); it.hasNext();)
        {
            Token token = it.next();
            if (token.type == TokenType.IMPORT)
            {
                if (it.hasNext())
                {
                    String path = it.next().lexeme;
                    paths.add(path);
                }
            }
        }
        System.out.println(paths);
        /*
         for (Iterator<Node> it = descendants.iterator(); it.hasNext(); ) {
            Node next = it.next();
            if (next == null) {
                continue;
            }
            if (it.hasNext()) {
                ((Node) next).print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                ((Node) next).print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
         */


        return null;
    }



}
