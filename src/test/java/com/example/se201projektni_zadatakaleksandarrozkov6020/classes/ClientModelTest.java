package com.example.se201projektni_zadatakaleksandarrozkov6020.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientModelTest {

    @Test
    void testConstructorAndGetters() {
        ClientModel client = new ClientModel("5", "Mina", "+381601234567", "mina@example.com");

        assertEquals("5", client.getClient_id());
        assertEquals("Mina", client.getName());
        assertEquals("+381601234567", client.getPhone());
        assertEquals("mina@example.com", client.getEmail());
    }
}
