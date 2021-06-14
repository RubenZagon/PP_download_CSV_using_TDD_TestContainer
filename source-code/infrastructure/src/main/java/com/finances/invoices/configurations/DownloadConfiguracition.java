package com.finances.invoices.configurations;

import com.finances.invoices.adapters.DownloadInvoiceCSV;
import com.finances.invoices.ports.DownloadInvoiceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DownloadConfiguracition {

    @Bean
    public DownloadInvoiceService downloadService() {
        return new DownloadInvoiceCSV();
    }

}
