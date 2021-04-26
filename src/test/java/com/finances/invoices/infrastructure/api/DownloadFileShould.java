package com.finances.invoices.infrastructure.api;

import com.finances.invoices.helpers.DatabaseTestSuite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void download_a_invoice_file_csv() throws Exception {

        mockMvc.perform(
                get(DOWNLOAD_INVOICE_CSV + "/" + "2021" + "/" + "JULIO" )
        ).andExpect(status().isOk());
//                .andExpect(content().json(PAYMENT_STILL_NOT_MADE));
    }
}