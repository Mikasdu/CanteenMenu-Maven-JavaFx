package lt.mikasdu.ui.controller.menu;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lt.mikasdu.Formatter;
import lt.mikasdu.WeekMenuRecipes;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.pdfCreator.PdfFile;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuGenerateFileController implements Initializable {

    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;
    @FXML private Button cancelButton;
    private ObservableList<WeekMenuRecipes> weekMenuRecipes;


    public void buttonSave() throws IOException {
        if (dateFrom.getValue() != null & dateTo.getValue() != null) {
            LocalDate dateFromValue = dateFrom.getValue();
            LocalDate dateToValue = dateTo.getValue();
            if (dateFromValue.isBefore(dateToValue) || dateFromValue.isEqual(dateToValue)) {
                if (dateFromValue.getDayOfWeek().getValue() <= dateToValue.getDayOfWeek().getValue()
                        && dateToValue.isBefore(dateFromValue.plusDays(7))) {
//                    System.out.println("Saving PDF file, temp. comented/disabled");
//                    for (LocalDate date = dateFromValue; date.isBefore(dateToValue.plusDays(1)); date = date.plusDays(1)) {
//                        System.out.println(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
//                    }
                    //todo send days to generate
                    String dateFromTo = dateFromValue + " - " + dateToValue;
                    PdfFile.createMenuPdf(weekMenuRecipes, dateFromTo);
                    buttonCancel();
                } else
                    AlertBox.alertSimple(AlertMessage.ERROR_DATE,
                            "Negalima sugeneruoti meniu vėlesniam laikotarpiui nei paskutinė savaitės diena (Sekmadienis)");
            } else
                AlertBox.alertSimple(AlertMessage.ERROR_DATE, "Pradžios data yra vėlesnė nei pabaigos.");
        } else
            AlertBox.alertSimple(AlertMessage.ERROR_DATE, "Neįvedėte datos.");
    }

    public void buttonCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate ld1 = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        dateFrom.setValue(ld1);
        dateFormat(dateFrom);
        dateTo.setValue(ld1.plusDays(7 - dateFrom.getValue().getDayOfWeek().getValue()));
        dateFormat(dateTo);
        dateFrom.valueProperty().addListener((ov, oldValue, newValue) -> {
            int daysToAdd = 7 - dateFrom.getValue().getDayOfWeek().getValue();
            dateTo.setValue(newValue.plusDays(daysToAdd));
        });
    }

    private void dateFormat(DatePicker date) {
        String pattern = "yyyy-MM-dd";
        date.setPromptText(pattern.toLowerCase());
        date.setConverter(Formatter.formatDate());
    }

    public void initData(ObservableList<WeekMenuRecipes> weekMenuRecipes) {
        this.weekMenuRecipes = weekMenuRecipes;
    }
}
