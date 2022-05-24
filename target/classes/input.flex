package lexer;

import java.util.ArrayList;
import java.util.List;
%%

%class Lexer
%public
%byaccj
%standalone
%char
%column

%eof{
tokens.add(new Token(TokenType.EOF, "eof", null, 0, 0));
%eof}

Whitespace = [\ \t]+
Newline = \r|\n|\r\n
Integer = -?[0-9]+
Double = -?[0-9]+.[0-9]+
Identifier = [a-zA-Z]+[a-zA-Z0-9_]+
%{
public List<Token> tokens = new ArrayList();
%}

%%

{Newline} {yyline++;}

{Whitespace} {}

"(" {tokens.add(new Token(TokenType.LEFT_PAREN, "(", null, yyline, yycolumn));}

")" {tokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, yyline, yycolumn));}

"{" {tokens.add(new Token(TokenType.LEFT_BRACE, "{", null, yyline, yycolumn));}

"}" {tokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, yyline, yycolumn));}

"[" {tokens.add(new Token(TokenType.LEFT_SQUARE_BRACE, "[", null, yyline, yycolumn));}

"]" {tokens.add(new Token(TokenType.RIGHT_SQUARE_BRACE, "]", null, yyline, yycolumn));}

"," {tokens.add(new Token(TokenType.COMMA, ",", null, yyline, yycolumn));}

"." {tokens.add(new Token(TokenType.DOT, ".", null, yyline, yycolumn));}

"-" {tokens.add(new Token(TokenType.MINUS, "-", null, yyline, yycolumn));}

"+" {tokens.add(new Token(TokenType.PLUS, "+", null, yyline, yycolumn));}

"/" {tokens.add(new Token(TokenType.SLASH, "/", null, yyline, yycolumn));}

"*" {tokens.add(new Token(TokenType.STAR, "*", null, yyline, yycolumn));}

"%" {tokens.add(new Token(TokenType.PERCENT, "%", null, yyline, yycolumn));}

"=" {tokens.add(new Token(TokenType.ASSIGNMENT, "=", null, yyline, yycolumn));}

"==" {tokens.add(new Token(TokenType.EQUAL, "==", null, yyline, yycolumn));}

"/=" {tokens.add(new Token(TokenType.NOT_EQUAL, "/=", null, yyline, yycolumn));}

">" {tokens.add(new Token(TokenType.GREATER, ">", null, yyline, yycolumn));}

">=" {tokens.add(new Token(TokenType.GREATER_EQUAL, ">=", null, yyline, yycolumn));}

"<" {tokens.add(new Token(TokenType.LESS, "<", null, yyline, yycolumn));}

"<=" {tokens.add(new Token(TokenType.LESS_EQUAL, "<=", null, yyline, yycolumn));}

".." {tokens.add(new Token(TokenType.DOT_DOT, "..", null, yyline, yycolumn));}

"(" {tokens.add(new Token(TokenType.LEFT_PAREN, "(", null, yyline, yycolumn));}

"int" {tokens.add(new Token(TokenType.TYPE_INT, "int", null, yyline, yycolumn));}

"double" {tokens.add(new Token(TokenType.TYPE_DOUBLE, "double", null, yyline, yycolumn));}

"boolean" {tokens.add(new Token(TokenType.TYPE_BOOLEAN, "boolean", null, yyline, yycolumn));}

"string" {tokens.add(new Token(TokenType.TYPE_STRING, "string", null, yyline, yycolumn));}

"and" {tokens.add(new Token(TokenType.AND, "and", null, yyline, yycolumn));}

"or" {tokens.add(new Token(TokenType.OR, "or", null, yyline, yycolumn));}

"xor" {tokens.add(new Token(TokenType.XOR, "xor", null, yyline, yycolumn));}

"function" {tokens.add(new Token(TokenType.FUNCTION, "function", null, yyline, yycolumn));}

"is" {tokens.add(new Token(TokenType.IS, "is", null, yyline, yycolumn));}

"end" {tokens.add(new Token(TokenType.END, "end", null, yyline, yycolumn));}

"if" {tokens.add(new Token(TokenType.IF, yytext(), null, yyline, yycolumn));}

"then" {tokens.add(new Token(TokenType.THEN, yytext(), null, yyline, yycolumn));}

"else" {tokens.add(new Token(TokenType.ELSE, yytext(), null, yyline, yycolumn));}

"var" {tokens.add(new Token(TokenType.VAR, yytext(), null, yyline, yycolumn));}

"return" {tokens.add(new Token(TokenType.RETURN, "return", null, yyline, yycolumn));}

"print" {tokens.add(new Token(TokenType.PRINT, "print", null, yyline, yycolumn));}

":" {tokens.add(new Token(TokenType.COLON, "colon", null, yyline, yycolumn));}

"=>" {tokens.add(new Token(TokenType.ARROW, "arrow", null, yyline, yycolumn));}

"func" {tokens.add(new Token(TokenType.FUNC, "func", null, yyline, yycolumn));}

{Integer} {tokens.add(new Token(TokenType.INTEGER, yytext(), null, yyline, yycolumn));}

{Double} {tokens.add(new Token(TokenType.DOUBLE, yytext(), null, yyline, yycolumn));}

{Identifier} {tokens.add(new Token(TokenType.IDENTIFIER, yytext(), null, yyline, yycolumn));}

[^] {tokens.add(new Token(TokenType.IDENTIFIER, yytext(), null, yyline, yycolumn));}

