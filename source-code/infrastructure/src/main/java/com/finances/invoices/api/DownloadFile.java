package com.finances.invoices.api;

import com.amazonaws.services.s3.Headers;
import com.finances.invoices.dto.InvoiceDTO;
import com.finances.invoices.ports.DownloadInvoiceService;
import com.finances.invoices.ports.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class DownloadFile {

    private static final String URL_GENERATE_INVOICE = "/download/csv/{cycle}/{month}";

    private final TransactionRepository invoiceRetriever;
    private final DownloadInvoiceService downloadInvoiceCSV;

    public DownloadFile(TransactionRepository invoiceRetriever, DownloadInvoiceService downloadInvoiceCSV) {
        this.invoiceRetriever = invoiceRetriever;
        this.downloadInvoiceCSV = downloadInvoiceCSV;
    }

    /**
     * Generate and download CSV file of invoices.
     */
    @RequestMapping(value = URL_GENERATE_INVOICE,
            method = RequestMethod.GET,
            produces = {"text/csv;charset=UTF-8"}
    )
    public ResponseEntity downloadInvoiceCSV(
            @PathVariable(name = "cycle") final  Integer cycle,
            @PathVariable(name = "month") String month
    ) {
            List<InvoiceDTO> invoiceLines = invoiceRetriever.getAllInvoicesWith(cycle, month);
            String body = downloadInvoiceCSV.execute(invoiceLines);
            MultiValueMap<String, String> headers = getDownloadCSVHeaders(cycle, month);
            return new ResponseEntity(body, headers, HttpStatus.OK);
    }

    private MultiValueMap<String, String> getDownloadCSVHeaders(Integer cycle, String invoiceNumber) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(Headers.CONTENT_TYPE, "text/csv;charset=UTF-8");
        String attachmentFile = String.format("attachment; filename=factura_%s_%s.csv", cycle, invoiceNumber);
        headers.add(Headers.CONTENT_DISPOSITION, attachmentFile);
        return headers;
    }
}
