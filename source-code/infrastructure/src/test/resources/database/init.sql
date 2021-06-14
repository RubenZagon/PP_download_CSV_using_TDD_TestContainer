DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices
(
    ID           SERIAL PRIMARY KEY NOT NULL,
    CONCEPT      VARCHAR(255),
    CATEGORY     VARCHAR(255),
    AMOUNT       INTEGER,
    MONTH        VARCHAR(255),
    CYCLE        INTEGER
);

INSERT INTO public.invoices (concept, category, amount, month, cycle) VALUES ('guachinche', 'RESTAURANTE', 10, 'JULIO', 2021);
INSERT INTO public.invoices (concept, category, amount, month, cycle) VALUES ('netflix', 'OCIO', 12, 'JULIO', 2021);
INSERT INTO public.invoices (concept, category, amount, month, cycle) VALUES ('xin-xin sushi', 'RESTAURANTE', 30, 'JULIO', 2021);