package com.finances.invoices;


import com.finances.invoices.dto.InvoiceDTO;

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
