package terminal.input;

import terminal.lang.Status;
import terminal.page.Page;
import terminal.page.Pages;

import java.util.List;

/**
 * The framework to allow for screen nav
 * Some just flip vars in Main but that's
 * okay for now . . .
 */
public final class Navigator {
    private Page page = Pages.MAIN_MENU;
    private int index = 0;
    private boolean debug = false;
    private boolean exit = false;

    private boolean capturing = false;
    private Status status = null;
    private Action rebindAction = null;

    public Page page() { return page; }
    public int index() { return index; }
    public boolean debug() { return debug; }
    public boolean exit() {return exit; }
    public boolean capturing() { return capturing; }
    public Status status() { return status; }

    public void handleInput(Action action) {
        if (action == null) { return; }

        switch (action) {
            case UP -> {status = null; if (index > 0) index--; }
            case DOWN -> {status = null; if (index < page.maxIndex()) index++; }
            case ENTER -> {status = null; page.widgets()[index].onEnter(this); }
            case EXIT -> {
                status = null;
                Page prevPage = page.prevPage();
                if (prevPage != null) {
                    goToPage(prevPage);
                } else exit = true;
            }
            case DEBUG -> debug = !debug;
        }
    }

    public void goToPage(Page toPage) {
        page = toPage;
        index = 0;

    }

    public void startRebind(Action action) {
        rebindAction = action;
        capturing = true;
    }

    public void getKey(List<Integer> key) {
        status = Keybinds.rebind(rebindAction, key);
        capturing = false;
        rebindAction = null;
    }
}
