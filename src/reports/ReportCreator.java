package reports;

import alerts.DisplayError;
import alerts.SuccessAlert;
import database.DatabaseActions;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportCreator {
    static public void createReport(String dgv, File file) throws SQLException {
        String internQuery = "select fullname, telephone, course, amount, regdate from "+ DatabaseActions.tableNameForIntern+" where data_group_value = '"+dgv+"' ";
        String otherQuery = "select item, amount, cashflow, transac_date from "+DatabaseActions.tableNameForOthers+" where data_group_value = '"+dgv+"' ";
        String totalInternAmount = "select sum(amount) from "+DatabaseActions.tableNameForIntern+" where data_group_value = '"+dgv+"' ";
        String totalOtherAmount = "select sum(amount) from "+DatabaseActions.tableNameForOthers+" where data_group_value = '"+dgv+"' ";
        Statement statement = DatabaseActions.connectToDB().createStatement();
        ResultSet rsInternQuery = statement.executeQuery(internQuery);
        String fileName = dgv + ".xls";
        Workbook wb = new HSSFWorkbook();
        try(OutputStream os = new FileOutputStream(file+"\\"+fileName)) {
            Sheet sheet1 = wb.createSheet("Intern Reports");
            Row row = sheet1.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("FULLNAME");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("TELEPHONE");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("COURSE");
            Cell cell4 = row.createCell(3);
            cell4.setCellValue("AMOUNT");
            Cell cell5 = row.createCell(4);
            cell5.setCellValue("REGISTRATION DATE");

            int rowNum = 1;
            while (rsInternQuery.next()){
                row = sheet1.createRow(rowNum);
                Cell cellA1 = row.createCell(0);
                cellA1.setCellValue(rsInternQuery.getString("fullname"));
                Cell cellA2 = row.createCell(1);
                cellA2.setCellValue(rsInternQuery.getString("telephone"));
                Cell cellA3 = row.createCell(2);
                cellA3.setCellValue(rsInternQuery.getString("course"));
                Cell cellA4 = row.createCell(3);
                cellA4.setCellValue(rsInternQuery.getString("Amount"));
                Cell cellA5 = row.createCell(4);
                cellA5.setCellValue(rsInternQuery.getString("regdate"));
                rowNum++;
            }
            rsInternQuery.close();

            ResultSet rsTotalAmountIntern = statement.executeQuery(totalInternAmount);
            int totalAmountIntern = 0;
            while (rsTotalAmountIntern.next()){
                totalAmountIntern = rsTotalAmountIntern.getInt("sum(amount)");
            }

            Row row3 = sheet1.createRow(rowNum+1);
            Cell cellB1 = row3.createCell(0);
            cellB1.setCellValue("Total amount");
            Cell cellB2 = row3.createCell(1);
            cellB2.setCellValue(totalAmountIntern);
            rsTotalAmountIntern.close();

            ResultSet rsOtherQuery = statement.executeQuery(otherQuery);
            Sheet sheet2 = wb.createSheet("Other Reports");
            Row rowSheet2 = sheet2.createRow(0);
            Cell cellSheetA1 = rowSheet2.createCell(0);
            cellSheetA1.setCellValue("ITEM");
            Cell cellSheetA2 = rowSheet2.createCell(1);
            cellSheetA2.setCellValue("AMOUNT");
            Cell cellSheetA3 = rowSheet2.createCell(2);
            cellSheetA3.setCellValue("CASHFLOW");
            Cell cellSheetA4 = rowSheet2.createCell(3);
            cellSheetA4.setCellValue("TRANSACTION DATE");

            int rowNumSheet2 = 1;
            while (rsOtherQuery.next()){
                Row row2 = sheet2.createRow(rowNumSheet2);
                Cell cellA1 = row2.createCell(0);
                cellA1.setCellValue(rsOtherQuery.getString("item"));
                Cell cellA2 = row2.createCell(1);
                cellA2.setCellValue(rsOtherQuery.getString("amount"));
                Cell cellA3 = row2.createCell(2);
                cellA3.setCellValue(rsOtherQuery.getString("cashflow"));
                Cell cellA4 = row2.createCell(3);
                cellA4.setCellValue(rsOtherQuery.getString("transac_Date"));
                rowNumSheet2++;
            }
            rsOtherQuery.close();

            ResultSet rsTotalOtherAmount = statement.executeQuery(totalOtherAmount);
            int totalAmountForOther = 0;
            while (rsTotalOtherAmount.next()){
                totalAmountForOther = rsTotalOtherAmount.getInt("sum(amount)");
            }
            Row row4 = sheet2.createRow(rowNumSheet2+1);
            Cell cellC1 = row4.createCell(0);
            cellC1.setCellValue("Total amount");
            Cell cellC2 = row4.createCell(1);
            cellC2.setCellValue(totalAmountForOther);
            rsTotalOtherAmount.close();

            wb.write(os);
            SuccessAlert.showAlert("Done!");
        }catch(Exception e) {
                DisplayError.showErrorAlert("An error occurred when writing report.");
                e.printStackTrace();
        }
    }
}
