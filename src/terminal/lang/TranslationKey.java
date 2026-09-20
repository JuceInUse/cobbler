package terminal.lang;

/**
 * Defines the TranslationKey var type
 */
public record TranslationKey(String id, String fallback) {

    public String get() {
        return Translations.containsKey(id) ? Translations.translate(id) : fallback;
    }

    @Override
    public String toString() { return get(); }
}