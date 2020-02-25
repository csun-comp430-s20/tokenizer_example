public class VariableToken implements Token {
    public final String name;

    public VariableToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        if (other instanceof VariableToken) {
            final VariableToken otherVar = (VariableToken)other;
            return name.equals(otherVar.name);
        } else {
            return false;
        }
    }
}

