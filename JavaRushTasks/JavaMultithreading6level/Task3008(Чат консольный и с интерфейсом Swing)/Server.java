package com.javarush.task.task30.task3008;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            if (socket != null || socket.getRemoteSocketAddress() != null)
                ConsoleHelper.writeMessage("Установлено новое соединение с удаленным адресом : " + socket.getRemoteSocketAddress());
            String userName = null;
            try (Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(connection, userName);
                serverMainLoop(connection, userName);
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом.");
            }
            if (userName != null) {
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            }
            ConsoleHelper.writeMessage("Соединение с удаленным адресом : " + socket.getRemoteSocketAddress() + " закрыто.");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(MessageType.NAME_REQUEST, "Пожалуйста, сообщите свое имя"));
            Message message = connection.receive();
            if (message.getType() != MessageType.USER_NAME)
                return serverHandshake(connection);
            String userName = message.getData();
            if (userName.isEmpty())
                return serverHandshake(connection);
            if (connectionMap.containsKey(userName))
                return serverHandshake(connection);
            connectionMap.put(userName, connection);
            connection.send(new Message(MessageType.NAME_ACCEPTED,  String.format("Ваше имя: %s, принято.", userName)));
            return userName;
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> e : connectionMap.entrySet()) {
                if (!e.getKey().equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, e.getKey()));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message != null && message.getType() == MessageType.TEXT) {
                    sendBroadcastMessage(new Message(MessageType.TEXT,String.format("%s: %s", userName, message.getData())));
                } else {
                    ConsoleHelper.writeMessage("Сообщение не отправлено из за ошибки.");
                }
            }
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (Connection c : connectionMap.values()) {
            try {
                c.send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Сообщение не отправлено из за ошибки.");
            }
        }
    }

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите адрес серверного порта: ");
        try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            ConsoleHelper.writeMessage("Сервер запущен.");
            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Что то не так, серверный сокет закрыт");
        }

    }
}
