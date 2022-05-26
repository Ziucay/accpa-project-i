import lexer.Token;
import lexer.TokenType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Importer {

    private List<Token> tokens;

    private List<String> extractPathsFromTokens()
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
                    path = path.substring(1);
                    path = path.substring(0, path.length() - 1);
                    paths.add(path);
                }
            }
        }
        return paths;
    }

    private List<String> getSourcesFromPaths(List<String> paths) throws IOException {
        List<String> sources = new ArrayList<>();

        for (String path : paths)
        {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            String source = new String(bytes, Charset.defaultCharset());
            sources.add(source);
        }

        return sources;
    }

    private List<String> getSourcesFromTokens(List<Token> tokens) throws IOException {
        this.tokens = tokens;
        List<String> paths = extractPathsFromTokens();
        List<String> sources = getSourcesFromPaths(paths);

        return sources;
    }

    public String addCodeFromImports(List<Token> tokens) throws IOException {
        List<String> additionalSources = this.getSourcesFromTokens(tokens);

        String newText = "";
        for (String s : additionalSources) {
            newText = newText + s + "\n";
        }

        return newText;
    }



}
