import terminal.TerminalMode;
import terminal.input.Action;
import terminal.input.Keybinds;
import terminal.input.Keypress;
import terminal.input.Navigator;
import terminal.lang.Translate;
import terminal.lang.Translations;
import terminal.page.Page;
import terminal.widget.Widget;

void main() throws IOException {
    TerminalMode.setRaw(true);
    Translations.load("en_us");

    Navigator nav = new Navigator();
    ArrayList<Integer> key = new ArrayList<>();

    // Terminal Loop
    do {
        renderScreen(nav.page(), nav.index(), nav.debug(), key);
        System.out.flush();
        key = Keypress.readKey();
        Action action = Keybinds.get(key);
        nav.handleInput(action);
    } while (!nav.exit());

    TerminalMode.setRaw(false);
}

/**
 * Clear the contents of the screen and print page
 */
void renderScreen(Page page, int index, boolean debug, ArrayList<Integer> key) {
    Widget[] widgets = page.widgets();
    IO.print(TerminalMode.CLEAR);
    for (int i = 0; i < widgets.length; i++) {
        printRaw(String.format(Translate.WIDGET_FORMAT.get(), (i == index) ? Translate.WIDGET_SELECTOR : " ", widgets[i].label()));
    }
    if (debug) {
        printRaw("");
        printRaw(String.format(Translate.DEBUG_KEYPRESS.get(),
                key.isEmpty() ? "N/A" : key.stream().map(Object::toString)
                        .collect(Collectors.joining(", ")))
        );
        printRaw(String.format(Translate.DEBUG_INDEX.get(), index));
        printRaw(String.format(Translate.DEBUG_PAGE.get(), page.id()));
    }
    System.out.flush();

}

void printRaw(String str) {
    IO.println(String.format("%s\r", str));
}