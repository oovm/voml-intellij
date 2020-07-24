/* The following code was generated by JFlex 1.7.0 tweaked for IntelliJ platform */

package com.github.voml.voml_idea.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.voml.voml_idea.language.psi.VomlTypes.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>_VomlLexer.flex</tt>
 */
public class _VomlLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
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
   * Translates characters to character classes
   * Chosen bits are [7, 7, 7]
   * Total runtime size is 1928 bytes
   */
  public static int ZZ_CMAP(int ch) {
    return ZZ_CMAP_A[(ZZ_CMAP_Y[ZZ_CMAP_Z[ch>>14]|((ch>>7)&0x7f)]<<7)|(ch&0x7f)];
  }

  /* The ZZ_CMAP_Z table has 68 entries */
  static final char ZZ_CMAP_Z[] = zzUnpackCMap(
    "\1\0\103\200");

  /* The ZZ_CMAP_Y table has 256 entries */
  static final char ZZ_CMAP_Y[] = zzUnpackCMap(
    "\1\0\1\1\53\2\1\3\22\2\1\4\37\2\1\3\237\2");

  /* The ZZ_CMAP_A table has 640 entries */
  static final char ZZ_CMAP_A[] = zzUnpackCMap(
    "\11\0\1\1\1\2\2\1\1\2\22\0\1\1\1\0\1\47\1\0\1\54\3\0\1\36\1\37\1\4\1\26\1"+
    "\53\1\26\1\24\1\3\1\17\11\16\1\51\1\52\1\45\1\50\1\46\1\0\1\31\1\61\1\22\1"+
    "\56\1\55\1\25\1\21\2\15\1\57\2\15\1\62\1\60\1\15\1\20\10\15\1\20\2\15\1\40"+
    "\1\27\1\41\1\44\1\23\1\0\1\12\1\22\1\33\1\34\1\10\1\11\1\15\1\35\1\32\2\15"+
    "\1\13\1\15\1\30\1\20\2\15\1\6\1\14\1\5\1\7\2\15\1\20\2\15\1\42\1\0\1\43\7"+
    "\0\1\1\32\0\1\1\337\0\1\1\177\0\13\1\35\0\2\1\5\0\1\1\57\0\1\1\40\0");

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\1\1\3\4\4\2\5\1\6"+
    "\1\7\1\10\1\4\1\1\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\4\1\30\1\0\2\4\1\31"+
    "\1\5\1\32\2\0\1\32\2\4\1\0\1\4\1\0"+
    "\2\4\1\0\1\33\1\0\1\4\1\34\1\0\1\4"+
    "\1\35\1\36\1\32\1\37\2\0\1\4\2\0\1\4"+
    "\2\0\1\4\2\0\1\40\1\41\1\42";

  private static int [] zzUnpackAction() {
    int [] result = new int[74];
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
    "\0\0\0\63\0\146\0\231\0\63\0\314\0\377\0\u0132"+
    "\0\u0165\0\u0198\0\u01cb\0\u01fe\0\63\0\63\0\u0231\0\u0264"+
    "\0\63\0\63\0\63\0\63\0\63\0\63\0\63\0\63"+
    "\0\63\0\63\0\63\0\63\0\63\0\63\0\63\0\u0297"+
    "\0\u02ca\0\u02fd\0\u0330\0\u0363\0\377\0\u0396\0\u03c9\0\u03fc"+
    "\0\u042f\0\u0462\0\u0495\0\u04c8\0\u04fb\0\u052e\0\u0561\0\u0594"+
    "\0\u05c7\0\u05fa\0\u062d\0\u0660\0\u0693\0\377\0\u06c6\0\u06f9"+
    "\0\63\0\377\0\u0660\0\377\0\u072c\0\u075f\0\u0792\0\u07c5"+
    "\0\u07f8\0\u082b\0\u085e\0\u0891\0\u08c4\0\u08f7\0\u092a\0\377"+
    "\0\63\0\63";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[74];
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
    "\1\2\2\3\1\4\1\5\1\6\3\7\1\10\1\11"+
    "\3\7\1\12\1\13\4\7\1\14\1\7\1\15\1\16"+
    "\1\17\1\20\4\7\1\21\1\22\1\23\1\24\1\25"+
    "\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\5\7\64\0\2\3\63\0\1\41"+
    "\1\42\63\0\1\7\1\43\15\7\1\0\1\7\2\0"+
    "\1\7\1\0\4\7\17\0\6\7\5\0\17\7\1\0"+
    "\1\7\2\0\1\7\1\0\4\7\17\0\6\7\5\0"+
    "\5\7\1\44\11\7\1\0\1\7\2\0\1\7\1\0"+
    "\4\7\17\0\6\7\5\0\7\7\1\45\7\7\1\0"+
    "\1\7\2\0\1\7\1\0\4\7\17\0\6\7\16\0"+
    "\2\12\3\0\1\46\1\47\54\0\2\50\1\51\1\0"+
    "\1\51\1\0\1\47\54\0\2\52\50\0\2\7\1\53"+
    "\2\7\1\54\11\7\1\0\1\7\2\0\1\7\1\0"+
    "\4\7\17\0\6\7\32\0\1\55\35\0\17\7\1\0"+
    "\1\56\2\0\1\7\1\0\4\7\17\0\6\7\2\41"+
    "\1\0\60\41\4\42\1\57\56\42\5\0\2\7\1\60"+
    "\14\7\1\0\1\7\2\0\1\7\1\0\4\7\17\0"+
    "\6\7\5\0\6\7\1\61\10\7\1\0\1\7\2\0"+
    "\1\7\1\0\4\7\17\0\6\7\16\0\2\46\3\0"+
    "\1\46\43\0\1\62\11\0\2\47\61\0\2\50\4\0"+
    "\1\47\46\0\3\63\3\0\2\63\1\0\2\63\2\0"+
    "\1\63\5\0\2\63\20\0\2\63\2\0\1\63\11\0"+
    "\1\64\5\0\2\52\5\0\1\64\42\0\6\7\1\65"+
    "\10\7\1\0\1\7\2\0\1\7\1\0\4\7\17\0"+
    "\6\7\5\0\17\7\1\0\1\7\2\0\1\66\1\0"+
    "\4\7\17\0\6\7\30\0\1\67\37\0\17\7\1\0"+
    "\1\7\2\0\1\7\1\0\4\7\17\0\1\7\1\70"+
    "\4\7\3\42\1\71\1\57\56\42\5\0\3\7\1\72"+
    "\13\7\1\0\1\7\2\0\1\7\1\0\4\7\17\0"+
    "\6\7\5\0\7\7\1\60\7\7\1\0\1\7\2\0"+
    "\1\7\1\0\4\7\17\0\6\7\4\0\1\64\66\0"+
    "\3\63\3\0\2\63\1\0\3\63\1\0\1\63\5\0"+
    "\2\63\20\0\2\63\2\0\1\63\17\0\2\73\50\0"+
    "\6\7\1\74\10\7\1\0\1\7\2\0\1\7\1\0"+
    "\4\7\17\0\6\7\33\0\1\75\1\0\1\76\32\0"+
    "\17\7\1\0\1\7\2\0\1\7\1\0\4\7\17\0"+
    "\2\7\1\77\3\7\13\0\1\100\57\0\1\101\57\0"+
    "\17\7\1\0\1\7\2\0\1\7\1\0\4\7\17\0"+
    "\3\7\1\102\2\7\7\0\1\103\61\0\1\104\61\0"+
    "\17\7\1\0\1\7\2\0\1\7\1\0\4\7\17\0"+
    "\4\7\1\105\1\7\34\0\1\106\60\0\1\107\35\0"+
    "\17\7\1\0\1\7\2\0\1\7\1\0\4\7\17\0"+
    "\5\7\1\110\10\0\1\111\57\0\1\112\55\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2397];
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


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\2\1\1\11\7\1\2\11\2\1\17\11"+
    "\2\1\1\0\5\1\2\0\3\1\1\0\1\1\1\0"+
    "\2\1\1\0\1\1\1\0\2\1\1\0\1\1\1\11"+
    "\3\1\2\0\1\1\2\0\1\1\2\0\1\1\2\0"+
    "\1\1\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[74];
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

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  public _VomlLexer() {
    this((java.io.Reader)null);
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public _VomlLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    int size = 0;
    for (int i = 0, length = packed.length(); i < length; i += 2) {
      size += packed.charAt(i);
    }
    char[] map = new char[size];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < packed.length()) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position {@code pos} from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

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
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
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
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + ZZ_CMAP(zzInput) ];
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
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { return NON_ESCAPE;
            } 
            // fall through
          case 35: break;
          case 2: 
            { return WHITE_SPACE;
            } 
            // fall through
          case 36: break;
          case 3: 
            { return STAR;
            } 
            // fall through
          case 37: break;
          case 4: 
            { return SYMBOL;
            } 
            // fall through
          case 38: break;
          case 5: 
            { return INTEGER;
            } 
            // fall through
          case 39: break;
          case 6: 
            { return DOT;
            } 
            // fall through
          case 40: break;
          case 7: 
            { return SIGN;
            } 
            // fall through
          case 41: break;
          case 8: 
            { return ESCAPE;
            } 
            // fall through
          case 42: break;
          case 9: 
            { return PARENTHESIS_L;
            } 
            // fall through
          case 43: break;
          case 10: 
            { return PARENTHESIS_R;
            } 
            // fall through
          case 44: break;
          case 11: 
            { return BRACKET_L;
            } 
            // fall through
          case 45: break;
          case 12: 
            { return BRACKET_R;
            } 
            // fall through
          case 46: break;
          case 13: 
            { return BRACE_L;
            } 
            // fall through
          case 47: break;
          case 14: 
            { return BRACE_R;
            } 
            // fall through
          case 48: break;
          case 15: 
            { return ACCENT;
            } 
            // fall through
          case 49: break;
          case 16: 
            { return ANGLE_L;
            } 
            // fall through
          case 50: break;
          case 17: 
            { return ANGLE_R;
            } 
            // fall through
          case 51: break;
          case 18: 
            { return QUOTATION;
            } 
            // fall through
          case 52: break;
          case 19: 
            { return EQ;
            } 
            // fall through
          case 53: break;
          case 20: 
            { return COLON;
            } 
            // fall through
          case 54: break;
          case 21: 
            { return SEMICOLON;
            } 
            // fall through
          case 55: break;
          case 22: 
            { return COMMA;
            } 
            // fall through
          case 56: break;
          case 23: 
            { return CITE;
            } 
            // fall through
          case 57: break;
          case 24: 
            { return COMMENT;
            } 
            // fall through
          case 58: break;
          case 25: 
            { return AS;
            } 
            // fall through
          case 59: break;
          case 26: 
            { return FLOAT;
            } 
            // fall through
          case 60: break;
          case 27: 
            { return BYTE;
            } 
            // fall through
          case 61: break;
          case 28: 
            { return NAN;
            } 
            // fall through
          case 62: break;
          case 29: 
            { return BLOCK_COMMENT;
            } 
            // fall through
          case 63: break;
          case 30: 
            { return BOOLEAN;
            } 
            // fall through
          case 64: break;
          case 31: 
            { return NULL;
            } 
            // fall through
          case 65: break;
          case 32: 
            { return DECIMAL;
            } 
            // fall through
          case 66: break;
          case 33: 
            { return INCLUDE;
            } 
            // fall through
          case 67: break;
          case 34: 
            { return INHERIT;
            } 
            // fall through
          case 68: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
