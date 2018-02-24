package io.ermdev.transferit.integration;

public interface NoProtocol {

    /**
     * Create connection
     */
    Integer STATUS_CREATE = 100;

    /**
     * Stop connection
     */
    Integer STATUS_STOP = 200;

    /**
     * Accept connection
     */
    Integer STATUS_ACCEPT = 300;

    /**
     * BUSY connection
     */
    Integer STATUS_BUSY = 400;
}
