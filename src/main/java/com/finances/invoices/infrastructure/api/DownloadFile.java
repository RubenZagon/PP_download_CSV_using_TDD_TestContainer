package com.finances.invoices.infrastructure.api;

import com.amazonaws.services.s3.Headers;
import com.finances.invoices.application.DownloadInvoiceCSV;
import com.finances.invoices.application.InvoiceRetrieve;
import com.finances.invoices.domain.dto.InvoiceDTO;
import com.finances.invoices.infrastructure.api.exception.InvoiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class DownloadFile {

    private static final String URL_GENERATE_INVOICE = "/download/csv/{cycle}/{month}";

    InvoiceRetrieve invoiceRetriever;
    DownloadInvoiceCSV downloadInvoiceCSV;

    public DownloadFile(InvoiceRetrieve invoiceRetriever, DownloadInvoiceCSV downloadInvoiceCSV) {
        this.invoiceRetriever = invoiceRetriever;
        this.downloadInvoiceCSV = downloadInvoiceCSV;
    }

    /**
     * Generate and download CSV file of invoices.
     */
    @RequestMapping(value = URL_GENERATE_INVOICE,
            method = RequestMethod.GET,
            produces = "text/csv"
    )
    public ResponseEntity downloadInvoiceCSV(
            @PathVariable(name = "cycle") final  Integer cycle,
            @PathVariable(name = "month") String month,
            HttpServletResponse response
    ) {
        try {
            List<InvoiceDTO> invoiceLines = invoiceRetriever.execute(cycle, month);
            downloadInvoiceCSV.execute(response.getWriter(),invoiceLines);
            MultiValueMap<String, String> headers = getDownloadCSVHeaders(cycle, month);
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } catch (InvoiceNotFoundException | IOException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    private MultiValueMap<String, String> getDownloadCSVHeaders(Integer cycle, String invoiceNumber) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(Headers.CONTENT_TYPE, "text/csv");
        String attachmentFile = String.format("attachment; filename=factura_%s_%s.csv", cycle, invoiceNumber);
        headers.add(Headers.CONTENT_DISPOSITION, attachmentFile);
        return headers;
    }
}
