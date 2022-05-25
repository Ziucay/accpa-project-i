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
public final static short IMPORT=320;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    2,    2,    2,    2,    6,
    5,    9,   11,    3,    3,    3,    3,    3,    4,    4,
    4,   14,   14,   13,    8,    8,    8,    8,    8,    8,
    8,   12,   16,   16,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   24,   23,   18,   19,   19,   19,
   26,   26,   25,   27,   27,   27,   20,   28,   29,   30,
   31,   15,   21,   32,   32,   22,   22,   10,   10,   10,
   10,   10,   33,   33,   33,   33,   33,   33,   33,   34,
   34,   34,   34,   35,   35,   35,   36,   36,   37,   37,
   37,   37,   37,   37,    7,    7,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    1,    1,    2,
    4,    8,    4,    4,    6,   11,   12,   12,    9,   10,
   10,    1,    3,    3,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    0,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    2,    3,    3,    4,    4,
    1,    3,    1,    1,    3,    0,    5,    1,    1,    1,
    1,    1,    6,    4,    5,    5,    7,    3,    3,    3,
    1,    1,    3,    3,    3,    3,    3,    3,    1,    3,
    3,    3,    1,    3,    3,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
   62,    0,    0,    0,    0,    0,    0,    0,    6,    7,
    8,    9,    0,   95,    0,   96,    0,    0,   10,    1,
    3,    0,    0,    0,    0,    0,    0,    0,   92,   90,
   91,   93,   89,    0,    0,   72,    0,    0,    0,    0,
   87,   31,   27,   25,   26,   28,   30,   29,   11,    0,
    0,    0,    0,    0,    0,    0,    0,   13,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   88,
   48,   53,    0,    0,   68,   69,   70,    0,   77,   78,
   75,   76,   73,   74,   81,   80,   82,   85,   84,   15,
    0,    0,   24,    0,    0,   23,    0,   49,    0,   50,
    0,    0,    0,    0,    0,    0,    0,   52,    0,    0,
    0,    0,   59,   60,    0,    0,   58,   40,   41,    0,
   44,    0,   32,    0,   35,   36,   37,   38,   39,   42,
   43,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   45,   46,    0,   19,   33,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,    0,    0,    0,    0,   20,
   21,   16,    0,    0,    0,    0,    0,    0,    0,    0,
   17,   18,    0,   57,    0,    0,    0,   61,   66,    0,
    0,    0,   64,   63,    0,    0,   65,   67,   12,
};
final static short yydgoto[] = {                          6,
    7,    8,  128,  129,   11,   12,   34,   49,  131,   82,
   16,  132,   54,   55,   13,  133,  134,  135,   36,  137,
  138,  139,  140,  141,   83,   84,    0,  142,  143,  144,
  190,  168,   37,   38,   39,   40,   41,
};
final static short yysindex[] = {                      -216,
    0, -262, -262, -262, -290,    0, -289, -216,    0,    0,
    0,    0, -262,    0, -251,    0, -240, -233,    0,    0,
    0, -168,  191, -264, -264, -264, -182,  191,    0,    0,
    0,    0,    0, -128, -219,    0, -274, -155,  -95, -153,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -265,
 -248, -220, -183, -158, -160, -156,  194,    0,  191,  191,
  191,  191,  191,  191,  191,  191,  191,  191,  191,  191,
  191,  191,  191, -143, -264, -264, -194, -262, -149,    0,
    0,    0, -133, -123,    0,    0,    0, -116,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -171, -145,    0, -264, -117,    0, -264,    0,  191,    0,
 -162, -125,  -96,  204, -130, -112,  -83,    0,  -52, -107,
 -106, -262,    0,    0,  191,  191,    0,    0,    0, -229,
    0,  -78,    0,  204,    0,    0,    0,    0,    0,    0,
    0,  191, -262,  191,  204,  204,  204,  -39,  -36, -172,
    0,    0,  191,    0,    0,  -74, -236,  -81,  -62,  -57,
  -26,  204,  204, -264,    0,  204, -257,  -61,  204,    0,
    0,    0,  -22,  -17,  -24,  -44,  191,  -25,  204, -134,
    0,    0,    3,    0,  -15,  191,  -31,    0,    0,  204,
  191,  191,    0,    0,  -30,    4,    0,    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,  -50,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -79,    0,    0,  164,  104,   44,  -19,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -151,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -79,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,    0,    0,    0,    0,    0,
    0,    0,    0,  -21,    0,    0,   11,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -254,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -21,  -21,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   14,   14,    0,    0,  -21,    0,    0, -113,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -21,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -21,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  265,    0,   75,   91,    0,    0,    2,  -23,    0,  -16,
    0,  230,  -68,  -67,    0,  143,    0,    0,  -84,    0,
    0,    0,    0,    0,  169,  171,    0,    0,    0,    0,
    0,    0,  138,  148,   51,    0,    0,
};
final static int YYTABLESIZE=521;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
    2,   50,   51,   15,   17,   18,   35,   34,   23,  105,
  106,   56,   59,   42,   22,   14,   43,   44,   45,   23,
   14,   46,   19,   23,   20,   60,   23,   57,   53,  136,
   23,   73,  112,  113,   29,   34,   34,   61,   30,   25,
  153,   58,   85,   86,   87,   24,   26,  177,   74,  136,
   31,  102,  103,   47,   48,   32,  100,   33,  167,   75,
  136,  136,  136,   88,   88,   88,   88,   88,   88,   88,
   88,   88,   88,   88,    9,   52,   23,  136,  136,   53,
  115,  136,    9,  116,  136,  104,  111,   23,   27,    1,
   10,   23,    2,    3,  136,   14,   76,   79,   10,   77,
    4,   80,   53,    5,   78,  136,   14,  164,  151,  152,
   14,   71,   72,  101,   62,  130,   63,   64,   65,   66,
   67,   98,   99,  150,  108,  156,   14,  158,   57,  109,
  107,   23,  120,   14,  110,  130,  165,   78,   14,   14,
  175,   14,   14,   23,  157,   78,  130,  130,  130,   14,
  178,  114,   14,  119,   14,  188,  189,   14,   14,   14,
  185,  121,   14,  130,  130,   14,  145,  130,   14,  193,
  130,   68,   69,   70,  196,  197,   34,   34,   94,  109,
  130,   94,   94,   94,  146,   94,   94,   94,   94,   94,
   94,  130,   94,   94,   94,   94,   94,   94,   94,   89,
   90,   91,   92,   93,   94,   94,  147,   94,  148,  149,
   94,   94,  154,   94,   94,   95,   96,   97,   94,  162,
   94,   94,  163,  166,   94,  169,   94,   94,  170,   94,
   94,   94,   94,  171,   94,  172,  179,   94,   86,  181,
   94,   86,   86,   86,  182,  183,  184,   86,   86,   86,
   86,  186,   86,   86,   86,   86,   86,   86,   86,  194,
  198,  192,  191,    4,  199,   86,   22,   86,   51,   34,
   86,   86,   21,   86,   86,   34,  155,  117,   86,  118,
   86,   86,    0,    0,   86,    0,   86,   86,    0,   86,
   86,   86,   86,    0,   86,    0,    0,   86,    0,    0,
   86,   83,    0,    0,   83,   83,   83,    0,    0,    0,
    0,    0,    0,   83,    5,   83,   83,   83,   83,   83,
   83,   83,    0,    0,    0,    0,    0,    0,   83,    0,
   83,    0,    0,   83,   83,    0,   83,   83,    0,    0,
    0,   83,    0,   83,   83,    0,    0,   83,    0,   83,
   83,    0,   83,   83,   83,   83,    0,   83,    0,    0,
   83,   79,    0,   83,   79,   79,   79,    0,    0,    0,
    0,    0,    0,    0,  159,  160,  161,    0,    0,    0,
   79,   79,    0,    0,    0,    0,    0,    0,   79,    0,
   79,  173,  174,   79,   79,  176,   79,   79,  180,    0,
    0,   79,    0,   79,   79,    0,    0,   79,  187,   79,
   79,    0,   79,   79,   79,   79,    0,   79,    0,  195,
   79,   71,    0,   79,   71,   71,   71,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   71,   71,    0,    0,    0,    0,    0,   28,   71,    0,
   28,   81,    0,   71,   71,    0,   71,   71,    0,    0,
    0,   71,    0,    0,   71,    0,    0,   71,   14,   71,
   71,   14,   71,   71,   71,    0,    0,   71,    0,    0,
   71,   14,   29,   71,    0,   29,   30,    0,  122,   30,
    0,    0,    0,    0,    0,    0,  123,  124,   31,    0,
    0,   31,    0,   32,  125,   33,   32,  126,   33,    1,
    0,    0,    0,    3,  127,    0,    0,    0,    0,    0,
    4,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        257,
    0,   25,   26,    2,    3,    4,   23,  262,  260,   78,
   78,   28,  287,  278,   13,  278,  281,  282,  283,  260,
  278,  286,  313,  260,  314,  300,  260,  257,   27,  114,
  260,  297,  101,  101,  292,  290,  291,  312,  296,  280,
  270,  261,   59,   60,   61,  297,  280,  305,  297,  134,
  308,   75,   76,  318,  319,  313,   73,  315,  295,  280,
  145,  146,  147,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   71,   72,    0,  258,  260,  162,  163,   78,
  104,  166,    8,  107,  169,  280,  258,  260,  257,  306,
    0,  260,  309,  310,  179,  278,  280,  258,    8,  258,
  317,  258,  101,  320,  263,  190,  278,  280,  125,  126,
  262,  265,  266,  257,  270,  114,  272,  273,  274,  275,
  276,   71,   72,  122,  258,  142,  278,  144,  257,  263,
  280,  260,  258,  285,  258,  134,  153,  263,  290,  291,
  164,  293,  294,  260,  143,  263,  145,  146,  147,  301,
  167,  297,  304,  316,  306,  290,  291,  309,  310,  311,
  177,  258,  314,  162,  163,  317,  297,  166,  320,  186,
  169,  267,  268,  269,  191,  192,  290,  291,  258,  263,
  179,  261,  262,  263,  297,  265,  266,  267,  268,  269,
  270,  190,  272,  273,  274,  275,  276,  277,  278,   62,
   63,   64,   65,   66,   67,  285,  259,  287,  316,  316,
  290,  291,  291,  293,  294,   68,   69,   70,  298,  259,
  300,  301,  259,  298,  304,  307,  306,  307,  291,  309,
  310,  311,  312,  291,  314,  262,  298,  317,  258,  262,
  320,  261,  262,  263,  262,  270,  291,  267,  268,  269,
  270,  277,  272,  273,  274,  275,  276,  277,  278,  291,
  291,  277,  260,  314,  261,  285,  258,  287,  258,  291,
  290,  291,    8,  293,  294,  262,  134,  109,  298,  109,
  300,  301,   -1,   -1,  304,   -1,  306,  307,   -1,  309,
  310,  311,  312,   -1,  314,   -1,   -1,  317,   -1,   -1,
  320,  258,   -1,   -1,  261,  262,  263,   -1,   -1,   -1,
   -1,   -1,   -1,  270,  314,  272,  273,  274,  275,  276,
  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,  285,   -1,
  287,   -1,   -1,  290,  291,   -1,  293,  294,   -1,   -1,
   -1,  298,   -1,  300,  301,   -1,   -1,  304,   -1,  306,
  307,   -1,  309,  310,  311,  312,   -1,  314,   -1,   -1,
  317,  258,   -1,  320,  261,  262,  263,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  145,  146,  147,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,  285,   -1,
  287,  162,  163,  290,  291,  166,  293,  294,  169,   -1,
   -1,  298,   -1,  300,  301,   -1,   -1,  304,  179,  306,
  307,   -1,  309,  310,  311,  312,   -1,  314,   -1,  190,
  317,  258,   -1,  320,  261,  262,  263,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,   -1,  257,  285,   -1,
  257,  258,   -1,  290,  291,   -1,  293,  294,   -1,   -1,
   -1,  298,   -1,   -1,  301,   -1,   -1,  304,  278,  306,
  307,  278,  309,  310,  311,   -1,   -1,  314,   -1,   -1,
  317,  278,  292,  320,   -1,  292,  296,   -1,  285,  296,
   -1,   -1,   -1,   -1,   -1,   -1,  293,  294,  308,   -1,
   -1,  308,   -1,  313,  301,  315,  313,  304,  315,  306,
   -1,   -1,   -1,  310,  311,   -1,   -1,   -1,   -1,   -1,
  317,
};
}
final static short YYFINAL=6;
final static short YYMAXTOKEN=320;
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
"IMPORT",
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
"Line : UserType",
"Line : Import",
"Import : IMPORT STRING",
"UserType : TYPE ModifiablePrimary IS Type",
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
"Type : IDENTIFIER",
"Body : Statements",
"Statements : Statement Statements",
"Statements :",
"Statement : Assignment",
"Statement : FunctionCall",
"Statement : WhileLoop",
"Statement : ForLoop",
"Statement : IfStatement",
"Statement : VariableDeclaration",
"Statement : FunctionDeclaration",
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
"Expression : FunctionCall",
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

//#line 272 "parser.y"
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
//#line 538 "Parser.java"
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
//#line 92 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 7:
//#line 93 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 8:
//#line 94 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 11:
//#line 103 "parser.y"
{yyval = new ParserVal(new Node("type-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 12:
//#line 107 "parser.y"
{ yyval = new ParserVal(new Node("array-declaration", null, Arrays.asList(val_peek(6).obj, val_peek(4).obj, val_peek(1).obj)));}
break;
case 13:
//#line 111 "parser.y"
{yyval = new ParserVal(new Node("array-access", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 14:
//#line 115 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 15:
//#line 116 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj)));}
break;
case 16:
//#line 117 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(9).obj,new Node("parameters", null), val_peek(7).obj, val_peek(1).obj)));}
break;
case 17:
//#line 118 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(10).obj,new Node("parameters", null, Arrays.asList(val_peek(5).obj)),val_peek(8).obj, val_peek(1).obj)));}
break;
case 18:
//#line 119 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(10).obj,val_peek(5).obj, val_peek(8).obj, val_peek(1).obj)));}
break;
case 19:
//#line 123 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(7).obj,new Node("parameters", null, Arrays.asList(val_peek(8).obj)), val_peek(3).obj,val_peek(1).obj)));}
break;
case 20:
//#line 124 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, new Node("parameters", null, Arrays.asList(val_peek(6).obj)),val_peek(3).obj,val_peek(1).obj)));}
break;
case 21:
//#line 125 "parser.y"
{Collections.reverse(val_peek(6).obj.descendants);yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, val_peek(6).obj,val_peek(3).obj,val_peek(1).obj)));}
break;
case 22:
//#line 129 "parser.y"
{yyval = new ParserVal(new Node("parameters", null, Arrays.asList(val_peek(0).obj)));}
break;
case 23:
//#line 130 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 24:
//#line 134 "parser.y"
{yyval = new ParserVal(new Node("parameter-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 25:
//#line 138 "parser.y"
{yyval = new ParserVal(new Node("type-integer", null));}
break;
case 26:
//#line 139 "parser.y"
{yyval = new ParserVal(new Node("type-real", null));}
break;
case 27:
//#line 140 "parser.y"
{yyval = new ParserVal(new Node("type-boolean", null));}
break;
case 28:
//#line 141 "parser.y"
{yyval = new ParserVal(new Node("type-string", null));}
break;
case 29:
//#line 142 "parser.y"
{yyval = new ParserVal(new Node("type-void", null));}
break;
case 30:
//#line 143 "parser.y"
{yyval = new ParserVal(new Node("type-auto", null));}
break;
case 31:
//#line 144 "parser.y"
{yyval = new ParserVal(new Node("user-type", null));}
break;
case 32:
//#line 148 "parser.y"
{yyval = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
break;
case 35:
//#line 156 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 36:
//#line 157 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 37:
//#line 158 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 38:
//#line 159 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 39:
//#line 160 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 40:
//#line 161 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 41:
//#line 162 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 42:
//#line 163 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 43:
//#line 164 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 44:
//#line 165 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 45:
//#line 169 "parser.y"
{yyval = new ParserVal(new Node("print", null, Arrays.asList(val_peek(0).obj)));}
break;
case 46:
//#line 172 "parser.y"
{yyval = new ParserVal(new Node("return", null, Arrays.asList(val_peek(0).obj)));}
break;
case 47:
//#line 175 "parser.y"
{yyval = new ParserVal(new Node("assignment", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 48:
//#line 179 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(2).obj, new Node("arguments", null))));}
break;
case 49:
//#line 180 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj,new Node("arguments", null, Arrays.asList(val_peek(1).obj)))));}
break;
case 50:
//#line 181 "parser.y"
{Collections.reverse(val_peek(1).obj.descendants); yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 51:
//#line 185 "parser.y"
{yyval = new ParserVal(new Node("arguments", null, Arrays.asList(val_peek(0).obj)));}
break;
case 52:
//#line 186 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 53:
//#line 190 "parser.y"
{yyval = new ParserVal(new Node("argument", null, Arrays.asList(val_peek(0).obj)));}
break;
case 54:
//#line 194 "parser.y"
{yyval = val_peek(0);}
break;
case 55:
//#line 195 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 57:
//#line 200 "parser.y"
{yyval = new ParserVal(new Node("while", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 58:
//#line 203 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 59:
//#line 204 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 60:
//#line 205 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 61:
//#line 206 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 62:
//#line 207 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 63:
//#line 210 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 64:
//#line 214 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 65:
//#line 215 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 66:
//#line 219 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 67:
//#line 220 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 68:
//#line 224 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 69:
//#line 225 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 70:
//#line 226 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 71:
//#line 227 "parser.y"
{yyval = val_peek(0);}
break;
case 72:
//#line 228 "parser.y"
{yyval = val_peek(0);}
break;
case 73:
//#line 232 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 74:
//#line 233 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 75:
//#line 234 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 76:
//#line 235 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 77:
//#line 236 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 78:
//#line 237 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 79:
//#line 238 "parser.y"
{yyval = val_peek(0);}
break;
case 80:
//#line 242 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 81:
//#line 243 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 82:
//#line 244 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 83:
//#line 245 "parser.y"
{yyval = val_peek(0);}
break;
case 84:
//#line 249 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 85:
//#line 250 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 86:
//#line 251 "parser.y"
{yyval = val_peek(0);}
break;
case 87:
//#line 254 "parser.y"
{yyval = val_peek(0);}
break;
case 88:
//#line 255 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 89:
//#line 259 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 90:
//#line 260 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 91:
//#line 261 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 92:
//#line 262 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 93:
//#line 263 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, val_peek(0).sval));}
break;
case 94:
//#line 264 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 95:
//#line 267 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
case 96:
//#line 268 "parser.y"
{yyval = val_peek(0);}
break;
//#line 1031 "Parser.java"
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
