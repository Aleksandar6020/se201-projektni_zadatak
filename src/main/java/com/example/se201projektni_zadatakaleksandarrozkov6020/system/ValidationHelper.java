package com.example.se201projektni_zadatakaleksandarrozkov6020.system;

public class ValidationHelper {
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("^\\+\\d{9,15}$");
    }

    public static boolean isValidDecimal(String number) {
        return number.matches("\\d+(\\.\\d{1,2})?");
    }
}
