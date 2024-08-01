package com.kerberos.travel.models;

public class TransportModel {
    private String type, from, to, name, id;
    private boolean multiCity;
    private int pax, price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMultiCity() {
        return multiCity;
    }

    public void setMultiCity(boolean multiCity) {
        this.multiCity = multiCity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public int getPax() {
        return pax;
    }
}
