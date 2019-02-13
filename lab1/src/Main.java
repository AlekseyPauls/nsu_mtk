import java.io.IOException;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
        String test = "2 / 0 + 5";
        StringReader reader = new StringReader(test);
        try {
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            long answer = parser.execute();
            System.out.println(answer);
        }catch (ParserException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
