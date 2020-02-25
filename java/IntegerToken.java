public class IntegerToken implements Token {
    public final int value;

    public IntegerToken(final int value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        if (other instanceof IntegerToken) {
            final IntegerToken otherInt = (IntegerToken)other;
            return value == otherInt.value;
        } else {
            return false;
        }
    }
}
