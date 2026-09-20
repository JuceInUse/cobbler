package terminal.color;

public record Color(int r, int g, int b) {
    public static final String RESET = "\u001B[0m";

    public String foreground(String str) { return String.format("\u001B[38;2;%s;%s;%sm%s", r, g, b, str + RESET); }
    public String background(String str) { return String.format("\u001B[48;2;%s;%s;%sm%s", r, g, b, str + RESET); }
}