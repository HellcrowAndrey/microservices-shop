create table public.accounts
(
    id int8 generated by default as identity,
    create_at timestamp not null,
    status varchar(255),
    update_at timestamp not null,
    user_id uuid not null,
    address_id int8,
    contact_id int8,
    profile_id int8,
    primary key (id)
);

create table public.addresses
(
    id int8 generated by default as identity,
    city varchar(128) not null,
    country varchar(128) not null,
    create_at timestamp not null,
    flat_number varchar(50),
    region varchar(128),
    status varchar(255),
    street varchar(128) not null,
    street_number varchar(128) not null,
    update_at timestamp not null,
    zip_code varchar(255) not null,
    primary key (id)
);

create table public.contacts
(
    id int8 generated by default as identity,
    create_at timestamp not null,
    company_name varchar(150) not null,
    phone varchar(150) not null,
    status varchar(255),
    update_at timestamp not null,
    primary key (id)
);

create table public.profiles
(
    id int8 generated by default as identity,
    create_at timestamp not null,
    date_of_birth varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    patronymic varchar(255) not null,
    sex varchar(255),
    status varchar(255),
    update_at timestamp not null,
    primary key (id)
);

create table public.views
(
    id int8 generated by default as identity,
    create_at timestamp not null,
    product_id int8 not null,
    status varchar(255),
    update_at timestamp not null,
    view_id int8,
    primary key (id)
);

create index account_user_id_idx on public.accounts (user_id);

alter table if exists public.accounts drop constraint if exists uk_account_user_id_login;

alter table if exists public.accounts add constraint uk_account_user_id_login unique (user_id);

alter table if exists public.accounts add constraint account_address_fk foreign key (address_id) references public.addresses;

alter table if exists public.accounts add constraint account_contact_fk foreign key (contact_id) references public.contacts;

alter table if exists public.accounts add constraint account_profile_fk foreign key (profile_id) references public.profiles;

alter table if exists public.views add constraint accounts_view_fk foreign key (view_id) references public.accounts;
