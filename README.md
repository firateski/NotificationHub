# Notification Hub Project

### Usage
1- Create a new company:
```java
Company company = new Company();
company.setId(1);
company.setLanguage(Language.TR);
company.setName("Trendyol");
```

2- Create a new subscription and assign it to the company:
```java
Subscription subscription = new Subscription();
subscription.setNotificationType(Email.class.getName());
subscription.setSubscriptionType(SubscriptionType.STANDARD);
subscription.setGetSubscriptionEndDate(new Date());
company.addSubscription(subscription);
```

3- Create an e-mail or SMS DTO to keep notification details:
```java
EmailDTO emailDTO = new EmailDTO();
emailDTO.setCompany(company);
emailDTO.setEmailFrom("ik@trendyoltech.com");
emailDTO.setEmailTo("firateski@outlook.com");
emailDTO.setSubject("Backend Developer Position");
emailDTO.setMessage("Congrats! You're hired!");
```

4- Initialize and send the notification:
```java
Notification email = new Email();
email.send(emailDTO);
```

5- Optionally, you can initialize a language manager and use it, if you need multiple languages:
```java
LanguageManager languageManager = new LanguageManager(company.getLanguage());
String localizedString = languageManager.getString("sample_key")
```

### Project Structure
```
src
├── main
│   ├── java
│   │   ├── Main.java
│   │   ├── exceptions
│   │   │   ├── CompanyBlacklistedException.java
│   │   │   └── NotSubscribedException.java
│   │   ├── i18n
│   │   │   ├── Dictionary.java
│   │   │   ├── Language.java
│   │   │   └── LanguageManager.java
│   │   ├── model
│   │   │   ├── Company.java
│   │   │   ├── Subscription.java
│   │   │   └── SubscriptionType.java
│   │   ├── notification
│   │   │   ├── Notification.java
│   │   │   ├── NotificationDTO.java
│   │   │   └── channel
│   │   │       ├── email
│   │   │       │   ├── Email.java
│   │   │       │   └── EmailDTO.java
│   │   │       └── sms
│   │   │           ├── Sms.java
│   │   │           └── SmsDTO.java
│   │   ├── subscriptionManager
│   │   │   └── SubscriptionManager.java
│   │   └── util
│   │       └── DateUtil.java
│   └── resources
└── test
    └── java
        ├── i18n
        │   └── LanguageManagerTest.java
        ├── notification
        │   ├── SubscriptionManagerTest.java
        │   └── channel
        │       ├── email
        │       │   └── EmailTest.java
        │       └── sms
        │           └── SmsTest.java
        └── util
            └── DateUtilTest.java

20 directories, 22 files
```
