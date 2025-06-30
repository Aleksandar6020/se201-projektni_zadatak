package com.example.se201projektni_zadatakaleksandarrozkov6020.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WishModelTest {

    @Test
    void testConstructorAndGetters() {
        WishModel wish = new WishModel("1", "10", "50000", "100000", "60", "Belgrade", "sale");

        assertEquals("1", wish.getWish_id());
        assertEquals("10", wish.getClient_id());
        assertEquals("50000", wish.getMin_budget());
        assertEquals("100000", wish.getMax_budget());
        assertEquals("60", wish.getMin_area());
        assertEquals("Belgrade", wish.getPreferred_location());
        assertEquals("sale", wish.getWish_type());
    }
}
