public class Lexeme {
    private LexemeType type;
    private String value;

    public Lexeme(LexemeType type, String lexeme) {
        this.type = type;
        this.value = lexeme;
    }

    public LexemeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
