package terminal.widget;

import terminal.page.Page;

/**
 * Gen def of a widget
 */
public interface Widget {
    String label();

    default Page toPage() {
        return null;
    }
}