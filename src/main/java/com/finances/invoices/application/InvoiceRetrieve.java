package com.finances.invoices.application;

import com.finances.invoices.domain.dto.InvoiceDTO;
import com.finances.invoices.infrastructure.api.exception.InvoiceNotFoundException;
import com.finances.invoices.infrastructure.repository.JdbcInvoicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class InvoiceRetrieve {

    @Autowired
    private final JdbcInvoicesRepository invoicesRepository;

    public InvoiceRetrieve(JdbcInvoicesRepository invoicesRepository) {
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
