package com.example.se201projektni_zadatakaleksandarrozkov6020.classes;

public class ClientModel {
    String client_id, name, phone, email;

    public ClientModel(String client_id, String name, String phone, String email) {
        this.client_id = client_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "client_id='" + client_id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}