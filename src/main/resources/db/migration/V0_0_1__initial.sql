CREATE DATABASE finances;

CREATE TABLE invoices
(
    ID           SERIAL PRIMARY KEY NOT NULL,
    CONCEPT      VARCHAR(255),
    CATEGORY     VARCHAR(255),
    AMOUNT       INTEGER
);


INSERT INTO public.invoices (concept, category, amount) VALUES ('guachinche', 'Restaurante', 10);
INSERT INTO public.invoices (concept, category, amount) VALUES ('netflix', 'Suscripciones', 12);
INSERT INTO public.invoices (concept, category, amount) VALUES ('xin-xin sushi', 'Restaurante', 30);
