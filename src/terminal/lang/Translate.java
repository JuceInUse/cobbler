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
    public static final TranslationKey PAGE_MAIN_MENU = genKey("page.cobbler.main_menu", "Main Menu");
    public static final TranslationKey PAGE_KEYBINDS = genKey("page.cobbler.keybinds", "Keybinds");
    public static final TranslationKey PAGE_SETTINGS = genKey("page.cobbler.settings", "Settings");

    public static final TranslationKey KEYBIND_WIDGET_KEYBINDS = genKey("widget.cobbler.keybind", "%s: %s");
    public static final TranslationKey PAGE_WIDGET_KEYBINDS = genKey("widget.cobbler.keybinds", "Keybinds");
    public static final TranslationKey PAGE_WIDGET_PROJECTS = genKey("widget.cobbler.projects", "Projects");
    public static final TranslationKey PAGE_WIDGET_SETTINGS = genKey("widget.cobbler.settings", "Settings");

    public static final TranslationKey ERROR_KEY_IN_USE = genKey("error.cobbler.key_in_use", "'%s' already used by '%s'");
    public static final TranslationKey ERROR_SAVE_FAILED = genKey("error.cobbler.save_failed", "Could not save keybind '%s'");

    public static final TranslationKey SUCCESS_SAVED_KEYBINDS = genKey("success.cobbler.saved_keybinds", "Mapped '%s' to '%s'");

    public static final TranslationKey DEBUG_INDEX = genKey("debug.cobbler.index", "Index: %s");
    public static final TranslationKey DEBUG_PAGE = genKey("debug.cobbler.page", "Page: %s");
    public static final TranslationKey DEBUG_KEYPRESS = genKey("debug.cobbler.keypress", "Last Keypress: %s[%s]%s");
    public static final TranslationKey DEBUG_KEYPRESS_ACTION = genKey("debug.cobbler.keypress_action", "%s ");
    public static final TranslationKey DEBUG_KEYPRESS_ALIAS = genKey("debug.cobbler.keypress_alias", " (%s)");
    public static final TranslationKey DEBUG_TOPAGE = genKey("debug.cobbler.topage", "toPage: %s");

    public static final TranslationKey FORMAT_CAPTURE = genKey("format.cobbler.capture", " (Press Any Key)");
    public static final TranslationKey FORMAT_SELECTOR = genKey("format.cobbler.selector", ">");
    public static final TranslationKey FORMAT_WIDGET = genKey("format.cobbler.widget", " %s %s");

    private static TranslationKey genKey(String id, String fallback) {
        TranslationKey key = new TranslationKey(id, fallback);
        KEYS.add(key);
        return key;
    }

    public static List<TranslationKey> allKeys() {
        return List.copyOf(KEYS);
    }
}