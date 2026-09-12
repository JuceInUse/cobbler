import static java.util.Map.entry;

// Terminal Mode CMDs
final String[] RAW_MODE = {"/bin/sh", "-c", "stty raw -echo </dev/tty"};
final String[] COOKED_MODE = {"/bin/sh", "-c", "stty sane </dev/tty"};

// Program Vars
boolean debugMode = true;
int key = -1;
int index = 0;
int page = 0;
String err = "";
boolean highlight = false;
boolean rebind = false;
final List<String[]> PAGES = new ArrayList<>(List.of(
        new String[]{
                "Keybinds",
                "File Tree"
        },
        BINDS.entrySet().stream()
                .map(entry -> String.format(
                        "%s: %s",
                        entry.getKey(),
                        ALIASES.getOrDefault(entry.getValue(), String.valueOf((char) (int) entry.getValue()))
                )).toArray(String[]::new)
));

public static final Map<String, Integer> BINDS = new HashMap<>(Map.ofEntries(
        entry("UP", 65), // UP
        entry("DOWN", 66), // DOWN
        entry("ENTER", 13), // ENTER
        entry("EXIT",27) // EXIT
));

public static final Map<Integer, String> ALIASES = Map.of(
        13, "↵ Return",
        27, "⎋ Escape",
        65, "↑",
        66, "↓",
        67, "→",
        68, "←",
        127, "Backspace"
);

void main() throws IOException, InterruptedException {
    // Raw Mode
    Runtime.getRuntime().exec(RAW_MODE).waitFor();

    // Render Init Frame
    renderFrame(PAGES.getFirst());

    try {
        while (key != BINDS.get("EXIT")) {
            int keyCache = key;
            key = System.in.read();

            // Get Key
            if (System.in.available() > 0) {
                do {
                    key = System.in.read();
                } while (System.in.available() > 0);
            }

            // Rebind Behavior

            if (key != keyCache && rebind) {
                List<String> keys = BINDS.keySet().stream().toList();
                List<Integer> values = BINDS.values().stream().toList();
                String line = Arrays.stream(PAGES.get(1)).toList().get(index);
                if (values.contains(key) && values.get(index) != key) {
                    err = String.format("'%s' already used for %s",ALIASES.getOrDefault(key,String.valueOf((char) key)),keys.get(values.indexOf(key)));
                } else if (values.get(index) != key) {
                    BINDS.replace(line.substring(0,line.indexOf(':')),key);
                    PAGES.set(1,BINDS.entrySet().stream()
                            .map(entry -> String.format(
                                    "%s: %s",
                                    entry.getKey(),
                                    ALIASES.getOrDefault(entry.getValue(), String.valueOf((char) (int) entry.getValue()))
                            )).toArray(String[]::new));
                }
                key = -1;
                rebind = false;
            }

            // WASD
            if (key == BINDS.get("UP")) {
                err = "";
                if (index > 0) index--;
            };
            if (key == BINDS.get("DOWN")) {
                err = "";
                if (index < PAGES.get(page).length - 1) index++;
            }

            // Enter
            if (key == BINDS.get("ENTER")) {
                err = "";
                if (page == 1) {
                    rebind = !rebind;
                    if (rebind) key = -1;
                }
                if (page == 0 && index == 0) page++;
            }

            // Exit
            if (key == BINDS.get("EXIT")) {
                err = "";
                if (page > 0) {
                    page--;
                    index = 0;
                    key = keyCache;
                }
            }

            // Page Render
            renderFrame(PAGES.get(page));
        }
    } finally {
        // Reset Terminal
        IO.print("\u001B[2J\u001B[H\u001B[?25h");
        System.out.flush();
        Runtime.getRuntime().exec(COOKED_MODE).waitFor();
    }
}

private void renderFrame(String[] lines) {
    highlight = rebind;
    IO.print("\u001B[2J\u001B[H\u001B[?25l"); // Clear
    for (int i = 0; i < lines.length; i++) {
        IO.print(i == index ? " > " : "   ");
        if (i == index) IO.print(highlight ? "\u001B[7m" : "\u001B[5m");
        IO.print(lines[i]);
        if (highlight && page == 1 && i == index) IO.print("\u001B[0m (Enter Any Key)");
        IO.print("\u001B[0m\r\n");
    }
    if (!err.isEmpty()) {
        IO.print(String.format("\u001B[31m\r\n|\r\n|  %s\r\n|\u001B[0m\r\n", err));
    }
    if (debugMode) IO.print(String.format("\r\nLast Keypress: %s\r\n", key));
    if (debugMode) IO.print(String.format("Index: %s\r\n", index));
    if (debugMode) IO.print(String.format("Page: %s\r\n", page));
    System.out.flush(); // Clear Cache
}