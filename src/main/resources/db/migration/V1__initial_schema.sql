CREATE TABLE accounts
(
    id       uuid PRIMARY KEY,
    login    varchar not null unique
);

create table exchange_accounts
(
    id            uuid primary key,
    amount        numeric(10, 2) default 0.00
        constraint positive_amount check (amount > 0),
    currency_code varchar not null,
    account_id    uuid    not null,
    constraint account_id_accounts_id_fk
        foreign key (account_id)
            references accounts (id)
);

create table exchange_account_transactions
(
    id                   serial                      not null primary key,
    happened_at          timestamp                   not null,
    type                 varchar                     not null,
    target_amount        numeric(10, 2) default 0.00 not null
        constraint positive_target_amount check (target_amount > 0),
    target_currency_code varchar                     not null,
    exchange_account_id  uuid                        not null,
    details              jsonb,
    constraint exchange_account_id_exchange_account_id_fk
        foreign key (exchange_account_id)
            references exchange_accounts (id),
    unique (id, exchange_account_id)
);

INSERT INTO accounts (id, login)
VALUES ('8fa4a163-af34-4190-bfba-5f2f1f282a11', 'test');

CREATE TABLE exchange_rates
(
    id             uuid primary key,
    code           varchar,
    buy_price            numeric(10, 6) not null
        constraint positive_buy_price check (buy_price > 0),
    sell_price            numeric(10, 6) not null
        constraint positive_sell_price check (sell_price > 0),
    effective_date date           not null,
    unique (code, effective_date)
);