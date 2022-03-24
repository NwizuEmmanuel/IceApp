package reports;

import alerts.DisplayError;
import database.DatabaseActions;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportCreator {
    static DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
    static DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static public void createInternReport(File file, LocalDate date) throws SQLException {
        String internQuery = "select fullname, telephone, course, amount from "+ DatabaseActions.tableNameForIntern+" where regdate = '"+date.format(dateTimeFormatter2)+"' ";
        DatabaseActions da = new DatabaseActions();
        Statement statement = da.connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(internQuery);
        String fileName = dateTimeFormatter1.format(date) + ".doc";
        XWPFDocument doc = new XWPFDocument();
        try(OutputStream os = new FileOutputStream(file+"\\"+fileName)) {
            XWPFParagraph paragraph = doc.createParagraph();
            XWPFRun run = paragraph.createRun();
            XWPFRun run1 = paragraph.createRun();
            run.setText("Icehub Reports");
            run.isCapitalized();
            run.isBold();

            run1.setText("Intern reports");
            run1.isBold();

            XWPFTable tab = doc.createTable();
            XWPFTableRow row = tab.getRow(0);

            row.getCell(0).setText("S/N");
            row.addNewTableCell().setText("Full name");
            row.addNewTableCell().setText("Telephone");
            row.addNewTableCell().setText("Course");
            row.addNewTableCell().setText("Amount");

            int rowNum = 0;

            while (rs.next()){
                rowNum++;
                row = tab.createRow();
                row.getCell(0).setText(rowNum+"");
                row.getCell(1).setText(rs.getString("fullname"));
                row.getCell(2).setText(rs.getString("telephone"));
                row.getCell(3).setText(rs.getString("course"));
                row.getCell(4).setText(rs.getString(rs.getString("amount")));
            }
            doc.write(os);
        }catch(Exception e) {
            DisplayError.showErrorAlert("Path does not exit.");
        }
    }
}
