package terminal.page;

import terminal.widget.Widget;

public final class Page {
    private final String id;
    private final Widget[] widgets;
    private Page prevPage;

    public Page(String id, Widget[] widgets) {
        this.id = id;
        this.widgets = widgets;
    }

    public String id() { return id; }
    public int maxIndex() { return widgets.length - 1; }
    public Widget[] widgets() { return widgets; }

    public Page prevPage() { return prevPage; }
    public Page prevPage(Page prevPage) { return this.prevPage = prevPage; }
}
