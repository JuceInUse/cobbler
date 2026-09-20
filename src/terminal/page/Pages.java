package terminal.page;

import terminal.input.Action;
import terminal.lang.Translate;
import terminal.widget.KeybindWidget;
import terminal.widget.LabelWidget;
import terminal.widget.PageWidget;
import terminal.widget.Widget;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all pages used by Cobbler
 */
public final class Pages {
    private static final List<Page> PAGES = new ArrayList<>();

    public static final Page KEYBINDS = registerKeybindsPage("page.cobbler.keybinds");

    public static final Page SETTINGS = register(new Page("page.cobbler.settings", new Widget[]{
            new PageWidget(Translate.PAGE_WIDGET_KEYBINDS, KEYBINDS)
    }));

    public static final Page MAIN_MENU = register(new Page("page.cobbler.main_menu", new Widget[]{
            new LabelWidget(Translate.PAGE_WIDGET_PROJECTS),
            new PageWidget(Translate.PAGE_WIDGET_SETTINGS, SETTINGS)
    }));

    static {
        SETTINGS.prevPage(MAIN_MENU);
        KEYBINDS.prevPage(SETTINGS);
    }

    private static Page register(Page page) {
        PAGES.add(page);
        return page;
    }

    private static Page registerKeybindsPage(String id) {
        List<Widget> widgets = new ArrayList<>();
        for (Action action : Action.values()) { widgets.add(new KeybindWidget(action)); }
        return register(new Page(id, widgets.toArray(new Widget[0])));
    }
}
