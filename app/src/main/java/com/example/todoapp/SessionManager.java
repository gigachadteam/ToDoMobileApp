package com.example.todoapp;

public class SessionManager {
    private static String USR_NAME;

    public SessionManager(String usrName) {
        USR_NAME = usrName;
    }

    public void setSession(String usrName) {
        USR_NAME = usrName;
    }

    public String getSession() {
        return USR_NAME;
    }
    public void clearSession() {
        USR_NAME = "";
    }
}
