import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class NondeterministicFCM {
    private ArrayList<Integer> finalStates = new ArrayList<>();
    private HashMap<Integer, HashMap<Character, ArrayList<Integer>>>  transitionRules = new HashMap<>();
    private int defaultCurrentState = 1;

    private class FCMConf {
        public int pos;
        public int state;

        public FCMConf(int pos, int state) {
            this.pos = pos;
            this.state = state;
        }
    }

    public NondeterministicFCM() {}

    public void init(String fileName) throws NondeterministicFCMException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;

        while ((line = reader.readLine()) != null && line.startsWith("#")){}
        if (line == null) {
            throw new NondeterministicFCMException("Wrong file format");
        }

        for (String number : line.replace("\n", "").split(" ")) {
            try {
                int i = Integer.valueOf(number);
                if (i < 1) {
                    throw new NondeterministicFCMException("Wrong file format: incorrect final state");
                }
            } catch (NumberFormatException e) {
                throw new NondeterministicFCMException("Wrong file format: incorrect final state");
            }
            finalStates.add(Integer.valueOf(number));
        }
        if (finalStates.isEmpty()) {
            throw new NondeterministicFCMException("Wrong file format: empty final states");
        }

        while ((line = reader.readLine()) != null){
            if (line.startsWith("#")) { continue; }

            String[] tmp = line.replace("\n", "").split(" ");
            if (tmp.length != 3) {
                throw new NondeterministicFCMException("Wrong file format");
            }

            int startState = Integer.valueOf(tmp[0]);
            char symbol = tmp[1].toCharArray()[0];
            int endState = Integer.valueOf(tmp[2]);
            try {
                startState = Integer.valueOf(tmp[0]);
                symbol = tmp[1].toCharArray()[0];
                endState = Integer.valueOf(tmp[2]);
                if (startState < 1 || endState < 1) {
                    throw new NondeterministicFCMException("Wrong file format: incorrect start or end state state");
                }
                if (!Character.isLetter(symbol)) {
                    throw new NondeterministicFCMException("Wrong file format: incorrect transition symbol");
                }
            } catch (NumberFormatException e) {
                throw new NondeterministicFCMException("Wrong file format: incorrect start or end state");
            }

            if (!transitionRules.containsKey(startState)) {
                transitionRules.put(startState, new HashMap<>());
            }
            if (!transitionRules.get(startState).containsKey(symbol)) {
                transitionRules.get(startState).put(symbol, new ArrayList<>());
            }
            if (transitionRules.get(startState).get(symbol).contains(endState)) {
                throw new NondeterministicFCMException("Duplicate transition");
            }
            transitionRules.get(startState).get(symbol).add(endState);
        }
        if (transitionRules.isEmpty()) {
            throw new NondeterministicFCMException("Wrong file format: empty transition rules");
        }
    }

    public boolean checkSequence(String seq) throws NondeterministicFCMException {
        Stack<FCMConf> confStack = new Stack<>();
        confStack.push(new FCMConf(0, defaultCurrentState));

        if (finalStates.isEmpty() || transitionRules.isEmpty()) {
            throw new NondeterministicFCMException("No init yet");
        }

        boolean recognized = false;
        while (!confStack.empty()) {
            FCMConf curConf = confStack.pop();
            if (curConf.pos == seq.length()) {
                if (finalStates.contains(curConf.state)) {
                    recognized = true;
                }
                continue;
            }
            if (!Character.isLetter(seq.charAt(curConf.pos))) {
                throw new NondeterministicFCMException("Bad sequence: unexpected symbol");
            }
            if (!transitionRules.containsKey(curConf.state) ||
                    !transitionRules.get(curConf.state).containsKey(seq.charAt(curConf.pos))) {
                continue;
            }
            for (Integer endState : transitionRules.get(curConf.state).get(seq.charAt(curConf.pos))) {
                confStack.push(new FCMConf(curConf.pos + 1, endState));
            }
        }

        return recognized;
    }
}
