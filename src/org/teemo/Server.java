package org.teemo;

public enum Server {

    NA("NA", "104.160.131.3"),
    EUW("EUW", "104.160.141.3"),
    EUNE("EUNE", "104.160.142.3"),
    OCE("OCE", "104.160.156.1"),
    LAN("LAN", "104.160.136.3");

    private final String name;
    private final String ip;

    Server(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getIp() {
        return ip;
    }
}
