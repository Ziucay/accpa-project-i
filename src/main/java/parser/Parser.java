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
public final static short ARROW=316;
public final static short FUNC=317;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    2,    2,    5,    3,    3,
    3,    3,    4,    4,    4,    4,    4,    4,   10,   10,
   13,    7,    7,    7,   11,   14,   14,   15,   15,   15,
   15,   15,   15,   15,   15,   22,   21,   16,   17,   17,
   17,    6,   24,   24,   23,   25,   25,   25,   18,   26,
   27,   28,   29,   12,   19,   30,   30,   20,   20,    8,
    8,    8,    8,   31,   31,   31,   31,   31,   31,   31,
   32,   32,   32,   32,   33,   33,   33,   34,   34,   35,
   35,   35,   35,   35,    9,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    8,    4,    6,
    4,   12,    7,    8,    8,    9,   10,   10,    1,    3,
    3,    1,    1,    1,    1,    2,    0,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    2,    3,    3,    4,
    4,    1,    1,    3,    1,    1,    3,    0,    5,    1,
    1,    1,    1,    1,    6,    4,    5,    5,    7,    3,
    3,    3,    1,    3,    3,    3,    3,    3,    3,    1,
    3,    3,    3,    1,    3,    3,    1,    1,    3,    1,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
   54,    0,    0,    0,    0,    0,    6,    7,    0,   85,
    0,    0,    1,    3,   42,    0,    0,    0,    0,    0,
   24,   22,   23,    0,    0,   83,   81,   82,   80,   11,
   84,    0,    0,    0,    0,   78,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   10,   79,   60,   61,   62,   68,
   69,   66,   67,   64,   65,   72,   71,   73,   76,   75,
    0,    0,   51,   52,    0,    0,   50,   33,    0,    0,
   25,    0,   28,   29,   30,   31,   32,   34,   35,    0,
    0,    0,   21,    0,    0,    0,    0,   20,    0,    0,
    0,   36,   37,    0,    0,   13,   26,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   39,   45,    0,    0,
   38,    0,    0,    0,    0,    0,   15,    0,   14,    0,
   16,   40,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   44,   49,    0,    0,    0,   53,   58,
    0,   18,   17,    0,    0,   56,   55,    0,   12,   57,
   59,
};
final static short yydgoto[] = {                          4,
    5,    6,   88,    8,    0,   16,   24,  128,   31,   40,
   90,    9,  109,   91,   92,   93,   94,   95,   96,   97,
   98,   99,  129,  130,    0,  100,  101,  102,  161,  134,
   32,   33,   34,   35,   36,
};
final static short yysindex[] = {                      -260,
    0, -229, -229,    0, -296, -260,    0,    0, -209,    0,
 -272, -201,    0,    0,    0, -171, -267, -237, -267, -249,
    0,    0,    0, -217, -237,    0,    0,    0,    0,    0,
    0, -277,  -94, -177, -213,    0, -204, -263, -184, -157,
 -252, -237, -154, -237, -237, -237, -237, -237, -237, -237,
 -237, -237, -237, -237, -237, -237, -237, -146, -267, -168,
 -267, -261, -241, -229,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -229, -185,    0,    0, -237, -237,    0,    0, -227, -175,
    0, -168,    0,    0,    0,    0,    0,    0,    0, -237,
 -229, -237,    0, -267, -168, -267, -168,    0, -149, -141,
 -168,    0,    0, -245, -237,    0,    0, -179, -174, -187,
 -173, -164, -167, -162, -193, -159,    0,    0, -218, -123,
    0, -168, -220, -153, -168, -168,    0, -168,    0, -118,
    0,    0, -237,    0, -147, -237, -131, -168, -230, -143,
 -140, -168, -111,    0,    0, -124, -237, -135,    0,    0,
 -168,    0,    0, -105, -237,    0,    0, -133,    0,    0,
    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0,    0, -152,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   47,    0,    0,    0,    0,    0,    0,
    0,   69,   12,  -46, -103,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -130,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -259,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -130,    0, -130,    0,  -90,    0,
 -130,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -130,    0,    0, -226, -130,    0, -130,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -130,    0,    0,
    0,  -85,  -75,    0,    0,    0,    0,    0,    0,    0,
 -130,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  179,    0,   48,    0,    0,    0,  -17,  -18,    2,  -43,
  -30,    0,  166,   97,    0,    0,    0,    0,    0,    0,
    0,    0,   49,   50,    0,    0,    0,    0,    0,    0,
  171,   45,   17,    0,    0,
};
final static int YYTABLESIZE=386;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
    2,   37,   27,   11,   12,   63,   43,   17,   38,   44,
   64,   25,  127,   21,   22,   23,   59,   13,  104,   25,
  108,   39,   45,   65,   18,   67,   68,   69,   10,  114,
   27,   27,   10,   60,   46,  105,   25,  110,  106,  142,
   10,   82,  115,  103,  143,    1,   26,    7,   10,    2,
   27,   56,   57,    7,   26,  107,    3,   10,   27,  159,
  160,   89,   28,   27,   27,   39,  112,  113,   15,   29,
   28,   26,   79,   80,  122,   27,  124,   29,   19,   42,
  126,  118,   39,  120,  146,   20,  121,   28,  123,   53,
   54,   55,   58,   89,   29,   61,  131,   76,   77,   78,
   62,  145,  119,   66,  149,  150,   89,  151,   89,   10,
   81,  111,   89,   64,  147,  116,  125,  158,  132,  135,
  133,  164,  140,  136,   83,   84,  137,  156,  139,  138,
  168,  141,   85,   89,  144,   86,   89,   89,  166,   89,
  152,    2,   87,  155,  148,  157,  170,  162,    3,   89,
  163,  143,  165,   89,   77,  167,  169,  171,   77,   77,
   27,    4,   89,   77,   77,   77,   77,   19,   77,   77,
   77,   77,   77,   77,   77,   47,   27,   48,   49,   50,
   51,   52,   43,   77,   14,   41,   77,   77,  117,   77,
   77,  153,  154,    0,   77,    0,   77,   77,    0,    0,
   77,    0,   77,   77,    0,    0,   77,   77,   77,    0,
   77,   74,    0,   77,    0,   74,   74,   70,   71,   72,
   73,   74,   75,   74,    0,   74,   74,   74,   74,   74,
   74,   74,    0,    0,    0,    0,    0,    0,    0,    0,
   74,    0,    0,   74,   74,    0,   74,   74,    0,    0,
    0,   74,    0,   74,   74,    0,    0,   74,    0,   74,
   74,    0,    0,   74,   74,   74,    0,   74,    0,   70,
   74,    0,    0,   70,   70,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   70,   70,
    0,    0,    0,    0,    0,    0,    0,    0,   70,    0,
    0,   70,   70,    0,   70,   70,    0,    0,    9,   70,
    0,   70,   70,    0,    5,   70,    0,   70,   70,    0,
    0,   70,   70,   70,    9,   70,   63,    0,   70,    0,
   63,   63,    0,    0,    0,    0,    9,    9,    0,    9,
    9,    0,    0,    0,    0,   63,   63,    9,    0,    0,
    9,    0,    9,    0,    0,    0,    9,    9,   63,   63,
    9,   63,   63,    9,    0,    0,   63,    0,    0,   63,
    0,    0,   63,    0,   63,   63,    0,    0,   63,   63,
    0,    0,   63,    0,    0,   63,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         18,
    0,   19,  262,    2,    3,  258,   25,  280,  258,  287,
  263,  257,  258,  281,  282,  283,  280,  314,  280,  257,
   64,   20,  300,   42,  297,   44,   45,   46,  278,  257,
  290,  291,  278,  297,  312,  297,  257,   81,  280,  258,
  278,   59,  270,   61,  263,  306,  292,    0,  278,  310,
  296,  265,  266,    6,  292,  297,  317,  278,  296,  290,
  291,   60,  308,  290,  291,   64,   85,   86,  278,  315,
  308,  292,   56,   57,  105,  296,  107,  315,  280,  297,
  111,  100,   81,  102,  305,  257,  104,  308,  106,  267,
  268,  269,  297,   92,  315,  280,  115,   53,   54,   55,
  258,  132,  101,  258,  135,  136,  105,  138,  107,  278,
  257,  297,  111,  263,  133,  291,  258,  148,  298,  307,
  295,  152,  316,  297,  293,  294,  291,  146,  291,  297,
  161,  291,  301,  132,  258,  304,  135,  136,  157,  138,
  259,  310,  311,  291,  298,  277,  165,  291,  317,  148,
  291,  263,  277,  152,  258,  291,  262,  291,  262,  263,
  291,  314,  161,  267,  268,  269,  270,  258,  272,  273,
  274,  275,  276,  277,  278,  270,  262,  272,  273,  274,
  275,  276,  258,  287,    6,   20,  290,  291,   92,  293,
  294,  143,  143,   -1,  298,   -1,  300,  301,   -1,   -1,
  304,   -1,  306,  307,   -1,   -1,  310,  311,  312,   -1,
  314,  258,   -1,  317,   -1,  262,  263,   47,   48,   49,
   50,   51,   52,  270,   -1,  272,  273,  274,  275,  276,
  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  287,   -1,   -1,  290,  291,   -1,  293,  294,   -1,   -1,
   -1,  298,   -1,  300,  301,   -1,   -1,  304,   -1,  306,
  307,   -1,   -1,  310,  311,  312,   -1,  314,   -1,  258,
  317,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  287,   -1,
   -1,  290,  291,   -1,  293,  294,   -1,   -1,  262,  298,
   -1,  300,  301,   -1,  314,  304,   -1,  306,  307,   -1,
   -1,  310,  311,  312,  278,  314,  258,   -1,  317,   -1,
  262,  263,   -1,   -1,   -1,   -1,  290,  291,   -1,  293,
  294,   -1,   -1,   -1,   -1,  277,  278,  301,   -1,   -1,
  304,   -1,  306,   -1,   -1,   -1,  310,  311,  290,  291,
  314,  293,  294,  317,   -1,   -1,  298,   -1,   -1,  301,
   -1,   -1,  304,   -1,  306,  307,   -1,   -1,  310,  311,
   -1,   -1,  314,   -1,   -1,  317,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=317;
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
"VAR","WHILE","XOR","STRING","EOF","DOUBLE","ARROW","FUNC",
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
"ArrayDeclaration : ARRAY Identifier COLON Type EQUAL LEFT_SQUARE_BRACE Expression RIGHT_SQUARE_BRACE",
"VariableDeclaration : VAR ModifiablePrimary COLON Type",
"VariableDeclaration : VAR ModifiablePrimary COLON Type IS Expression",
"VariableDeclaration : VAR ModifiablePrimary IS Expression",
"VariableDeclaration : FUNC ModifiablePrimary COLON Type IS LEFT_PAREN Parameters RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE",
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
"FunctionCall : ModifiablePrimary LEFT_PAREN RIGHT_PAREN",
"FunctionCall : ModifiablePrimary LEFT_PAREN ArgumentDeclaration RIGHT_PAREN",
"FunctionCall : ModifiablePrimary LEFT_PAREN Arguments RIGHT_PAREN",
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

//#line 250 "parser.y"
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
//#line 487 "Parser.java"
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
//#line 89 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 7:
//#line 90 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 8:
//#line 94 "parser.y"
{ yyval = new ParserVal(new Node("array-declaration", null, Arrays.asList(val_peek(6).obj, val_peek(4).obj, val_peek(1).obj)));}
break;
case 9:
//#line 97 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 10:
//#line 98 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj)));}
break;
case 11:
//#line 99 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 12:
//#line 100 "parser.y"
{yyval = new ParserVal(new Node("function-variable-declaration", null, Arrays.asList(val_peek(10).obj, val_peek(8).obj)));}
break;
case 13:
//#line 104 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(5).obj,new Node("parameters", null), val_peek(1).obj)));}
break;
case 14:
//#line 105 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(6).obj,new Node("parameters", null, Arrays.asList(val_peek(4).obj)), val_peek(1).obj)));}
break;
case 15:
//#line 106 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(6).obj, val_peek(4).obj, val_peek(1).obj)));}
break;
case 16:
//#line 107 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(7).obj,new Node("parameters", null, Arrays.asList(val_peek(8).obj)), val_peek(3).obj,val_peek(1).obj)));}
break;
case 17:
//#line 108 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, new Node("parameters", null, Arrays.asList(val_peek(6).obj)),val_peek(3).obj,val_peek(1).obj)));}
break;
case 18:
//#line 109 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, val_peek(6).obj,val_peek(3).obj,val_peek(1).obj)));}
break;
case 19:
//#line 113 "parser.y"
{yyval = new ParserVal(new Node("parameters", null, Arrays.asList(val_peek(0).obj)));}
break;
case 20:
//#line 114 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 21:
//#line 118 "parser.y"
{yyval = new ParserVal(new Node("parameter-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 22:
//#line 122 "parser.y"
{yyval = new ParserVal(new Node("type-integer", null));}
break;
case 23:
//#line 123 "parser.y"
{yyval = new ParserVal(new Node("type-real", null));}
break;
case 24:
//#line 124 "parser.y"
{yyval = new ParserVal(new Node("type-boolean", null));}
break;
case 25:
//#line 128 "parser.y"
{yyval = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
break;
case 28:
//#line 136 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 29:
//#line 137 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 30:
//#line 138 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 31:
//#line 139 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 32:
//#line 140 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 33:
//#line 141 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 34:
//#line 142 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 35:
//#line 143 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 36:
//#line 147 "parser.y"
{yyval = new ParserVal(new Node("print", null, Arrays.asList(val_peek(0).obj)));}
break;
case 37:
//#line 150 "parser.y"
{yyval = new ParserVal(new Node("return", null, Arrays.asList(val_peek(0).obj)));}
break;
case 38:
//#line 153 "parser.y"
{yyval = new ParserVal(new Node("assignment", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 39:
//#line 157 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(2).obj, new Node("arguments", null))));}
break;
case 40:
//#line 158 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj,new Node("arguments", null, Arrays.asList(val_peek(1).obj)))));}
break;
case 41:
//#line 159 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 42:
//#line 163 "parser.y"
{yyval = new ParserVal(new Node(new String(yylval.sval), null));}
break;
case 43:
//#line 166 "parser.y"
{yyval = new ParserVal(new Node("arguments", null, Arrays.asList(val_peek(0).obj)));}
break;
case 44:
//#line 167 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 45:
//#line 171 "parser.y"
{yyval = new ParserVal(new Node("argument", null, Arrays.asList(val_peek(0).obj)));}
break;
case 46:
//#line 175 "parser.y"
{yyval = val_peek(0);}
break;
case 47:
//#line 176 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 49:
//#line 181 "parser.y"
{yyval = new ParserVal(new Node("while", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 50:
//#line 184 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 51:
//#line 185 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 52:
//#line 186 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 53:
//#line 187 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 54:
//#line 188 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 55:
//#line 191 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 56:
//#line 195 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 57:
//#line 196 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 58:
//#line 200 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 59:
//#line 201 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 60:
//#line 205 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 61:
//#line 206 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 62:
//#line 207 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 63:
//#line 208 "parser.y"
{yyval = val_peek(0);}
break;
case 64:
//#line 212 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 65:
//#line 213 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 66:
//#line 214 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 67:
//#line 215 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 68:
//#line 216 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 69:
//#line 217 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 70:
//#line 218 "parser.y"
{yyval = val_peek(0);}
break;
case 71:
//#line 222 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 72:
//#line 223 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 73:
//#line 224 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 74:
//#line 225 "parser.y"
{yyval = val_peek(0);}
break;
case 75:
//#line 229 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 76:
//#line 230 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 77:
//#line 231 "parser.y"
{yyval = val_peek(0);}
break;
case 78:
//#line 234 "parser.y"
{yyval = val_peek(0);}
break;
case 79:
//#line 235 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 80:
//#line 239 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 81:
//#line 240 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 82:
//#line 241 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 83:
//#line 242 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 84:
//#line 243 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 85:
//#line 246 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
//#line 944 "Parser.java"
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
