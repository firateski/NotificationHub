package model;

import i18n.Language;
import notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private Language language;

    private final List<Subscription> subscriptions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
    }
}
