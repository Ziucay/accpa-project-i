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
public final static short AUTO=318;
public final static short VOID=319;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    2,    2,    5,    9,    3,
    3,    3,    3,    3,    4,    4,    4,   12,   12,   11,
    7,    7,    7,    7,    7,    7,   10,   14,   14,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   22,   21,
   16,   17,   17,   17,   24,   24,   23,   25,   25,   25,
   18,   26,   27,   28,   29,   13,   19,   30,   30,   20,
   20,    8,    8,    8,    8,   31,   31,   31,   31,   31,
   31,   31,   32,   32,   32,   32,   33,   33,   33,   34,
   34,   35,   35,   35,   35,   35,   35,    6,    6,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    8,    4,    4,
    6,   11,   12,   12,    9,   10,   10,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    1,    2,    0,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    2,    2,
    3,    3,    4,    4,    1,    3,    1,    1,    3,    0,
    5,    1,    1,    1,    1,    1,    6,    4,    5,    5,
    7,    3,    3,    3,    1,    3,    3,    3,    3,    3,
    3,    1,    3,    3,    3,    1,    3,    3,    1,    1,
    3,    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
   56,    0,    0,    0,    0,    0,    6,    7,    0,   88,
    0,   89,    0,    1,    3,    0,    0,    0,    0,    0,
    0,   85,   83,   84,   86,   82,    0,    0,    0,    0,
    0,    0,   80,   23,   21,   22,   24,   26,   25,    0,
    0,    0,    0,    0,    0,    0,    9,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   81,   62,
   63,   64,   70,   71,   68,   69,   66,   67,   74,   73,
   75,   78,   77,   11,    0,    0,   20,    0,    0,   19,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   53,   54,    0,    0,   52,   35,   38,    0,    0,
   27,    0,   30,   31,   32,   33,   34,   36,   37,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   39,   40,
    0,    0,   15,   28,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,   47,    0,    0,   41,    0,    0,
    0,    0,   16,   17,   12,    0,    0,    0,   43,    0,
   44,    0,    0,    0,    0,    0,   13,   14,    0,    0,
   46,   51,    0,    0,    0,   55,   60,    0,    0,    0,
   58,   57,    0,    0,   59,   61,    8,
};
final static short yydgoto[] = {                          4,
    5,    6,  107,    8,  108,   27,   40,  145,   12,  110,
   44,   45,    9,  111,  112,  113,  114,  115,  116,  117,
  118,  119,  146,  147,    0,  120,  121,  122,  178,  151,
   29,   30,   31,   32,   33,
};
final static short yysindex[] = {                      -233,
    0, -270, -270,    0, -291, -233,    0,    0, -270,    0,
 -244,    0, -241,    0,    0, -242, -214, -269, -269, -237,
 -214,    0,    0,    0,    0,    0, -223, -221, -255, -164,
 -139, -150,    0,    0,    0,    0,    0,    0,    0, -239,
 -225, -253, -234, -188, -173, -166,    0, -214, -214, -214,
 -214, -214, -214, -214, -214, -214, -214, -214, -214, -214,
 -214, -214, -162, -269, -269, -182, -270, -180,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -195, -193,    0, -269, -161,    0,
 -269, -203, -170, -134,  189, -155, -152, -112, -163, -160,
 -270,    0,    0, -214, -214,    0,    0,    0, -189, -141,
    0,  189,    0,    0,    0,    0,    0,    0,    0, -214,
 -270, -214,  189,  189,  189, -107, -104, -194,    0,    0,
 -248, -214,    0,    0, -140, -240, -147, -130, -128, -105,
  189,  189, -269,    0,    0, -167,  -92,    0,  189, -254,
 -122,  189,    0,    0,    0,  -75,  -67,  -74,    0, -214,
    0,  -94, -214,  -79,  189, -172,    0,    0,  -61,  -63,
    0,    0,  -73, -214,  -89,    0,    0,  189, -214, -214,
    0,    0,  -86,  -53,    0,    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0,    0, -103,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -84,    0,  145,   88,
   31,  -27,    0,    0,    0,    0,    0,    0,    0,  175,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -46,    0,
    0,    0,    0,    0,  -78,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -262,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -78,  -78,  -47,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -47,  -47,    0,    0,    0,    0,    0,    0,  -78,    0,
    0, -154,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -78,    0,    0,    0,    0,  -40,
    0,    0,    0,    0,    0,    0,    0,  -78,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  213,    0,   25,    0,    0,    2,  -12,  -15,    0,   -3,
  -11,   -5,    0,  109,    0,    0,    0,    0,    0,    0,
    0,    0,   64,   65,    0,    0,    0,    0,    0,    0,
  117,   75,   80,    0,    0,
};
final static int YYTABLESIZE=506;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
    2,   28,   21,   11,   13,   46,   41,   10,   21,  144,
   16,   34,   35,   36,   20,   17,   37,   17,   17,   17,
   42,   43,   14,   10,    7,   17,   64,   29,   29,   10,
    7,   48,   70,   71,   72,   18,   17,   22,   19,   47,
   10,   23,   21,   22,   49,   65,   84,   23,   38,   39,
  163,   86,   87,   24,  150,   89,   50,   62,   25,   24,
   26,   90,   92,   10,   25,   17,   26,  131,   43,   66,
   17,   63,    1,   93,   67,   96,    2,   22,   97,   94,
  132,   23,   10,    3,   68,  143,   43,   99,  129,  130,
  159,   69,   67,   24,   85,  160,  109,   88,   25,   91,
   26,   67,  128,   95,  135,   51,  137,   52,   53,   54,
   55,   56,   98,  109,   60,   61,  148,  176,  177,  138,
  139,  140,  136,  100,  109,  109,  109,   57,   58,   59,
  158,   79,   80,   81,  164,   29,   29,  156,  157,   82,
   83,  123,  109,  109,  124,  162,  125,  173,  166,  133,
  109,  141,  126,  109,  142,  127,  155,  149,  181,  152,
  153,  175,  154,  184,  185,  161,  109,   73,   74,   75,
   76,   77,   78,   87,  183,  165,   87,   87,   87,  109,
   87,   87,   87,   87,   87,   87,  167,   87,   87,   87,
   87,   87,   87,   87,  168,  169,  172,  174,  179,  160,
   87,  182,   87,  180,  186,   87,   87,  187,   87,   87,
    4,   18,   29,   87,   29,   87,   87,   45,   15,   87,
  134,   87,   87,  170,  171,   87,   87,   87,    0,   87,
   79,    0,   87,   79,   79,   79,    0,    0,    0,   79,
   79,   79,   79,    0,   79,   79,   79,   79,   79,   79,
   79,    0,    0,    0,    0,    0,    0,   79,    0,   79,
    0,    0,   79,   79,    0,   79,   79,    0,    0,    0,
   79,    0,   79,   79,    0,    0,   79,    0,   79,   79,
    0,    0,   79,   79,   79,    0,   79,    0,   76,   79,
    0,   76,   76,   76,    0,    0,    0,    0,    0,    0,
   76,    0,   76,   76,   76,   76,   76,   76,   76,    0,
    0,    0,    0,    0,    5,   76,    0,   76,    0,    0,
   76,   76,    0,   76,   76,    0,    0,    0,   76,    0,
   76,   76,    0,    0,   76,    0,   76,   76,    0,    0,
   76,   76,   76,    0,   76,   72,    0,   76,   72,   72,
   72,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   72,   72,    0,    0,    0,    0,
    0,    0,   72,    0,   72,    0,    0,   72,   72,    0,
   72,   72,    0,    0,    0,   72,    0,   72,   72,    0,
    0,   72,    0,   72,   72,    0,    0,   72,   72,   72,
    0,   72,   65,    0,   72,   65,   65,   65,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   65,   65,    0,    0,    0,    0,    0,    0,   65,
    0,    0,    0,    0,   65,   65,   10,   65,   65,    0,
    0,    0,   65,    0,    0,   65,    0,    0,   65,    0,
   65,   65,   10,    0,   65,   65,    0,    0,   65,   10,
    0,   65,    0,    0,   10,   10,   10,   10,   10,    0,
    0,    0,    0,  101,    0,   10,    0,    0,   10,    0,
   10,  102,  103,    0,   10,   10,    0,    0,   10,  104,
    0,   10,  105,    0,    0,    0,    0,    0,    2,  106,
    0,    0,    0,    0,    0,    3,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        262,
    0,   17,  257,    2,    3,   21,   19,  278,  257,  258,
    9,  281,  282,  283,  257,  260,  286,  260,  260,  260,
  258,   20,  314,  278,    0,  260,  280,  290,  291,  278,
    6,  287,   48,   49,   50,  280,  260,  292,  280,  261,
  278,  296,  257,  292,  300,  280,   62,  296,  318,  319,
  305,   64,   65,  308,  295,   67,  312,  297,  313,  308,
  315,   67,  258,  278,  313,  260,  315,  257,   67,  258,
  260,  297,  306,   85,  263,   88,  310,  292,   91,   85,
  270,  296,  278,  317,  258,  280,   85,  258,  104,  105,
  258,  258,  263,  308,  257,  263,   95,  280,  313,  280,
  315,  263,  101,  297,  120,  270,  122,  272,  273,  274,
  275,  276,  316,  112,  265,  266,  132,  290,  291,  123,
  124,  125,  121,  258,  123,  124,  125,  267,  268,  269,
  143,   57,   58,   59,  150,  290,  291,  141,  142,   60,
   61,  297,  141,  142,  297,  149,  259,  163,  152,  291,
  149,  259,  316,  152,  259,  316,  262,  298,  174,  307,
  291,  165,  291,  179,  180,  258,  165,   51,   52,   53,
   54,   55,   56,  258,  178,  298,  261,  262,  263,  178,
  265,  266,  267,  268,  269,  270,  262,  272,  273,  274,
  275,  276,  277,  278,  262,  270,  291,  277,  260,  263,
  285,  291,  287,  277,  291,  290,  291,  261,  293,  294,
  314,  258,  291,  298,  262,  300,  301,  258,    6,  304,
  112,  306,  307,  160,  160,  310,  311,  312,   -1,  314,
  258,   -1,  317,  261,  262,  263,   -1,   -1,   -1,  267,
  268,  269,  270,   -1,  272,  273,  274,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,  285,   -1,  287,
   -1,   -1,  290,  291,   -1,  293,  294,   -1,   -1,   -1,
  298,   -1,  300,  301,   -1,   -1,  304,   -1,  306,  307,
   -1,   -1,  310,  311,  312,   -1,  314,   -1,  258,  317,
   -1,  261,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,  274,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,   -1,  314,  285,   -1,  287,   -1,   -1,
  290,  291,   -1,  293,  294,   -1,   -1,   -1,  298,   -1,
  300,  301,   -1,   -1,  304,   -1,  306,  307,   -1,   -1,
  310,  311,  312,   -1,  314,  258,   -1,  317,  261,  262,
  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,
   -1,   -1,  285,   -1,  287,   -1,   -1,  290,  291,   -1,
  293,  294,   -1,   -1,   -1,  298,   -1,  300,  301,   -1,
   -1,  304,   -1,  306,  307,   -1,   -1,  310,  311,  312,
   -1,  314,  258,   -1,  317,  261,  262,  263,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,  285,
   -1,   -1,   -1,   -1,  290,  291,  262,  293,  294,   -1,
   -1,   -1,  298,   -1,   -1,  301,   -1,   -1,  304,   -1,
  306,  307,  278,   -1,  310,  311,   -1,   -1,  314,  285,
   -1,  317,   -1,   -1,  290,  291,  278,  293,  294,   -1,
   -1,   -1,   -1,  285,   -1,  301,   -1,   -1,  304,   -1,
  306,  293,  294,   -1,  310,  311,   -1,   -1,  314,  301,
   -1,  317,  304,   -1,   -1,   -1,   -1,   -1,  310,  311,
   -1,   -1,   -1,   -1,   -1,  317,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=319;
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
"VAR","WHILE","XOR","STRING","EOF","DOUBLE","ARROW","FUNC","AUTO","VOID",
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
"ArrayDeclaration : TYPE_ARRAY ModifiablePrimary COLON Type EQUAL LEFT_SQUARE_BRACE Expression RIGHT_SQUARE_BRACE",
"ArrayAccess : ModifiablePrimary LEFT_SQUARE_BRACE Expression RIGHT_SQUARE_BRACE",
"VariableDeclaration : VAR ModifiablePrimary COLON Type",
"VariableDeclaration : VAR ModifiablePrimary COLON Type IS Expression",
"VariableDeclaration : FUNC ModifiablePrimary COLON Type IS LEFT_PAREN RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE",
"VariableDeclaration : FUNC ModifiablePrimary COLON Type IS LEFT_PAREN ParameterDeclaration RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE",
"VariableDeclaration : FUNC ModifiablePrimary COLON Type IS LEFT_PAREN Parameters RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE",
"FunctionDeclaration : FunctionKeyword ModifiablePrimary LEFT_PAREN RIGHT_PAREN COLON Type IS Body END",
"FunctionDeclaration : FunctionKeyword ModifiablePrimary LEFT_PAREN ParameterDeclaration RIGHT_PAREN COLON Type IS Body END",
"FunctionDeclaration : FunctionKeyword ModifiablePrimary LEFT_PAREN Parameters RIGHT_PAREN COLON Type IS Body END",
"Parameters : ParameterDeclaration",
"Parameters : ParameterDeclaration COMMA Parameters",
"ParameterDeclaration : ModifiablePrimary COLON Type",
"Type : TYPE_INT",
"Type : TYPE_DOUBLE",
"Type : TYPE_BOOLEAN",
"Type : TYPE_STRING",
"Type : VOID",
"Type : AUTO",
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
"Statement : ArrayDeclaration",
"Print : PRINT Expression",
"Return : RETURN Expression",
"Assignment : ModifiablePrimary EQUAL Expression",
"FunctionCall : ModifiablePrimary LEFT_PAREN RIGHT_PAREN",
"FunctionCall : ModifiablePrimary LEFT_PAREN ArgumentDeclaration RIGHT_PAREN",
"FunctionCall : ModifiablePrimary LEFT_PAREN Arguments RIGHT_PAREN",
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
"Primary : STRING",
"Primary : ModifiablePrimary",
"ModifiablePrimary : IDENTIFIER",
"ModifiablePrimary : ArrayAccess",
};

//#line 258 "parser.y"
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
//#line 520 "Parser.java"
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
//#line 91 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 7:
//#line 92 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 8:
//#line 96 "parser.y"
{ yyval = new ParserVal(new Node("array-declaration", null, Arrays.asList(val_peek(6).obj, val_peek(4).obj, val_peek(1).obj)));}
break;
case 9:
//#line 100 "parser.y"
{yyval = new ParserVal(new Node("array-access", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 10:
//#line 104 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 11:
//#line 105 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj)));}
break;
case 12:
//#line 106 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(9).obj,new Node("parameters", null), val_peek(7).obj, val_peek(1).obj)));}
break;
case 13:
//#line 107 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(10).obj,new Node("parameters", null, Arrays.asList(val_peek(5).obj)),val_peek(8).obj, val_peek(1).obj)));}
break;
case 14:
//#line 108 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(10).obj,val_peek(5).obj, val_peek(8).obj, val_peek(1).obj)));}
break;
case 15:
//#line 112 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(7).obj,new Node("parameters", null, Arrays.asList(val_peek(8).obj)), val_peek(3).obj,val_peek(1).obj)));}
break;
case 16:
//#line 113 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, new Node("parameters", null, Arrays.asList(val_peek(6).obj)),val_peek(3).obj,val_peek(1).obj)));}
break;
case 17:
//#line 114 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, val_peek(6).obj,val_peek(3).obj,val_peek(1).obj)));}
break;
case 18:
//#line 118 "parser.y"
{yyval = new ParserVal(new Node("parameters", null, Arrays.asList(val_peek(0).obj)));}
break;
case 19:
//#line 119 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 20:
//#line 123 "parser.y"
{yyval = new ParserVal(new Node("parameter-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 21:
//#line 127 "parser.y"
{yyval = new ParserVal(new Node("type-integer", null));}
break;
case 22:
//#line 128 "parser.y"
{yyval = new ParserVal(new Node("type-real", null));}
break;
case 23:
//#line 129 "parser.y"
{yyval = new ParserVal(new Node("type-boolean", null));}
break;
case 24:
//#line 130 "parser.y"
{yyval = new ParserVal(new Node("type-string", null));}
break;
case 25:
//#line 131 "parser.y"
{yyval = new ParserVal(new Node("type-void", null));}
break;
case 26:
//#line 132 "parser.y"
{yyval = new ParserVal(new Node("type-auto", null));}
break;
case 27:
//#line 136 "parser.y"
{yyval = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
break;
case 30:
//#line 144 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 31:
//#line 145 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 32:
//#line 146 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 33:
//#line 147 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 34:
//#line 148 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 35:
//#line 149 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 36:
//#line 150 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 37:
//#line 151 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 38:
//#line 152 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 39:
//#line 156 "parser.y"
{yyval = new ParserVal(new Node("print", null, Arrays.asList(val_peek(0).obj)));}
break;
case 40:
//#line 159 "parser.y"
{yyval = new ParserVal(new Node("return", null, Arrays.asList(val_peek(0).obj)));}
break;
case 41:
//#line 162 "parser.y"
{yyval = new ParserVal(new Node("assignment", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 42:
//#line 166 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(2).obj, new Node("arguments", null))));}
break;
case 43:
//#line 167 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj,new Node("arguments", null, Arrays.asList(val_peek(1).obj)))));}
break;
case 44:
//#line 168 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 45:
//#line 172 "parser.y"
{yyval = new ParserVal(new Node("arguments", null, Arrays.asList(val_peek(0).obj)));}
break;
case 46:
//#line 173 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 47:
//#line 177 "parser.y"
{yyval = new ParserVal(new Node("argument", null, Arrays.asList(val_peek(0).obj)));}
break;
case 48:
//#line 181 "parser.y"
{yyval = val_peek(0);}
break;
case 49:
//#line 182 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 51:
//#line 187 "parser.y"
{yyval = new ParserVal(new Node("while", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 52:
//#line 190 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 53:
//#line 191 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 54:
//#line 192 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 55:
//#line 193 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 56:
//#line 194 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 57:
//#line 197 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 58:
//#line 201 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 59:
//#line 202 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 60:
//#line 206 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 61:
//#line 207 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 62:
//#line 211 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 63:
//#line 212 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 64:
//#line 213 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 65:
//#line 214 "parser.y"
{yyval = val_peek(0);}
break;
case 66:
//#line 218 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 67:
//#line 219 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 68:
//#line 220 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 69:
//#line 221 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 70:
//#line 222 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 71:
//#line 223 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 72:
//#line 224 "parser.y"
{yyval = val_peek(0);}
break;
case 73:
//#line 228 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 74:
//#line 229 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 75:
//#line 230 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 76:
//#line 231 "parser.y"
{yyval = val_peek(0);}
break;
case 77:
//#line 235 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 78:
//#line 236 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 79:
//#line 237 "parser.y"
{yyval = val_peek(0);}
break;
case 80:
//#line 240 "parser.y"
{yyval = val_peek(0);}
break;
case 81:
//#line 241 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 82:
//#line 245 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 83:
//#line 246 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 84:
//#line 247 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 85:
//#line 248 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 86:
//#line 249 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, val_peek(0).sval));}
break;
case 87:
//#line 250 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 88:
//#line 253 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
case 89:
//#line 254 "parser.y"
{yyval = val_peek(0);}
break;
//#line 993 "Parser.java"
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
