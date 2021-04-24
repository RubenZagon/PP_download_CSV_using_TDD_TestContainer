package com.finances.invoices.application;

import com.finances.invoices.domain.dto.InvoiceDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceRetrieve {

    public List<InvoiceDTO> execute(Integer cycle, String month) {
        return new ArrayList<>();
    }
}
