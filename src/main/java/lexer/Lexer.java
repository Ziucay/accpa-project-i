// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: C:/Users/JustL/Desktop/ACCPA/jflex-1.8.2/input/input.flex

package lexer;

import java.util.ArrayList;
import java.util.List;

// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class Lexer {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0, 0
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\37\u0100\1\u0200\267\u0100\10\u0300\u1020\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\2\3\1\4\22\0\1\1\1\0"+
    "\1\5\2\0\1\6\2\0\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\12\17\1\20\1\0\1\21"+
    "\1\22\1\23\2\0\32\24\1\25\1\26\1\27\1\0"+
    "\1\30\1\0\1\31\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\1\41\2\24\1\42\1\24\1\43\1\44"+
    "\1\45\1\24\1\46\1\47\1\50\1\51\1\52\1\24"+
    "\1\53\1\54\1\24\1\55\1\0\1\56\7\0\1\3"+
    "\u01a2\0\2\3\326\0\u0100\3";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1024];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\2\3\1\1\1\4\1\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\1\20\1\21\1\1\1\22\1\23\15\1\1\24"+
    "\1\25\1\0\1\26\1\0\1\27\1\30\1\0\1\31"+
    "\1\32\1\33\1\34\10\1\1\35\1\1\1\36\1\37"+
    "\10\1\1\40\1\41\5\1\1\42\1\1\1\43\5\1"+
    "\1\44\1\1\1\45\1\1\1\46\2\1\1\47\1\50"+
    "\3\1\1\51\1\52\1\53\1\54\3\1\1\55\3\1"+
    "\1\56\1\1\1\57\1\60\1\61\1\1\1\62";

  private static int [] zzUnpackAction() {
    int [] result = new int[113];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\57\0\215\0\274\0\57\0\57"+
    "\0\57\0\57\0\57\0\57\0\353\0\u011a\0\u0149\0\u0178"+
    "\0\57\0\u01a7\0\u01d6\0\u0205\0\u0234\0\57\0\57\0\u0263"+
    "\0\u0292\0\u02c1\0\u02f0\0\u031f\0\u034e\0\u037d\0\u03ac\0\u03db"+
    "\0\u040a\0\u0439\0\u0468\0\u0497\0\57\0\57\0\274\0\57"+
    "\0\u04c6\0\57\0\57\0\u04f5\0\57\0\57\0\57\0\57"+
    "\0\u0524\0\u0553\0\u0582\0\u05b1\0\u05e0\0\u060f\0\u063e\0\u066d"+
    "\0\u0234\0\u069c\0\u0234\0\u0234\0\u06cb\0\u06fa\0\u0729\0\u0758"+
    "\0\u0787\0\u07b6\0\u07e5\0\u0814\0\u04f5\0\u0234\0\u0843\0\u0872"+
    "\0\u08a1\0\u08d0\0\u08ff\0\u0234\0\u092e\0\u0234\0\u095d\0\u098c"+
    "\0\u09bb\0\u09ea\0\u0a19\0\u0234\0\u0a48\0\u0234\0\u0a77\0\u0234"+
    "\0\u0aa6\0\u0ad5\0\u0234\0\u0b04\0\u0b33\0\u0b62\0\u0b91\0\u0234"+
    "\0\u0234\0\u0234\0\u0234\0\u0bc0\0\u0bef\0\u0c1e\0\u0234\0\u0c4d"+
    "\0\u0c7c\0\u0cab\0\u0234\0\u0cda\0\u0234\0\u0234\0\u0234\0\u0d09"+
    "\0\u0234";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[113];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\2\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\22\1\23\1\24\1\25\1\26\1\2\1\27"+
    "\1\2\1\30\1\31\1\25\1\32\1\33\1\34\2\25"+
    "\1\35\2\25\1\36\1\37\1\40\1\41\1\42\1\25"+
    "\1\43\1\44\1\25\1\45\1\46\60\0\1\3\57\0"+
    "\1\4\54\0\5\47\1\50\20\47\1\51\30\47\17\0"+
    "\1\20\54\0\1\52\63\0\1\53\34\0\2\54\3\0"+
    "\12\54\1\20\37\54\22\0\1\55\56\0\1\56\1\57"+
    "\55\0\1\60\53\0\1\25\4\0\1\25\3\0\25\25"+
    "\21\0\1\25\4\0\1\25\3\0\13\25\1\61\2\25"+
    "\1\62\2\25\1\63\3\25\21\0\1\25\4\0\1\25"+
    "\3\0\14\25\1\64\10\25\21\0\1\25\4\0\1\25"+
    "\3\0\14\25\1\65\10\25\21\0\1\25\4\0\1\25"+
    "\3\0\12\25\1\66\1\67\11\25\21\0\1\25\4\0"+
    "\1\25\3\0\21\25\1\70\3\25\21\0\1\25\4\0"+
    "\1\25\3\0\6\25\1\71\4\25\1\72\3\25\1\73"+
    "\5\25\21\0\1\25\4\0\1\25\3\0\16\25\1\74"+
    "\6\25\21\0\1\25\4\0\1\25\3\0\16\25\1\75"+
    "\6\25\21\0\1\25\4\0\1\25\3\0\5\25\1\76"+
    "\17\25\21\0\1\25\4\0\1\25\3\0\20\25\1\77"+
    "\4\25\21\0\1\25\4\0\1\25\3\0\10\25\1\100"+
    "\13\25\1\101\21\0\1\25\4\0\1\25\3\0\1\25"+
    "\1\102\12\25\1\103\10\25\21\0\1\25\4\0\1\25"+
    "\3\0\14\25\1\104\10\25\2\0\2\47\3\0\52\47"+
    "\17\0\1\105\56\0\1\25\4\0\1\25\3\0\4\25"+
    "\1\106\20\25\21\0\1\25\4\0\1\25\3\0\16\25"+
    "\1\107\6\25\21\0\1\25\4\0\1\25\3\0\20\25"+
    "\1\110\4\25\21\0\1\25\4\0\1\25\3\0\14\25"+
    "\1\111\10\25\21\0\1\25\4\0\1\25\3\0\21\25"+
    "\1\112\3\25\21\0\1\25\4\0\1\25\3\0\17\25"+
    "\1\113\5\25\21\0\1\25\4\0\1\25\3\0\4\25"+
    "\1\114\20\25\21\0\1\25\4\0\1\25\3\0\13\25"+
    "\1\115\11\25\21\0\1\25\4\0\1\25\3\0\20\25"+
    "\1\116\4\25\21\0\1\25\4\0\1\25\3\0\11\25"+
    "\1\117\13\25\21\0\1\25\4\0\1\25\3\0\20\25"+
    "\1\120\4\25\21\0\1\25\4\0\1\25\3\0\16\25"+
    "\1\121\6\25\21\0\1\25\4\0\1\25\3\0\5\25"+
    "\1\122\17\25\21\0\1\25\4\0\1\25\3\0\15\25"+
    "\1\123\7\25\21\0\1\25\4\0\1\25\3\0\16\25"+
    "\1\124\6\25\21\0\1\25\4\0\1\25\3\0\11\25"+
    "\1\125\13\25\21\0\1\25\4\0\1\25\3\0\16\25"+
    "\1\126\6\25\21\0\1\25\4\0\1\25\3\0\1\25"+
    "\1\127\23\25\21\0\1\25\4\0\1\25\3\0\14\25"+
    "\1\130\10\25\21\0\1\25\4\0\1\25\3\0\12\25"+
    "\1\131\12\25\21\0\1\25\4\0\1\25\3\0\2\25"+
    "\1\132\22\25\21\0\1\25\4\0\1\25\3\0\5\25"+
    "\1\133\17\25\21\0\1\25\4\0\1\25\3\0\3\25"+
    "\1\134\21\25\21\0\1\25\4\0\1\25\3\0\13\25"+
    "\1\135\11\25\21\0\1\25\4\0\1\25\3\0\21\25"+
    "\1\136\3\25\21\0\1\25\4\0\1\25\3\0\11\25"+
    "\1\137\13\25\21\0\1\25\4\0\1\25\3\0\13\25"+
    "\1\140\11\25\21\0\1\25\4\0\1\25\3\0\5\25"+
    "\1\141\17\25\21\0\1\25\4\0\1\25\3\0\4\25"+
    "\1\142\20\25\21\0\1\25\4\0\1\25\3\0\24\25"+
    "\1\143\21\0\1\25\4\0\1\25\3\0\5\25\1\144"+
    "\17\25\21\0\1\25\4\0\1\25\3\0\12\25\1\145"+
    "\12\25\21\0\1\25\4\0\1\25\3\0\20\25\1\146"+
    "\4\25\21\0\1\25\4\0\1\25\3\0\20\25\1\147"+
    "\4\25\21\0\1\25\4\0\1\25\3\0\16\25\1\150"+
    "\6\25\21\0\1\25\4\0\1\25\3\0\13\25\1\151"+
    "\11\25\21\0\1\25\4\0\1\25\3\0\1\25\1\152"+
    "\23\25\21\0\1\25\4\0\1\25\3\0\5\25\1\153"+
    "\17\25\21\0\1\25\4\0\1\25\3\0\11\25\1\154"+
    "\13\25\21\0\1\25\4\0\1\25\3\0\13\25\1\155"+
    "\11\25\21\0\1\25\4\0\1\25\3\0\7\25\1\156"+
    "\15\25\21\0\1\25\4\0\1\25\3\0\13\25\1\157"+
    "\11\25\21\0\1\25\4\0\1\25\3\0\14\25\1\160"+
    "\10\25\21\0\1\25\4\0\1\25\3\0\13\25\1\161"+
    "\11\25\2\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3384];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\2\1\6\11\4\1\1\11"+
    "\4\1\2\11\15\1\2\11\1\0\1\11\1\0\2\11"+
    "\1\0\4\11\101\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[113];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */
public List<Token> tokens = new ArrayList();


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
tokens.add(new Token(TokenType.EOF, "eof", null, 0, 0));
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return 0; }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { tokens.add(new Token(TokenType.IDENTIFIER, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 51: break;
          case 2:
            { 
            }
            // fall through
          case 52: break;
          case 3:
            { yyline++;
            }
            // fall through
          case 53: break;
          case 4:
            { tokens.add(new Token(TokenType.PERCENT, "%", null, yyline, yycolumn));
            }
            // fall through
          case 54: break;
          case 5:
            { tokens.add(new Token(TokenType.LEFT_PAREN, "(", null, yyline, yycolumn));
            }
            // fall through
          case 55: break;
          case 6:
            { tokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, yyline, yycolumn));
            }
            // fall through
          case 56: break;
          case 7:
            { tokens.add(new Token(TokenType.STAR, "*", null, yyline, yycolumn));
            }
            // fall through
          case 57: break;
          case 8:
            { tokens.add(new Token(TokenType.PLUS, "+", null, yyline, yycolumn));
            }
            // fall through
          case 58: break;
          case 9:
            { tokens.add(new Token(TokenType.COMMA, ",", null, yyline, yycolumn));
            }
            // fall through
          case 59: break;
          case 10:
            { tokens.add(new Token(TokenType.MINUS, "-", null, yyline, yycolumn));
            }
            // fall through
          case 60: break;
          case 11:
            { tokens.add(new Token(TokenType.DOT, ".", null, yyline, yycolumn));
            }
            // fall through
          case 61: break;
          case 12:
            { tokens.add(new Token(TokenType.SLASH, "/", null, yyline, yycolumn));
            }
            // fall through
          case 62: break;
          case 13:
            { tokens.add(new Token(TokenType.INTEGER, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 63: break;
          case 14:
            { tokens.add(new Token(TokenType.COLON, "colon", null, yyline, yycolumn));
            }
            // fall through
          case 64: break;
          case 15:
            { tokens.add(new Token(TokenType.LESS, "<", null, yyline, yycolumn));
            }
            // fall through
          case 65: break;
          case 16:
            { tokens.add(new Token(TokenType.EQUAL, "=", null, yyline, yycolumn));
            }
            // fall through
          case 66: break;
          case 17:
            { tokens.add(new Token(TokenType.GREATER, ">", null, yyline, yycolumn));
            }
            // fall through
          case 67: break;
          case 18:
            { tokens.add(new Token(TokenType.LEFT_SQUARE_BRACE, "[", null, yyline, yycolumn));
            }
            // fall through
          case 68: break;
          case 19:
            { tokens.add(new Token(TokenType.RIGHT_SQUARE_BRACE, "]", null, yyline, yycolumn));
            }
            // fall through
          case 69: break;
          case 20:
            { tokens.add(new Token(TokenType.LEFT_BRACE, "{", null, yyline, yycolumn));
            }
            // fall through
          case 70: break;
          case 21:
            { tokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, yyline, yycolumn));
            }
            // fall through
          case 71: break;
          case 22:
            { tokens.add(new Token(TokenType.STRING, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 72: break;
          case 23:
            { tokens.add(new Token(TokenType.DOT_DOT, "..", null, yyline, yycolumn));
            }
            // fall through
          case 73: break;
          case 24:
            { tokens.add(new Token(TokenType.NOT_EQUAL, "/=", null, yyline, yycolumn));
            }
            // fall through
          case 74: break;
          case 25:
            { tokens.add(new Token(TokenType.LESS_EQUAL, "<=", null, yyline, yycolumn));
            }
            // fall through
          case 75: break;
          case 26:
            { tokens.add(new Token(TokenType.EQUAl_EQUAL, "==", null, yyline, yycolumn));
            }
            // fall through
          case 76: break;
          case 27:
            { tokens.add(new Token(TokenType.ARROW, "arrow", null, yyline, yycolumn));
            }
            // fall through
          case 77: break;
          case 28:
            { tokens.add(new Token(TokenType.GREATER_EQUAL, ">=", null, yyline, yycolumn));
            }
            // fall through
          case 78: break;
          case 29:
            { tokens.add(new Token(TokenType.IF, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 79: break;
          case 30:
            { tokens.add(new Token(TokenType.IS, "is", null, yyline, yycolumn));
            }
            // fall through
          case 80: break;
          case 31:
            { tokens.add(new Token(TokenType.OR, "or", null, yyline, yycolumn));
            }
            // fall through
          case 81: break;
          case 32:
            { tokens.add(new Token(TokenType.DOUBLE, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 82: break;
          case 33:
            { tokens.add(new Token(TokenType.AND, "and", null, yyline, yycolumn));
            }
            // fall through
          case 83: break;
          case 34:
            { tokens.add(new Token(TokenType.END, "end", null, yyline, yycolumn));
            }
            // fall through
          case 84: break;
          case 35:
            { tokens.add(new Token(TokenType.TYPE_INT, "int", null, yyline, yycolumn));
            }
            // fall through
          case 85: break;
          case 36:
            { tokens.add(new Token(TokenType.VAR, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 86: break;
          case 37:
            { tokens.add(new Token(TokenType.XOR, "xor", null, yyline, yycolumn));
            }
            // fall through
          case 87: break;
          case 38:
            { tokens.add(new Token(TokenType.AUTO, "auto", null, yyline, yycolumn));
            }
            // fall through
          case 88: break;
          case 39:
            { tokens.add(new Token(TokenType.ELSE, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 89: break;
          case 40:
            { tokens.add(new Token(TokenType.FUNC, "func", null, yyline, yycolumn));
            }
            // fall through
          case 90: break;
          case 41:
            { tokens.add(new Token(TokenType.THEN, yytext(), null, yyline, yycolumn));
            }
            // fall through
          case 91: break;
          case 42:
            { tokens.add(new Token(TokenType.TYPE, "func", null, yyline, yycolumn));
            }
            // fall through
          case 92: break;
          case 43:
            { tokens.add(new Token(TokenType.VOID, "void", null, yyline, yycolumn));
            }
            // fall through
          case 93: break;
          case 44:
            { tokens.add(new Token(TokenType.TYPE_ARRAY, "array", null, yyline, yycolumn));
            }
            // fall through
          case 94: break;
          case 45:
            { tokens.add(new Token(TokenType.PRINT, "print", null, yyline, yycolumn));
            }
            // fall through
          case 95: break;
          case 46:
            { tokens.add(new Token(TokenType.TYPE_DOUBLE, "double", null, yyline, yycolumn));
            }
            // fall through
          case 96: break;
          case 47:
            { tokens.add(new Token(TokenType.RETURN, "return", null, yyline, yycolumn));
            }
            // fall through
          case 97: break;
          case 48:
            { tokens.add(new Token(TokenType.TYPE_STRING, "string", null, yyline, yycolumn));
            }
            // fall through
          case 98: break;
          case 49:
            { tokens.add(new Token(TokenType.TYPE_BOOLEAN, "boolean", null, yyline, yycolumn));
            }
            // fall through
          case 99: break;
          case 50:
            { tokens.add(new Token(TokenType.FUNCTION, "function", null, yyline, yycolumn));
            }
            // fall through
          case 100: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }

  /**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String[] argv) {
    if (argv.length == 0) {
      System.out.println("Usage : java Lexer [ --encoding <name> ] <inputfile(s)>");
    }
    else {
      int firstFilePos = 0;
      String encodingName = "UTF-8";
      if (argv[0].equals("--encoding")) {
        firstFilePos = 2;
        encodingName = argv[1];
        try {
          // Side-effect: is encodingName valid?
          java.nio.charset.Charset.forName(encodingName);
        } catch (Exception e) {
          System.out.println("Invalid encoding '" + encodingName + "'");
          return;
        }
      }
      for (int i = firstFilePos; i < argv.length; i++) {
        Lexer scanner = null;
        try {
          java.io.FileInputStream stream = new java.io.FileInputStream(argv[i]);
          java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
          scanner = new Lexer(reader);
          while ( !scanner.zzAtEOF ) scanner.yylex();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}
