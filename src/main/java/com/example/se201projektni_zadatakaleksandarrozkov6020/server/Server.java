package com.example.se201projektni_zadatakaleksandarrozkov6020.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    private static ServerSocket serverSocket;
    private static boolean working = false;
    private static Connection connection;

    private static void connect(){
        try {
            String url = "jdbc:mysql://localhost:3306/se201projektni_zadatak_2024";
            String user = "root";
            String key = "";
            connection = DriverManager.getConnection(url,user,key);
            System.out.println("connect ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startServer(){
        if(working) return;
        working = true;

        new Thread(() -> {
            connect();
            try {
                serverSocket = new ServerSocket(5000);
                System.out.println("connected");

                while (working){
                    try {
                        Socket socket = serverSocket.accept();
                        new Thread(() -> request(socket)).start();
                    } catch (IOException e) {
                        if (e instanceof java.net.SocketException) {
                            System.out.println("Server socket closed.");
                        } else {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (IOException e) {
                if (e instanceof java.net.SocketException) {
                    System.out.println("Server socket closed.");
                } else {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public static void stopServer() throws IOException {
        serverSocket.close();
        working = false;
    }

    private static void request(Socket socket){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String req = in.readLine();
            if(req == null || req.isEmpty()) return;

            String[] split = req.split("=");
            if(split.length < 2) return;
            String crud = split[0];
            System.out.println(crud);
            String ansver = switch (crud){
                case "Create" -> Create(split[1]);
                case "Read" -> Read(split[1]);
                case "Update" -> Update(split[1]);
                case "Delete" -> Delete(split[1]);
                default -> "Crud not ok";
            };
            System.out.println("Server: " + ansver);

            out.println(ansver.trim());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String Create(String req) {
        try {
            String[] data = req.split("\\|\\|\\|");
            String table = data[0];
            int id = Cid(table);

            String put = "";
            for (int i = 1; i < data.length; i++) {
                put += "?";
                if (i < data.length - 1) {
                    put += ",";
                }
            }

            String sql;
            PreparedStatement ps;

            if (id == -1) {
                sql = "INSERT INTO " + table + " VALUES (" + put + ")";
                ps = connection.prepareStatement(sql);
                for (int i = 1; i < data.length; i++) {
                    ps.setString(i, data[i]);
                }
                System.out.println("ps = " + ps);
                ps.executeUpdate();
                return "ok";
            } else {
                sql = "INSERT INTO " + table + " VALUES (?," + put + ")";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                for (int i = 1; i < data.length; i++) {
                    ps.setString(i + 1, data[i]);
                }
                System.out.println("ps = " + ps);
                ps.executeUpdate();
                return "ok:" + id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Create Error";
        }
    }

    private static String getIdColumnName(String table) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getPrimaryKeys(connection.getCatalog(), null, table);

        String idColumn = null;
        int count = 0;

        while (rs.next()) {
            idColumn = rs.getString("COLUMN_NAME");
            count++;
            if (count > 1) {
                return null;
            }
        }

        if (count == 1) {
            return idColumn;
        } else {
            throw new SQLException("ID column not found for table: " + table);
        }
    }

    private static int Cid(String table) {
        try {
            String idColumn = getIdColumnName(table);
            if (idColumn == null) {
                return -1;
            }
            String sql = "SELECT MAX(" + idColumn + ") FROM " + table;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static String Read(String table) {
        try {
            String sql = "SELECT * FROM " + table;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int colum = rs.getMetaData().getColumnCount();
            String back = "";
            while (rs.next()){
                for (int i  = 1; i <= colum;i++){
                    back += rs.getString(i);
                    if(i < colum){
                        back += "|||";
                    }
                }
                back += "###";
            }

            return back.trim();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Read Error";
        }
    }

    private static String Update(String req) {
        try {
            String[] data = req.split("\\|\\|\\|");
            String table = data[0];
            String need = data[1];

            String sql = "SELECT * FROM " + table;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int colum = meta.getColumnCount();

            String put = "";
            int pos = -1;
            for (int i = 1;i <= colum;i++){
                if(meta.getColumnName(i).equals(need)){
                    pos = i;
                    continue;
                }
                if(data[i+1].equals("")){
                    continue;
                }
                System.out.println(data[i] + " here " + i);
                put += meta.getColumnName(i) + "=?,";
            }
            put = put.substring(0,put.length()-1);
            sql = "UPDATE " + table + " SET " + put + " WHERE " + need + "=?";
            System.out.println("sql: " + sql);

            PreparedStatement ps1 = connection.prepareStatement(sql);

            int pos2 = 1;
            for (int i = 2;i < data.length; i++){
                if(data[i].isEmpty() || i == pos + 1){
                    continue;
                }
                ps1.setString(pos2,data[i]);
                pos2++;
            }
            ps1.setString(pos2,data[pos+1]);
            System.out.println("ps = " + ps1);
            ps1.executeUpdate();

            return "UPDATE is Ok";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Update Error";
        }
    }

    private static String Delete(String req) {
        try {
            String[] data = req.split("\\|\\|\\|");
            String table = data[0];
            String need = data[1];
            String nbr = data[2];
            System.out.println(data[2]);
            String sql = "DELETE FROM " + table + " WHERE " + need + "=?";
            System.out.println(sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,nbr);
            ps.executeUpdate();
            return "DELETE is ok";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Delete Error";
        }
    }
}