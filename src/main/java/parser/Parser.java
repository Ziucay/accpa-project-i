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
    0,    0,    1,    1,    1,    2,    2,    2,    5,    8,
   10,    3,    3,    3,    3,    3,    4,    4,    4,   13,
   13,   12,    7,    7,    7,    7,    7,    7,    7,   11,
   15,   15,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   23,   22,   17,   18,   18,   18,   25,   25,   24,
   26,   26,   26,   19,   27,   28,   29,   30,   14,   20,
   31,   31,   21,   21,    9,    9,    9,    9,   32,   32,
   32,   32,   32,   32,   32,   33,   33,   33,   33,   34,
   34,   34,   35,   35,   36,   36,   36,   36,   36,   36,
    6,    6,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    0,    1,    1,    1,    4,    8,
    4,    4,    6,   11,   12,   12,    9,   10,   10,    1,
    3,    3,    1,    1,    1,    1,    1,    1,    1,    1,
    2,    0,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    3,    3,    4,    4,    1,    3,    1,
    1,    3,    0,    5,    1,    1,    1,    1,    1,    6,
    4,    5,    5,    7,    3,    3,    3,    1,    3,    3,
    3,    3,    3,    3,    1,    3,    3,    3,    1,    3,
    3,    1,    1,    3,    1,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
   59,    0,    0,    0,    0,    0,    0,    6,    7,    8,
    0,   91,    0,   92,    0,    0,    1,    3,    0,    0,
    0,    0,    0,    0,    0,   88,   86,   87,   89,   85,
    0,    0,    0,    0,    0,    0,   83,   29,   25,   23,
   24,   26,   28,   27,    9,    0,    0,    0,    0,    0,
    0,    0,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   84,   65,   66,   67,   73,   74,
   71,   72,   69,   70,   77,   76,   78,   81,   80,   13,
    0,    0,   22,    0,    0,   21,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   56,   57,    0,
    0,   55,   38,    0,   41,    0,   30,    0,   33,   34,
   35,   36,   37,   39,   40,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,   43,    0,    0,   17,   31,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   45,
   50,    0,    0,   44,    0,    0,    0,    0,   18,   19,
   14,    0,    0,    0,   46,    0,   47,    0,    0,    0,
    0,    0,   15,   16,    0,    0,   49,   54,    0,    0,
    0,   58,   63,    0,    0,    0,   61,   60,    0,    0,
   62,   64,   10,
};
final static short yydgoto[] = {                          5,
    6,    7,  113,    9,   10,   31,   45,  115,  151,   14,
  116,   50,   51,   11,  117,  118,  119,  120,  121,  122,
  123,  124,  125,  152,  153,    0,  126,  127,  128,  184,
  157,   33,   34,   35,   36,   37,
};
final static short yysindex[] = {                      -205,
    0, -278, -278, -278,    0, -298, -205,    0,    0,    0,
 -278,    0, -243,    0, -250, -238,    0,    0, -168, -245,
 -247, -247, -247, -240, -245,    0,    0,    0,    0,    0,
 -235, -234, -272,   87, -161, -155,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -276, -223, -228, -199, -239,
 -170, -160,    0, -245, -245, -245, -245, -245, -245, -245,
 -245, -245, -245, -245, -245, -245, -245, -245, -141, -247,
 -247, -190, -278, -178,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -193, -176,    0, -247, -135,    0, -247, -186, -180, -120,
 -139, -154, -153, -117, -169, -164, -278,    0,    0, -245,
 -245,    0,    0, -173,    0, -138,    0, -139,    0,    0,
    0,    0,    0,    0,    0, -245, -278, -245, -139, -139,
 -139, -103, -100, -194,    0,    0, -255, -245,    0,    0,
 -137, -231, -144, -125, -124,  -94, -139, -139, -247,    0,
    0, -167,  -88,    0, -139, -246, -122, -139,    0,    0,
    0,  -83,  -81,  -77,    0, -245,    0, -109, -245,  -69,
 -139, -177,    0,    0,  -50,  -52,    0,    0,  -63, -245,
  -74,    0,    0, -139, -245, -245,    0,    0,  -73,  -42,
    0,    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0,    0,    0,  -93,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -78,    0,  150,   93,   36,  -21,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  180,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -34,    0,    0,    0,    0,    0,
  -66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -242,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -66,  -66,
  -35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -35,  -35,    0,    0,
    0,    0,    0,    0,  -66,    0,    0, -172,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -66,    0,    0,    0,    0,  -28,    0,    0,    0,    0,
    0,    0,    0,  -66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  228,    0,   73,    0,    0,    2,  -15,    0,  -11,    0,
   -7,    3,    4,    0,  120,    0,    0,    0,    0,    0,
    0,    0,    0,   77,   78,    0,    0,    0,    0,    0,
    0,  144,   72,   59,    0,    0,
};
final static int YYTABLESIZE=497;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         12,
    2,   25,  150,   13,   15,   16,   46,   47,   32,   20,
   25,   25,   19,   52,   54,   17,   20,   48,   72,   32,
   68,   20,   12,   73,   20,   49,   53,   55,   20,   22,
   38,   12,   12,   39,   40,   41,   26,   12,   42,   56,
   27,   23,   76,   77,   78,   26,   26,   32,   32,   27,
   27,   70,   28,   21,   92,   93,   90,   29,  169,   30,
   20,   28,   28,  156,   98,   20,   29,   29,   30,   30,
   43,   44,    8,   69,   49,   95,   96,  105,  102,    8,
   71,  103,   73,  137,   12,  149,   20,   74,   24,   94,
  165,   20,   49,   99,  100,  166,  138,   75,  135,  136,
    1,   97,  114,    2,    3,   63,   64,   65,  134,   66,
   67,    4,  182,  183,  141,   91,  143,   32,   32,  114,
  101,  144,  145,  146,   88,   89,  154,   73,  142,  104,
  114,  114,  114,  164,   85,   86,   87,  106,   12,  162,
  163,  131,  129,  130,  170,  107,  132,  168,  114,  114,
  172,  133,  139,  108,  109,  147,  114,  179,  148,  114,
  155,  110,  158,  181,  111,  159,  160,  161,  187,  167,
    3,  112,  114,  190,  191,  171,  189,    4,  173,   90,
  174,  178,   90,   90,   90,  114,   90,   90,   90,   90,
   90,   90,  175,   90,   90,   90,   90,   90,   90,   90,
   79,   80,   81,   82,   83,   84,   90,  180,   90,  185,
  166,   90,   90,  186,   90,   90,  188,  192,  193,   90,
    4,   90,   90,   20,   32,   90,   32,   90,   90,   48,
   90,   90,   90,   90,   18,   90,   82,  140,   90,   82,
   82,   82,  176,  177,    0,   82,   82,   82,   82,    0,
   82,   82,   82,   82,   82,   82,   82,    0,    0,    0,
    0,    0,    0,   82,    0,   82,    0,    0,   82,   82,
    0,   82,   82,    0,    0,    0,   82,    0,   82,   82,
    0,    0,   82,    0,   82,   82,    0,   82,   82,   82,
   82,    0,   82,   79,    0,   82,   79,   79,   79,    0,
    0,    0,    0,    0,    0,   79,    0,   79,   79,   79,
   79,   79,   79,   79,    5,    0,    0,    0,    0,    0,
   79,    0,   79,    0,    0,   79,   79,    0,   79,   79,
    0,    0,    0,   79,    0,   79,   79,    0,    0,   79,
    0,   79,   79,    0,   79,   79,   79,   79,    0,   79,
   75,    0,   79,   75,   75,   75,   57,    0,   58,   59,
   60,   61,   62,    0,    0,    0,    0,    0,    0,   75,
   75,    0,    0,    0,    0,    0,    0,   75,    0,   75,
    0,    0,   75,   75,    0,   75,   75,    0,    0,    0,
   75,    0,   75,   75,    0,    0,   75,    0,   75,   75,
    0,   75,   75,   75,   75,    0,   75,   68,    0,   75,
   68,   68,   68,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   68,   68,    0,    0,
    0,    0,    0,    0,   68,    0,    0,    0,    0,   68,
   68,   12,   68,   68,    0,    0,    0,   68,    0,    0,
   68,    0,    0,   68,    0,   68,   68,   12,   68,   68,
   68,    0,    0,   68,   12,    0,   68,    0,    0,   12,
   12,    0,   12,   12,    0,    0,    0,    0,    0,    0,
   12,    0,    0,   12,    0,   12,    0,    0,   12,   12,
   12,    0,    0,   12,    0,    0,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        278,
    0,  257,  258,    2,    3,    4,   22,   23,   20,  260,
  257,  257,   11,   25,  287,  314,  260,  258,  258,  262,
  297,  260,  278,  263,  260,   24,  261,  300,  260,  280,
  278,  278,  278,  281,  282,  283,  292,  278,  286,  312,
  296,  280,   54,   55,   56,  292,  292,  290,  291,  296,
  296,  280,  308,  297,   70,   71,   68,  313,  305,  315,
  260,  308,  308,  295,  258,  260,  313,  313,  315,  315,
  318,  319,    0,  297,   73,   73,   73,  258,   94,    7,
  280,   97,  263,  257,  278,  280,  260,  258,  257,  280,
  258,  260,   91,   91,   91,  263,  270,  258,  110,  111,
  306,  280,  101,  309,  310,  267,  268,  269,  107,  265,
  266,  317,  290,  291,  126,  257,  128,  290,  291,  118,
  297,  129,  130,  131,   66,   67,  138,  263,  127,  316,
  129,  130,  131,  149,   63,   64,   65,  258,  278,  147,
  148,  259,  297,  297,  156,  285,  316,  155,  147,  148,
  158,  316,  291,  293,  294,  259,  155,  169,  259,  158,
  298,  301,  307,  171,  304,  291,  291,  262,  180,  258,
  310,  311,  171,  185,  186,  298,  184,  317,  262,  258,
  262,  291,  261,  262,  263,  184,  265,  266,  267,  268,
  269,  270,  270,  272,  273,  274,  275,  276,  277,  278,
   57,   58,   59,   60,   61,   62,  285,  277,  287,  260,
  263,  290,  291,  277,  293,  294,  291,  291,  261,  298,
  314,  300,  301,  258,  291,  304,  262,  306,  307,  258,
  309,  310,  311,  312,    7,  314,  258,  118,  317,  261,
  262,  263,  166,  166,   -1,  267,  268,  269,  270,   -1,
  272,  273,  274,  275,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,  285,   -1,  287,   -1,   -1,  290,  291,
   -1,  293,  294,   -1,   -1,   -1,  298,   -1,  300,  301,
   -1,   -1,  304,   -1,  306,  307,   -1,  309,  310,  311,
  312,   -1,  314,  258,   -1,  317,  261,  262,  263,   -1,
   -1,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,  274,
  275,  276,  277,  278,  314,   -1,   -1,   -1,   -1,   -1,
  285,   -1,  287,   -1,   -1,  290,  291,   -1,  293,  294,
   -1,   -1,   -1,  298,   -1,  300,  301,   -1,   -1,  304,
   -1,  306,  307,   -1,  309,  310,  311,  312,   -1,  314,
  258,   -1,  317,  261,  262,  263,  270,   -1,  272,  273,
  274,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,  285,   -1,  287,
   -1,   -1,  290,  291,   -1,  293,  294,   -1,   -1,   -1,
  298,   -1,  300,  301,   -1,   -1,  304,   -1,  306,  307,
   -1,  309,  310,  311,  312,   -1,  314,  258,   -1,  317,
  261,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,   -1,   -1,  285,   -1,   -1,   -1,   -1,  290,
  291,  262,  293,  294,   -1,   -1,   -1,  298,   -1,   -1,
  301,   -1,   -1,  304,   -1,  306,  307,  278,  309,  310,
  311,   -1,   -1,  314,  285,   -1,  317,   -1,   -1,  290,
  291,   -1,  293,  294,   -1,   -1,   -1,   -1,   -1,   -1,
  301,   -1,   -1,  304,   -1,  306,   -1,   -1,  309,  310,
  311,   -1,   -1,  314,   -1,   -1,  317,
};
}
final static short YYFINAL=5;
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
"Line : UserType",
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

//#line 264 "parser.y"
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
//#line 526 "Parser.java"
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
//#line 93 "parser.y"
{root.descendants.add(val_peek(0).obj);}
break;
case 9:
//#line 97 "parser.y"
{yyval = new ParserVal(new Node("type-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 10:
//#line 101 "parser.y"
{ yyval = new ParserVal(new Node("array-declaration", null, Arrays.asList(val_peek(6).obj, val_peek(4).obj, val_peek(1).obj)));}
break;
case 11:
//#line 105 "parser.y"
{yyval = new ParserVal(new Node("array-access", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 12:
//#line 109 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 13:
//#line 110 "parser.y"
{yyval = new ParserVal(new Node("variable-declaration", null, Arrays.asList(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj)));}
break;
case 14:
//#line 111 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(9).obj,new Node("parameters", null), val_peek(7).obj, val_peek(1).obj)));}
break;
case 15:
//#line 112 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(10).obj,new Node("parameters", null, Arrays.asList(val_peek(5).obj)),val_peek(8).obj, val_peek(1).obj)));}
break;
case 16:
//#line 113 "parser.y"
{yyval = new ParserVal(new Node("function-expression", null, Arrays.asList(val_peek(10).obj,val_peek(5).obj, val_peek(8).obj, val_peek(1).obj)));}
break;
case 17:
//#line 117 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(7).obj,new Node("parameters", null, Arrays.asList(val_peek(8).obj)), val_peek(3).obj,val_peek(1).obj)));}
break;
case 18:
//#line 118 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, new Node("parameters", null, Arrays.asList(val_peek(6).obj)),val_peek(3).obj,val_peek(1).obj)));}
break;
case 19:
//#line 119 "parser.y"
{yyval = new ParserVal(new Node("function-declaration", null, Arrays.asList(val_peek(8).obj, val_peek(6).obj,val_peek(3).obj,val_peek(1).obj)));}
break;
case 20:
//#line 123 "parser.y"
{yyval = new ParserVal(new Node("parameters", null, Arrays.asList(val_peek(0).obj)));}
break;
case 21:
//#line 124 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 22:
//#line 128 "parser.y"
{yyval = new ParserVal(new Node("parameter-declaration", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 23:
//#line 132 "parser.y"
{yyval = new ParserVal(new Node("type-integer", null));}
break;
case 24:
//#line 133 "parser.y"
{yyval = new ParserVal(new Node("type-real", null));}
break;
case 25:
//#line 134 "parser.y"
{yyval = new ParserVal(new Node("type-boolean", null));}
break;
case 26:
//#line 135 "parser.y"
{yyval = new ParserVal(new Node("type-string", null));}
break;
case 27:
//#line 136 "parser.y"
{yyval = new ParserVal(new Node("type-void", null));}
break;
case 28:
//#line 137 "parser.y"
{yyval = new ParserVal(new Node("type-auto", null));}
break;
case 29:
//#line 138 "parser.y"
{yyval = new ParserVal(new Node("user-type", null));}
break;
case 30:
//#line 142 "parser.y"
{yyval = new ParserVal(new Node("body", null,blockStack.peek())); blockStack.pop();}
break;
case 33:
//#line 150 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 34:
//#line 151 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 35:
//#line 152 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 36:
//#line 153 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 37:
//#line 154 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 38:
//#line 155 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 39:
//#line 156 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 40:
//#line 157 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 41:
//#line 158 "parser.y"
{yyval = val_peek(0); blockStack.peek().add(val_peek(0).obj);}
break;
case 42:
//#line 162 "parser.y"
{yyval = new ParserVal(new Node("print", null, Arrays.asList(val_peek(0).obj)));}
break;
case 43:
//#line 165 "parser.y"
{yyval = new ParserVal(new Node("return", null, Arrays.asList(val_peek(0).obj)));}
break;
case 44:
//#line 168 "parser.y"
{yyval = new ParserVal(new Node("assignment", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 45:
//#line 172 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(2).obj, new Node("arguments", null))));}
break;
case 46:
//#line 173 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj,new Node("arguments", null, Arrays.asList(val_peek(1).obj)))));}
break;
case 47:
//#line 174 "parser.y"
{yyval = new ParserVal(new Node("function-call", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 48:
//#line 178 "parser.y"
{yyval = new ParserVal(new Node("arguments", null, Arrays.asList(val_peek(0).obj)));}
break;
case 49:
//#line 179 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 50:
//#line 183 "parser.y"
{yyval = new ParserVal(new Node("argument", null, Arrays.asList(val_peek(0).obj)));}
break;
case 51:
//#line 187 "parser.y"
{yyval = val_peek(0);}
break;
case 52:
//#line 188 "parser.y"
{((Node)val_peek(0).obj).descendants.add(val_peek(2).obj); yyval = val_peek(0);}
break;
case 54:
//#line 193 "parser.y"
{yyval = new ParserVal(new Node("while", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 55:
//#line 196 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 56:
//#line 197 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 57:
//#line 198 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 58:
//#line 199 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 59:
//#line 200 "parser.y"
{ blockStack.push(new LinkedList<>());}
break;
case 60:
//#line 203 "parser.y"
{yyval = new ParserVal(new Node("for", null, Arrays.asList(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 61:
//#line 207 "parser.y"
{yyval = new ParserVal(new Node("range", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 62:
//#line 208 "parser.y"
{yyval = new ParserVal(new Node("range-reverse", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 63:
//#line 212 "parser.y"
{yyval = new ParserVal(new Node("if", null, Arrays.asList(val_peek(3).obj, val_peek(1).obj)));}
break;
case 64:
//#line 213 "parser.y"
{yyval = new ParserVal(new Node("if-else", null, Arrays.asList(val_peek(5).obj, val_peek(3).obj, val_peek(1).obj)));}
break;
case 65:
//#line 217 "parser.y"
{yyval = new ParserVal(new Node("and", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 66:
//#line 218 "parser.y"
{yyval = new ParserVal(new Node("or", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 67:
//#line 219 "parser.y"
{yyval = new ParserVal(new Node("xor", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 68:
//#line 220 "parser.y"
{yyval = val_peek(0);}
break;
case 69:
//#line 224 "parser.y"
{yyval = new ParserVal(new Node("less", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 70:
//#line 225 "parser.y"
{yyval = new ParserVal(new Node("less or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 71:
//#line 226 "parser.y"
{yyval = new ParserVal(new Node("more", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 72:
//#line 227 "parser.y"
{yyval = new ParserVal(new Node("more or equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 73:
//#line 228 "parser.y"
{yyval = new ParserVal(new Node("equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 74:
//#line 229 "parser.y"
{yyval = new ParserVal(new Node("not equal", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 75:
//#line 230 "parser.y"
{yyval = val_peek(0);}
break;
case 76:
//#line 234 "parser.y"
{yyval = new ParserVal(new Node("multiply", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 77:
//#line 235 "parser.y"
{yyval = new ParserVal(new Node("divide", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 78:
//#line 236 "parser.y"
{yyval = new ParserVal(new Node("percent", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 79:
//#line 237 "parser.y"
{yyval = val_peek(0);}
break;
case 80:
//#line 241 "parser.y"
{yyval = new ParserVal(new Node("plus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 81:
//#line 242 "parser.y"
{yyval = new ParserVal(new Node("minus", null, Arrays.asList(val_peek(2).obj, val_peek(0).obj)));}
break;
case 82:
//#line 243 "parser.y"
{yyval = val_peek(0);}
break;
case 83:
//#line 246 "parser.y"
{yyval = val_peek(0);}
break;
case 84:
//#line 247 "parser.y"
{yyval = new ParserVal(new Node("summand", null, Arrays.asList(val_peek(1).obj)));}
break;
case 85:
//#line 251 "parser.y"
{yyval = new ParserVal(new Node(yylval.dval.toString(), Double.valueOf(val_peek(0).dval)));}
break;
case 86:
//#line 252 "parser.y"
{yyval = new ParserVal(new Node(yylval.ival.toString(), Integer.valueOf(val_peek(0).ival)));}
break;
case 87:
//#line 253 "parser.y"
{yyval = new ParserVal(new Node("true", Boolean.valueOf(true)));}
break;
case 88:
//#line 254 "parser.y"
{yyval = new ParserVal(new Node("false", Boolean.valueOf(false)));}
break;
case 89:
//#line 255 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, val_peek(0).sval));}
break;
case 90:
//#line 256 "parser.y"
{yyval = new ParserVal(new Node("modifiable", null, Arrays.asList(val_peek(0).obj)));}
break;
case 91:
//#line 259 "parser.y"
{yyval = new ParserVal(new Node(yylval.sval, null));}
break;
case 92:
//#line 260 "parser.y"
{yyval = val_peek(0);}
break;
//#line 1011 "Parser.java"
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
