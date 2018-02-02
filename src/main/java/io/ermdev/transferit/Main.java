package io.ermdev.transferit;

import io.ermdev.transferit.local.client.Client;
import io.ermdev.transferit.local.server.Server;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        System.out.print("Run as server? (y\\n) ");
        String input = new Scanner(System.in).next();
        if (input.equalsIgnoreCase("y")) {
            Server server = new Server(23411);
            server.openConnection();
        } else {
            Client client = new Client();
            client.findConnection();
        }
    }
}
