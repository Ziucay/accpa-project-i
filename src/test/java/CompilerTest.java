import checker.TypeChecker;
import interpreter.Interpreter;
import lexer.Lexer;
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
    public void simpleFunction() throws Exception {

        final String text = """
                function main () : void is
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

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
    }

    @Test
    public void oneFunctionParseError() throws IOException {

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
        interpreter.traverseTree(parser.root);
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

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
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

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
    }

    @Test
    public void auto() throws Exception {

        final String text = """
                function main () : auto is
                    var a : auto is 8 + 5.1
                    var b : auto is a + a + 5
                    print b
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(29,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
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

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
    }

    @Test
    public void twoFunctionsWithSameName() throws Exception {

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

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
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

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(43,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
    }

    @Test
    public void loop() throws IOException {

        final String text = """
                function main () : void is
                    var a : auto is 0
                    for a in 0..10 loop
                        print a
                    end
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        assertEquals(25,lexer.tokens.size());

        parser.setTokens(lexer.tokens);
        parser.run();

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
    }

    @Test
    public void imports() throws Exception {

        final String text = """
                import "math"
                
                function main () : auto is
                    var a : auto is 8 + 5.1
                    print sum(a, 5)
                    print multiplication(a,5)
                end
                
                function sum (a : auto, b : auto) : auto is
                    return a + b
                    print a + b
                    return a + b
                end""";

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        Importer importer = new Importer();
        List<String> additionalSources = importer.getSourcesFromTokens(lexer.tokens);

        String newText = text + "\n";
        for (String s: additionalSources) {
            newText = newText + s;
        }

        initLexer(newText);

        System.out.println(newText);

        int newResult = lexer.yylex();

        assertEquals(0, newResult);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        parser.setTokens(lexer.tokens);
        parser.run();

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
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

        initLexer(text);
        initParser();
        initInterpreter();

        int result = lexer.yylex();

        assertEquals(0, result);

        Importer importer = new Importer();
        List<String> additionalSources = importer.getSourcesFromTokens(lexer.tokens);

        String newText = text + "\n";
        for (String s: additionalSources) {
            newText = newText + s;
        }

        initLexer(newText);

        System.out.println(newText);

        int newResult = lexer.yylex();

        assertEquals(0, newResult);

        System.out.println("Lexer tokens: ");
        System.out.println(lexer.tokens);

        parser.setTokens(lexer.tokens);
        parser.run();

        TypeChecker checker = new TypeChecker();
        checker.check(parser.root);

        assertTrue(parser.errors == 0);

        System.out.println("Built AST tree: ");
        System.out.println(parser.root.toString());

        System.out.println("Interpreter output: ");
        interpreter.traverseTree(parser.root);
    }
}
