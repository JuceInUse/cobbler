package terminal.input;

import java.io.IOException;
import java.util.ArrayList;

public final class Keypress {
    /** Blocks input until keypress and clears byte buffer */
    public static ArrayList<Integer> readKey() throws IOException {
        ArrayList<Integer> key = new ArrayList<>();
        do {
            key.add(System.in.read());
        } while (System.in.available() > 0);
        return key;
    }
}