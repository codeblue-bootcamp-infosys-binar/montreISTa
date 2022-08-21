package com.codeblue.montreISTA.service;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {

    private DataSource dataSource;

    private Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public JasperPrint generateJasperPrintHistoryTransaction(Long transactionId) throws Exception{
        InputStream fileReport = new ClassPathResource("reports/HistoryBuyer_Table_Based.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",transactionId);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());
        return jasperPrint;
    }

    public JasperPrint generateJasperPrintTransactionById(Long transactionId) throws Exception{
        InputStream fileReport = new ClassPathResource("reports/HistorySellerDetails.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",transactionId);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
        return jasperPrint;
    }
}
