//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "parser.y"
package parser;
import lexer.Token;

import java.util.*;
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short LEFT_PAREN=257;
public final static short RIGHT_PAREN=258;
public final static short LEFT_BRACE=259;
public final static short LEFT_SQUARE_BRACE=260;
public final static short RIGHT_SQUARE_BRACE=261;
public final static short RIGHT_BRACE=262;
public final static short COMMA=263;
public final static short DOT=264;
public final static short MINUS=265;
public final static short PLUS=266;
public final static short SLASH=267;
public final static short STAR=268;
public final static short PERCENT=269;
public final static short EQUAL=270;
public final static short EQUAL_EQUAL=271;
public final static short SLASH_EQUAL=272;
public final static short GREATER=273;
public final static short GREATER_EQUAL=274;
public final static short LESS=275;
public final static short LESS_EQUAL=276;
public final static short DOT_DOT=277;
public final static short IDENTIFIER=278;
public final static short SEMICOLON=279;
public final static short COLON=280;
public final static short TYPE_BOOLEAN=281;
public final static short TYPE_INT=282;
public final static short TYPE_DOUBLE=283;
public final static short TYPE_RECORD=284;
public final static short TYPE_ARRAY=285;
public final static short TYPE_STRING=286;
public final static short AND=287;
public final static short ARRAY=288;
public final static short BOOLEAN=289;
public final static short ELSE=290;
public final static short END=291;
public final static short FALSE=292;
public final static short FOR=293;
public final static short IF=294;
public final static short IN=295;
public final static short INTEGER=296;
public final static short IS=297;
public final static short LOOP=298;
public final static short NOT=299;
public final static short OR=300;
public final static short PRINT=301;
public final static short REAL=302;
public final static short RECORD=303;
public final static short RETURN=304;
public final static short REVERSE=305;
public final static short FUNCTION=306;
public final static short THEN=307;
public final static short TRUE=308;
public final static short TYPE=309;
public final static short VAR=310;
public final static short WHILE=311;
public final static short XOR=312;
public final static short STRING=313;
public final static short EOF=314;
public final static short DOUBLE=315;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    2,    2,    3,    3,    3,
    4,    4,    9,    9,    9,   11,    6,    6,    6,   10,
   12,   12,   13,   13,   13,   13,   13,   13,   13,   19,
   14,   15,   20,   20,   16,   21,   22,   23,   24,    8,
   17,   25,   25,   18,   18,    7,    7,    7,    7,   26,
   26,   26,   26,   26,   26,   26,   27,   27,   27,   27,
   28,   28,   28,   29,   29,   30,   30,   30,   30,   30,
    5,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    4,    6,    4,
    8,   10,    1,    3,    0,    3,    1,    1,    1,    1,
    2,    0,    1,    1,    1,    1,    1,    1,    1,    2,
    3,    4,    1,    3,    5,    1,    1,    1,    1,    1,
    6,    4,    5,    5,    7,    3,    3,    3,    1,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    3,    1,
    3,    3,    1,    1,    3,    1,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
   40,    0,    0,    0,    0,    6,    7,    0,   71,    0,
    1,    3,    0,    0,    0,    0,   19,   17,   18,    0,
    0,   69,   67,   68,   66,   70,   10,    0,    0,    0,
    0,   64,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,   65,   46,   47,   48,   54,
   55,   52,   53,   50,   51,   58,   57,   59,   62,   61,
   16,    0,    0,   14,    0,   37,   38,    0,   36,   28,
    0,    0,   20,    0,   23,   24,   25,   26,   27,   29,
    0,    0,    0,    0,   30,    0,    0,   11,   21,    0,
    0,    0,    0,    0,    0,   31,    0,    0,    0,    0,
   12,    0,   32,    0,    0,    0,    0,    0,   34,   35,
    0,    0,    0,   39,   44,    0,    0,   42,   41,    0,
   43,   45,
};
final static short yydgoto[] = {                          3,
    4,    5,   80,    7,   26,   20,  104,    8,   34,   82,
   35,   83,   84,   85,   86,   87,   88,   89,   90,  105,
   91,   92,   93,  126,  109,   28,   29,   30,   31,   32,
};
final static short yysindex[] = {                      -273,
    0, -268,    0, -302, -273,    0,    0, -268,    0, -276,
    0,    0, -240, -219, -248, -268,    0,    0,    0, -278,
 -248,    0,    0,    0,    0,    0,    0, -280, -217, -199,
 -177,    0, -237, -218, -216, -248, -192, -248, -248, -248,
 -248, -248, -248, -248, -248, -248, -248, -248, -248, -248,
 -248, -219, -275, -268,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -219, -265,    0, -214,    0,    0, -248,    0,    0,
 -221, -212,    0, -265,    0,    0,    0,    0,    0,    0,
 -248, -268, -248, -265,    0, -248, -248,    0,    0, -207,
 -195, -226, -190, -160, -154,    0, -265, -254, -191, -265,
    0, -248,    0, -185, -248, -167, -265, -197,    0,    0,
 -165, -248, -178,    0,    0, -265, -248,    0,    0, -174,
    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0, -196,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -139,    0,    0,    0,  -17,
    0,    0,    0,    0,    0,    0,    0,  -39,  -64, -102,
 -147,    0,    0,    0, -126,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -139,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -158,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -194,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -158,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -124,    0,    0, -158,    0,    0, -194,
    0,    0,    0,    0,    0,    0, -158,    0,    0,    0,
    0,    0,    0,    0,    0, -158,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  130,    0,   11,    0,   -2,  -37,  -13,    0,   82,  -76,
    0,   53,    0,    0,    0,    0,    0,    0,    0,   26,
    0,    0,    0,    0,    0,   31,   38,   48,    0,    0,
};
final static int YYTABLESIZE=315;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         10,
    2,   27,   21,   14,   72,   13,   38,   37,   21,    9,
    6,   11,    9,   33,   71,    6,   16,  103,   36,   39,
   15,   73,   55,    9,   57,   58,   59,   76,   77,    9,
  114,   40,    1,  118,   75,   96,    2,   22,   78,   53,
  123,   23,   52,   22,    2,   79,   54,   23,   97,  130,
  115,   33,   41,   24,   42,   43,   44,   45,   46,   24,
   25,   17,   18,   19,   95,   56,   25,   47,   48,   49,
   81,   60,   61,   62,   63,   64,   65,  100,   98,  102,
  110,   81,   94,  106,   66,   67,   68,   50,   51,  101,
  107,   81,  124,  125,  116,   22,   22,   69,   70,  108,
  111,  121,  112,  113,   81,  120,  117,   81,  128,  122,
   63,  127,  129,  131,   81,   63,  132,    4,   15,   63,
   63,   63,   63,   81,   63,   63,   63,   63,   63,   63,
   63,   13,   22,   33,   12,   74,   99,  119,    0,   63,
    0,    0,   63,   63,    0,   63,   63,    0,    0,    0,
   63,    0,   63,    0,    0,   60,   63,    0,   63,   63,
   60,    0,   63,   63,   63,    0,   63,   60,    0,   60,
   60,   60,   60,   60,   60,   60,    0,    0,    0,    0,
    0,    0,    0,    0,   60,    0,    0,   60,   60,    0,
   60,   60,    0,   56,    0,   60,    0,   60,   56,    0,
    0,   60,    0,   60,   60,    0,    0,   60,   60,   60,
    0,   60,   56,   56,    0,    0,    0,    0,   49,    0,
    0,    0,   56,   49,    0,   56,   56,    0,   56,   56,
    0,    0,    0,   56,    0,   56,    0,   49,   49,   56,
    0,   56,   56,    0,    0,   56,   56,   56,    0,   56,
   49,   49,    0,   49,   49,    0,    0,    0,   49,    0,
    8,    0,    0,    0,   49,    0,   49,   49,    0,    0,
   49,   49,    8,    8,   49,    8,    8,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    8,    0,    8,    0,
    0,    0,    8,    8,    0,    0,    8,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    5,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,   15,  257,  280,  280,    8,  287,   21,  257,  278,
    0,  314,  278,   16,   52,    5,  257,   94,  297,  300,
  297,  297,   36,  278,   38,   39,   40,  293,  294,  278,
  107,  312,  306,  110,   72,  257,  310,  292,  304,  258,
  117,  296,  280,  292,  310,  311,  263,  296,  270,  126,
  305,   54,  270,  308,  272,  273,  274,  275,  276,  308,
  315,  281,  282,  283,   78,  258,  315,  267,  268,  269,
   73,   41,   42,   43,   44,   45,   46,   91,  291,   93,
  307,   84,  297,   97,   47,   48,   49,  265,  266,   92,
  298,   94,  290,  291,  108,  290,  291,   50,   51,  295,
  291,  115,  263,  258,  107,  291,  298,  110,  122,  277,
  258,  277,  291,  127,  117,  263,  291,  314,  258,  267,
  268,  269,  270,  126,  272,  273,  274,  275,  276,  277,
  278,  258,  291,  258,    5,   54,   84,  112,   -1,  287,
   -1,   -1,  290,  291,   -1,  293,  294,   -1,   -1,   -1,
  298,   -1,  300,   -1,   -1,  258,  304,   -1,  306,  307,
  263,   -1,  310,  311,  312,   -1,  314,  270,   -1,  272,
  273,  274,  275,  276,  277,  278,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  287,   -1,   -1,  290,  291,   -1,
  293,  294,   -1,  258,   -1,  298,   -1,  300,  263,   -1,
   -1,  304,   -1,  306,  307,   -1,   -1,  310,  311,  312,
   -1,  314,  277,  278,   -1,   -1,   -1,   -1,  258,   -1,
   -1,   -1,  287,  263,   -1,  290,  291,   -1,  293,  294,
   -1,   -1,   -1,  298,   -1,  300,   -1,  277,  278,  304,
   -1,  306,  307,   -1,   -1,  310,  311,  312,   -1,  314,
  290,  291,   -1,  293,  294,   -1,   -1,   -1,  298,   -1,
  278,   -1,   -1,   -1,  304,   -1,  306,  307,   -1,   -1,
  310,  311,  290,  291,  314,  293,  294,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  304,   -1,  306,   -1,
   -1,   -1,  310,  311,   -1,   -1,  314,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  314,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=315;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"LEFT_PAREN","RIGHT_PAREN","LEFT_BRACE","LEFT_SQUARE_BRACE",
"RIGHT_SQUARE_BRACE","RIGHT_BRACE","COMMA","DOT","MINUS","PLUS","SLASH","STAR",
"PERCENT","EQUAL","EQUAL_EQUAL","SLASH_EQUAL","GREATER","GREATER_EQUAL","LESS",
"LESS_EQUAL","DOT_DOT","IDENTIFIER","SEMICOLON","COLON","TYPE_BOOLEAN",
"TYPE_INT","TYPE_DOUBLE","TYPE_RECORD","TYPE_ARRAY","TYPE_STRING","AND","ARRAY",
"BOOLEAN","ELSE","END","FALSE","FOR","IF","IN","INTEGER","IS","LOOP","NOT","OR",
"PRINT","REAL","RECORD","RETURN","REVERSE","FUNCTION","THEN","TRUE","TYPE",
"VAR","WHILE","XOR","STRING","EOF","DOUBLE",
};
final static String yyrule[] = {
"$accept : Program",
"Program : Lines EOF",
"Program :",
"Lines : Line Lines",
"Lines : Line",
"Lines :",
"Line : VariableDeclaration",
"Line : FunctionDeclaration",
"VariableDeclaration : VAR ModifiablePrimary COLON Type",
"VariableDeclaration : VAR ModifiablePrimary COLON Type IS Expression",
"VariableDeclaration : VAR ModifiablePrimary IS Expression",
"FunctionDeclaration : FunctionKeyword ModifiablePrimary LEFT_PAREN Parameters RIGHT_PAREN IS Body END",
"FunctionDeclaration : FunctionKeyword ModifiablePrimary LEFT_PAREN Parameters RIGHT_PAREN COLON Type IS Body END",
"Parameters : ParameterDeclaration",
"Parameters : ParameterDeclaration COMMA Parameters",
"Parameters :",
"ParameterDeclaration : ModifiablePrimary COLON Type",
"Type : TYPE_INT",
"Type : TYPE_DOUBLE",
"Type : TYPE_BOOLEAN",
"Body : Statements",
"Statements : Statement Statements",
"Statements :",
"Statement : Assignment",
"Statement : FunctionCall",
"Statement : WhileLoop",
"Statement : ForLoop",
"Statement : IfStatement",
"Statement : VariableDeclaration",
"Statement : Return",
"Return : RETURN Expression",
"Assignment : ModifiablePrimary EQUAL Expression",
"FunctionCall : ModifiablePrimary LEFT_PAREN Expressions RIGHT_PAREN",
"Expressions : Expression",
"Expressions : Expression COMMA Expressions",
"WhileLoop : WhileKeyword Expression LOOP Body END",
"WhileKeyword : WHILE",
"ForKeyword : FOR",
"IfKeyword : IF",
"ElseKeyword : ELSE",
"FunctionKeyword : FUNCTION",
"ForLoop : ForKeyword ModifiablePrimary Range LOOP Body END",
"Range : IN Expression DOT_DOT Expression",
"Range : IN REVERSE Expression DOT_DOT Expression",
"IfStatement : IfKeyword Expression THEN Body END",
"IfStatement : IfKeyword Expression THEN Body ElseKeyword Body END",
"Expression : Relation AND Expression",
"Expression : Relation OR Expression",
"Expression : Relation XOR Expression",
"Expression : Relation",
"Relation : Simple LESS Relation",
"Relation : Simple LESS_EQUAL Relation",
"Relation : Simple GREATER Relation",
"Relation : Simple GREATER_EQUAL Relation",
"Relation : Simple EQUAL Relation",
"Relation : Simple SLASH_EQUAL Relation",
"Relation : Simple",
"Simple : Factor STAR Simple",
"Simple : Factor SLASH Simple",
"Simple : Factor PERCENT Simple",
"Simple : Factor",
"Factor : Summand PLUS Factor",
"Factor : Summand MINUS Factor",
"Factor : Summand",
"Summand : Primary",
"Summand : LEFT_PAREN Expression RIGHT_PAREN",
"Primary : DOUBLE",
"Primary : INTEGER",
"Primary : TRUE",
"Primary : FALSE",
"Primary : ModifiablePrimary",
"ModifiablePrimary : IDENTIFIER",
};

//#line 221 "parser.y"
    Stack<List<Node>> blockStack = new Stack<>();

    List<Token> tokens;
    int tokenPointer = 0;
    public Node root = new Node("root", null, new LinkedList<>());


    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
        tokenPointer = 0;
    }

private void yyerror(String syntax_error) {
	System.out.println("Error: " + syntax_error);
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
//#line 440 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 6:
//#line 87 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 7:
//#line 88 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 8:
//#line 92 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 9:
//#line 93 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj)));}
break;
case 10:
//#line 94 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 11:
//#line 98 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(6).obj, val_peek(4).obj, val_peek(1).obj)));}
break;
case 12:
//#line 99 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, val_peek(6).obj,val_peek(3).obj,val_peek(1).obj)));}
break;
case 13:
//#line 103 "parser.y"
{yyval = val_peek(0);}
break;
case 14:
//#line 104 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 15:
//#line 105 "parser.y"
{yyval = new ParserVal(new Node("parameters", null));}
break;
case 16:
//#line 109 "parser.y"
{yyval = new ParserVal(new Node("parameter-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 17:
//#line 113 "parser.y"
{yyval = new ParserVal(new Node("type-integer", null));}
break;
case 18:
//#line 114 "parser.y"
{yyval = new ParserVal(new Node("type-real", null));}
break;
case 19:
//#line 115 "parser.y"
{yyval = new ParserVal(new Node("type-boolean", null));}
break;
case 20:
//#line 119 "parser.y"
{yyval = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
break;
case 23:
//#line 127 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 24:
//#line 128 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 25:
//#line 129 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 26:
//#line 130 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 27:
//#line 131 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 28:
//#line 132 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 29:
//#line 133 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 30:
//#line 137 "parser.y"
{yyval = new ParserVal(new Node("return", null, Arrays.asList(val_peek(0).obj)));}
break;
case 31:
//#line 140 "parser.y"
{yyval = new ParserVal(new Node("assignment", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 32:
//#line 144 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 33:
//#line 147 "parser.y"
{yyval = val_peek(0);}
break;
case 34:
//#line 148 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 35:
//#line 152 "parser.y"
{yyval = new ParserVal(new Node("while", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 36:
//#line 155 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 37:
//#line 156 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 38:
//#line 157 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 39:
//#line 158 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 40:
//#line 159 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 41:
//#line 162 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 42:
//#line 166 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 43:
//#line 167 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 44:
//#line 171 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 45:
//#line 172 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 46:
//#line 176 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 47:
//#line 177 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 48:
//#line 178 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 49:
//#line 179 "parser.y"
{yyval = val_peek(0);}
break;
case 50:
//#line 183 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 51:
//#line 184 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 52:
//#line 185 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 53:
//#line 186 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 54:
//#line 187 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 55:
//#line 188 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 56:
//#line 189 "parser.y"
{yyval = val_peek(0);}
break;
case 57:
//#line 193 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 58:
//#line 194 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 59:
//#line 195 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 60:
//#line 196 "parser.y"
{yyval = val_peek(0);}
break;
case 61:
//#line 200 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 62:
//#line 201 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 63:
//#line 202 "parser.y"
{yyval = val_peek(0);}
break;
case 64:
//#line 205 "parser.y"
{yyval = val_peek(0);}
break;
case 65:
//#line 206 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 66:
//#line 210 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 67:
//#line 211 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 68:
//#line 212 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 69:
//#line 213 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 70:
//#line 214 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 71:
//#line 217 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
//#line 845 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
