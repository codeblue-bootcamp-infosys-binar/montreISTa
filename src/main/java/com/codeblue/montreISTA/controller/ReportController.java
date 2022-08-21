package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Tag(name = "14. Reports Controller")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor  //kalau udah pakai AllArgsConstructor ngga perlu lagi pakai anotasi @AutoWired
@RequestMapping("/team3")

public class ReportController {
    private ReportService reportService;

    private HttpServletResponse response;

    @GetMapping("/print/HistoryDetailsSeller/{id}")
    public void getProductHistoryTransactionSellerById(Long transactionId) throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"History Detail Transaction Seller.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrintHistoryTransaction(transactionId);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping("/print/HistoryDetailBuyer/{id}")
    public void getProductUReportTransactionBuyerById(Long transactionId) throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"History Detail Transaction Buyer.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrintTransactionById(transactionId);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

}
