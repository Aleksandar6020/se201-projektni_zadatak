package com.example.se201projektni_zadatakaleksandarrozkov6020.classes;

public class WishModel {
    String wish_id, client_id, min_budget, max_budget, min_area, preferred_location, wish_type;

    public WishModel(String wish_id, String client_id, String min_budget, String max_budget, String min_area, String preferred_location, String wish_type) {
        this.wish_id = wish_id;
        this.client_id = client_id;
        this.min_budget = min_budget;
        this.max_budget = max_budget;
        this.min_area = min_area;
        this.preferred_location = preferred_location;
        this.wish_type = wish_type;
    }

    public String getWish_id() {
        return wish_id;
    }

    public void setWish_id(String wish_id) {
        this.wish_id = wish_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getMin_budget() {
        return min_budget;
    }

    public void setMin_budget(String min_budget) {
        this.min_budget = min_budget;
    }

    public String getMax_budget() {
        return max_budget;
    }

    public void setMax_budget(String max_budget) {
        this.max_budget = max_budget;
    }

    public String getMin_area() {
        return min_area;
    }

    public void setMin_area(String min_area) {
        this.min_area = min_area;
    }

    public String getPreferred_location() {
        return preferred_location;
    }

    public void setPreferred_location(String preferred_location) {
        this.preferred_location = preferred_location;
    }

    public String getWish_type() {
        return wish_type;
    }

    public void setWish_type(String wish_type) {
        this.wish_type = wish_type;
    }

    @Override
    public String toString() {
        return "WishModel{" +
                "wish_id='" + wish_id + '\'' +
                ", client_id='" + client_id + '\'' +
                ", min_budget='" + min_budget + '\'' +
                ", max_budget='" + max_budget + '\'' +
                ", min_area='" + min_area + '\'' +
                ", preferred_location='" + preferred_location + '\'' +
                ", wish_type='" + wish_type + '\'' +
                '}';
    }
}