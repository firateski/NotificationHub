package i18n;

public class LanguageManager {
    private Language language;

    public LanguageManager(Language language) {
        this.language = language;
    }

    public String getString(String key) {
        if (language.equals(Language.TR)) {
            return Dictionary.dictionaryTR.get(key);
        } else {
            return Dictionary.dictionaryEN.get(key);
        }
    }
}
