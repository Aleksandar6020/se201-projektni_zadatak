package com.example.se201projektni_zadatakaleksandarrozkov6020.classes;

public class RealEstateModel {
    String real_estate_id, address, square_meters, price_or_rent, type;

    public RealEstateModel(String real_estate_id, String address, String square_meters, String price_or_rent, String type) {
        this.real_estate_id = real_estate_id;
        this.address = address;
        this.square_meters = square_meters;
        this.price_or_rent = price_or_rent;
        this.type = type;
    }

    public String getReal_estate_id() {
        return real_estate_id;
    }

    public void setReal_estate_id(String real_estate_id) {
        this.real_estate_id = real_estate_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSquare_meters() {
        return square_meters;
    }

    public void setSquare_meters(String square_meters) {
        this.square_meters = square_meters;
    }

    public String getPrice_or_rent() {
        return price_or_rent;
    }

    public void setPrice_or_rent(String price_or_rent) {
        this.price_or_rent = price_or_rent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RealEstateModel{" +
                "real_estate_id='" + real_estate_id + '\'' +
                ", address='" + address + '\'' +
                ", square_meters='" + square_meters + '\'' +
                ", price_or_rent='" + price_or_rent + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}