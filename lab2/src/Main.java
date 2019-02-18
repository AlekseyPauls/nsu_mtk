import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            FiniteStateMachine fcm = new FiniteStateMachine();
            fcm.init(args[0]);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[1])));
            String sequence = reader.readLine();

            //System.out.println(fcm.checkSequence(sequence));
            System.out.println(fcm.checkSequence("abaa"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
