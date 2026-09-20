package terminal.widget;

import terminal.input.Navigator;
import terminal.page.Page;

/**
 * Gen def of a widget
 */
public interface Widget {
    String label();

    default Page toPage() {
        return null;
    }

    default void onEnter(Navigator nav) {
        Page toPage = toPage();
        if (toPage != null) nav.goToPage(toPage);
    }
}