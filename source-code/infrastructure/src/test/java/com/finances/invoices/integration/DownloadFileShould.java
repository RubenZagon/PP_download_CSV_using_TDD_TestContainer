package com.finances.invoices.integration;

import com.finances.invoices.CONCEPT_CATEGORY;
import com.finances.invoices.application.src.InvoiceRetrieve;
import com.finances.invoices.builders.Invoice;
import com.finances.invoices.domain.src.CONCEPT_CATEGORY;
import com.finances.invoices.domain.src.dto.InvoiceDTO;
import com.finances.invoices.helpers.DatabaseTestSuite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class DownloadFileShould extends DatabaseTestSuite {


    static {
        initScript = "database/init.sql";
    }

    private static final String DOWNLOAD_INVOICE_CSV = "/api/v1/download/csv";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private InvoiceRetrieve invoiceRetrieve;

    @Test
    void download_a_invoice_file_csv() throws Exception {
        Integer CYCLE = 2021;
        String MONTH = "JULIO";
        List<InvoiceDTO> invoiceLines = new ArrayList<>();
        invoiceLines.add(new Invoice()
                                 .withCategory(CONCEPT_CATEGORY.RESTAURANTE)
                                 .withConcept("guachinche")
                                 .withAmount(BigDecimal.valueOf(10))
                                 .build());
        invoiceLines.add(new Invoice()
                                 .withCategory(CONCEPT_CATEGORY.OCIO)
                                 .withConcept("netflix")
                                 .withAmount(BigDecimal.valueOf(12))
                                 .build());
        invoiceLines.add(new Invoice()
                                 .withCategory(CONCEPT_CATEGORY.RESTAURANTE)
                                 .withConcept("xin-xin sushi")
                                 .withAmount(BigDecimal.valueOf(30))
                                 .build());

        when(invoiceRetrieve.execute(CYCLE, MONTH))
                .thenReturn(invoiceLines);

        mockMvc
                .perform(get(DOWNLOAD_INVOICE_CSV + "/" + CYCLE + "/" + MONTH).contentType("text/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent(invoiceLines)));
    }

    private String expectedContent(List<InvoiceDTO> invoiceLines) {
        String CSV_HEADERS = "Concepto, Categoría, Cantidad\n";
        String SEPARATOR = ",";
        StringBuilder body = new StringBuilder(CSV_HEADERS);
        invoiceLines.stream()
                .map(mapToInvoiceLine(SEPARATOR))
                .forEach(body::append);
        return body.toString();
    }

    private Function<InvoiceDTO, String> mapToInvoiceLine(String SEPARATOR) {
        return invoiceLine ->
                invoiceLine.getConcept() + SEPARATOR +
                invoiceLine.getCategory() + SEPARATOR +
                invoiceLine.getAmount() + "\n";
    }
}