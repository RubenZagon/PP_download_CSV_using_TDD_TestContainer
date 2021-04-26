package com.finances.invoices.application;

import com.finances.invoices.domain.dto.InvoiceDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class DownloadInvoiceCSV {

    private static final String JUMP_LINE = "\n";
    private static final String SEPARATOR = ",";
    private static final String CSV_HEADERS = "Concepto, Categoría, Cantidad" + JUMP_LINE;

    public String execute(List<InvoiceDTO> invoiceLines) {
        StringBuilder body = new StringBuilder(CSV_HEADERS);
        invoiceLines
                .stream()
                .map(mapToInvoiceLine())
                .forEach(body::append);
        return body.toString().replaceAll("null", "");
    }

    private Function<InvoiceDTO, String> mapToInvoiceLine() {
        return invoiceLine ->
                invoiceLine.getConcept() + SEPARATOR +
                invoiceLine.getCategory() + SEPARATOR +
                invoiceLine.getAmount() + JUMP_LINE; }
}
