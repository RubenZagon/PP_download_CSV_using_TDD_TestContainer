package com.finances.invoices.exceptions;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException() {
        super();
    }

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
