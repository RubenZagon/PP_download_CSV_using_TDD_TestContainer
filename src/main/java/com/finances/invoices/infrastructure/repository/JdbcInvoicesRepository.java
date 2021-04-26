package com.finances.invoices.infrastructure.repository;

import com.finances.invoices.domain.CONCEPT_CATEGORY;
import com.finances.invoices.domain.dto.InvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcInvoicesRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<InvoiceDTO> getAllInvoicesWith(Integer cycle, String month) {

        String query = "" +
                " SELECT * FROM invoices " +
                "  WHERE cycle= " + cycle;


                return jdbcTemplate.query(query, (rs, rowNum) ->
                        new InvoiceDTO(
                                rs.getString("concept"),
                                rs.getObject("category", CONCEPT_CATEGORY.class),
                                rs.getBigDecimal("amount")
                        ));
    }
}
