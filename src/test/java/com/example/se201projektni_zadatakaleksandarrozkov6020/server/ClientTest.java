package com.example.se201projektni_zadatakaleksandarrozkov6020.server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @BeforeAll
    static void setup() throws InterruptedException {
        Server.startServer();
        Thread.sleep(300);
    }

    @Test
    void testCreateReadDeleteClient() {
        String name = "TestName";
        String phone = "+381601112223";
        String email = "test@email.com";

        String createResult = Client.Create("client", name + "|||" + phone + "|||" + email);
        assertTrue(createResult.startsWith("ok"), "Client creation failed: " + createResult);

        String readResult = Client.Read("client");
        assertTrue(readResult.contains(name), "Client not found in DB");

        String clientId = createResult.substring(3).trim();
        String deleteResult = Client.Delete("client", "client_id", clientId);
        assertEquals("DELETE is ok", deleteResult);
    }

}
