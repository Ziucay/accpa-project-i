import checker.TypeChecker;
import interpreter.Interpreter;
import lexer.Lexer;
import lexer.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.List;

public class CompilerTest {

    private static final String LOCALE = "UTF-8";
    private static final Boolean IS_PARSER_PRINTS_DEBUG = false;

    StringBufferInputStream stream;
    Reader reader;
    Lexer lexer;
    Parser parser;
    Interpreter interpreter;
    TypeChecker checker;
    Importer importer;

    private void initLexer(String text) {
        try {
            stream = new StringBufferInputStream(text);
            reader = new InputStreamReader(stream, LOCALE);
            lexer = new Lexer(reader);
        } catch (java.io.IOException e) {
            System.out.println("IO error scanning file.");
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }
    }

    private void processLexer(String text) throws IOException {
        initLexer(text);

        int result = lexer.yylex();

        assertEquals(0, result);

        String newText = importer.addCodeFromImports(lexer.tokens);

        initLexer(text + "\n" + newText);

        int newResult = lexer.yylex();

        assertEquals(0, newResult);
    }
    private void processParser(List<Token> tokens)
    {
        parser.setTokens(tokens);
        parser.run();
    }

    private void processFlow(String text) throws Exception {
        processLexer(text);

        processParser(lexer.tokens);

        assertTrue(parser.errors == 0);

        checker.check(parser.root);

        interpreter.traverseTree(parser.root);
    }

    @BeforeEach
    public void init()
    {
        parser = new Parser(IS_PARSER_PRINTS_DEBUG);
        interpreter = new Interpreter();
        checker = new TypeChecker();
        importer = new Importer();
    }

    @AfterEach
    public void end()
    {
        if (parser.root != null)
            System.out.println(parser.root);
    }


    @Test
    public void simpleFunction() throws Exception {

        final String text = """
                function main () : void is
                    var a : int is 8
                    print 1 + a
                end""";

        processFlow(text);
    }

    @Test
    public void oneFunctionParseError() throws Exception {

        final String text = """
                function Aaa () : int is
                    print 1 +
                end""";

        processFlow(text);
    }

    @Test
    public void twoFunctions() throws Exception {

        final String text = """
                function main () : void is
                    var b : int is 8 + 7
                    print b
                    Bbb ()
                end
                                
                function Bbb () : void is
                    var a : int is 8 + 5
                    print a
                end""";

        processFlow(text);
    }

    @Test
    public void twoFunctionsWithParameters() throws Exception {

        final String text = """
                function sum (a : int, b : int) : void is
                    print a + b
                end
                                
                function main () : void is
                    sum(10, 27)
                end""";

        processFlow(text);
    }

    @Test
    public void auto() throws Exception {

        final String text = """
                function main () : auto is
                    var a : auto is 8 + 5.1
                    var b : auto is a + a + 5
                    print b
                end""";

        processFlow(text);
    }

    @Test
    public void customTypes() throws Exception {

        final String text = """
                type Random is auto
                function main () : auto is
                    var a : Random is 8 + 5.1
                    var b : Random is a + a + 5
                    print b
                end""";

        processFlow(text);
    }

    @Test
    public void recursion() throws Exception {

        final String text = """
                function main() : void is
                    recursion(10)
                end
                                
                function recursion (a : auto) : auto is
                    print a
                    if a > 0 then
                        recursion(a - 1)
                    end
                end""";

        processFlow(text);
    }

    @Test
    public void twoFunctionsWithFullAuto() throws Exception {

        final String text = """
                function main () : auto is
                    var a : auto is 8 + 5.1
                    print sum(a, 5)
                end
                                
                function sum (a : auto, b : auto) : auto is
                    print a + b
                end""";

        processFlow(text);
    }

    @Test
    public void firstclass() throws Exception {

        final String text = """
                function main () : auto is
                    func a : auto is (b : auto) => {
                        return 1 + b
                    }
                    print a(4)
                end""";

        processFlow(text);
    }

    @Test
    public void loop() throws Exception {

        final String text = """
                function main () : void is
                    var a : auto is 0
                    for a in 0..10 loop
                        print a
                    end
                end""";

        processFlow(text);
    }

    @Test
    public void imports() throws Exception {

        final String text = """
                import "math"
                //This is a single line comment
                function main () : void is
                    var a : int is 8 + 5
                    print sum(a, 5)
                    print multiplication(a,5)
                end
                                
                function sum (a : auto, b : auto) : auto is
                    return a + b
                end""";

        processFlow(text);
    }

    @Test
    public void reallynested() throws Exception {

        final String text = """
                function main () : auto is
                    function sumed (a : auto, b : auto) : auto is
                        function sum (a : auto, b : auto) : auto is
                            return a + b
                        end
                        return sum(a, b)
                    end
                    print sumed(1, 2)
                end""";

        processFlow(text);
    }

    @Test
    public void nested() throws Exception {

        final String text = """
                function main () : auto is
                    function sum (a : auto, b : auto) : auto is
                        print a + b
                    end
                    sum(1, 2)
                end""";

        processFlow(text);
    }

    @Test
    public void demo() throws Exception {

        final String text = """
                import "math"
                //This is a single line comment
                function main () : void is
                    var a : auto is factorial(10)
                    
                    print sum(a, 5)
                    
                    function quoted(quotable: auto):void is
                        print "<"
                        print quotable
                        print ">"
                    end
                    
                    quoted(a)
                end
                                
                function sum (a : auto, b : auto) : auto is
                    return a + b
                end""";

        processFlow(text);
    }
}
