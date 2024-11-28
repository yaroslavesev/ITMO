package org.example.managers;
public class UserStatusManager {
    private boolean status;
    private String user_name;
    public UserStatusManager(boolean status, String user_name){
        this.status = status;
        this.user_name = user_name;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public boolean getStatus() {
        return status;
    }
}
