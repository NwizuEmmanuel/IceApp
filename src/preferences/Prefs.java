/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package preferences;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;
import keys.PrefKeys;

/**
 *
 * @author Star fruit
 */
public class Prefs {

    static Prefs prefs = new Prefs();
    static Preferences preferences = Preferences.userRoot().node(prefs.getClass().getName());

    public void writeIpAddress(String ipAddress) {
        preferences.remove(PrefKeys.ipKey);
        preferences.put(PrefKeys.ipKey, ipAddress);
    }

    public void writerLoginInfo(String username, String password) {
        preferences.remove(PrefKeys.userKey);
        preferences.remove(PrefKeys.passKey);
        preferences.put(PrefKeys.userKey, username);
        preferences.put(PrefKeys.passKey, password);
    }

    public void writeTableValue(String value) {
        preferences.remove(PrefKeys.tableKey);
        preferences.put(PrefKeys.tableKey, value);
    }

    public static void putDataManager(String internValue, String otherValue){
        preferences.remove(PrefKeys.internDMKey);
        preferences.remove(PrefKeys.otherDMKey);
        preferences.put(PrefKeys.internDMKey,internValue);
        preferences.put(PrefKeys.otherDMKey, otherValue);
    }
}