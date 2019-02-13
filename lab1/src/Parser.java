import java.io.IOException;

public class Parser {
    private Lexeme curLexeme;
    private final Lexer lexer;

    public Parser(Lexer lexer) throws IOException, ParserException {
        this.lexer = lexer;
        this.curLexeme = lexer.getLexeme();
    }

    private Lexeme readLexeme() throws IOException, ParserException {
        Lexeme res = this.curLexeme;
        this.curLexeme = lexer.getLexeme();
        return res;
    }

    public long calculate() throws IOException, ParserException {
        long res = parseExpr();
        if (curLexeme.getType() != LexemeType.EOF) {
            throw new ParserException("Wrong expression: EOF expected but not found");
        }
        return res;
    }

    long parseExpr() throws IOException, ParserException {
        long value = parseTerm();
        while (curLexeme.getType() == LexemeType.ADD || curLexeme.getType() == LexemeType.SUB) {
            Lexeme cur = readLexeme();
            if (cur.getType() == LexemeType.ADD) {
                value += parseTerm();
            } else {
                value -= parseTerm();
            }
        }
        return value;
    }

    long parseTerm() throws IOException, ArithmeticException, ParserException {
        long value = parseFactor();
        while (curLexeme.getType() == LexemeType.MULT || curLexeme.getType() == LexemeType.DIV) {
            Lexeme cur = readLexeme();
            if (cur.getType() == LexemeType.MULT) {
                value *= parseFactor();
            } else {
                value /= parseFactor();
            }
        }
        return value;
    }

    long parseFactor() throws IOException, ParserException {
        long power = parsePower();
        if (curLexeme.getType() == LexemeType.POW) {
            readLexeme();
            power = (long)Math.pow(power, parseFactor());
        }
        return power;
    }

    long parsePower() throws IOException, ParserException {
        if (curLexeme.getType() == LexemeType.SUB) {
            readLexeme();
            return -parseAtom();
        }
        return parseAtom();
    }

    long parseAtom() throws IOException, ParserException {
        if (curLexeme.getType() == LexemeType.NUMBER) {
            int res = Integer.parseInt(curLexeme.getValue());
            readLexeme();
            return res;
        } else if (curLexeme.getType() == LexemeType.OPEN) {
            readLexeme();
            long expr = parseExpr();
            if (curLexeme.getType() != LexemeType.CLOSE)
                throw new ParserException("Wrong expression: expected close bracket but not found");
            readLexeme();
            return expr;
        } else {
            throw new ParserException("Wrong expression: undefined lexeme");
        }
    }
}
