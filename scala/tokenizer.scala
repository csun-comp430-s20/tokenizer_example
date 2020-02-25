sealed trait Token
case class VariableToken(name: String) extends Token
case class IntegerToken(value: Int) extends Token
case object IfToken extends Token
case object LeftParenToken extends Token
case object RightParenToken extends Token
case object ElseToken extends Token

case class TokenizerException(msg: String) extends Exception(msg)

object Tokenizer {
  def apply(input: String): Tokenizer = {
    new Tokenizer(input.toCharArray.toList)
  }
}

class Tokenizer(private var input: List[Char]) {
  private def tryTokenizeVariableOrReservedWord(): Option[Token] = {
    @scala.annotation.tailrec
    def readLetters(accum: String): String = {
      input match {
        case head :: tail if Character.isLetterOrDigit(head) => {
          input = tail
          readLetters(accum + head)
        }
        case _ => accum
      }
    }

    input match {
      case head :: tail if Character.isLetter(head) => {
        input = tail
        readLetters("" + head) match {
          case "if" => Some(IfToken)
          case "else" => Some(ElseToken)
          case other => Some(VariableToken(other))
        }
      }
      case _ => None
    }
  } // tryTokenizeVariableOrReservedWord

  private def tryTokenizeInteger(): Option[IntegerToken] = {
    @scala.annotation.tailrec
    def readDigits(accum: String): Option[IntegerToken] = {
      input match {
        case head :: tail if Character.isDigit(head) => {
          input = tail
          readDigits(accum + head)
        }
        case _ => {
          if (accum.length > 0) {
            Some(IntegerToken(accum.toInt))
          } else {
            None
          }
        }
      }
    }

    input match {
      case '-' :: tail => readDigits("-")
      case _ => readDigits("")
    }
  } // tryTokenizeInteger

  @scala.annotation.tailrec
  private def skipWhitespace() {
    input match {
      case head :: tail if Character.isWhitespace(head) => {
        input = tail
        skipWhitespace()
      }
      case _ => ()
    }
  } // skipWhitespace

  // assumes it's not starting on whitespace
  private def tokenizeOne(): Token = {
    tryTokenizeVariableOrReservedWord().getOrElse {
      tryTokenizeInteger().getOrElse {
        input match {
          case '(' :: tail => {
            input = tail
            LeftParenToken
          }
          case ')' :: tail => {
            input = tail
            RightParenToken
          }
          case _ :: _ => {
            throw TokenizerException("Have input, but it's not valid")
          }
          case Nil => {
            throw TokenizerException("Have no more input")
          }
        }
      }
    }
  } // tokenizeOne

  def tokenize(): Seq[Token] = {
    @scala.annotation.tailrec
    def withAccum(accum: List[Token]): Seq[Token] = {
      input match {
        case _ :: _ => {
          skipWhitespace()
          input match {
            case _ :: _ => withAccum(tokenizeOne() :: accum)
            case Nil => accum.reverse.toSeq
          }
        }
        case Nil => accum.reverse.toSeq
      }
    }

    withAccum(List())
  } // tokenize
} // Tokenizer


