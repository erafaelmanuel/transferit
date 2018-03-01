package io.ermdev.transferit.integration;

public enum Status {

   CREATE(100), ACCEPT(101), INFO(102), SCAN(103), STOP(200), REJECT(201);

    private int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
