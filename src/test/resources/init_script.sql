CREATE SCHEMA banking;
SET
search_path = banking;
create table accounts
(
    id             serial
        constraint accounts_pk
            primary key,
    account_number varchar                       not null,
    owner_name     varchar                       not null,
    balance        double precision default 0    not null,
    currency       varchar          default 'USD':: character varying not null,
    active         boolean          default true not null
);

create
unique index accounts_account_number_uindex
    on accounts (account_number);

create
unique index accounts_id_uindex
    on accounts (id);

create table transactions
(
    id                  serial
        constraint transactions_pk
            primary key,
    from_account_number varchar
        constraint transactions_accounts_account_number_fk
            references accounts (account_number),
    to_account_number   varchar
        constraint transactions_accounts_account_number_fk_2
            references accounts (account_number),
    amount              double precision         default 0     not null,
    creation_time       timestamp with time zone default now() not null,
    type                varchar(32)              default 'INTERNAL':: character varying not null,
    status              varchar(32)              default 'CREATED':: character varying not null,
    description         varchar,
    transaction_number  varchar(16)                            not null
);

create
unique index transactions_id_uindex
    on transactions (id);

create
unique index transactions_transaction_number_uindex
    on transactions (transaction_number);

create
unique index transactions_transaction_number_uindex_2
    on transactions (transaction_number);

create table transactions_history
(
    id             serial
        constraint transactions_history_pk
            primary key,
    transaction_id integer                                not null
        constraint transactions_history_transactions_id_fk
            references transactions,
    status         varchar(16)                            not null,
    comment        varchar(1024),
    created_at     timestamp with time zone default now() not null
);

create
unique index transactions_history_id_uindex
    on transactions_history (id);