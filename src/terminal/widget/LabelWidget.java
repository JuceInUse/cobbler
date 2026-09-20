package terminal.widget;

import terminal.lang.TranslationKey;

/**
 * Widgets that cannot be interacted with
 * use this format
 */
public final class LabelWidget implements Widget {
    private final TranslationKey id;
    private final String fallback;

    public LabelWidget(TranslationKey id) {
        this.id = id;
        this.fallback = null;
    }

    public LabelWidget(String fallback) {
        this.id = null;
        this.fallback = fallback;
    }

    @Override
    public String label() {
        return id != null ? id.get() : fallback;
    }
}