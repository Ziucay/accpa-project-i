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
    4,    4,    4,    4,    4,    4,   12,   12,   11,    6,
    6,    6,   10,   13,   13,   14,   14,   14,   14,   14,
   14,   14,   14,   21,   20,   15,   16,   16,   16,    9,
   23,   23,   22,   24,   24,   24,   17,   25,   26,   27,
   28,    8,   18,   29,   29,   19,   19,    7,    7,    7,
    7,   30,   30,   30,   30,   30,   30,   30,   31,   31,
   31,   31,   32,   32,   32,   33,   33,   34,   34,   34,
   34,   34,    5,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    4,    6,    4,
    7,    8,    8,    9,   10,   10,    1,    3,    3,    1,
    1,    1,    1,    2,    0,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    2,    3,    3,    4,    4,    1,
    1,    3,    1,    1,    3,    0,    5,    1,    1,    1,
    1,    1,    6,    4,    5,    5,    7,    3,    3,    3,
    1,    3,    3,    3,    3,    3,    3,    1,    3,    3,
    3,    1,    3,    3,    1,    1,    3,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
   52,    0,    0,    0,    0,    6,    7,    0,   83,    0,
    1,    3,   40,    0,    0,    0,    0,   22,   20,   21,
    0,    0,   81,   79,   80,   78,   82,   10,    0,    0,
    0,    0,   76,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    9,
   77,   58,   59,   60,   66,   67,   64,   65,   62,   63,
   70,   69,   71,   74,   73,    0,    0,   49,   50,    0,
    0,   48,   31,    0,    0,   23,    0,   26,   27,   28,
   29,   30,   32,   33,    0,    0,    0,   19,    0,    0,
    0,   18,    0,    0,    0,    0,   34,   35,    0,   11,
   24,    0,    0,    0,    0,    0,    0,    0,    0,   37,
   43,    0,    0,   36,    0,    0,    0,    0,    0,   12,
    0,   13,   14,   38,    0,   39,    0,    0,    0,    0,
    0,    0,    0,    0,   42,   47,    0,    0,    0,   51,
   56,    0,   15,   16,    0,   54,   53,    0,   55,   57,
};
final static short yydgoto[] = {                          3,
    4,    5,   83,    7,   27,   21,  121,    8,   14,   85,
   36,   37,   86,   87,   88,   89,   90,   91,   92,   93,
   94,  122,  123,    0,   95,   96,   97,  152,  127,   29,
   30,   31,   32,   33,
};
final static short yysindex[] = {                      -268,
    0, -257,    0, -307, -268,    0,    0, -238,    0, -267,
    0,    0,    0, -213, -210, -245, -255,    0,    0,    0,
 -251, -245,    0,    0,    0,    0,    0,    0, -271, -166,
 -124, -206,    0, -263, -232, -252, -208, -245, -204, -245,
 -245, -245, -245, -245, -245, -245, -245, -245, -245, -245,
 -245, -245, -245, -210, -179, -210, -262, -257, -261,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -240, -205,    0,    0, -245,
 -245,    0,    0, -192, -209,    0, -179,    0,    0,    0,
    0,    0,    0,    0, -245, -257, -245,    0, -210, -179,
 -177,    0, -210, -179, -179, -253,    0,    0, -245,    0,
    0, -207, -211, -196, -184, -198, -169, -190, -186,    0,
    0, -249, -128,    0, -179, -247, -165, -179, -179,    0,
 -179,    0,    0,    0, -245,    0, -152, -245, -137, -179,
 -216, -130, -129, -100,    0,    0, -113, -245, -126,    0,
    0, -179,    0,    0, -245,    0,    0, -123,    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0, -147,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -214,    0,    0,    0,    0,    0,    0,    0,  -12,  -37,
  -76, -121,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -120,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -96,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -202,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -120,
  -83,    0,    0, -120, -120,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -120,    0,    0, -202, -120,    0,
 -120,    0,    0,    0,    0,    0,    0,    0,    0, -120,
    0,    0,    0,  -82,    0,    0,    0,    0,    0,    0,
    0, -120,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  173,    0,   32,    0,   -2,  -34,  -14,    0,    0,  180,
  123,  126,  101,    0,    0,    0,    0,    0,    0,    0,
    0,   57,   60,    0,    0,    0,    0,    0,    0,   73,
  109,   83,    0,    0,
};
final static int YYTABLESIZE=332;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         10,
    2,   28,   34,   22,  120,   57,   11,   39,  134,   22,
   58,   22,   15,  135,   35,   40,   54,   99,  103,   76,
    9,   98,    9,   60,    9,   62,   63,   64,   41,   16,
    9,    6,    9,   55,  100,  104,    6,    1,   23,   13,
   42,    2,   24,   17,   23,   38,   23,   56,   24,   59,
   24,  106,   84,   61,   25,   35,  105,  138,   52,   53,
   25,   26,   25,    8,  115,  107,  108,   26,  117,   26,
   18,   19,   20,  150,  151,    8,    8,  109,    8,    8,
  112,  110,  114,  126,   84,   58,    8,   25,   25,    8,
  125,    8,  130,  113,  124,    8,    8,   84,   77,    8,
  132,   84,   84,   43,  133,   44,   45,   46,   47,   48,
  128,  139,  129,   78,   79,   65,   66,   67,   68,   69,
   70,   80,   84,  147,   81,   84,   84,  131,   84,  136,
    2,   82,  140,  156,   74,   75,   75,   84,  146,  148,
  159,   75,   49,   50,   51,   75,   75,   75,   75,   84,
   75,   75,   75,   75,   75,   75,   75,   71,   72,   73,
  153,  154,  135,  155,  157,   75,    4,  160,   75,   75,
   25,   75,   75,   83,   17,   41,   75,   12,   75,   75,
  101,   72,   75,  102,   75,   75,   72,  111,   75,   75,
   75,  144,   75,   72,  145,   72,   72,   72,   72,   72,
   72,   72,    0,    0,    0,    0,    0,    0,    0,    0,
   72,    0,    0,   72,   72,    0,   72,   72,    0,    0,
   68,   72,    0,   72,   72,   68,    0,   72,    0,   72,
   72,    0,    0,   72,   72,   72,    0,   72,    0,   68,
   68,    0,    0,    0,    0,   61,    0,    0,    0,   68,
   61,    0,   68,   68,    0,   68,   68,    0,    0,    0,
   68,    0,   68,   68,   61,   61,   68,    0,   68,   68,
    0,    0,   68,   68,   68,    0,   68,   61,   61,  116,
   61,   61,    0,  118,  119,   61,    0,    0,   61,    0,
    0,   61,    0,   61,   61,    0,    0,   61,   61,    0,
    0,   61,    0,    0,  137,    0,    0,  141,  142,    0,
  143,    0,    0,    0,    5,    0,    0,    0,    0,  149,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  158,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,   16,  258,  257,  258,  258,  314,   22,  258,  257,
  263,  257,  280,  263,   17,  287,  280,  280,  280,   54,
  278,   56,  278,   38,  278,   40,   41,   42,  300,  297,
  278,    0,  278,  297,  297,  297,    5,  306,  292,  278,
  312,  310,  296,  257,  292,  297,  292,  280,  296,  258,
  296,  257,   55,  258,  308,   58,  297,  305,  265,  266,
  308,  315,  308,  278,   99,   80,   81,  315,  103,  315,
  281,  282,  283,  290,  291,  290,  291,  270,  293,  294,
   95,  291,   97,  295,   87,  263,  301,  290,  291,  304,
  298,  306,  291,   96,  109,  310,  311,  100,  278,  314,
  291,  104,  105,  270,  291,  272,  273,  274,  275,  276,
  307,  126,  297,  293,  294,   43,   44,   45,   46,   47,
   48,  301,  125,  138,  304,  128,  129,  297,  131,  258,
  310,  311,  298,  148,   52,   53,  258,  140,  291,  277,
  155,  263,  267,  268,  269,  267,  268,  269,  270,  152,
  272,  273,  274,  275,  276,  277,  278,   49,   50,   51,
  291,  291,  263,  277,  291,  287,  314,  291,  290,  291,
  291,  293,  294,  270,  258,  258,  298,    5,  300,  301,
   58,  258,  304,   58,  306,  307,  263,   87,  310,  311,
  312,  135,  314,  270,  135,  272,  273,  274,  275,  276,
  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  287,   -1,   -1,  290,  291,   -1,  293,  294,   -1,   -1,
  258,  298,   -1,  300,  301,  263,   -1,  304,   -1,  306,
  307,   -1,   -1,  310,  311,  312,   -1,  314,   -1,  277,
  278,   -1,   -1,   -1,   -1,  258,   -1,   -1,   -1,  287,
  263,   -1,  290,  291,   -1,  293,  294,   -1,   -1,   -1,
  298,   -1,  300,  301,  277,  278,  304,   -1,  306,  307,
   -1,   -1,  310,  311,  312,   -1,  314,  290,  291,  100,
  293,  294,   -1,  104,  105,  298,   -1,   -1,  301,   -1,
   -1,  304,   -1,  306,  307,   -1,   -1,  310,  311,   -1,
   -1,  314,   -1,   -1,  125,   -1,   -1,  128,  129,   -1,
  131,   -1,   -1,   -1,  314,   -1,   -1,   -1,   -1,  140,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  152,
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
"FunctionDeclaration : FunctionKeyword Identifier LEFT_PAREN RIGHT_PAREN IS Body END",
"FunctionDeclaration : FunctionKeyword Identifier LEFT_PAREN ParameterDeclaration RIGHT_PAREN IS Body END",
"FunctionDeclaration : FunctionKeyword Identifier LEFT_PAREN Parameters RIGHT_PAREN IS Body END",
"FunctionDeclaration : FunctionKeyword Identifier LEFT_PAREN RIGHT_PAREN COLON Type IS Body END",
"FunctionDeclaration : FunctionKeyword Identifier LEFT_PAREN ParameterDeclaration RIGHT_PAREN COLON Type IS Body END",
"FunctionDeclaration : FunctionKeyword Identifier LEFT_PAREN Parameters RIGHT_PAREN COLON Type IS Body END",
"Parameters : ParameterDeclaration",
"Parameters : ParameterDeclaration COMMA Parameters",
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
"Statement : Print",
"Print : PRINT Expression",
"Return : RETURN Expression",
"Assignment : ModifiablePrimary EQUAL Expression",
"FunctionCall : IDENTIFIER LEFT_PAREN RIGHT_PAREN",
"FunctionCall : IDENTIFIER LEFT_PAREN ArgumentDeclaration RIGHT_PAREN",
"FunctionCall : IDENTIFIER LEFT_PAREN Arguments RIGHT_PAREN",
"Identifier : IDENTIFIER",
"Arguments : ArgumentDeclaration",
"Arguments : ArgumentDeclaration COMMA Arguments",
"ArgumentDeclaration : Expression",
"Expressions : Expression",
"Expressions : Expression COMMA Expressions",
"Expressions :",
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

//#line 243 "parser.y"
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
//#line 465 "Parser.java"
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
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(5).obj,new Node("parameters", null), val_peek(1).obj)));}
break;
case 12:
//#line 99 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(6).obj,new Node("arguments", null, Arrays.asList(val_peek(4).obj)), val_peek(1).obj)));}
break;
case 13:
//#line 100 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(6).obj, val_peek(4).obj, val_peek(1).obj)));}
break;
case 14:
//#line 101 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(7).obj,new Node("parameters", null, Arrays.asList(val_peek(8).obj)), val_peek(3).obj,val_peek(1).obj)));}
break;
case 15:
//#line 102 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, new Node("arguments", null, Arrays.asList(val_peek(6).obj)),val_peek(3).obj,val_peek(1).obj)));}
break;
case 16:
//#line 103 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, val_peek(6).obj,val_peek(3).obj,val_peek(1).obj)));}
break;
case 17:
//#line 107 "parser.y"
{yyval = new ParserVal(new Node("parameters", null, Arrays.asList(val_peek(0).obj)));}
break;
case 18:
//#line 108 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 19:
//#line 112 "parser.y"
{yyval = new ParserVal(new Node("parameter-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 20:
//#line 116 "parser.y"
{yyval = new ParserVal(new Node("type-integer", null));}
break;
case 21:
//#line 117 "parser.y"
{yyval = new ParserVal(new Node("type-real", null));}
break;
case 22:
//#line 118 "parser.y"
{yyval = new ParserVal(new Node("type-boolean", null));}
break;
case 23:
//#line 122 "parser.y"
{yyval = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
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
//#line 134 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 31:
//#line 135 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 32:
//#line 136 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 33:
//#line 137 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 34:
//#line 141 "parser.y"
{yyval = new ParserVal(new Node("print", null, Arrays.asList(val_peek(0).obj)));}
break;
case 35:
//#line 144 "parser.y"
{yyval = new ParserVal(new Node("return", null, Arrays.asList(val_peek(0).obj)));}
break;
case 36:
//#line 147 "parser.y"
{yyval = new ParserVal(new Node("assignment", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 37:
//#line 151 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(new Node(yylval.sval, null), new Node("arguments", null))));}
break;
case 38:
//#line 152 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(new Node(yylval.sval, null),new Node("arguments", null, Arrays.asList(val_peek(1).obj)))));}
break;
case 39:
//#line 153 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(new Node(yylval.sval, null), val_peek(1).obj)));}
break;
case 40:
//#line 156 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
case 41:
//#line 159 "parser.y"
{yyval = new ParserVal(new Node("arguments", null, Arrays.asList(val_peek(0).obj)));}
break;
case 42:
//#line 160 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 43:
//#line 164 "parser.y"
{yyval = new ParserVal(new Node("argument", null, Arrays.asList(val_peek(0).obj)));}
break;
case 44:
//#line 168 "parser.y"
{yyval = val_peek(0);}
break;
case 45:
//#line 169 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 47:
//#line 174 "parser.y"
{yyval = new ParserVal(new Node("while", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 48:
//#line 177 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 49:
//#line 178 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 50:
//#line 179 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 51:
//#line 180 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 52:
//#line 181 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 53:
//#line 184 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 54:
//#line 188 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 55:
//#line 189 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 56:
//#line 193 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 57:
//#line 194 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 58:
//#line 198 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 59:
//#line 199 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 60:
//#line 200 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 61:
//#line 201 "parser.y"
{yyval = val_peek(0);}
break;
case 62:
//#line 205 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 63:
//#line 206 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 64:
//#line 207 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 65:
//#line 208 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 66:
//#line 209 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 67:
//#line 210 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 68:
//#line 211 "parser.y"
{yyval = val_peek(0);}
break;
case 69:
//#line 215 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 70:
//#line 216 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 71:
//#line 217 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 72:
//#line 218 "parser.y"
{yyval = val_peek(0);}
break;
case 73:
//#line 222 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 74:
//#line 223 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 75:
//#line 224 "parser.y"
{yyval = val_peek(0);}
break;
case 76:
//#line 227 "parser.y"
{yyval = val_peek(0);}
break;
case 77:
//#line 228 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 78:
//#line 232 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 79:
//#line 233 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 80:
//#line 234 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 81:
//#line 235 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 82:
//#line 236 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 83:
//#line 239 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
//#line 914 "Parser.java"
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
