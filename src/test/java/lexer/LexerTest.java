package lexer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;


import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class LexerTest {

    private static final String LOCALE = "UTF-8";

    StringBufferInputStream stream;
    Reader reader;
    Lexer lexer;

    private void initLexer(String text) throws UnsupportedEncodingException {
        try {
            stream = new StringBufferInputStream(text);
            reader = new InputStreamReader(stream, LOCALE);
            lexer = new Lexer(reader);
        }
        catch (java.io.IOException e) {
            System.out.println("IO error scanning file.");
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }
    }

    @Test
    public void simpleFunctionTest() throws IOException {

        final String text = """
                function Aaa () : int is
                    var a : int is 8
                    print 1 + a
                end""";

        initLexer(text);

        lexer.yylex();

        System.out.println(lexer.tokens);

        assertEquals(19,lexer.tokens.size());

    }

}
