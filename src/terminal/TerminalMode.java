package terminal;

import java.io.IOException;

public class TerminalMode {
    private static final String[] RAW_MODE = new String[]{"stty", "raw", "-echo"};
    private static final String[] COOKED_MODE = new String[]{"stty", "sane"};
    public static final String CLEAR = "\u001B[2J\u001B[H";
    public static final String RESTORE = "\u001b[?1049l";
    public static final String ENTER_ALT = "\u001b[?1049h\u001B[?25l";
    public static final String EXIT_ALT = "\u001b[?1049l\u001B[?25h";

    public static void setRaw(boolean raw) {
        ProcessBuilder builder = new ProcessBuilder(raw ? RAW_MODE : COOKED_MODE);
        try {
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        IO.print(raw ? ENTER_ALT + CLEAR : EXIT_ALT + RESTORE);
    }
}