package lt.mikasdu.ui.pdfCreator;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import javafx.collections.ObservableList;
import lt.mikasdu.Products;
import lt.mikasdu.WeekMenuRecipes;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PdfFile {




    public static void createFile(ObservableList<Products> items) throws IOException {
        //todo jeigu atidarytas failas errora ismeta
        Document document = createDocument("Pirkiniu_Sarasas");
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD, PdfEncodings.CP1257);
        PdfFont font2 = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN, PdfEncodings.CP1257);
        String text = "Pirkinių sąrašas";
        Paragraph para = new Paragraph(text).setFont(font);
        para.setTextAlignment(TextAlignment.CENTER);
        List list = new List().setFont(font2);
        items.forEach(product -> {
            String productLine = product.getName() + " " + product.getQuantity() + " " + product.getMeasure();
            list.add(productLine);
        } );
        document.add(para);
        document.add(list);
        document.close();
        AlertBox.alertSimple(AlertMessage.INFO_FILE_CREATED);
    }


    private static Document createDocument(String fileName) throws FileNotFoundException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HHmmss");
        String dest = "documents\\"+ fileName + sdf.format(cal.getTime()) +".pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        return new Document(pdf);
    }

    public static void createMenuPdf(ObservableList<WeekMenuRecipes> items) throws IOException {
        //TODO padaryti kad failo direktorija is nustatymu paimtu
        Document document = createDocument("Savaites_meniu");
        document.setMargins(10, 50, 10, 100);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD, PdfEncodings.CP1257);
        PdfFont font2 = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN, PdfEncodings.CP1257);
        String text = "Dienos pietūs 2019-09-05 - 2019-09-10";
        Paragraph para = new Paragraph(text).setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(16);

        Paragraph day1 = new Paragraph("Pirmadienis").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day1Menu = new Paragraph().setFont(font2).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day2 = new Paragraph("Antradienis").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day2Menu = new Paragraph().setFont(font2).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day3 = new Paragraph("Trečiadienis").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day3Menu = new Paragraph().setFont(font2).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day4 = new Paragraph("Ketvirtadienis").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day4Menu = new Paragraph().setFont(font2).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day5 = new Paragraph("Penktadienis").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day5Menu = new Paragraph().setFont(font2).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day6 = new Paragraph("Šeštadienis").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day6Menu = new Paragraph().setFont(font2).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day7 = new Paragraph("Sekmadienis").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        Paragraph day7Menu = new Paragraph().setFont(font2).setTextAlignment(TextAlignment.CENTER).setFontSize(10);

        for (WeekMenuRecipes item : items) {
            String menuItem = item.getRecipe().getName() + " " + item.getRecipe().getPrice() + " € \n";
            if(item.getWeekDayNumber() == 1)
                day1Menu.add(menuItem);
            else if(item.getWeekDayNumber() == 2)
                day2Menu.add(menuItem);
            else if(item.getWeekDayNumber() == 3)
                day3Menu.add(menuItem);
            else if(item.getWeekDayNumber() == 4)
                day4Menu.add(menuItem);
            else if(item.getWeekDayNumber() == 5)
                day5Menu.add(menuItem);
            else if(item.getWeekDayNumber() == 6)
                day6Menu.add(menuItem);
            else if(item.getWeekDayNumber() == 7)
                day7Menu.add(menuItem);
        }
        document.add(para);
        if (!day1Menu.isEmpty()) {
            document.add(day1).add(day1Menu);
        }
        if (!day2Menu.isEmpty()) {
            document.add(day2).add(day2Menu);
        }
        if (!day3Menu.isEmpty()) {
            document.add(day3).add(day3Menu);
        }
        if (!day4Menu.isEmpty()) {
            document.add(day4).add(day4Menu);
        }
        if (!day5Menu.isEmpty()) {
            document.add(day5).add(day5Menu);
        }
        if (!day6Menu.isEmpty()) {
            document.add(day6).add(day6Menu);
        }
        if (!day7Menu.isEmpty()) {
            document.add(day7).add(day7Menu);
        }
        document.close();
        AlertBox.alertSimple(AlertMessage.INFO_FILE_CREATED);
    }

}
