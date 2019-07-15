package br.edu.utfpr.pb.ProjetoFinal.util;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GenerateReport {

    public JasperViewer getReport(Connection conn,
                                  Map<String, Object> parameters,
                                  InputStream file) throws JRException {
        JasperReport report = (JasperReport) JRLoader.loadObject(file);

        JasperPrint printer = JasperFillManager.fillReport(report, parameters,
                conn);
        JasperViewer viewer = new JasperViewer(printer, false);
        viewer.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
        viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
        return viewer;
    }
}
