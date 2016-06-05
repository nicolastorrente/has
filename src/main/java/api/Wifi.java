package api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Wifi {
    private String password;

    public Wifi() {
        this.password = this.generatePassword();
    }

    private String generatePassword() {
        SimpleDateFormat newDateFormat = new SimpleDateFormat();
        newDateFormat.applyPattern("EEEEdMMMyy");
        return newDateFormat.format(new Date());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
