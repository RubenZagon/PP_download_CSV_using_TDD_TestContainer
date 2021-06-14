package com.finances.invoices.ports;

import com.finances.invoices.dto.InvoiceDTO;

import java.util.List;

public interface TransactionRepository {
    List<InvoiceDTO> getAllInvoicesWith(Integer cycle, String month);
}
