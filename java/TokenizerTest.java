import java.util.ArrayList;
import java.util.List;

public class TokenizerTest {
    public static void testTokenizes(final String input,
                                     final Token... expectedTokens) throws TokenizerException {
        final List<Token> expected = new ArrayList<Token>();
        for (final Token token : expectedTokens) {
            expected.add(token);
        }
        final Tokenizer tokenizer = new Tokenizer(input);
        final List<Token> receivedTokens = tokenizer.tokenize();
        assert(receivedTokens.equals(expected));
    }
    
    // input: "("
    // output: LeftParenToken
    public static void testLeftParen() throws TokenizerException {
        testTokenizes("(", new LeftParenToken());
    }

    // input: ")"
    // output: RightParenToken
    public static void testRightParen() throws TokenizerException {
        testTokenizes(")", new RightParenToken());
    }

    public static void testVariableAlone() throws TokenizerException {
        testTokenizes("x", new VariableToken("x"));
    }

    public static void testVariableWithWhitespaceBefore() throws TokenizerException {
        testTokenizes(" x", new VariableToken("x"));
    }

    public static void testVariableWithWhitespaceAfter() throws TokenizerException {
        testTokenizes("x ", new VariableToken("x"));
    }

    public static void testVariableContainingReservedWords() throws TokenizerException {
        testTokenizes("ifelse", new VariableToken("ifelse"));
    }

    public static void testTwoReservedWords() throws TokenizerException {
        testTokenizes("if else",
                      new IfToken(),
                      new ElseToken());
    }
    
    public static void main(String[] args) throws TokenizerException {
        testLeftParen();
        testRightParen();
        testVariableAlone();
        testVariableWithWhitespaceBefore();
        testVariableWithWhitespaceAfter();
        testVariableContainingReservedWords();
        testTwoReservedWords();
    }
}

        
