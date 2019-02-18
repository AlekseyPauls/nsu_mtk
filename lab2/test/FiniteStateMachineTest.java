import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;


public class FiniteStateMachineTest {
        @Test
        public void evenSymbolRecognition() throws FiniteStateMachineException, IOException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("evensymbol.txt");
                assertTrue(fcm.checkSequence("bb"));
                assertTrue(fcm.checkSequence("aaabb"));
                assertTrue(fcm.checkSequence("ababa"));
                assertTrue(fcm.checkSequence("bbaaa"));
                assertTrue(fcm.checkSequence("bbabbbabbabababbb"));
                assertTrue(fcm.checkSequence("bbbb"));
                assertTrue(fcm.checkSequence("a"));
                assertTrue(fcm.checkSequence(""));

                assertFalse(fcm.checkSequence("b"));
                assertFalse(fcm.checkSequence("abaa"));
                assertFalse(fcm.checkSequence("ababab"));
                assertFalse(fcm.checkSequence("bbbbbbbbb"));
        }

        @Test
        public void multipleThreeRecognition() throws FiniteStateMachineException, IOException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("multiplethree.txt");
                assertTrue(fcm.checkSequence(""));
                assertTrue(fcm.checkSequence("ab"));
                assertTrue(fcm.checkSequence("abab"));
                assertTrue(fcm.checkSequence("bbbaaa"));
                assertTrue(fcm.checkSequence("ababbababbbaaa"));
                assertTrue(fcm.checkSequence("bbb"));
                assertTrue(fcm.checkSequence("bbbba"));

                assertFalse(fcm.checkSequence("b"));
                assertFalse(fcm.checkSequence("a"));
                assertFalse(fcm.checkSequence("aba"));
                assertFalse(fcm.checkSequence("abb"));
        }

        @Test
        public void startsWithThreeSymbolsRecognition() throws FiniteStateMachineException, IOException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("startsthree.txt");
                assertTrue(fcm.checkSequence("xxx"));
                assertTrue(fcm.checkSequence("xxxyz"));
                assertTrue(fcm.checkSequence("xxxxxxxxxx"));
                assertTrue(fcm.checkSequence("xxxyxyxzxzzzz"));

                assertFalse(fcm.checkSequence(""));
                assertFalse(fcm.checkSequence("x"));
                assertFalse(fcm.checkSequence("xx"));
                assertFalse(fcm.checkSequence("y"));
                assertFalse(fcm.checkSequence("yyy"));
                assertFalse(fcm.checkSequence("z"));
                assertFalse(fcm.checkSequence("zzzz"));
                assertFalse(fcm.checkSequence("yzy"));
                assertFalse(fcm.checkSequence("yxxx"));
                assertFalse(fcm.checkSequence("xxyxxx"));
        }

        @Test
        public void endsWithThreeSymbolsRecognition() throws FiniteStateMachineException, IOException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("endsthree.txt");
                assertTrue(fcm.checkSequence("xxx"));
                assertTrue(fcm.checkSequence("xxxxx"));
                assertTrue(fcm.checkSequence("xyzxxx"));
                assertTrue(fcm.checkSequence("xxxyxxzxxx"));

                assertFalse(fcm.checkSequence(""));
                assertFalse(fcm.checkSequence("x"));
                assertFalse(fcm.checkSequence("xx"));
                assertFalse(fcm.checkSequence("y"));
                assertFalse(fcm.checkSequence("yyy"));
                assertFalse(fcm.checkSequence("z"));
                assertFalse(fcm.checkSequence("zzzz"));
                assertFalse(fcm.checkSequence("yzy"));
                assertFalse(fcm.checkSequence("yxxxy"));
                assertFalse(fcm.checkSequence("yxx"));
                assertFalse(fcm.checkSequence("yx"));
                assertFalse(fcm.checkSequence("xxyxxzzz"));
        }

        @Test
        public void startsAndEndsWithThreeSymbolsRecognition() throws FiniteStateMachineException, IOException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("startsandendsthree.txt");
                assertTrue(fcm.checkSequence("xxx"));
                assertTrue(fcm.checkSequence("xxxxx"));
                assertTrue(fcm.checkSequence("xxxyxxx"));
                assertTrue(fcm.checkSequence("xxxyxyxyxxxxyxxx"));

                assertFalse(fcm.checkSequence(""));
                assertFalse(fcm.checkSequence("x"));
                assertFalse(fcm.checkSequence("xx"));
                assertFalse(fcm.checkSequence("y"));
                assertFalse(fcm.checkSequence("yyy"));
                assertFalse(fcm.checkSequence("yxxxy"));
                assertFalse(fcm.checkSequence("yxx"));
                assertFalse(fcm.checkSequence("yx"));
                assertFalse(fcm.checkSequence("xxyyxx"));
                assertFalse(fcm.checkSequence("xxyyx"));
                assertFalse(fcm.checkSequence("xyyxxxy"));
                assertFalse(fcm.checkSequence("xyyxx"));
                assertFalse(fcm.checkSequence("xyyx"));
                assertFalse(fcm.checkSequence("xxxyyxx"));
                assertFalse(fcm.checkSequence("xxxyyx"));
        }

        @Test
        public void startsAndEndsWithEqualSymbolsRecognition() throws FiniteStateMachineException, IOException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("startsandendsequal.txt");
                assertTrue(fcm.checkSequence("x"));
                assertTrue(fcm.checkSequence("y"));
                assertTrue(fcm.checkSequence("z"));
                assertTrue(fcm.checkSequence("xx"));
                assertTrue(fcm.checkSequence("yyy"));
                assertTrue(fcm.checkSequence("zzzz"));
                assertTrue(fcm.checkSequence("xyzx"));
                assertTrue(fcm.checkSequence("xxyxx"));
                assertTrue(fcm.checkSequence("zxz"));
                assertTrue(fcm.checkSequence("xyxyx"));

                assertFalse(fcm.checkSequence(""));
                assertFalse(fcm.checkSequence("xy"));
                assertFalse(fcm.checkSequence("xxy"));
                assertFalse(fcm.checkSequence("yz"));
                assertFalse(fcm.checkSequence("yxx"));
                assertFalse(fcm.checkSequence("xxyyxxz"));
                assertFalse(fcm.checkSequence("zxxyyx"));
        }

        @Test(expected = FiniteStateMachineException.class)
        public void unexpectedTransition() throws IOException, FiniteStateMachineException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("evensymbol.txt");
                assertTrue(fcm.checkSequence("123"));
                assertTrue(fcm.checkSequence("ababc"));
                assertTrue(fcm.checkSequence(" "));
                assertTrue(fcm.checkSequence("!!!?)"));
                assertTrue(fcm.checkSequence("a!a, ne, ne a"));
                assertTrue(fcm.checkSequence("  "));
        }

        @Test(expected = FiniteStateMachineException.class)
        public void wrongFileFormat() throws IOException, FiniteStateMachineException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.init("wrongformat1.txt");
                fcm.init("wrongformat2.txt");
                fcm.init("wrongformat3.txt");
                fcm.init("wrongformat4.txt");
                fcm.init("wrongformat5.txt");
        }

        @Test(expected = FiniteStateMachineException.class)
        public void notInitializedUsing() throws IOException, FiniteStateMachineException {
                FiniteStateMachine fcm = new FiniteStateMachine();
                fcm.checkSequence("123");
        }
}
