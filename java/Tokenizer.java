import java.util.List;
import java.util.ArrayList;

public class Tokenizer {    
    private final char[] input;
    private int inputPos;

    public Tokenizer(final char[] input) {
        this.input = input;
        inputPos = 0;
    }

    public Tokenizer(final String input) {
        this(input.toCharArray());
    }
    
    // variables start with letters
    // variables can contain letters or digits
    private Token tryTokenizeVariableOrReservedWord() {
        String letters = "";
        
        if (inputPos < input.length &&
            Character.isLetter(input[inputPos])) {
            letters += input[inputPos];
            inputPos++;

            while (inputPos < input.length &&
                   Character.isLetterOrDigit(input[inputPos])) {
                letters += input[inputPos];
                inputPos++;
            }

            if (letters.equals("if")) {
                return new IfToken();
            } else if (letters.equals("else")) {
                return new ElseToken();
            } else {
                return new VariableToken(letters);
            }
        } else {
            return null;
        }
    }

    // returns null if it couldn't tokenize
    // an integer
    private IntegerToken tryTokenizeInteger() {
        String digits = "";

        if (inputPos < input.length &&
            input[inputPos] == '-') {
            digits += input[inputPos];
            inputPos++;
        }
        
        while (inputPos < input.length &&
               Character.isDigit(input[inputPos])) {
            digits += input[inputPos];
            inputPos++;
        }

        if (digits.length() > 0) {
            return new IntegerToken(Integer.parseInt(digits));
        } else {
            return null;
        }
    }

    private void skipWhitespace() {
        while (inputPos < input.length &&
               Character.isWhitespace(input[inputPos])) {
            inputPos++;
        }
    }
    
    public List<Token> tokenize() throws TokenizerException {
        List<Token> tokens = new ArrayList<Token>();

        while (inputPos < input.length) {
            skipWhitespace();
            if (inputPos < input.length) {
                tokens.add(tokenizeOne());
            }
        }

        return tokens;
    }

    // assumes it's not starting on whitespace
    private Token tokenizeOne() throws TokenizerException {
        Token read = tryTokenizeVariableOrReservedWord();
        if (read != null) {
            return read;
        } else {
            read = tryTokenizeInteger();
            if (read != null) {
                return read;
            } else {
                if (inputPos < input.length) {
                    if (input[inputPos] == '(') {
                        inputPos++;
                        return new LeftParenToken();
                    } else if (input[inputPos] == ')') {
                        inputPos++;
                        return new RightParenToken();
                    } else {
                        throw new TokenizerException("Have input, but it's not valid");
                    }
                } else {
                    throw new TokenizerException("Have no more input");
                }
            }
        }
    }
}
