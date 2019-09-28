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
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PdfFile {


private static Settings settings = new Settings();

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
        String dest = settings.getFilesPath() + "\\" + fileName + sdf.format(cal.getTime()) +".pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        return new Document(pdf);
    }
    private static Paragraph newParagraphTittle(String name) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD, PdfEncodings.CP1257);
        return new Paragraph(name).setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
    }
    private static Paragraph newParagraph() throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN, PdfEncodings.CP1257);
        return new Paragraph().setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
    }
    public static void createMenuPdf(ObservableList<WeekMenuRecipes> items) throws IOException {
        Document document = createDocument("Savaites_meniu");
        document.setMargins(10, 50, 10, 100);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD, PdfEncodings.CP1257);

        Paragraph day1 = newParagraphTittle("Pirmadienis");
        Paragraph day1Menu = newParagraph();
        Paragraph day2 = newParagraphTittle("Antradienis");
        Paragraph day2Menu = newParagraph();
        Paragraph day3 = newParagraphTittle("Trečiadienis");
        Paragraph day3Menu = newParagraph();
        Paragraph day4 = newParagraphTittle("Ketvirtadienis");
        Paragraph day4Menu = newParagraph();
        Paragraph day5 = newParagraphTittle("Penktadienis");
        Paragraph day5Menu = newParagraph();
        Paragraph day6 = newParagraphTittle("Šeštadienis");
        Paragraph day6Menu = newParagraph();
        Paragraph day7 = newParagraphTittle("Sekmadienis");
        Paragraph day7Menu = newParagraph();

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

        String text = "Dienos pietūs 2019-09-05 - 2019-09-10";
        Paragraph para = new Paragraph(text).setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(16);
        document.add(para);
        if (!day1Menu.isEmpty())
            document.add(day1).add(day1Menu);
        if (!day2Menu.isEmpty())
            document.add(day2).add(day2Menu);
        if (!day3Menu.isEmpty())
            document.add(day3).add(day3Menu);
        if (!day4Menu.isEmpty())
            document.add(day4).add(day4Menu);
        if (!day5Menu.isEmpty())
            document.add(day5).add(day5Menu);
        if (!day6Menu.isEmpty())
            document.add(day6).add(day6Menu);
        if (!day7Menu.isEmpty())
            document.add(day7).add(day7Menu);
        document.close();
        AlertBox.alertSimple(AlertMessage.INFO_FILE_CREATED);
    }

}
