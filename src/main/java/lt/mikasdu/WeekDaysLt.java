package lt.mikasdu;

import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

public enum WeekDaysLt {
    MONDAY(1, "Pirmadienis"),
    TUESDAY(2, "Antradienis"),
    WEDNESDAY(3, "Trečiadienis"),
    THURSDAY(4, "Ketvirtadienis"),
    FRIDAY(5, "Penktadienis"),
    SATURDAY(6, "Šeštadienis"),
    SUNDAY(7, "Sekmadienis");

    int day;
    String name;

    WeekDaysLt(int i, String dayName) {
        this.day = i;
        this.name = dayName;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName(){
        return this.name;
    }
    public int getDayNumber(){
        return this.day;
    }

    public static WeekDaysLt getById(int id) {
        for(WeekDaysLt day : values()) {
            if(day.getDayNumber() == id) return day;
        }
        AlertBox.alertSimple(AlertMessage.ERROR_DAY_UNKNOWN);
        SUNDAY.name = "NENUSTATYTA";
        SUNDAY.day = 8;
        return SUNDAY;
    }
}
