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
    4,   15,   15,   14,    8,    8,    8,    8,    8,    8,
    8,   13,   17,   17,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   25,   24,   19,   20,   20,   20,
   27,   27,   26,   28,   28,   28,   21,   29,   30,   31,
   32,   16,   12,   22,   33,   33,   23,   23,   10,   10,
   10,   10,   10,   34,   34,   34,   34,   34,   34,   34,
   35,   35,   35,   35,   36,   36,   36,   37,   37,   38,
   38,   38,   38,   38,   38,    7,    7,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    1,    1,    2,
    4,    8,    4,    4,    6,   11,   12,   12,    9,   10,
   10,    1,    3,    3,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    0,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    2,    3,    3,    4,    4,
    1,    3,    1,    1,    3,    0,    5,    1,    1,    1,
    1,    1,    1,    6,    4,    5,    5,    7,    3,    3,
    3,    1,    1,    3,    3,    3,    3,    3,    3,    1,
    3,    3,    3,    1,    3,    3,    1,    1,    3,    1,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
   62,    0,    0,   63,    0,    0,    0,    0,    6,    7,
    8,    9,    0,    0,   96,    0,   97,    0,   10,    1,
    3,    0,    0,    0,    0,    0,    0,    0,    0,   93,
   91,   92,   94,   90,    0,    0,   73,    0,    0,    0,
    0,   88,   31,   27,   25,   26,   28,   30,   29,   11,
    0,    0,    0,    0,    0,    0,    0,    0,   13,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   89,   48,   53,    0,    0,   69,   70,   71,    0,   78,
   79,   76,   77,   74,   75,   82,   81,   83,   86,   85,
   15,    0,    0,   24,    0,    0,   23,    0,   49,    0,
   50,    0,    0,    0,    0,    0,    0,    0,   52,    0,
    0,    0,    0,   59,   60,    0,    0,   58,   40,   41,
    0,   44,    0,   32,    0,   35,   36,   37,   38,   39,
   42,   43,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   45,   46,    0,   19,   33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   47,    0,    0,    0,    0,
   20,   21,   16,    0,    0,    0,    0,    0,    0,    0,
    0,   17,   18,    0,   57,    0,    0,    0,   61,   67,
    0,    0,    0,   65,   64,    0,    0,   66,   68,   12,
};
final static short yydgoto[] = {                          6,
    7,    8,  129,  130,   11,   12,   35,   50,  132,   83,
   17,   13,  133,   55,   56,   14,  134,  135,  136,   37,
  138,  139,  140,  141,  142,   84,   85,    0,  143,  144,
  145,  191,  169,   38,   39,   40,   41,   42,
};
final static short yysindex[] = {                      -217,
    0, -242, -242,    0, -253,    0, -236, -217,    0,    0,
    0,    0, -242, -242,    0, -260,    0, -239,    0,    0,
    0, -237, -207, -254,  213,  213,  213, -230, -254,    0,
    0,    0,    0,    0, -166, -174,    0, -278,  100, -145,
 -246,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -195, -192, -167, -231, -181, -142, -132,  196,    0, -254,
 -254, -254, -254, -254, -254, -254, -254, -254, -254, -254,
 -254, -254, -254, -254, -127,  213,  213, -144, -242, -141,
    0,    0,    0, -168, -111,    0,    0,    0, -107,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -226, -134,    0,  213, -108,    0,  213,    0, -254,
    0, -148, -157,  -85,  229, -123, -122,  -82,    0,  -81,
 -137, -130, -242,    0,    0, -254, -254,    0,    0,    0,
 -243,    0,  -83,    0,  229,    0,    0,    0,    0,    0,
    0,    0, -254, -242, -254,  229,  229,  229,  -48,  -45,
 -225,    0,    0, -254,    0,    0,  -88, -248,  -90,  -73,
  -72,  -41,  229,  229,  213,    0,  229,  193,  -74,  229,
    0,    0,    0,  -37,  -35,  -40,  -56, -254,  -39,  229,
 -182,    0,    0,  -23,    0,  -36, -254,  -44,    0,    0,
  229, -254, -254,    0,    0,  -43,  -15,    0,    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,  -61,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -78,    0,    0,  166,  106,   46,
  -18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -150,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -78,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,
    0,    0,    0,    0,  -29,    0,    0,    5,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -251,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -29,  -29,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    4,    4,    0,    0,  -29,    0,    0, -171,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -29,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  256,    0,   18,   76,    0,    0,    2,  -20,    0,  -16,
    0,    0,  -49,  -77,  -69,    0,  130,    0,    0,  -84,
    0,    0,    0,    0,    0,  158,  160,    0,    0,    0,
    0,    0,    0,  138,   63,   85,    0,    0,
};
final static int YYTABLESIZE=546;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         24,
    2,  106,   29,   16,   18,   51,   52,   36,   60,  107,
   34,   24,   57,   58,   22,   23,   24,    9,   72,   73,
   24,   61,   24,   15,  113,    9,  154,   53,   24,   54,
  137,  112,  114,   62,   24,   15,   25,   30,   34,   34,
   26,   31,   27,   86,   87,   88,  168,   15,   77,   28,
  137,   15,   24,   32,  165,  103,  104,  101,   33,   19,
   34,  137,  137,  137,   89,   89,   89,   89,   89,   89,
   89,   89,   89,   89,   89,   10,   78,   20,  137,  137,
   54,   79,  137,   10,  116,  137,   59,  117,    1,  109,
   58,    2,    3,   24,  110,  137,  160,  161,  162,    4,
  121,   74,    5,   54,   75,   79,  137,  189,  190,  152,
  153,   14,   76,  174,  175,   80,  131,  177,   34,   34,
  181,   69,   70,   71,  151,   81,  157,   14,  159,  102,
  188,   96,   97,   98,   14,  105,  131,  166,  108,   14,
   14,  196,   14,   14,  176,  158,  111,  131,  131,  131,
   14,  179,   24,   14,   79,   14,   99,  100,   14,   14,
   14,  186,  115,   14,  131,  131,   14,  120,  131,   14,
  194,  131,  122,  146,  147,  197,  198,  148,  149,   95,
  110,  131,   95,   95,   95,  150,   95,   95,   95,   95,
   95,   95,  131,   95,   95,   95,   95,   95,   95,   95,
   90,   91,   92,   93,   94,   95,   95,  155,   95,  167,
  163,   95,   95,  164,   95,   95,  170,  171,  172,   95,
  173,   95,   95,  180,  182,   95,  183,   95,   95,  184,
   95,   95,   95,   95,  185,   95,  192,  187,   95,   87,
  193,   95,   87,   87,   87,  200,  195,  199,   87,   87,
   87,   87,    4,   87,   87,   87,   87,   87,   87,   87,
   22,   34,   51,   21,  156,   34,   87,  118,   87,  119,
    0,   87,   87,    0,   87,   87,    0,    0,    0,   87,
    0,   87,   87,    0,    0,   87,    0,   87,   87,    0,
   87,   87,   87,   87,    0,   87,    0,    0,   87,    0,
    0,   87,    0,   84,    0,    0,   84,   84,   84,    0,
    0,    0,    0,    0,    5,   84,    0,   84,   84,   84,
   84,   84,   84,   84,    0,    0,    0,    0,    0,    0,
   84,    0,   84,    0,    0,   84,   84,    0,   84,   84,
    0,    0,    0,   84,    0,   84,   84,    0,    0,   84,
    0,   84,   84,    0,   84,   84,   84,   84,    0,   84,
    0,    0,   84,   80,    0,   84,   80,   80,   80,   63,
    0,   64,   65,   66,   67,   68,    0,    0,    0,    0,
    0,    0,   80,   80,    0,    0,    0,    0,    0,    0,
   80,    0,   80,    0,    0,   80,   80,    0,   80,   80,
    0,    0,    0,   80,    0,   80,   80,    0,    0,   80,
    0,   80,   80,    0,   80,   80,   80,   80,    0,   80,
    0,    0,   80,   72,    0,   80,   72,   72,   72,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   72,   72,    0,    0,    0,    0,    0,   29,
   72,    0,   29,   82,    0,   72,   72,    0,   72,   72,
    0,    0,    0,   72,    0,    0,   72,    0,    0,   72,
   15,   72,   72,   15,   72,   72,   72,    0,    0,   72,
    0,    0,   72,    0,   30,   72,    0,   30,   31,    0,
   43,   31,    0,   44,   45,   46,    0,  178,   47,    0,
   32,    0,    0,   32,    0,   33,   15,   34,   33,    0,
   34,    0,    0,  123,    0,    0,    0,    0,    0,    0,
    0,  124,  125,    0,    0,    0,    0,    0,    0,  126,
   48,   49,  127,    0,    1,    0,    0,    0,    3,  128,
    0,    0,    0,    0,    0,    4,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        260,
    0,   79,  257,    2,    3,   26,   27,   24,  287,   79,
  262,  260,   29,  257,   13,   14,  260,    0,  265,  266,
  260,  300,  260,  278,  102,    8,  270,  258,  260,   28,
  115,  258,  102,  312,  260,  278,  297,  292,  290,  291,
  280,  296,  280,   60,   61,   62,  295,  278,  280,  257,
  135,  278,  260,  308,  280,   76,   77,   74,  313,  313,
  315,  146,  147,  148,   63,   64,   65,   66,   67,   68,
   69,   70,   71,   72,   73,    0,  258,  314,  163,  164,
   79,  263,  167,    8,  105,  170,  261,  108,  306,  258,
  257,  309,  310,  260,  263,  180,  146,  147,  148,  317,
  258,  297,  320,  102,  297,  263,  191,  290,  291,  126,
  127,  262,  280,  163,  164,  258,  115,  167,  290,  291,
  170,  267,  268,  269,  123,  258,  143,  278,  145,  257,
  180,   69,   70,   71,  285,  280,  135,  154,  280,  290,
  291,  191,  293,  294,  165,  144,  258,  146,  147,  148,
  301,  168,  260,  304,  263,  306,   72,   73,  309,  310,
  311,  178,  297,  314,  163,  164,  317,  316,  167,  320,
  187,  170,  258,  297,  297,  192,  193,  259,  316,  258,
  263,  180,  261,  262,  263,  316,  265,  266,  267,  268,
  269,  270,  191,  272,  273,  274,  275,  276,  277,  278,
   63,   64,   65,   66,   67,   68,  285,  291,  287,  298,
  259,  290,  291,  259,  293,  294,  307,  291,  291,  298,
  262,  300,  301,  298,  262,  304,  262,  306,  307,  270,
  309,  310,  311,  312,  291,  314,  260,  277,  317,  258,
  277,  320,  261,  262,  263,  261,  291,  291,  267,  268,
  269,  270,  314,  272,  273,  274,  275,  276,  277,  278,
  258,  291,  258,    8,  135,  262,  285,  110,  287,  110,
   -1,  290,  291,   -1,  293,  294,   -1,   -1,   -1,  298,
   -1,  300,  301,   -1,   -1,  304,   -1,  306,  307,   -1,
  309,  310,  311,  312,   -1,  314,   -1,   -1,  317,   -1,
   -1,  320,   -1,  258,   -1,   -1,  261,  262,  263,   -1,
   -1,   -1,   -1,   -1,  314,  270,   -1,  272,  273,  274,
  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,
  285,   -1,  287,   -1,   -1,  290,  291,   -1,  293,  294,
   -1,   -1,   -1,  298,   -1,  300,  301,   -1,   -1,  304,
   -1,  306,  307,   -1,  309,  310,  311,  312,   -1,  314,
   -1,   -1,  317,  258,   -1,  320,  261,  262,  263,  270,
   -1,  272,  273,  274,  275,  276,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,
  285,   -1,  287,   -1,   -1,  290,  291,   -1,  293,  294,
   -1,   -1,   -1,  298,   -1,  300,  301,   -1,   -1,  304,
   -1,  306,  307,   -1,  309,  310,  311,  312,   -1,  314,
   -1,   -1,  317,  258,   -1,  320,  261,  262,  263,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,  257,
  285,   -1,  257,  258,   -1,  290,  291,   -1,  293,  294,
   -1,   -1,   -1,  298,   -1,   -1,  301,   -1,   -1,  304,
  278,  306,  307,  278,  309,  310,  311,   -1,   -1,  314,
   -1,   -1,  317,   -1,  292,  320,   -1,  292,  296,   -1,
  278,  296,   -1,  281,  282,  283,   -1,  305,  286,   -1,
  308,   -1,   -1,  308,   -1,  313,  278,  315,  313,   -1,
  315,   -1,   -1,  285,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  293,  294,   -1,   -1,   -1,   -1,   -1,   -1,  301,
  318,  319,  304,   -1,  306,   -1,   -1,   -1,  310,  311,
   -1,   -1,   -1,   -1,   -1,  317,
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
"VariableDeclaration : FuncKeyword ModifiablePrimary COLON Type IS LEFT_PAREN RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE",
"VariableDeclaration : FuncKeyword ModifiablePrimary COLON Type IS LEFT_PAREN ParameterDeclaration RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE",
"VariableDeclaration : FuncKeyword ModifiablePrimary COLON Type IS LEFT_PAREN Parameters RIGHT_PAREN ARROW LEFT_BRACE Body RIGHT_BRACE",
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
"FuncKeyword : FUNC",
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

//#line 273 "parser.y"
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
//#line 543 "Parser.java"
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
{yyval = new ParserVal(new Node(yylval.sval, null));}
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
//#line 208 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 64:
//#line 211 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 65:
//#line 215 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 66:
//#line 216 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 67:
//#line 220 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 68:
//#line 221 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 69:
//#line 225 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 70:
//#line 226 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 71:
//#line 227 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 72:
//#line 228 "parser.y"
{yyval = val_peek(0);}
break;
case 73:
//#line 229 "parser.y"
{yyval = val_peek(0);}
break;
case 74:
//#line 233 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 75:
//#line 234 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 76:
//#line 235 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 77:
//#line 236 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 78:
//#line 237 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 79:
//#line 238 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 80:
//#line 239 "parser.y"
{yyval = val_peek(0);}
break;
case 81:
//#line 243 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 82:
//#line 244 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 83:
//#line 245 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 84:
//#line 246 "parser.y"
{yyval = val_peek(0);}
break;
case 85:
//#line 250 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 86:
//#line 251 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 87:
//#line 252 "parser.y"
{yyval = val_peek(0);}
break;
case 88:
//#line 255 "parser.y"
{yyval = val_peek(0);}
break;
case 89:
//#line 256 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 90:
//#line 260 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 91:
//#line 261 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 92:
//#line 262 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 93:
//#line 263 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 94:
//#line 264 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, val_peek(0).sval));}
break;
case 95:
//#line 265 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 96:
//#line 268 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
case 97:
//#line 269 "parser.y"
{yyval = val_peek(0);}
break;
//#line 1040 "Parser.java"
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
