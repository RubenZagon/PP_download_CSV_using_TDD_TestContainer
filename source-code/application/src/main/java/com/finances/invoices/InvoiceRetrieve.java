package com.finances.invoices;


import com.finances.invoices.dto.InvoiceDTO;
import com.finances.invoices.exceptions.InvoiceNotFoundException;
import com.finances.invoices.ports.TransactionRepository;

import java.util.List;

import static java.lang.String.format;

public class InvoiceRetrieve {

    private final TransactionRepository invoicesRepository;

    public InvoiceRetrieve(TransactionRepository invoicesRepository) {
        this.invoicesRepository = invoicesRepository;
    }

    public List<InvoiceDTO> execute(Integer cycle, String month) {
        List<InvoiceDTO> invoices = invoicesRepository.getAllInvoicesWith(cycle, month);
        if (invoices.isEmpty()){
            throw new InvoiceNotFoundException(format("invoice of month %s and cycle %s not found", month, cycle));
        }
        return invoices;
    }
}
