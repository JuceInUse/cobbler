package terminal.lang;

import terminal.color.Color;
import terminal.color.Colors;

public record Status(String text, AppendType type) {
    public static Status info(String text) {return new Status(text, AppendType.INFO); }
    public static Status error(String text) {return new Status(text, AppendType.ERROR); }
    public static Status success(String text) {return new Status(text, AppendType.SUCCESS); }

    public Color color() {
        return switch (type) {
            case INFO -> null;
            case ERROR -> Colors.ERROR;
            case SUCCESS -> Colors.SUCCESS;
        };
    }
}
