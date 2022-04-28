package reports;

import alerts.DisplayError;
import alerts.SuccessAlert;
import database.DatabaseActions;
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
        String otherQuery = "select item, amount, transac_date from "+DatabaseActions.tableNameForOthers+" where data_group_value = '"+dgv+"' ";
        String other2Query = "select * from "+DatabaseActions.tableNameForOthers2+"";
        String totalInternAmount = "select sum(amount) from "+DatabaseActions.tableNameForIntern+" where data_group_value = '"+dgv+"' ";
        String totalOtherExpense = "select sum(amount) from "+DatabaseActions.tableNameForOthers+" where data_group_value = '"+dgv+"' ";
        String totalVat = "select sum(vat) from "+DatabaseActions.tableNameForOthers2+"";
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
//            End of intern report builder

            ResultSet rsOtherQuery = statement.executeQuery(otherQuery);
            Sheet sheet2 = wb.createSheet("Other Reports(Expense)");
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
            Cell cellSheetA4 = rowSheet2.createCell(2);
            cellSheetA4.setCellValue("TRANSACTION DATE");
            cellSheetA4.setCellStyle(cellStyleSheet2);

            int rowNumSheet2 = 1;
            while (rsOtherQuery.next()){
                Row row2 = sheet2.createRow(rowNumSheet2);
                Cell cellA1 = row2.createCell(0);
                cellA1.setCellValue(rsOtherQuery.getString("item"));
                Cell cellA2 = row2.createCell(1);
                cellA2.setCellValue(Integer.parseInt(rsOtherQuery.getString("amount")));
                Cell cellA4 = row2.createCell(2);
                cellA4.setCellValue(rsOtherQuery.getString("transac_Date"));
                rowNumSheet2++;
            }
            rsOtherQuery.close();

            ResultSet rsTotalOtherExpense = statement.executeQuery(totalOtherExpense);
            int totalExpenseForOther = 0;
            while (rsTotalOtherExpense.next()){
                totalExpenseForOther = rsTotalOtherExpense.getInt("sum(amount)");
            }
            Row row5 = sheet2.createRow(rowNumSheet2+1);
            Cell cellD1 = row5.createCell(0);
            cellD1.setCellValue("Total = ₦"+totalExpenseForOther);
            cellD1.setCellStyle(cellStyleSheet2);
            sheet2.addMergedRegion(new CellRangeAddress(rowNumSheet2+1,rowNumSheet2+1,0,2));
            sheet2.autoSizeColumn(0);
            sheet2.autoSizeColumn(1);
            sheet2.autoSizeColumn(2);
            rsTotalOtherExpense.close();
//            End of other report builder

            ResultSet rsOther2Query = statement.executeQuery(other2Query);
            Sheet sheet3 = wb.createSheet("Other Reports(Income)");
            Font fontSheet3 = wb.createFont();
            fontSheet3.setBold(true);
            CellStyle cellStyleSheet3 = wb.createCellStyle();
            cellStyleSheet3.setFont(fontSheet3);
            Row rowSheet3 = sheet3.createRow(0);
            Cell cellSheetB1 = rowSheet3.createCell(0);
            cellSheetB1.setCellValue("CUSTOMER");
            cellSheetB1.setCellStyle(cellStyleSheet3);
            Cell cellSheetB2 = rowSheet3.createCell(1);
            cellSheetB2.setCellValue("ADDRESS");
            cellSheetB2.setCellStyle(cellStyleSheet3);
            Cell cellSheetB3 = rowSheet3.createCell(2);
            cellSheetB3.setCellValue("TELEPHONE");
            cellSheetB3.setCellStyle(cellStyleSheet3);
            Cell cellSheetB4 = rowSheet3.createCell(3);
            cellSheetB4.setCellValue("DESCRIPTION");
            cellSheetB4.setCellStyle(cellStyleSheet3);
            Cell cellSheetB5 = rowSheet3.createCell(4);
            cellSheetB5.setCellValue("AMOUNT");
            cellSheetB5.setCellStyle(cellStyleSheet3);
            Cell cellSheetB9 = rowSheet3.createCell(5);
            cellSheetB9.setCellValue("QUANTITY");
            cellSheetB9.setCellStyle(cellStyleSheet3);
            Cell cellSheetB6 = rowSheet3.createCell(6);
            cellSheetB6.setCellValue("TRANSACTION DATE");
            cellSheetB6.setCellStyle(cellStyleSheet3);
            Cell cellSheetB7 = rowSheet3.createCell(7);
            cellSheetB7.setCellValue("DUE DATE");
            cellSheetB7.setCellStyle(cellStyleSheet3);
            Cell cellSheetB8 = rowSheet3.createCell(8);
            cellSheetB8.setCellValue("VAT");
            cellSheetB8.setCellStyle(cellStyleSheet3);

            int rowNumSheet3 = 1;
            while (rsOther2Query.next()){
                Row row2 = sheet3.createRow(rowNumSheet3);
                Cell cellA1 = row2.createCell(0);
                cellA1.setCellValue(rsOther2Query.getString("customer"));
                Cell cellA2 = row2.createCell(1);
                cellA2.setCellValue(rsOther2Query.getString("address"));
                Cell cellA3 = row2.createCell(2);
                cellA3.setCellValue(rsOther2Query.getString("telephone"));
                Cell cellA4 = row2.createCell(3);
                cellA4.setCellValue(rsOther2Query.getString("description"));
                Cell cellA5 = row2.createCell(4);
                cellA5.setCellValue(Double.parseDouble(rsOther2Query.getString("amount")));
                Cell cellA9 = row2.createCell(5);
                cellA9.setCellValue(Integer.parseInt(rsOther2Query.getString("quantity")));
                Cell cellA6 = row2.createCell(6);
                cellA6.setCellValue(rsOther2Query.getString("tran_date"));
                Cell cellA7 = row2.createCell(7);
                cellA7.setCellValue(rsOther2Query.getString("due_date"));
                Cell cellA8 = row2.createCell(8);
                cellA8.setCellValue(rsOther2Query.getString("vat"));
                rowNumSheet3++;
            }
            rsOther2Query.close();

            int totalVatNum = 0;
            ResultSet rsTotalVat = statement.executeQuery(totalVat);
            while (rsTotalVat.next()){
                totalVatNum = rsTotalVat.getInt("sum(vat)");
            }
            rsTotalVat.close();

            Row row6_1 = sheet3.createRow(rowNumSheet3+1);
            Cell cellE2 = row6_1.createCell(0);
            cellE2.setCellValue("Total VAT = ₦"+totalVatNum);
            cellE2.setCellStyle(cellStyleSheet3);
            sheet3.addMergedRegion(new CellRangeAddress(rowNumSheet3+1,rowNumSheet3+1,0,2));
            sheet3.autoSizeColumn(0);
            sheet3.autoSizeColumn(1);
            sheet3.autoSizeColumn(2);
            sheet3.autoSizeColumn(3);
            sheet3.autoSizeColumn(4);
            sheet3.autoSizeColumn(5);
            sheet3.autoSizeColumn(6);
            sheet3.autoSizeColumn(7);
            sheet3.autoSizeColumn(8);
//            End of other2 report builder

            wb.write(os);
            SuccessAlert.showAlert("Done!");
        }catch(Exception e) {
                DisplayError.showErrorAlert("An error occurred when writing report.");
                e.printStackTrace();
        }
    }
}
