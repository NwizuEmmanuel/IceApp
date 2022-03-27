package reports;

import alerts.DisplayError;
import alerts.SuccessAlert;
import database.DatabaseActions;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

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
            Font fontSheet1 = wb.createFont();
            fontSheet1.setBold(true);
            CellStyle cellStyleSheet1 = wb.createCellStyle();
            cellStyleSheet1.setFont(fontSheet1);
            Row row = sheet1.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("FULLNAME");
            cell.setCellStyle(cellStyleSheet1);
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("TELEPHONE");
            cell2.setCellStyle(cellStyleSheet1);
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("COURSE");
            cell3.setCellStyle(cellStyleSheet1);
            Cell cell4 = row.createCell(3);
            cell4.setCellValue("AMOUNT(₦)");
            cell4.setCellStyle(cellStyleSheet1);
            Cell cell5 = row.createCell(4);
            cell5.setCellValue("REGISTRATION DATE");
            cell5.setCellStyle(cellStyleSheet1);

            int rowNum = 1;
            while (rsInternQuery.next()){
                row = sheet1.createRow(rowNum);
                Cell cellA1 = row.createCell(0);
                cellA1.setCellValue(rsInternQuery.getString("fullname"));
                Cell cellA2 = row.createCell(1);
                String tel = rsInternQuery.getString("telephone");
                String tel2 = tel.replaceFirst("^[0]","+234");
                cellA2.setCellValue(tel2);
                Cell cellA3 = row.createCell(2);
                cellA3.setCellValue(rsInternQuery.getString("course"));
                Cell cellA4 = row.createCell(3);
                cellA4.setCellValue(Integer.parseInt(rsInternQuery.getString("Amount")));
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
            cellB1.setCellValue("Total amount = ₦"+totalAmountIntern);
            cellB1.setCellStyle(cellStyleSheet1);
            sheet1.addMergedRegion(new CellRangeAddress(rowNum+1,rowNum+1,0,1));
            sheet1.autoSizeColumn(0);
            sheet1.autoSizeColumn(1);
            sheet1.autoSizeColumn(2);
            sheet1.autoSizeColumn(3);
            sheet1.autoSizeColumn(4);
            rsTotalAmountIntern.close();

            ResultSet rsOtherQuery = statement.executeQuery(otherQuery);
            Sheet sheet2 = wb.createSheet("Other Reports");
            Font fontSheet2 = wb.createFont();
            fontSheet2.setBold(true);
            CellStyle cellStyleSheet2 = wb.createCellStyle();
            cellStyleSheet2.setFont(fontSheet2);
            Row rowSheet2 = sheet2.createRow(0);
            Cell cellSheetA1 = rowSheet2.createCell(0);
            cellSheetA1.setCellValue("ITEM");
            cellSheetA1.setCellStyle(cellStyleSheet2);
            Cell cellSheetA2 = rowSheet2.createCell(1);
            cellSheetA2.setCellValue("AMOUNT(₦)");
            cellSheetA2.setCellStyle(cellStyleSheet2);
            Cell cellSheetA3 = rowSheet2.createCell(2);
            cellSheetA3.setCellValue("CASHFLOW");
            cellSheetA3.setCellStyle(cellStyleSheet2);
            Cell cellSheetA4 = rowSheet2.createCell(3);
            cellSheetA4.setCellValue("TRANSACTION DATE");
            cellSheetA4.setCellStyle(cellStyleSheet2);

            int rowNumSheet2 = 1;
            while (rsOtherQuery.next()){
                Row row2 = sheet2.createRow(rowNumSheet2);
                Cell cellA1 = row2.createCell(0);
                cellA1.setCellValue(rsOtherQuery.getString("item"));
                Cell cellA2 = row2.createCell(1);
                cellA2.setCellValue(Integer.parseInt(rsOtherQuery.getString("amount")));
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
            cellC1.setCellValue("Total amount = ₦"+totalAmountForOther);
            cellC1.setCellStyle(cellStyleSheet2);
            sheet2.addMergedRegion(new CellRangeAddress(rowNumSheet2+1,rowNumSheet2+1,0,2));
            sheet2.autoSizeColumn(0);
            sheet2.autoSizeColumn(1);
            sheet2.autoSizeColumn(2);
            sheet2.autoSizeColumn(3);
            rsTotalOtherAmount.close();

            wb.write(os);
            SuccessAlert.showAlert("Done!");
        }catch(Exception e) {
                DisplayError.showErrorAlert("An error occurred when writing report.");
                e.printStackTrace();
        }
    }
}
