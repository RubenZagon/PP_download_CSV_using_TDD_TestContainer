package com.finances.invoices.adapters;

import com.finances.invoices.dto.InvoiceDTO;
import com.finances.invoices.ports.DownloadInvoiceService;

import java.util.List;
import java.util.function.Function;

public class DownloadInvoiceCSV implements DownloadInvoiceService {

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
