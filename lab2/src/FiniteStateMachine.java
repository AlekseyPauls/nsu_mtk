import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FiniteStateMachine {
    private ArrayList<Integer> finalStates = new ArrayList<>();
    private HashMap<Integer, HashMap<Character, Integer>>  transitionRules = new HashMap<>();
    private int currentState = 1;

    public FiniteStateMachine() {}

    public void init(String fileName) throws FiniteStateMachineException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;

        while ((line = reader.readLine()) != null && line.startsWith("#")){}
        if (line == null) {
            throw new FiniteStateMachineException("Wrong file format");
        }

        for (String number : line.replace("\n", "").split(" ")) {
            try {
                int i = Integer.valueOf(number);
                if (i < 1) {
                    throw new FiniteStateMachineException("Wrong file format");
                }
            } catch (NumberFormatException e) {
                throw new FiniteStateMachineException("Wrong file format");
            }
            finalStates.add(Integer.valueOf(number));
        }
        while ((line = reader.readLine()) != null){
            if (line.startsWith("#")) { continue; }

            String[] tmp = line.replace("\n", "").split(" ");
            if (tmp.length != 3) {
                throw new FiniteStateMachineException("Wrong file format");
            }

            int startState = Integer.valueOf(tmp[0]);
            char symbol = tmp[1].toCharArray()[0];
            int endState = Integer.valueOf(tmp[2]);

            if (!transitionRules.containsKey(startState)) {
                transitionRules.put(startState, new HashMap<>());
            }
            if (transitionRules.get(startState).containsKey(symbol)) {
                throw new FiniteStateMachineException("Duplicate transition");
            }
            transitionRules.get(startState).put(symbol, endState);
        }
    }

    public boolean checkSequence(String seq) throws FiniteStateMachineException {
        currentState = 1;
        if (finalStates.isEmpty() || transitionRules.isEmpty()) {
            throw new FiniteStateMachineException("No init yet");
        }

        for (Character c : seq.toCharArray()) {
           if (!transitionRules.get(currentState).containsKey(c)) {
               throw new FiniteStateMachineException("Unexpected transition");
           }

           currentState = transitionRules.get(currentState).get(c);
        }

        return finalStates.contains(currentState);
    }

}
