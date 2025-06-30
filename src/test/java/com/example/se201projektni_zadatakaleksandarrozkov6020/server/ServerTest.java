package com.example.se201projektni_zadatakaleksandarrozkov6020.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void testStartAndStopServer() {
        try {
            Server.startServer();
            Thread.sleep(300);

            Socket socket = new Socket("localhost", 5000);
            assertTrue(socket.isConnected());
            socket.close();

            Server.stopServer();
            Thread.sleep(100);

            assertThrows(IOException.class, () -> new Socket("localhost", 5000));

        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }
}
