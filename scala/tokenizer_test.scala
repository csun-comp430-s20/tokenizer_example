object TokenizerTest {
  def testTokenizes(input: String, expectedTokens: Token*) {
    val tokenizer = Tokenizer(input)
    val receivedTokens = tokenizer.tokenize()
    assert(receivedTokens == expectedTokens.toSeq)
  }

  def testLeftParen() {
    testTokenizes("(", LeftParenToken)
  }

  def testRightParen() {
    testTokenizes(")", RightParenToken)
  }

  def testVariableAlone() {
    testTokenizes("x", VariableToken("x"))
  }

  def testVariableWithWhitespaceBefore() {
    testTokenizes(" x", VariableToken("x"))
  }

  def testVariableWithWhitespaceAfter() {
    testTokenizes("x ", VariableToken("x"))
  }

  def testVariableContainingReservedWords() {
    testTokenizes("ifelse", VariableToken("ifelse"))
  }

  def testTwoReservedWords() {
    testTokenizes("if else", IfToken, ElseToken)
  }

  def main(args: Array[String]) {
    testLeftParen()
    testRightParen()
    testVariableAlone()
    testVariableWithWhitespaceBefore()
    testVariableWithWhitespaceAfter()
    testVariableContainingReservedWords()
    testTwoReservedWords()
  } // main
} // TokenizerTest
