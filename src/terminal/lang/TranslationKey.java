package terminal.lang;

public class TranslationKey {
    private final String id;
    private final String fallback;

    public TranslationKey(String id, String fallback) {
        this.id = id;
        this.fallback = fallback;
    }

    public String id() { return id; }
    public String fallback() { return fallback; }

    public String get() {
        return Translations.containsKey(id) ? Translations.translate(id) : fallback;
    }

    @Override
    public String toString() {
        return get();
    }
}