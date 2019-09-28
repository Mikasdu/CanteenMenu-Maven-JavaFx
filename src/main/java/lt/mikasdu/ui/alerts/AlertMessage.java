package lt.mikasdu.ui.alerts;

import javafx.scene.control.Alert;

public enum AlertMessage {
    ERROR_CUSTOM(Alert.AlertType.ERROR, "Klaida", "Įvyko klaida."),
    ERROR_NAME(Alert.AlertType.ERROR, "Klaida", "Prašome įvesti pavadinimą. Ne mažiau 5 ženklų ir ne daugiau 50. "),
    ERROR_MEASUREUNTI(Alert.AlertType.ERROR, "Klaida", "Pasirinkite matavimo vienetus. "),
    ERROR_CATEGORY(Alert.AlertType.ERROR, "Klaida", "Pasirinkite kategoriją. "),
    ERROR_DAY(Alert.AlertType.ERROR, "Klaida", "Pasirinkite savaitės dieną"),
    ERROR_DAY_UNKNOWN(Alert.AlertType.ERROR, "Klaida", "Įvyko klaida, nepavyko nustatyti dienos"),
    ERROR_DATE(Alert.AlertType.ERROR,"Klaida", "Neteisingai įvesta data."),
    ERROR_RECIPE(Alert.AlertType.ERROR, "Klaida", "Pasirinkite receptą"),
    ERROR_PRODUCT(Alert.AlertType.ERROR, "Klaida", "Pasirinkite produktą iš sąrašo. "),
    ERROR_QUANTITY(Alert.AlertType.ERROR,"Klaida", "Įveskite kiekį"),
    ERROR_PRICE(Alert.AlertType.ERROR, "Klaida", "Įveskite kainą"),
    ERROR_CANTDELETE(Alert.AlertType.ERROR, "Klaida", "Negalima ištrinti įrašo kuris yra susietas su kitais įrašais. "),
    ERROR_PLEASECHOOSE(Alert.AlertType.ERROR, "Klaida", "Prieš atlikdami veiksmus pasirinkite įrašą iš sąrašo. "),
    CONFIRM_DELETE(Alert.AlertType.CONFIRMATION, "Patvirtinkite", "Ar tikrai norite ištrinti įrašą? "),
    INFO_FILE_CREATED(Alert.AlertType.INFORMATION, "Failas sukurtas", "Failas sėkmingai sukurtas"),
    INFO(Alert.AlertType.INFORMATION, "Info", ""),
    ABOUT(Alert.AlertType.INFORMATION, "About" ,"Dienos pietų meniu sudarymo programa. \n Autorius Mikas Du.");


    private String err = "Klaida";
    private Alert.AlertType alertType;
    private String title;
    private String message;
    private String customMessage = "";


    AlertMessage(Alert.AlertType alertType, String title, String message) {
        this.alertType = alertType;
        this.title = title;
        setMessage(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = "";
        this.customMessage = "\n" + customMessage;
    }

    public String getMessage() {
        return this.message + this.customMessage;
    }

    public String getTitle() {
        return this.title;
    }

    public Alert.AlertType getAlertType() {
        return this.alertType;
    }
}
