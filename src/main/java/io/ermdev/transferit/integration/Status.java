package io.ermdev.transferit.integration;

public enum Status {

   CREATE(100), STOP(200), ACCEPT(300), REJECT(400);

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
