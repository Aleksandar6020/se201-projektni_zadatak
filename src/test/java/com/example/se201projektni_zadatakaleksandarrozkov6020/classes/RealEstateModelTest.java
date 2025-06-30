package com.example.se201projektni_zadatakaleksandarrozkov6020.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RealEstateModelTest {

    @Test
    void testConstructorAndGetters() {
        RealEstateModel estate = new RealEstateModel("2", "Palm Street", "85", "120000", "sale");

        assertEquals("2", estate.getReal_estate_id());
        assertEquals("Palm Street", estate.getAddress());
        assertEquals("85", estate.getSquare_meters());
        assertEquals("120000", estate.getPrice_or_rent());
        assertEquals("sale", estate.getType());
    }
}
