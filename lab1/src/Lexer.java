import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private Reader reader;
    private int curLexeme;

    public Lexer(Reader reader) throws IOException{
        this.reader = reader;
        curLexeme = reader.read();
    }

    public Lexeme getLexeme()throws IOException, ParserException {
        while (curLexeme == ' ' || curLexeme == '\t')
            nextLexeme();

        int lexeme = nextLexeme();

        if (lexeme == -1) {
            return new Lexeme(LexemeType.EOF, "EOF");
        } else if (Character.isDigit(lexeme)) {
            StringBuffer number = new StringBuffer(lexeme);
            while (Character.isDigit(this.curLexeme)) {
                number.append(nextLexeme());
            }
            return new Lexeme(LexemeType.NUMBER, number.toString());
        } else if (lexeme == '(') {
            return new Lexeme(LexemeType.OPEN, "(");
        } else if (lexeme == ')') {
            return new Lexeme(LexemeType.CLOSE, ")");
        } else if (lexeme == '+') {
            return new Lexeme(LexemeType.ADD, "+");
        } else if (lexeme == '-') {
            return new Lexeme(LexemeType.SUB, "-");
        } else if (lexeme == '*') {
            return new Lexeme(LexemeType.MULT, "*");
        } else if (lexeme == '/') {
            return new Lexeme(LexemeType.DIV, "/");
        } else if (lexeme == '^') {
            return new Lexeme(LexemeType.POW, "^");
        } else {
            throw new ParserException("Wrong input: undefined lexeme");
        }
    }

    private int nextLexeme() throws IOException{
        int ret = this.curLexeme;
        this.curLexeme = reader.read();
        return ret;
    }
}
