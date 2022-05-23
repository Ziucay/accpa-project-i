import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import parser.Parser;
import interpreter.Interpreter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Application {

    public static void main(String[] argv) throws IOException {

        String filename = argv[0];

        try {

            byte[] bytes = Files.readAllBytes(Paths.get(filename));
            String source = new String(bytes, Charset.defaultCharset());
            java.io.StringBufferInputStream stream = new java.io.StringBufferInputStream(source);
            java.io.Reader reader = new java.io.InputStreamReader(stream, "UTF-8");
            Lexer lexer = new Lexer(reader);
            lexer.yylex();

            Parser parser = new Parser(true);
            parser.setTokens(lexer.tokens);
            parser.run();

            System.out.println(lexer.tokens);
            System.out.println(parser.root.toString());

            System.out.println("Program execution starts");

            Interpreter interpreter = new Interpreter();
            interpreter.traverseTree(parser.root, argv[1]);
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("File not found : \""+filename+"\"");
        }
        catch (java.io.IOException e) {
            System.out.println("IO error scanning file \""+filename+"\"");
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }
    }
}
