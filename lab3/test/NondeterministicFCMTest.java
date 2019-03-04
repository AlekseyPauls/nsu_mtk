import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;


public class NondeterministicFCMTest {
        @Test
        public void startsABRecognition() throws NondeterministicFCMException, IOException {
                NondeterministicFCM fcm = new NondeterministicFCM();
                fcm.init("startsab.txt");
                assertTrue(fcm.checkSequence("ab"));
                assertTrue(fcm.checkSequence("abb"));
                assertTrue(fcm.checkSequence("ababa"));
                assertTrue(fcm.checkSequence("abaaaaaaaaaaaaaaa"));

                assertFalse(fcm.checkSequence("b"));
                assertFalse(fcm.checkSequence("aa"));
                assertFalse(fcm.checkSequence("bbabab"));
                assertFalse(fcm.checkSequence("ba"));
                assertFalse(fcm.checkSequence(""));
        }

        @Test
        public void EndsABRecognition() throws NondeterministicFCMException, IOException {
                NondeterministicFCM fcm = new NondeterministicFCM();
                fcm.init("endsab.txt");
                assertTrue(fcm.checkSequence("ab"));
                assertTrue(fcm.checkSequence("bab"));
                assertTrue(fcm.checkSequence("ababab"));
                assertTrue(fcm.checkSequence("aaaaaaaaaaaaaaab"));

                assertFalse(fcm.checkSequence("b"));
                assertFalse(fcm.checkSequence("aa"));
                assertFalse(fcm.checkSequence("bbabb"));
                assertFalse(fcm.checkSequence("ba"));
                assertFalse(fcm.checkSequence(""));
        }

        @Test
        public void ContainsABRecognition() throws NondeterministicFCMException, IOException {
                NondeterministicFCM fcm = new NondeterministicFCM();
                fcm.init("containsab.txt");
                assertTrue(fcm.checkSequence("ab"));
                assertTrue(fcm.checkSequence("abb"));
                assertTrue(fcm.checkSequence("baba"));
                assertTrue(fcm.checkSequence("abaaaaaaaaaaaaaaa"));
                assertTrue(fcm.checkSequence("bbbbbbbbaaaaaaaab"));

                assertFalse(fcm.checkSequence("b"));
                assertFalse(fcm.checkSequence("aa"));
                assertFalse(fcm.checkSequence("bbba"));
                assertFalse(fcm.checkSequence("bbbbb"));
                assertFalse(fcm.checkSequence(""));
        }

        @Test
        public void startsAAndEndsBRecognition() throws NondeterministicFCMException, IOException {
                NondeterministicFCM fcm = new NondeterministicFCM();
                fcm.init("startsaandendsb.txt");
                assertTrue(fcm.checkSequence("ab"));
                assertTrue(fcm.checkSequence("abb"));
                assertTrue(fcm.checkSequence("abaabababbbbabb"));
                assertTrue(fcm.checkSequence("abaaaaaaaaaaaaaab"));

                assertFalse(fcm.checkSequence("b"));
                assertFalse(fcm.checkSequence("aa"));
                assertFalse(fcm.checkSequence("bbabab"));
                assertFalse(fcm.checkSequence("ba"));
                assertFalse(fcm.checkSequence(""));
        }

        @Test(expected = NondeterministicFCMException.class)
        public void unexpectedTransition() throws IOException, NondeterministicFCMException {
                NondeterministicFCM fcm = new NondeterministicFCM();
                fcm.init("startsab.txt");
                assertTrue(fcm.checkSequence("123"));
                assertTrue(fcm.checkSequence("ababc"));
                assertTrue(fcm.checkSequence(" "));
                assertTrue(fcm.checkSequence("!!!?)"));
                assertTrue(fcm.checkSequence("a!a, ne, ne a"));
        }

        @Test(expected = NondeterministicFCMException.class)
        public void wrongFileFormat() throws IOException, NondeterministicFCMException {
                NondeterministicFCM fcm = new NondeterministicFCM();
                fcm.init("wrongformat1.txt");
                fcm.init("wrongformat2.txt");
                fcm.init("wrongformat3.txt");
                fcm.init("wrongformat4.txt");
                fcm.init("wrongformat5.txt");
                fcm.init("wrongformat6.txt");
                fcm.init("wrongformat7.txt");
                fcm.init("wrongformat8.txt");
        }

        @Test(expected = NondeterministicFCMException.class)
        public void notInitializedUsing() throws IOException, NondeterministicFCMException {
                NondeterministicFCM fcm = new NondeterministicFCM();
                fcm.checkSequence("123");
        }
}
