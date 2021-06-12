package com.finances.invoices.ports;

import com.finances.invoices.dto.InvoiceDTO;

import java.util.List;

public interface DownloadInvoiceService {
    String execute(List<InvoiceDTO> invoiceLines);
}
