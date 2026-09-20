import terminal.TerminalMode;
import terminal.color.Color;
import terminal.input.Action;
import terminal.input.Keybinds;
import terminal.input.Keypress;
import terminal.input.Navigator;
import terminal.lang.Status;
import terminal.lang.Translate;
import terminal.lang.Translations;
import terminal.page.Page;
import terminal.widget.Widget;

void main() throws IOException {
    TerminalMode.setRaw(true);
    Translations.load("en_us");
    Keybinds.load();

    Navigator nav = new Navigator();
    List<Integer> key = new ArrayList<>();

    // Terminal Loop
    do {
        renderScreen(nav.page(), nav.capturing(), nav.status(), nav.index(), nav.debug(), key);
        System.out.flush();
        key = Keypress.readKey();
        if (!nav.capturing()) {
            Action action = Keybinds.get(key);
            nav.handleInput(action);
        } else { nav.getKey(key); }
    } while (!nav.exit());

    TerminalMode.setRaw(false);
}

/**
 * Clear the contents of the screen and print page
 */
void renderScreen(Page page, boolean capturing, Status status, int index, boolean debug, List<Integer> key) {
    Widget[] widgets = page.widgets();
    Widget widget = widgets[index];
    IO.print(TerminalMode.CLEAR);
    for (int i = 0; i < widgets.length; i++) {
        boolean selected = i == index;
        String line = String.format(Translate.FORMAT_WIDGET.get(),
                selected ? Translate.FORMAT_SELECTOR : " ", widgets[i].label());
        printRaw(
                selected && capturing ? line + Translate.FORMAT_CAPTURE.get() : line);
    }
    if (status != null) {
        printRaw("");

        printStatus(status);
    }
    if (debug) {
        Action keyAct = Keybinds.get(key);
        String keySeq = key.isEmpty() ? "N/A" : key.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        String keyLabel = Keybinds.keyLabel(key);
        printRaw("");

        printRaw(String.format(Translate.DEBUG_KEYPRESS.get(),
                (keyAct == null) ?
                        "" :
                        String.format(Translate.DEBUG_KEYPRESS_ACTION.get(), keyAct.name()),
                keySeq,
                (Objects.equals(keyLabel, keySeq)) ?
                        "" :
                        String.format(Translate.DEBUG_KEYPRESS_ALIAS.get(), keyLabel)
        ));

        printRaw(String.format(Translate.DEBUG_INDEX.get(), index));
        printRaw(String.format(Translate.DEBUG_PAGE.get(), page.id()));
        printRaw(String.format(Translate.DEBUG_TOPAGE.get(), (widget.toPage() == null) ? "N/A" : widget.toPage().id()));
    }
    System.out.flush();

}

void printRaw(String str) {
    IO.println(String.format("%s\r", str));
}

void printStatus(Status status) {
    String text = status.text();
    Color color = status.color();
    if (color == null) printRaw(text);
    else printFormatted(text, color);
}

void printFormatted(String str, Color color) {
    printRaw(color.foreground(str));
}