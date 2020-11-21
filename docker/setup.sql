CREATE USER currencystock WITH PASSWORD 'currencystock';
CREATE DATABASE currencystock WITH OWNER currencystock ENCODING 'UTF8';

\c currencystock

GRANT ALL PRIVILEGES ON DATABASE currencystock TO currencystock;
