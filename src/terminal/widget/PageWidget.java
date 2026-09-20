package terminal.widget;

import terminal.lang.TranslationKey;
import terminal.page.Page;

/**
 * Widgets that navigate pages
 * use this format
 */
public class PageWidget implements Widget {
    private final TranslationKey id;
    private final String fallback;
    private Page nextPage;

    public PageWidget(TranslationKey id, Page nextPage) {
        this.id = id;
        this.fallback = null;
        this.nextPage = nextPage;
    }

    public PageWidget(String fallback, Page nextPage) {
        this.id = null;
        this.fallback = fallback;
        this.nextPage = nextPage;
    }

    @Override
    public String label() { return id != null ? id.get() : fallback; }

    @Override
    public Page toPage() { return nextPage; }

    public void setNextPage(Page nextPage) { this.nextPage = nextPage; }
}
