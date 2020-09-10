import exceptions.CompanyBlacklistedException;
import exceptions.NotSubscribedException;
import i18n.Language;
import i18n.LanguageManager;
import model.Company;
import model.Subscription;
import model.SubscriptionType;
import notification.Notification;
import notification.channel.email.Email;
import notification.channel.email.EmailDTO;

import java.util.Date;

public class Main {
    public static void main(String[] args){
        // Create a new company
        Company company = new Company();
        company.setId(1);
        company.setLanguage(Language.TR);
        company.setName("Trendyol");

        // Create and add a new subscription to company
        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getName());
        subscription.setSubscriptionType(SubscriptionType.STANDARD);
        subscription.setGetSubscriptionEndDate(new Date());
        company.addSubscription(subscription);

        // Create email DTO which keeps e-mail content
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setCompany(company);
        emailDTO.setEmailFrom("ik@trendyoltech.com");
        emailDTO.setEmailTo("firateski@outlook.com");
        emailDTO.setSubject("Backend Developer Position");
        emailDTO.setMessage("Congrats! You're hired!");

        // Initialize a language manager to translate error messages
        LanguageManager languageManager = new LanguageManager(company.getLanguage());

        // Initialize a new e-mail notification
        Notification email = new Email();

        try {
            email.send(emailDTO);
        }catch (CompanyBlacklistedException e){
            System.out.println(languageManager.getString("company_black_listed_message"));
        }catch (NotSubscribedException e){
            System.out.println(languageManager.getString("company_not_subscribed"));
        }
    }
}
