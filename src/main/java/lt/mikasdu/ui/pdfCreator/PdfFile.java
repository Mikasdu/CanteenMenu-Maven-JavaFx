package lt.mikasdu.ui.pdfCreator;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import javafx.collections.ObservableList;
import lt.mikasdu.Products;
import lt.mikasdu.WeekDaysLt;
import lt.mikasdu.WeekMenuRecipes;
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PdfFile {


    private static Settings settings = new Settings();

    public static void createFile(ObservableList<Products> items) throws IOException {
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
        });
        document.add(para);
        document.add(list);
        document.close();
        AlertBox.alertSimple(AlertMessage.INFO_FILE_CREATED);
    }


    private static Document createDocument(String fileName) throws FileNotFoundException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HHmmss");
        String dest = settings.getFilesPath() + "\\" + fileName + sdf.format(cal.getTime()) + ".pdf";
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

    public static void createMenuPdf(ObservableList<WeekMenuRecipes> items, String dateFromTo) throws IOException {
        Document document = createDocument("Savaites_meniu ");
        document.setMargins(10, 50, 10, 100);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD, PdfEncodings.CP1257);
        HashMap<Integer, Paragraph> paragraphHashMap = new HashMap<>();
        for (int i = 1; i < 8; i++) {
            paragraphHashMap.put(i, newParagraph());
        }
        for (WeekMenuRecipes item : items) {
            item.setParagraph(paragraphHashMap);
        }
        String headerText = settings.getUserName() + " " + dateFromTo + "\n" + settings.getAppDescription();
        //headerText += "\n new line \n";
        Paragraph para = new Paragraph(headerText).setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(16);
        document.add(para);



        paragraphHashMap.forEach((key, value) -> {
            if (!value.isEmpty()) {
                try {
                    Paragraph tittleParagraph = newParagraphTittle(WeekDaysLt.getById(key).getName());
                    document.add(tittleParagraph).add(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        document.close();
        AlertBox.alertSimple(AlertMessage.INFO_FILE_CREATED);
    }
}
