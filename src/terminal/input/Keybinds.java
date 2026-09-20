package terminal.input;

import terminal.lang.Status;
import terminal.lang.Translate;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public final class Keybinds {
    private static final String RESOURCES_DIR = "resources";
    private static final File CONFIG_FILE = new File(RESOURCES_DIR + "/config", "keybinds.properties");
    /** Default Bindings */
    private static final Map<List<Integer>, Action> BINDS = new HashMap<>(Map.ofEntries(
            entry(List.of(27, 91, 65), Action.UP),
            entry(List.of(27, 91, 66), Action.DOWN),
            entry(List.of(13), Action.ENTER),
            entry(List.of(127), Action.EXIT),
            entry(List.of(27, 79, 82), Action.DEBUG)
    ));

    private static final Map<List<Integer>, String> ALIASES = Map.ofEntries(
            entry(List.of(27, 91, 65), "↑"),
            entry(List.of(27, 91, 66), "↓"),
            entry(List.of(27, 91, 67), "→"),
            entry(List.of(27, 91, 68), "←"),
            entry(List.of(13), "↵ Enter"),
            entry(List.of(127), "⌫ Backspace"),
            entry(List.of(9), "⭾ Tab"),
            entry(List.of(27), "Escape"),
            entry(List.of(27, 91, 50, 126), "Insert"),
            entry(List.of(27, 91, 51, 126), "Delete"),
            entry(List.of(27, 91, 72), "Home"),
            entry(List.of(27, 91, 70), "End"),
            entry(List.of(27, 91, 53, 126), "Page Up"),
            entry(List.of(27, 91, 54, 126), "Page Down"),
            entry(List.of(27, 79, 80), "F1"),
            entry(List.of(27, 79, 81), "F2"),
            entry(List.of(27, 79, 82), "F3"),
            entry(List.of(27, 79, 83), "F4"),
            entry(List.of(27, 91, 49, 53, 126), "F5"),
            entry(List.of(27, 91, 49, 55, 126), "F6"),
            entry(List.of(27, 91, 49, 56, 126), "F7"),
            entry(List.of(27, 91, 49, 57, 126), "F8"),
            entry(List.of(27, 91, 50, 48, 126), "F9"),
            entry(List.of(27, 91, 50, 52, 126), "F12")
    );

    private static boolean isPrintableSingle(List<Integer> key) {
        return key.size() == 1 && 32 <= key.getFirst() && key.getFirst() <= 126;
    }

    public static Action get(List<Integer> key) { return BINDS.get(key); }

    public static String keyLabel(List<Integer> key) {
        String alias = ALIASES.get(key);
        if (key == null) return "N/A";
        if (isPrintableSingle(key)) return String.valueOf((char) (int) key.getFirst());
        if (alias != null) return alias;
        return key.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    public static List<Integer> keyOf(Action action) {
        for (Map.Entry<List<Integer>, Action> entry : BINDS.entrySet()) {
            if (entry.getValue() == action) return entry.getKey();
        }
        return null;
    }

    public static Status rebind(Action action, List<Integer> newKey) {
        Action exists = BINDS.get(newKey);
        if (exists != null && exists != action) {
            return Status.error(String.format(Translate.ERROR_KEY_IN_USE.get(), keyLabel(newKey), exists.name()));
        }

        List<Integer> oldKey = keyOf(action);
        if (oldKey != null) BINDS.remove(oldKey);
        BINDS.put(List.copyOf(newKey), action);


        return save(action, newKey);
    }

    public static void load() throws IOException {
        Properties props = new Properties();

        if (CONFIG_FILE.exists()) {
            try (InputStream in = new FileInputStream(CONFIG_FILE)) {
                props.load(in);
            }
        } else { CONFIG_FILE.getParentFile().mkdirs(); }

        for (Action action : Action.values()) {
            String raw = props.getProperty(action.name());
            if (raw == null) continue;

            List<Integer> key = parseKey(raw);
            if (key == null) continue;

            List<Integer> old = keyOf(action);
            if (old != null) BINDS.remove(old);
            BINDS.put(key, action);
        }
        save(null, null);
    }

    private static Status save(Action action, List<Integer> key) {
        Properties props = new Properties();
        for (Map.Entry<List<Integer>, Action> entry: BINDS.entrySet()) {
            props.setProperty(entry.getValue().name(), serializeKey(entry.getKey()));
        }

        try (OutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Keybinds");
            return (action == null && key == null) ?
                    null :
                    Status.success(String.format(Translate.SUCCESS_SAVED_KEYBINDS.get(), action, keyLabel(key)));
        } catch (IOException e) {
            return Status.error(String.format(Translate.ERROR_SAVE_FAILED.get(), e.getMessage()));
        }
    }

    private static String serializeKey(List<Integer> key) {
        return key.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    private static List<Integer> parseKey(String raw) {
        try {
            return Arrays.stream(raw.split(", "))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            return null;
        }
    }
}