package com.kerberos.travel.models;

import androidx.annotation.NonNull;

public class BookingsModel {
    private String name, price, ticketType, pax, from, to, date, time, userId;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = pax;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @NonNull
    @Override
    public String toString() {
        return "BookingsModel{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", pax='" + pax + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
