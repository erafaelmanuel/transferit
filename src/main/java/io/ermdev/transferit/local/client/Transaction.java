package io.ermdev.transferit.local.client;

public class Transaction {

    private int number;
    private String filename;
    private int transfer;

    public Transaction(int number, String filename, int transfer) {
        this.number = number;
        this.filename = filename;
        this.transfer = transfer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public String getPercentage() {
        return transfer + "%";
    }
}
