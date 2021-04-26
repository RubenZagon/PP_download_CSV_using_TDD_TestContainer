CREATE TABLE invoices
(
    ID           SERIAL PRIMARY KEY NOT NULL,
    CONCEPT      VARCHAR(255),
    CATEGORY     VARCHAR(255),
    AMOUNT       INTEGER
);

INSERT INTO public.invoices (concept, category, amount) VALUES ('guachinche', 'RESTAURANTE', 10);
INSERT INTO public.invoices (concept, category, amount) VALUES ('netflix', 'OCIO', 12);
INSERT INTO public.invoices (concept, category, amount) VALUES ('xin-xin sushi', 'RESTAURANTE', 30);