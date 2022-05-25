import interpreter.Interpreter;
import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class CompilerTest {

    private static final String LOCALE = "UTF-8";
    private static final Boolean IS_PARSER_PRINTS_DEBUG = false;

    StringBufferInputStream stream;
    Reader reader;
    Lexer lexer;
    Parser parser;
    Interpreter interpreter;

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

    private void initParser()
    {
        parser = new Parser(IS_PARSER_PRINTS_DEBUG);
    }

    private void initInterpreter()
    {
        interpreter = new Interpreter();
    }

    @Test
    public void simpleFunction() throws IOException {

        final String startingFunction = "Aaa";
        final String text = """
                function Aaa () : int is
                    var a : int is 8
                    print 1 + a
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(19,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

    @Test
    public void oneFunctionParseError() throws IOException {

        final String startingFunction = "Aaa";
        final String text = """
                function Aaa () : int is
                    print 1 +
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(12,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertFalse(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

    @Test
    public void twoFunctions() throws IOException {

        final String startingFunction = "Aaa";
        final String text = """
                function Aaa () : int is
                    var b : int is 8 + 7
                    print b
                    Bbb ()
                                
                end
                                
                function Bbb () : int is
                    var a : int is 8 + 5
                    print a
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(40,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

    @Test
    public void twoFunctionsWithParameters() throws IOException {

        final String startingFunction = "Aaa";
        final String text = """
                function sum (a : int, b : int) : int is
                    print a + b
                end
                                
                function Aaa () : int is
                    sum(10, 27)
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(34,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

    @Test
    public void oneParameterFunction() throws IOException {

        final String startingFunction = "printInt";
        final String text = """
                function printInt (a : int) : int is
                    print a
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(14,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

    @Test
    public void recursion() throws IOException {

        final String startingFunction = "main";
        final String text = """
                
                function main() : void is
                    recursion(10)
                end
                
                function recursion (a : int) : void is
                    print a
                    if a > 0 then
                        recursion(a - 1)
                    end
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(38,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

    @Test
    public void array() throws IOException {

        final String startingFunction = "arrayTest";
        final String text = """
                function arrayTest (a : int) : void is
                    array testArray : int = [a]
                    a[0] = 1
                    a[1] = 3
                    print a[0]
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(37,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

    @Test
    public void twoFunctionsWithSameName() throws IOException {

        final String startingFunction = "main";
        final String text = """
                function main () : void is
                    identical()
                end
                
                function identical () : void is
                    print 1
                end
                
                function identical () : void is
                    print 2
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(32,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root, startingFunction);
    }

}
