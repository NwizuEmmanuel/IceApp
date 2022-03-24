package constants;

import alerts.SuccessAlert;
import preferences.Prefs;

import java.time.LocalDate;

public class DataManager {
    static LocalDate localDate = LocalDate.now();

    // DG means Data group
    static String preffix = "DG";
    static public void putDataValue(){
        String internDataValue = "I"+preffix+localDate.getMonth()+localDate.getYear();
        String otherDataValue = "O"+preffix+localDate.getMonth()+localDate.getYear();
        Prefs.putDataManager(internDataValue, otherDataValue);
        SuccessAlert.showAlert("New data value group successfully created.");
    }
}
