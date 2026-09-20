package terminal.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the default translations and
 * allows for key generation for use in
 * widgets
 */
public final class Translate {
    private static final List<TranslationKey> KEYS = new ArrayList<>();
    public static final TranslationKey MAIN_MENU = genKey("page.cobbler.main_menu", "Main Menu");

    public static final TranslationKey KEYBIND_WIDGET = genKey("widget.cobbler.keybinds", "Keybinds");
    public static final TranslationKey PROJECTS_WIDGET = genKey("widget.cobbler.projects", "Projects");
    public static final TranslationKey SETTINGS_WIDGET = genKey("widget.cobbler.settings", "Settings");

    public static final TranslationKey DEBUG_INDEX = genKey("debug.cobbler.index", "Index: %s");
    public static final TranslationKey DEBUG_PAGE = genKey("debug.cobbler.page", "Page: %s");
    public static final TranslationKey DEBUG_KEYPRESS = genKey("debug.cobbler.keypress", "Last Keypress: [%s]");

    public static final TranslationKey WIDGET_SELECTOR = genKey("format.cobbler.selector", ">");
    public static final TranslationKey WIDGET_FORMAT = genKey("format.cobbler.widget", " %s %s");

    private static TranslationKey genKey(String id, String fallback) {
        TranslationKey key = new TranslationKey(id, fallback);
        KEYS.add(key);
        return new TranslationKey(id, fallback);
    }

    public static List<TranslationKey> allKeys() {
        return List.copyOf(KEYS);
    }
}