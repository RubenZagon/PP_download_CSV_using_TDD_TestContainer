package com.finances.invoices.application;

import com.finances.invoices.domain.dto.InvoiceDTO;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;

@Service
public class DownloadInvoiceCSV {

    private static final String CSV_HEADERS = "Concepto, Categoría, Cantidad";
    private static final String SEPARATOR = ",";

    public void execute(PrintWriter printWriter, List<InvoiceDTO> invoiceLines) {
        printWriter.write(CSV_HEADERS);
        invoiceLines
                .stream()
                .map(mapToInvoiceLine())
                .forEach(printWriter::write);
    }

    private Function<InvoiceDTO, String> mapToInvoiceLine() {
        return invoiceLine ->
                invoiceLine.getCategory() + SEPARATOR +
                invoiceLine.getConcept() + SEPARATOR +
                invoiceLine.getAmount() + "\n"; }
}
