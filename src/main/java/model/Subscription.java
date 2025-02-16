package model;

import notification.Notification;

import java.util.Date;

public class Subscription {
    private Company company;
    private double subscriptionPrice;
    private double exceedPricePerOperation;

    private double billedPrice = 0;
    private boolean isPaid;

    private int quota;
    private int totalUsage;

    private Date getSubscriptionEndDate;

    private String notificationType;
    private SubscriptionType subscriptionType;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(double price) {
        this.subscriptionPrice = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(int totalUsage) {
        this.totalUsage = totalUsage;
    }

    public double getExceedPricePerOperation() {
        return exceedPricePerOperation;
    }

    public void setExceedPricePerOperation(double exceedPricePerOperation) {
        this.exceedPricePerOperation = exceedPricePerOperation;
    }

    public double getBilledPrice() {
        return billedPrice;
    }

    public void addBilledPrice(double billedPriceToAdd) {
        this.billedPrice += billedPriceToAdd;
    }

    public Date getSubscriptionEndDate() {
        return getSubscriptionEndDate;
    }

    public void setGetSubscriptionEndDate(Date getSubscriptionEndDate) {
        this.getSubscriptionEndDate = getSubscriptionEndDate;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}
