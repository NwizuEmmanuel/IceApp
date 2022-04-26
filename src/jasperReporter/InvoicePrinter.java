package jasperReporter;

import database.DatabaseActions;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;

public class InvoicePrinter {
    static JasperDesign jasperDesign;
    static public void printInvoice(String customer,int x) throws JRException {
        String sql = "select * from "+DatabaseActions.tableNameForOthers2+" where customer='"+customer+"'";
        if(x == 1){
            jasperDesign = JRXmlLoader.load("./src/jasperReporter/report_vat.jrxml");
        }else if(x == 2){
            jasperDesign = JRXmlLoader.load("./src/jasperReporter/");
        }
        JRDesignQuery query = new JRDesignQuery();
        query.setText(sql);
        jasperDesign.setQuery(query);

        JasperReport report = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint printer = JasperFillManager.fillReport(report,null, DatabaseActions.connectToDB());

        JasperViewer.viewReport(printer);
    }

    public static void main(String[] args) throws JRException, SQLException, IOException {
        InvoicePrinter.printInvoice("Chumet",1);
    }
}
