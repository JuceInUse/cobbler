package terminal;

import java.io.IOException;

/**
 * Handles the changing of a terminal from raw to
 * cooked mode. Currently only supports Unix-based
 * terminals (I think?)
 */

public class TerminalMode {
    private static final String[] RAW_MODE = {"/bin/sh", "-c", "stty raw -echo </dev/tty"};
    private static final String[] COOKED_MODE = {"/bin/sh", "-c", "stty sane </dev/tty"};

    public static void enableRaw() throws IOException, InterruptedException {
        Runtime.getRuntime().exec(RAW_MODE).waitFor();
    }

    public static void enableCooked() throws IOException, InterruptedException {
        Runtime.getRuntime().exec(COOKED_MODE).waitFor();
    }
}