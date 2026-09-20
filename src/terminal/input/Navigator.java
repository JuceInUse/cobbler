package terminal.input;

import terminal.page.Page;
import terminal.page.Pages;

public final class Navigator {
    private Page page = Pages.MAIN_MENU;
    private int index = 0;
    private boolean debug = false;
    private boolean exit = false;

    public Page page() { return page; }
    public int index() { return index; }
    public boolean debug() { return debug; }
    public boolean exit() {return exit; }

    public void handleInput(Action action) {
        if (action == null) { return; }

        switch (action) {
            case UP -> { if (index > 0) index--; }
            case DOWN -> { if (index < page.maxIndex()) index++; }
            case ENTER -> {
                Page toPage = page.widgets()[index].toPage();
                if (toPage != null) {
                    page = toPage;
                    index = 0;
                }
            }
            case EXIT -> {
                Page prevPage = page.prevPage();
                if (prevPage != null) {
                    page = prevPage;
                    index = 0;
                } else {
                    exit = true;
                }
            }
            case DEBUG -> debug = !debug;
        }
    }
}
