package com.finances.invoices.builders;

import com.finances.invoices.CONCEPT_CATEGORY;
import com.finances.invoices.dto.InvoiceDTO;

import java.math.BigDecimal;

public class Invoice {
    private String concept;
    private CONCEPT_CATEGORY category;
    private BigDecimal amount;

    public Invoice withConcept(String concept) {
        this.concept = concept;
        return this;
    }

    public Invoice withCategory(CONCEPT_CATEGORY category) {
        this.category = category;
        return this;
    }

    public Invoice withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public InvoiceDTO build() {
        return new InvoiceDTO(this.concept,
                              this.category,
                              this.amount);
    }
}
