package terminal.widget;

import terminal.input.Action;
import terminal.input.Keybinds;
import terminal.input.Navigator;
import terminal.lang.Translate;

import java.util.Arrays;
import java.util.List;

/**
 * Widgets for usage in rebinding
 * use this format
 */
public final class KeybindWidget implements Widget {
    private static final int MAX_WIDTH = Arrays.stream(Action.values())
            .mapToInt(a -> a.name().length())
            .max()
            .orElse(0);

    private final Action action;

    public KeybindWidget(Action action) { this.action = action; }

    public Action action() { return action; }

    @Override
    public String label() {
        int actionWidth = action.name().length();
        List<Integer> keys = Keybinds.keyOf(action);
        return String.format(
                Translate.KEYBIND_WIDGET_KEYBINDS.get(),
                " ".repeat(MAX_WIDTH - actionWidth) + action.name(),
                Keybinds.keyLabel(keys));
    }

    @Override
    public void onEnter(Navigator nav) {
        nav.startRebind(action);
    }
}