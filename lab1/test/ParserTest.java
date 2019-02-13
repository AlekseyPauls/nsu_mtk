import org.junit.Test;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;


public class ParserTest {

    @Test
    public void simpleNumber() throws IOException, ParserException {
        assertEquals((new Parser(new Lexer(new StringReader("1")))).calculate(), 1);
        assertEquals((new Parser(new Lexer(new StringReader("300")))).calculate(), 300);
        assertEquals((new Parser(new Lexer(new StringReader("007")))).calculate(), 7);
    }

    @Test
    public void simpleOperation() throws IOException, ParserException {
        assertEquals((new Parser(new Lexer(new StringReader("1 + 1")))).calculate(), 2);
        assertEquals((new Parser(new Lexer(new StringReader("2 - 3")))).calculate(), -1);
        assertEquals((new Parser(new Lexer(new StringReader("7 * 7 * 7")))).calculate(), 343);
        assertEquals((new Parser(new Lexer(new StringReader("6 / 3")))).calculate(), 2);
        assertEquals((new Parser(new Lexer(new StringReader("3 ^ 3")))).calculate(), 27);
    }

    @Test
    public void simpleBrackets() throws IOException, ParserException {
        assertEquals((new Parser(new Lexer(new StringReader("((1))")))).calculate(), 1);
        assertEquals((new Parser(new Lexer(new StringReader("(300)")))).calculate(), 300);
        assertEquals((new Parser(new Lexer(new StringReader("(-1 + 1)")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader("(2) - (3)")))).calculate(), -1);
        assertEquals((new Parser(new Lexer(new StringReader("(7 * 7 )* 7")))).calculate(), 343);
        assertEquals((new Parser(new Lexer(new StringReader("(6\t/\t3)")))).calculate(), 2);
        assertEquals((new Parser(new Lexer(new StringReader("(3 ^ 3)")))).calculate(), 27);
    }

    @Test
    public void operationsOrder() throws IOException, ParserException {
        assertEquals((new Parser(new Lexer(new StringReader("2 + 2 * 3")))).calculate(), 8);
        assertEquals((new Parser(new Lexer(new StringReader("2 * 2 * 2 / 2 + 1")))).calculate(), 5);
        assertEquals((new Parser(new Lexer(new StringReader("(100 - 500)/(4)")))).calculate(), -100);
    }

    @Test
    public void hardComplexExpressions() throws IOException, ParserException {
        assertEquals((new Parser(new Lexer(new StringReader("2 + (((2 * 3)) - 4 / 2) + 25 - 2 ^ (2 * 2)")))).calculate(), 15);
        assertEquals((new Parser(new Lexer(new StringReader("2 ^ 2 ^ 2")))).calculate(), 16);
        assertEquals((new Parser(new Lexer(new StringReader("1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9")))).calculate(), 362880);
    }

    @Test(expected = ParserException.class)
    public void wrongOperationsSyntax() throws IOException, ParserException {
        assertEquals((new Parser(new Lexer(new StringReader("")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader(",")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader("2 4")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader("- +")))).calculate(), 8);
        assertEquals((new Parser(new Lexer(new StringReader(" * 3")))).calculate(), 8);
        assertEquals((new Parser(new Lexer(new StringReader("4 /")))).calculate(), 8);
    }

    @Test(expected = ParserException.class)
    public void wrongBracketsSyntax() throws IOException, ParserException {
        assertEquals((new Parser(new Lexer(new StringReader("(")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader(")")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader("( )")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader(")\t(")))).calculate(), 8);
        assertEquals((new Parser(new Lexer(new StringReader("(5 + 4")))).calculate(), 8);
        assertEquals((new Parser(new Lexer(new StringReader(")4")))).calculate(), 8);
        assertEquals((new Parser(new Lexer(new StringReader("(3 * 76))")))).calculate(), 8);
    }

    @Test(expected = ArithmeticException.class)
    public void divisionByZeroAndInfinity() throws IOException, ParserException, ArithmeticException {
        assertEquals((new Parser(new Lexer(new StringReader("1/0")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader("2 * 3 / (4 - 2^2)")))).calculate(), 0);
        assertEquals((new Parser(new Lexer(new StringReader("90 ^ 90")))).calculate(), 0);
    }
}
