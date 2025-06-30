package com.example.se201projektni_zadatakaleksandarrozkov6020.system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationHelperTest {

    @Test
    void isValidEmail() {
        assertTrue(ValidationHelper.isValidEmail("test@example.com"));
        assertFalse(ValidationHelper.isValidEmail("invalid@"));
    }

    @Test
    void isValidPhone() {
        assertTrue(ValidationHelper.isValidPhone("+381601234567"));
        assertFalse(ValidationHelper.isValidPhone("0601234567"));
    }

    @Test
    void isValidDecimal() {
        assertTrue(ValidationHelper.isValidDecimal("99.99"));
        assertFalse(ValidationHelper.isValidDecimal("123.456"));
    }
}
