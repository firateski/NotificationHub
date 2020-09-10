package i18n;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LanguageManagerTest {
    @Test
    public void should_return_correct_value_for_tr(){
        LanguageManager languageManager = new LanguageManager(Language.TR);

        String output = languageManager.getString("company_black_listed_message");
        String expected = "Şirket kara listeye alınmış.";

        assertEquals(expected, output);
    }

    @Test
    public void should_return_correct_value_for_en(){
        LanguageManager languageManager = new LanguageManager(Language.EN);

        String output = languageManager.getString("company_black_listed_message");
        String expected = "The company black-listed.";

        assertEquals(expected, output);
    }

    @Test
    public void should_return_null_when_key_not_found_in_dictionary(){
        LanguageManager languageManager = new LanguageManager(Language.EN);

        String output = languageManager.getString("random_string_which_is_not_in_dictionary");

        assertNull(output);
    }
}
