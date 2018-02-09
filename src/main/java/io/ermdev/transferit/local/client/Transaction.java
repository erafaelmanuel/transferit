package io.ermdev.transferit.local.client;

public class Transaction {

    private int number;
    private String filename;
    private double transfer;
    private String percentage;

    public Transaction(int number, String filename, double transfer) {
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

    public double getTransfer() {
        return transfer;
    }

    public void setTransfer(double transfer) {
        this.transfer = transfer;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
    public String getPercentage() {
        if(percentage == null) {
            return String.format("%.2f", transfer) + "%";
        } else {
            return percentage;
        }
    }
}
