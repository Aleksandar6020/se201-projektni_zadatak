package com.example.se201projektni_zadatakaleksandarrozkov6020.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static String Create(String table, String data) {
        String mes = "Create=" + table + "|||" + data;
        System.out.println(mes);
        return send(mes);
    }

    public static String Read(String table) {
        String mes = "Read=" + table;
        System.out.println(mes);
        return send(mes);
    }

    public static String Update(String table, String update, String when) {
        String mes = "Update=" + table + "|||" + update + "|||" + when;
        System.out.println(mes);
        return send(mes);
    }

    public static String Delete(String table, String column, String value) {
        String mes = "Delete=" + table + "|||" + column + "|||" + value;
        System.out.println(mes);
        return send(mes);
    }

    public static String send(String req) {
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(req);
            return in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR: connection failed";
        }
    }
}