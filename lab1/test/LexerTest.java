import org.junit.Test;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;


public class LexerTest {
        public void assertLexemeTypes(String input, LexemeType... types) throws IOException, ParserException {
                Lexer lexer = new Lexer(new StringReader(input));
                for (LexemeType type : types) {
                        assertEquals(type, lexer.getLexeme().getType());
                }
        }

        public void assertLexemeValues(String input, String... values) throws IOException, ParserException {
                Lexer lexer = new Lexer(new StringReader(input));
                for (String value : values) {
                        assertEquals(value, lexer.getLexeme().getValue());
                }
        }

        @Test
        public void simpleLexemeTypeNumber() throws IOException, ParserException {
                assertLexemeTypes("0", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("1", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("2", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("3", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("4", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("5", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("6", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("7", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("8", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("9", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("012345678987654321", LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("33333333333333333333333333333333333333333", LexemeType.NUMBER, LexemeType.EOF);
        }

        @Test
        public void simpleLexemeTypeExceptNumber() throws IOException, ParserException {
                assertLexemeTypes("", LexemeType.EOF);
                assertLexemeTypes(" ", LexemeType.EOF);
                assertLexemeTypes("\t", LexemeType.EOF);
                assertLexemeTypes("   ", LexemeType.EOF);
                assertLexemeTypes("\t\t\t", LexemeType.EOF);
                assertLexemeTypes("\t \t ", LexemeType.EOF);
                assertLexemeTypes("+", LexemeType.ADD, LexemeType.EOF);
                assertLexemeTypes("-", LexemeType.SUB, LexemeType.EOF);
                assertLexemeTypes("*", LexemeType.MULT, LexemeType.EOF);
                assertLexemeTypes("/", LexemeType.DIV, LexemeType.EOF);
                assertLexemeTypes("^", LexemeType.POW, LexemeType.EOF);
                assertLexemeTypes("(", LexemeType.OPEN, LexemeType.EOF);
                assertLexemeTypes(")", LexemeType.CLOSE, LexemeType.EOF);
        }

        @Test
        public void simpleLexemeValueNumber() throws IOException, ParserException {
                assertLexemeValues("0", "0");
                assertLexemeValues("1", "1");
                assertLexemeValues("2", "2");
                assertLexemeValues("3", "3");
                assertLexemeValues("4", "4");
                assertLexemeValues("5", "5");
                assertLexemeValues("6", "6");
                assertLexemeValues("7", "7");
                assertLexemeValues("8", "8");
                assertLexemeValues("9", "9");
                assertLexemeValues("0123456789", "0123456789");
                assertLexemeValues("55555555555555555555555", "55555555555555555555555");
        }

        // Testing for values of not NUMBER lexemes is useful - this values never using and we already checked types
        // (if the type is correct then the value is correct for note NUMBER lexemes)

        @Test
        public void complexLexemeTypeExceptNumber() throws IOException, ParserException {
                assertLexemeTypes("+-*/^()", LexemeType.ADD, LexemeType.SUB, LexemeType.MULT, LexemeType.DIV, LexemeType.POW, LexemeType.OPEN, LexemeType.CLOSE, LexemeType.EOF);
                assertLexemeTypes("+++", LexemeType.ADD, LexemeType.ADD, LexemeType.ADD, LexemeType.EOF);
                assertLexemeTypes("---", LexemeType.SUB, LexemeType.SUB, LexemeType.SUB, LexemeType.EOF);
                assertLexemeTypes("***", LexemeType.MULT, LexemeType.MULT, LexemeType.MULT, LexemeType.EOF);
                assertLexemeTypes("///", LexemeType.DIV, LexemeType.DIV, LexemeType.DIV, LexemeType.EOF);
                assertLexemeTypes("^^^", LexemeType.POW, LexemeType.POW, LexemeType.POW, LexemeType.EOF);
                assertLexemeTypes("(((", LexemeType.OPEN, LexemeType.OPEN, LexemeType.OPEN, LexemeType.EOF);
                assertLexemeTypes(")))", LexemeType.CLOSE, LexemeType.CLOSE, LexemeType.CLOSE, LexemeType.EOF);
                assertLexemeTypes("+ - * / ^ ( )", LexemeType.ADD, LexemeType.SUB, LexemeType.MULT, LexemeType.DIV, LexemeType.POW, LexemeType.OPEN, LexemeType.CLOSE, LexemeType.EOF);
                assertLexemeTypes(" + + +", LexemeType.ADD, LexemeType.ADD, LexemeType.ADD, LexemeType.EOF);
                assertLexemeTypes(" - - -", LexemeType.SUB, LexemeType.SUB, LexemeType.SUB, LexemeType.EOF);
                assertLexemeTypes(" * * *", LexemeType.MULT, LexemeType.MULT, LexemeType.MULT, LexemeType.EOF);
                assertLexemeTypes(" / / /", LexemeType.DIV, LexemeType.DIV, LexemeType.DIV, LexemeType.EOF);
                assertLexemeTypes(" ^ ^ ^", LexemeType.POW, LexemeType.POW, LexemeType.POW, LexemeType.EOF);
                assertLexemeTypes("( ( (", LexemeType.OPEN, LexemeType.OPEN, LexemeType.OPEN, LexemeType.EOF);
                assertLexemeTypes(" ) ) )", LexemeType.CLOSE, LexemeType.CLOSE, LexemeType.CLOSE, LexemeType.EOF);
                assertLexemeTypes("+\t-\t*\t/\t^\t(\t)", LexemeType.ADD, LexemeType.SUB, LexemeType.MULT, LexemeType.DIV, LexemeType.POW, LexemeType.OPEN, LexemeType.CLOSE, LexemeType.EOF);
                assertLexemeTypes("\t+\t+\t+", LexemeType.ADD, LexemeType.ADD, LexemeType.ADD, LexemeType.EOF);
                assertLexemeTypes("\t-\t-\t-", LexemeType.SUB, LexemeType.SUB, LexemeType.SUB, LexemeType.EOF);
                assertLexemeTypes("\t*\t*\t*", LexemeType.MULT, LexemeType.MULT, LexemeType.MULT, LexemeType.EOF);
                assertLexemeTypes("\t/\t/\t/", LexemeType.DIV, LexemeType.DIV, LexemeType.DIV, LexemeType.EOF);
                assertLexemeTypes("\t^\t^\t^", LexemeType.POW, LexemeType.POW, LexemeType.POW, LexemeType.EOF);
                assertLexemeTypes("\t(\t(\t(", LexemeType.OPEN, LexemeType.OPEN, LexemeType.OPEN, LexemeType.EOF);
                assertLexemeTypes("\t)\t)\t)", LexemeType.CLOSE, LexemeType.CLOSE, LexemeType.CLOSE, LexemeType.EOF);
        }


        @Test
        public void complexLexemeTypeNumber() throws IOException, ParserException {
                assertLexemeTypes(" 3 + 4/5", LexemeType.NUMBER, LexemeType.ADD, LexemeType.NUMBER, LexemeType.DIV, LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("* 123 //17", LexemeType.MULT, LexemeType.NUMBER, LexemeType.DIV, LexemeType.DIV, LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("\t3\t+ \t4/5", LexemeType.NUMBER, LexemeType.ADD, LexemeType.NUMBER, LexemeType.DIV, LexemeType.NUMBER, LexemeType.EOF);
                assertLexemeTypes("*\t 123\t \t//17", LexemeType.MULT, LexemeType.NUMBER, LexemeType.DIV, LexemeType.DIV, LexemeType.NUMBER, LexemeType.EOF);
        }

        @Test
        public void complexLexemeTypeValue() throws IOException, ParserException {
                assertLexemeValues(" 3 + 4/5", "3", "+", "4", "/", "5");
                assertLexemeValues("* 123 //17", "*", "123", "/", "/", "17");
                assertLexemeValues("\t3\t+ \t4/5", "3", "+", "4", "/", "5");
                assertLexemeValues("*\t 123\t \t//17", "*", "123", "/", "/", "17");
        }


        @Test(expected = ParserException.class)
        public void wrongLexeme() throws IOException, ParserException {
                assertLexemeTypes(".", LexemeType.EOF);
                assertLexemeTypes("***,", LexemeType.EOF);
                assertLexemeTypes("!&$#", LexemeType.EOF);
                assertLexemeTypes("5 + 4 _ 2", LexemeType.NUMBER, LexemeType.ADD, LexemeType.NUMBER, LexemeType.MULT, LexemeType.NUMBER, LexemeType.EOF);
        }
}
