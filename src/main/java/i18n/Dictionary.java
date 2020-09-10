package i18n;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Dictionary {
    public static Map<String, String> dictionaryTR = new HashMap<String, String>() {
        {
            put("company_black_listed_message", "Şirket kara listeye alınmış.");
            put("company_not_subscribed", "Bu iletişim biçimini kullanmak için aboneliğiniz bulunmamaktadır.");
        }
    };

    public static Map<String, String> dictionaryEN = new HashMap<String, String>() {
        {
            put("company_black_listed_message", "The company black-listed.");
            put("company_not_subscribed", "The company has no subscription to use this notification.");
        }
    };
}
