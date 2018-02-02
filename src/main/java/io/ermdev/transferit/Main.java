package io.ermdev.transferit;

import io.ermdev.transferit.local.client.Client;
import io.ermdev.transferit.local.server.Server;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        System.out.print("Send or Receive? (type s/r) : ");
        String input = new Scanner(System.in).next();
        if (input.equalsIgnoreCase("r")) {
            Server server = new Server(23411);
            server.openConnection();
        } else if(input.equalsIgnoreCase("s")) {
            Client client = new Client();
            client.findConnection();
        }
        System.out.println("Shutting down!");
    }
}
