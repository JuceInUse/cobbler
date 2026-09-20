package terminal.lang;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class Translations {
    private static final String RESOURCES_DIR = "resources";
    private static final String LANG_DIR = RESOURCES_DIR + "/lang";
    private static final Map<String, String> TRANSLATIONS = new HashMap<>();

    public static void load(String lang) throws IOException {
        Properties prop = new Properties();
        File file = new File(LANG_DIR, lang + ".properties");
        boolean exists = file.exists();

        if (exists) {
            try (InputStream in = new FileInputStream(file)) {
                prop.load(in);
            }
        } else {
            file.getParentFile().mkdirs();
        }

        boolean changed = !exists;
        for (TranslationKey key : Translate.allKeys()) {
            if (!prop.containsKey(key.id())) {
                prop.setProperty(key.id(), key.fallback());
                changed = true;
            }
        }

        if (changed) {
            try (OutputStream out = new FileOutputStream(file)) {
                prop.store(out, String.format("Translations (%s)", lang));
            }
        }

        TRANSLATIONS.clear();
        for (String key : prop.stringPropertyNames()) {
            TRANSLATIONS.put(key, prop.getProperty(key));
        }
    }

    public static boolean containsKey(String key) {
        return TRANSLATIONS.containsKey(key);
    }

    /** Get translations or raw string if not available */
    public static String translate(String key) {
        return TRANSLATIONS.getOrDefault(key, key);
    }
}
