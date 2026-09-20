package terminal.page;

import terminal.lang.Translate;
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

    public static final Page SETTINGS = register(new Page("page.cobbler.settings", new Widget[]{
            new LabelWidget(Translate.KEYBIND_WIDGET)
    }));

    public static final Page MAIN_MENU = register(new Page("page.cobbler.main_menu", new Widget[]{
            new LabelWidget(Translate.PROJECTS_WIDGET),
            new PageWidget(Translate.SETTINGS_WIDGET, SETTINGS)
    }));

    static {
        SETTINGS.prevPage(MAIN_MENU);
    }

    private static Page register(Page page) {
        PAGES.add(page);
        return page;
    }
}
