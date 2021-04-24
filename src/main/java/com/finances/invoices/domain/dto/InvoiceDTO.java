package com.finances.invoices.domain.dto;

import com.finances.invoices.domain.CONCEPT_CATEGORY;

import java.math.BigDecimal;
import java.util.Objects;


public class InvoiceDTO {

    private final String concept;
    private final CONCEPT_CATEGORY category;
    private final BigDecimal amount;

    public InvoiceDTO(String concept, CONCEPT_CATEGORY category, BigDecimal amount) {
        this.concept = concept;
        this.category = category;
        this.amount = amount;
    }

    public String getConcept() { return concept; }

    public CONCEPT_CATEGORY getCategory() { return category; }

    public BigDecimal getAmount() { return amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDTO that = (InvoiceDTO) o;
        return Objects.equals(concept, that.concept) && category == that.category && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concept, category, amount);
    }
}
