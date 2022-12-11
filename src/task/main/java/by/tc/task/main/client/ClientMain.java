package by.tc.task.main.client;
import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import by.tc.task.main.requests.Requests;
import by.tc.task.main.requests.UserClient;
import by.tc.task.service.exception.ServiceException;

public class ClientMain {
    private static Socket clientSocket;
    public static void main(String[] args) {
        String login = "Joshua";
        String passwordToHash = getHashedString("Joshua");
        PrintWriter out = null;
        BufferedReader in = null;
        Socket clientSocket = null;
        try {
            try {
                clientSocket = new Socket("localhost", 8000);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                UserClient userClient = new UserClient();
                while(userClient.getRequest() != Requests.Request.EXIT)
                {
                    System.out.println("Request:");
                    String requestString = reader.readLine();
                    try {
                        userClient.parseRequest(requestString);
                    } catch (ServiceException e) {
                        System.out.println("Something is wrong.\n");
                    }
                    if(userClient.getRequest() == Requests.Request.LOGIN && userClient.getRawParameters().length > 2) {
                        String[] requestContent = userClient.getRawParameters();
                        String hashedPassword = getHashedString(userClient.getRawParameters()[2]);
                        requestString = requestContent[0] + " " + requestContent[1] + " "+ hashedPassword;
                    }

                    out.write(requestString + "\n");
                    out.flush();

                    System.out.println("Answer:");
                    String serverWord = in.readLine();
                    System.out.println(serverWord);
                }

            } finally {
                System.out.println("Closing client");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong :(");
        }
    }
    public static String getHashedString(String stringToHash) {
        String generatedPassword = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(stringToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hashing went wrong :(");
        }
        return generatedPassword;
    }
}