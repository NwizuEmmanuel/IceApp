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
    static JRDesignQuery query;
    static public void printInvoice(String customer,int x) throws JRException {
        String sql = "select * from "+DatabaseActions.printerTable+" where customer='"+customer+"'";
        String sql1 = "select * from "+DatabaseActions.printerTable2+" where customer='"+customer+"'";
        if(x == 1){
            jasperDesign = JRXmlLoader.load("./src/jasperReporter/report_vat.jrxml");
            query = new JRDesignQuery();
            query.setText(sql);
            jasperDesign.setQuery(query);
        }else if(x == 2){
            jasperDesign = JRXmlLoader.load("./src/jasperReporter/report_other.jrxml");
            query = new JRDesignQuery();
            query.setText(sql1);
            jasperDesign.setQuery(query);
        }

        JasperReport report = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint printer = JasperFillManager.fillReport(report,null, DatabaseActions.connectToDB());

        JasperViewer.viewReport(printer,false);
    }

    public static void main(String[] args) throws JRException, SQLException, IOException {
        InvoicePrinter.printInvoice("Chumet",1);
    }
}
