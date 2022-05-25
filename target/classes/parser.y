%{
package parser;
import lexer.Token;

import java.util.*;
%}


%token LEFT_PAREN // (
%token RIGHT_PAREN // )
%token LEFT_BRACE // {
%token LEFT_SQUARE_BRACE // [
%token RIGHT_SQUARE_BRACE // ]
%token RIGHT_BRACE // }
%token COMMA // ,
%token DOT // .
%token MINUS // -
%token PLUS // +
%token SLASH // /
%token STAR // *
%token PERCENT // %
%token EQUAL // =
%token EQUAL_EQUAL // == 
%token SLASH_EQUAL // /=
%token GREATER // > 
%token GREATER_EQUAL // >=
%token LESS // <
%token LESS_EQUAL // <=
%token DOT_DOT // ..
%token IDENTIFIER
%token SEMICOLON // ;
%token COLON // :
%token TYPE_BOOLEAN // boolean
%token TYPE_INT // integer
%token TYPE_DOUBLE // real
%token TYPE_RECORD // record
%token TYPE_ARRAY
%token TYPE_STRING
%token AND
%token ARRAY
%token BOOLEAN
%token ELSE
%token END
%token FALSE
%token FOR
%token IF
%token IN
%token INTEGER
%token IS
%token LOOP
%token NOT
%token OR
%token PRINT
%token REAL
%token RECORD
%token RETURN
%token REVERSE
%token FUNCTION
%token THEN
%token TRUE
%token TYPE
%token VAR
%token WHILE
%token XOR
%token STRING
%token EOF
%token DOUBLE
%token ARROW
%token FUNC
%token AUTO
%token VOID

%left PLUS
%left MINUS
%left STAR
%left SLASH



%%
Program
	: Lines EOF
	| 
	;

Lines   : Line Lines
	| Line
	| 
	;

Line    : VariableDeclaration {root.descendants.add($1.obj);}
	| FunctionDeclaration {root.descendants.add($1.obj);}
	;

ArrayDeclaration
	: TYPE_ARRAY Identifier COLON Type EQUAL LEFT_SQUARE_BRACE Expression RIGHT_SQUARE_BRACE { $$ = new ParserVal(new Node("array-declaration", null, Arrays.asList($2.obj, $4.obj, $7.obj)));}
	;

ArrayAccess
	: Identifier LEFT_SQUARE_BRACE Expression RIGHT_SQUARE_BRACE {$$ = new ParserVal(new Node("array-access", null, Arrays.asList($1.obj, $3.obj)));}
	;

VariableDeclaration
	: VAR ModifiablePrimary COLON Type {$$ = new ParserVal(new Node("variable-declaration", null, Arrays.asList($2.obj, $4.obj)));}
	| VAR ModifiablePrimary COLON Type IS Expression {$$ = new ParserVal(new Node("variable-declaration", null, Arrays.asList($2.obj, $4.obj, $6.obj)));}
	| FUNC ModifiablePrimary COLON Type IS LEFT_PAREN RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE {$$ = new ParserVal(new Node("function-expression", null, Arrays.asList($2.obj,new Node("parameters", null), $4.obj, $10.obj)));}
	| FUNC ModifiablePrimary COLON Type IS LEFT_PAREN ParameterDeclaration RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE {$$ = new ParserVal(new Node("function-expression", null, Arrays.asList($2.obj,new Node("parameters", null, Arrays.asList($7.obj)),$4.obj, $11.obj)));}
	| FUNC ModifiablePrimary COLON Type IS LEFT_PAREN Parameters RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE {$$ = new ParserVal(new Node("function-expression", null, Arrays.asList($2.obj,$7.obj, $4.obj, $11.obj)));}
	;

FunctionDeclaration
	: FunctionKeyword Identifier LEFT_PAREN RIGHT_PAREN COLON Type IS Body END {$$ = new ParserVal(new Node("function-declaration", null, Arrays.asList($2.obj,new Node("parameters", null, Arrays.asList($1.obj)), $6.obj,$8.obj)));}
	| FunctionKeyword Identifier LEFT_PAREN ParameterDeclaration RIGHT_PAREN COLON Type IS Body END {$$ = new ParserVal(new Node("function-declaration", null, Arrays.asList($2.obj, new Node("parameters", null, Arrays.asList($4.obj)),$7.obj,$9.obj)));}
	| FunctionKeyword Identifier LEFT_PAREN Parameters RIGHT_PAREN COLON Type IS Body END {$$ = new ParserVal(new Node("function-declaration", null, Arrays.asList($2.obj, $4.obj,$7.obj,$9.obj)));}
	;

Parameters
	: ParameterDeclaration {$$ = new ParserVal(new Node("parameters", null, Arrays.asList($1.obj)));}
	| ParameterDeclaration COMMA Parameters {((Node)$3.obj).descendants.add($1.obj); $$ = $3;}
	;

ParameterDeclaration
	: ModifiablePrimary COLON Type {$$ = new ParserVal(new Node("parameter-declaration", null, Arrays.asList($1.obj, $3.obj)));}
	;

Type
	: TYPE_INT {$$ = new ParserVal(new Node("type-integer", null));}
	| TYPE_DOUBLE {$$ = new ParserVal(new Node("type-real", null));}
	| TYPE_BOOLEAN {$$ = new ParserVal(new Node("type-boolean", null));}
	| VOID {$$ = new ParserVal(new Node("void", null));}
	| AUTO {$$ = new ParserVal(new Node("auto", null));}
	;

Body
	: Statements {$$ = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
	;

Statements : Statement Statements
	   | 
	   ;

Statement 
	: Assignment {$$ = $1; blockStack.peek().add($1.obj);}
	| FunctionCall {$$ = $1; blockStack.peek().add($1.obj);}
	| WhileLoop {$$ = $1; blockStack.peek().add($1.obj);}
	| ForLoop {$$ = $1; blockStack.peek().add($1.obj);}
	| IfStatement {$$ = $1; blockStack.peek().add($1.obj);}
	| VariableDeclaration {$$ = $1; blockStack.peek().add($1.obj);}
	| Return {$$ = $1; blockStack.peek().add($1.obj);}
	| Print {$$ = $1; blockStack.peek().add($1.obj);}
	| ArrayDeclaration {$$ = $1; blockStack.peek().add($1.obj);}
	| ArrayAccess {$$ = $1; blockStack.peek().add($1.obj);} 	
	;

Print
	: PRINT Expression {$$ = new ParserVal(new Node("print", null, Arrays.asList($2.obj)));}

Return
	: RETURN Expression {$$ = new ParserVal(new Node("return", null, Arrays.asList($2.obj)));}

Assignment
	: ModifiablePrimary EQUAL Expression {$$ = new ParserVal(new Node("assignment", null, Arrays.asList($1.obj, $3.obj)));}
	;

FunctionCall
	: ModifiablePrimary LEFT_PAREN RIGHT_PAREN {$$ = new ParserVal(new Node("function-call", null, Arrays.asList($1.obj, new Node("arguments", null))));}
	| ModifiablePrimary LEFT_PAREN ArgumentDeclaration RIGHT_PAREN {$$ = new ParserVal(new Node("function-call", null, Arrays.asList($1.obj,new Node("arguments", null, Arrays.asList($3.obj)))));}
	| ModifiablePrimary LEFT_PAREN Arguments RIGHT_PAREN {$$ = new ParserVal(new Node("function-call", null, Arrays.asList($1.obj, $3.obj)));}
	;

Identifier
	: IDENTIFIER {$$ = new ParserVal(new Node(new String(yylval.sval), null));}

Arguments
	: ArgumentDeclaration {$$ = new ParserVal(new Node("arguments", null, Arrays.asList($1.obj)));}
	| ArgumentDeclaration COMMA Arguments {((Node)$3.obj).descendants.add($1.obj); $$ = $3;}
	;

ArgumentDeclaration
	: Expression {$$ = new ParserVal(new Node("argument", null, Arrays.asList($1.obj)));}
	;

Expressions
	: Expression {$$ = $1;}
	| Expression COMMA Expressions {((Node)$3.obj).descendants.add($1.obj); $$ = $3;}
	| 
	;

WhileLoop
	: WhileKeyword Expression LOOP Body END {$$ = new ParserVal(new Node("while", null, Arrays.asList($2.obj, $4.obj)));}
	;

WhileKeyword: WHILE { blockStack.push(new LinkedList<>());}
ForKeyword: FOR { blockStack.push(new LinkedList<>());}
IfKeyword: IF { blockStack.push(new LinkedList<>());}
ElseKeyword: ELSE { blockStack.push(new LinkedList<>());}
FunctionKeyword: FUNCTION { blockStack.push(new LinkedList<>());}

ForLoop
	: ForKeyword ModifiablePrimary Range LOOP Body END {$$ = new ParserVal(new Node("for", null, Arrays.asList($2.obj, $3.obj, $5.obj)));}
	;

Range
	: IN Expression DOT_DOT Expression {$$ = new ParserVal(new Node("range", null, Arrays.asList($2.obj, $4.obj)));}
	| IN REVERSE Expression DOT_DOT Expression {$$ = new ParserVal(new Node("range-reverse", null, Arrays.asList($3.obj, $5.obj)));}
	;

IfStatement
	: IfKeyword Expression THEN Body END {$$ = new ParserVal(new Node("if", null, Arrays.asList($2.obj, $4.obj)));}
	| IfKeyword Expression THEN Body ElseKeyword Body END {$$ = new ParserVal(new Node("if-else", null, Arrays.asList($2.obj, $4.obj, $6.obj)));}
	;

Expression
	: Relation AND Expression {$$ = new ParserVal(new Node("and", null, Arrays.asList($1.obj, $3.obj)));}
	| Relation OR Expression {$$ = new ParserVal(new Node("or", null, Arrays.asList($1.obj, $3.obj)));}
	| Relation XOR Expression {$$ = new ParserVal(new Node("xor", null, Arrays.asList($1.obj, $3.obj)));}
	| Relation {$$ = $1;}
	; 

Relation
	: Simple LESS Relation {$$ = new ParserVal(new Node("less", null, Arrays.asList($1.obj, $3.obj)));}
	| Simple LESS_EQUAL Relation {$$ = new ParserVal(new Node("less or equal", null, Arrays.asList($1.obj, $3.obj)));}
	| Simple GREATER Relation {$$ = new ParserVal(new Node("more", null, Arrays.asList($1.obj, $3.obj)));}
	| Simple GREATER_EQUAL Relation {$$ = new ParserVal(new Node("more or equal", null, Arrays.asList($1.obj, $3.obj)));}
	| Simple EQUAL Relation {$$ = new ParserVal(new Node("equal", null, Arrays.asList($1.obj, $3.obj)));}
	| Simple SLASH_EQUAL Relation {$$ = new ParserVal(new Node("not equal", null, Arrays.asList($1.obj, $3.obj)));}
	| Simple {$$ = $1;}
	; 

Simple
	: Factor STAR Simple {$$ = new ParserVal(new Node("multiply", null, Arrays.asList($1.obj, $3.obj)));}
	| Factor SLASH Simple {$$ = new ParserVal(new Node("divide", null, Arrays.asList($1.obj, $3.obj)));}
	| Factor PERCENT Simple {$$ = new ParserVal(new Node("percent", null, Arrays.asList($1.obj, $3.obj)));}
	| Factor {$$ = $1;}
	; 

Factor
	: Summand PLUS Factor {$$ = new ParserVal(new Node("plus", null, Arrays.asList($1.obj, $3.obj)));}
	| Summand MINUS Factor {$$ = new ParserVal(new Node("minus", null, Arrays.asList($1.obj, $3.obj)));}
	| Summand {$$ = $1;}
	; 

Summand : Primary {$$ = $1;}
	| LEFT_PAREN Expression RIGHT_PAREN {$$ = new ParserVal(new Node("summand", null, Arrays.asList($2.obj)));}
	; 

Primary
	: DOUBLE {$$ = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf($1.dval)));}
	| INTEGER {$$ = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf($1.ival)));}
	| TRUE {$$ = new ParserVal(new Node("true", Boolean.valueOf(true)));}
	| FALSE {$$ = new ParserVal(new Node("false", Boolean.valueOf(false)));}
	| ModifiablePrimary {$$ = new ParserVal(new Node("modifiable", null, Arrays.asList($1.obj)));}
	;

ModifiablePrimary : IDENTIFIER {$$ = new ParserVal(new Node(yylval.sval, null));}
		  ;

%%
    Stack<List<Node>> blockStack = new Stack<>();
    List<Token> tokens;
    int tokenPointer = 0;
    public Node root = new Node("root", null, new LinkedList<>());
    public int errors = 0;


    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
        tokenPointer = 0;
    }

private void yyerror(String syntax_error) {
	System.out.println("Error: " + syntax_error);
        errors++;
}

    private int yylex() {
        if (tokenPointer == tokens.size())
            return -1;
        Token token = tokens.get(tokenPointer);
        switch (token.type) {
            case INTEGER -> this.yylval = new ParserVal(Integer.valueOf(token.lexeme).intValue());
            case DOUBLE -> this.yylval = new ParserVal(Double.valueOf(token.lexeme).doubleValue());
            case BOOLEAN -> this.yylval = new ParserVal(Boolean.valueOf(token.lexeme).booleanValue());
            case IDENTIFIER -> this.yylval = new ParserVal(token.lexeme);
            default -> this.yylval = new ParserVal(token.lexeme);
        }
        tokenPointer++;
        return tokens.get(tokenPointer - 1).TokenTypeToInt();
    }