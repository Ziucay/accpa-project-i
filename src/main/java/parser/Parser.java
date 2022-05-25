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
    7,    7,    7,    7,    7,   10,   14,   14,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   22,   21,   16,
   17,   17,   17,   24,   24,   23,   25,   25,   25,   18,
   26,   27,   28,   29,   13,   19,   30,   30,   20,   20,
    8,    8,    8,    8,   31,   31,   31,   31,   31,   31,
   31,   32,   32,   32,   32,   33,   33,   33,   34,   34,
   35,   35,   35,   35,   35,   35,    6,    6,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    8,    4,    4,
    6,   11,   12,   12,    9,   10,   10,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    2,    0,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    2,    2,    3,
    3,    4,    4,    1,    3,    1,    1,    3,    0,    5,
    1,    1,    1,    1,    1,    6,    4,    5,    5,    7,
    3,    3,    3,    1,    3,    3,    3,    3,    3,    3,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
   55,    0,    0,    0,    0,    0,    6,    7,    0,   87,
    0,   88,    0,    1,    3,    0,    0,    0,    0,    0,
    0,   84,   82,   83,   85,   81,    0,    0,    0,    0,
    0,    0,   79,   23,   21,   22,   25,   24,    0,    0,
    0,    0,    0,    0,    0,    9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   80,   61,   62,
   63,   69,   70,   67,   68,   65,   66,   73,   72,   74,
   77,   76,   11,    0,    0,   20,    0,    0,   19,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   52,   53,    0,    0,   51,   34,   37,    0,    0,   26,
    0,   29,   30,   31,   32,   33,   35,   36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   38,   39,    0,
    0,   15,   27,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   41,   46,    0,    0,   40,    0,    0,    0,
    0,   16,   17,   12,    0,    0,    0,   42,    0,   43,
    0,    0,    0,    0,    0,   13,   14,    0,    0,   45,
   50,    0,    0,    0,   54,   59,    0,    0,    0,   57,
   56,    0,    0,   58,   60,    8,
};
final static short yydgoto[] = {                          4,
    5,    6,  106,    8,  107,   27,   39,  144,   12,  109,
   43,   44,    9,  110,  111,  112,  113,  114,  115,  116,
  117,  118,  145,  146,    0,  119,  120,  121,  177,  150,
   29,   30,   31,   32,   33,
};
final static short yysindex[] = {                      -225,
    0, -261, -261,    0, -283, -225,    0,    0, -261,    0,
 -251,    0, -250,    0,    0, -202,  103, -269, -269, -240,
  103,    0,    0,    0,    0,    0, -215, -174, -284, -165,
 -170, -130,    0,    0,    0,    0,    0,    0, -206, -203,
 -157, -241, -243, -146, -143,    0,  103,  103,  103,  103,
  103,  103,  103,  103,  103,  103,  103,  103,  103,  103,
  103, -128, -269, -269, -153, -261, -149,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -235, -160,    0, -269, -124,    0, -269,
 -171, -179, -112,  143, -145, -141, -111, -159, -156, -261,
    0,    0,  103,  103,    0,    0,    0, -194, -132,    0,
  143,    0,    0,    0,    0,    0,    0,    0,  103, -261,
  103,  143,  143,  143,  -98,  -97, -233,    0,    0, -175,
  103,    0,    0, -133, -260, -121, -117, -116,  -68,  143,
  143, -269,    0,    0, -168,  -63,    0,  143, -164, -102,
  143,    0,    0,    0,  -65,  -64,  -71,    0,  103,    0,
  -90,  103,  -74,  143, -219,    0,    0,  -56,  -53,    0,
    0,  -70,  103,  -80,    0,    0,  143,  103,  103,    0,
    0,  -79,  -47,    0,    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0,    0,  -96,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -85,    0, -237,   86,
   29,  -28,    0,    0,    0,    0,    0,    0,  116,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -41,    0,    0,
    0,    0,    0,  -67,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -254,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -67,  -67,  -42,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -42,
  -42,    0,    0,    0,    0,    0,    0,  -67,    0,    0,
 -190,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -67,    0,    0,    0,    0,  -35,    0,
    0,    0,    0,    0,    0,    0,  -67,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  222,    0,   59,    0,    0,    2,  -12,  -15,    0,  291,
  -24,  -22,    0,  120,    0,    0,    0,    0,    0,    0,
    0,    0,   77,   78,    0,    0,    0,    0,    0,    0,
  117,   62,   95,    0,    0,
};
final static int YYTABLESIZE=468;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
    2,   28,   47,   11,   13,   45,   40,   28,   17,   17,
   16,   34,   35,   36,   65,   48,   10,   41,   17,   66,
   64,   42,   91,   64,   64,   64,   17,   49,   18,   19,
   14,   69,   70,   71,  149,   28,   28,   10,   64,   64,
   64,   88,   10,   89,   17,   83,  142,   64,   37,   38,
   85,   86,   64,   64,   20,   64,   64,   17,    7,   92,
   64,   93,  130,   64,    7,   17,   64,   42,   64,   64,
  175,  176,   64,   64,   95,  131,   64,   96,   98,   64,
    1,   21,  143,   66,    2,   42,   46,  128,  129,  158,
   61,    3,   21,   62,  159,  108,   56,   57,   58,   28,
   28,  127,   10,  134,   50,  136,   51,   52,   53,   54,
   55,   67,  108,   10,   68,  147,   22,   78,   79,   80,
   23,  135,   63,  108,  108,  108,   87,   22,   84,  157,
   90,   23,   24,  163,   59,   60,   94,   25,   66,   26,
  162,  108,  108,   24,   97,   99,  172,  124,   25,  108,
   26,  122,  108,   81,   82,  123,  125,  180,  132,  126,
  140,  141,  183,  184,  148,  108,   72,   73,   74,   75,
   76,   77,   86,  152,  153,   86,   86,   86,  108,   86,
   86,   86,   86,   86,   86,  151,   86,   86,   86,   86,
   86,   86,   86,  154,  160,  164,  166,  167,  168,   86,
  171,   86,  173,  178,   86,   86,  179,   86,   86,  159,
  181,  185,   86,  186,   86,   86,   18,    4,   86,   28,
   86,   86,   44,   28,   86,   86,   86,   15,   86,   78,
  133,   86,   78,   78,   78,  169,  170,    0,   78,   78,
   78,   78,    0,   78,   78,   78,   78,   78,   78,   78,
    0,    0,    0,    0,    0,    0,   78,    0,   78,    0,
    0,   78,   78,    0,   78,   78,    0,    0,    0,   78,
    0,   78,   78,    0,    0,   78,    0,   78,   78,    0,
    0,   78,   78,   78,    0,   78,   75,    0,   78,   75,
   75,   75,    0,    0,    0,    0,    0,    0,   75,    0,
   75,   75,   75,   75,   75,   75,   75,    0,    0,    0,
    0,    0,    0,   75,    5,   75,    0,    0,   75,   75,
    0,   75,   75,    0,    0,    0,   75,    0,   75,   75,
    0,    0,   75,    0,   75,   75,    0,    0,   75,   75,
   75,    0,   75,   71,    0,   75,   71,   71,   71,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   21,
    0,    0,   71,   71,    0,    0,    0,    0,    0,    0,
   71,    0,   71,    0,    0,   71,   71,   10,   71,   71,
   10,    0,    0,   71,    0,   71,   71,    0,    0,   71,
    0,   71,   71,   10,   22,   71,   71,   71,   23,   71,
   10,    0,   71,    0,    0,   10,   10,    0,   10,   10,
   24,    0,  137,  138,  139,   25,   10,   26,    0,   10,
   10,   10,    0,    0,    0,   10,   10,  100,    0,   10,
  155,  156,   10,    0,    0,  101,  102,    0,  161,    0,
    0,  165,    0,  103,    0,    0,  104,    0,    0,    0,
    0,    0,    2,  105,  174,    0,    0,    0,    0,    3,
    0,    0,    0,    0,    0,    0,    0,  182,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        260,
    0,   17,  287,    2,    3,   21,   19,  262,  260,  260,
    9,  281,  282,  283,  258,  300,  278,  258,  260,  263,
  258,   20,  258,  261,  262,  263,  260,  312,  280,  280,
  314,   47,   48,   49,  295,  290,  291,  278,  280,  277,
  278,   66,  278,   66,  260,   61,  280,  285,  318,  319,
   63,   64,  290,  291,  257,  293,  294,  260,    0,   84,
  298,   84,  257,  301,    6,  260,  304,   66,  306,  307,
  290,  291,  310,  311,   87,  270,  314,   90,  258,  317,
  306,  257,  258,  263,  310,   84,  261,  103,  104,  258,
  297,  317,  257,  297,  263,   94,  267,  268,  269,  290,
  291,  100,  278,  119,  270,  121,  272,  273,  274,  275,
  276,  258,  111,  278,  258,  131,  292,   56,   57,   58,
  296,  120,  280,  122,  123,  124,  280,  292,  257,  142,
  280,  296,  308,  149,  265,  266,  297,  313,  263,  315,
  305,  140,  141,  308,  316,  258,  162,  259,  313,  148,
  315,  297,  151,   59,   60,  297,  316,  173,  291,  316,
  259,  259,  178,  179,  298,  164,   50,   51,   52,   53,
   54,   55,  258,  291,  291,  261,  262,  263,  177,  265,
  266,  267,  268,  269,  270,  307,  272,  273,  274,  275,
  276,  277,  278,  262,  258,  298,  262,  262,  270,  285,
  291,  287,  277,  260,  290,  291,  277,  293,  294,  263,
  291,  291,  298,  261,  300,  301,  258,  314,  304,  262,
  306,  307,  258,  291,  310,  311,  312,    6,  314,  258,
  111,  317,  261,  262,  263,  159,  159,   -1,  267,  268,
  269,  270,   -1,  272,  273,  274,  275,  276,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,  285,   -1,  287,   -1,
   -1,  290,  291,   -1,  293,  294,   -1,   -1,   -1,  298,
   -1,  300,  301,   -1,   -1,  304,   -1,  306,  307,   -1,
   -1,  310,  311,  312,   -1,  314,  258,   -1,  317,  261,
  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,  270,   -1,
  272,  273,  274,  275,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,  285,  314,  287,   -1,   -1,  290,  291,
   -1,  293,  294,   -1,   -1,   -1,  298,   -1,  300,  301,
   -1,   -1,  304,   -1,  306,  307,   -1,   -1,  310,  311,
  312,   -1,  314,  258,   -1,  317,  261,  262,  263,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,
  285,   -1,  287,   -1,   -1,  290,  291,  262,  293,  294,
  278,   -1,   -1,  298,   -1,  300,  301,   -1,   -1,  304,
   -1,  306,  307,  278,  292,  310,  311,  312,  296,  314,
  285,   -1,  317,   -1,   -1,  290,  291,   -1,  293,  294,
  308,   -1,  122,  123,  124,  313,  301,  315,   -1,  304,
  278,  306,   -1,   -1,   -1,  310,  311,  285,   -1,  314,
  140,  141,  317,   -1,   -1,  293,  294,   -1,  148,   -1,
   -1,  151,   -1,  301,   -1,   -1,  304,   -1,   -1,   -1,
   -1,   -1,  310,  311,  164,   -1,   -1,   -1,   -1,  317,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  177,
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

//#line 257 "parser.y"
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
//#line 511 "Parser.java"
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
{yyval = new ParserVal(new Node("type-void", null));}
break;
case 25:
//#line 131 "parser.y"
{yyval = new ParserVal(new Node("type-auto", null));}
break;
case 26:
//#line 135 "parser.y"
{yyval = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
break;
case 29:
//#line 143 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
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
//#line 155 "parser.y"
{yyval = new ParserVal(new Node("print", null, Arrays.asList(val_peek(0).obj)));}
break;
case 39:
//#line 158 "parser.y"
{yyval = new ParserVal(new Node("return", null, Arrays.asList(val_peek(0).obj)));}
break;
case 40:
//#line 161 "parser.y"
{yyval = new ParserVal(new Node("assignment", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 41:
//#line 165 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(2).obj, new Node("arguments", null))));}
break;
case 42:
//#line 166 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj,new Node("arguments", null, Arrays.asList(val_peek(1).obj)))));}
break;
case 43:
//#line 167 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 44:
//#line 171 "parser.y"
{yyval = new ParserVal(new Node("arguments", null, Arrays.asList(val_peek(0).obj)));}
break;
case 45:
//#line 172 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 46:
//#line 176 "parser.y"
{yyval = new ParserVal(new Node("argument", null, Arrays.asList(val_peek(0).obj)));}
break;
case 47:
//#line 180 "parser.y"
{yyval = val_peek(0);}
break;
case 48:
//#line 181 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 50:
//#line 186 "parser.y"
{yyval = new ParserVal(new Node("while", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 51:
//#line 189 "parser.y"
{ blockStack.push(new LinkedList<>());}
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
//#line 196 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 57:
//#line 200 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 58:
//#line 201 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 59:
//#line 205 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 60:
//#line 206 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 61:
//#line 210 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 62:
//#line 211 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 63:
//#line 212 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 64:
//#line 213 "parser.y"
{yyval = val_peek(0);}
break;
case 65:
//#line 217 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 66:
//#line 218 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 67:
//#line 219 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 68:
//#line 220 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 69:
//#line 221 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 70:
//#line 222 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 71:
//#line 223 "parser.y"
{yyval = val_peek(0);}
break;
case 72:
//#line 227 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 73:
//#line 228 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 74:
//#line 229 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 75:
//#line 230 "parser.y"
{yyval = val_peek(0);}
break;
case 76:
//#line 234 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 77:
//#line 235 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 78:
//#line 236 "parser.y"
{yyval = val_peek(0);}
break;
case 79:
//#line 239 "parser.y"
{yyval = val_peek(0);}
break;
case 80:
//#line 240 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 81:
//#line 244 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 82:
//#line 245 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 83:
//#line 246 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 84:
//#line 247 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 85:
//#line 248 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 86:
//#line 249 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 87:
//#line 252 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
case 88:
//#line 253 "parser.y"
{yyval = val_peek(0);}
break;
//#line 980 "Parser.java"
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
