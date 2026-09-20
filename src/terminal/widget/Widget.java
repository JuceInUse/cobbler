package terminal.widget;

import terminal.page.Page;

public interface Widget {
    String label();

    default Page toPage() {
        return null;
    }
}