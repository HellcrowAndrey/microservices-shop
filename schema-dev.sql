DROP DATABASE IF EXISTS "products_dev.db";
CREATE DATABASE "products_dev.db";

DROP DATABASE IF EXISTS "orders_dev.db";
CREATE DATABASE "orders_dev.db";

DROP DATABASE IF EXISTS "users_dev.db";
CREATE DATABASE "users_dev.db";

DROP DATABASE IF EXISTS "statistics_dev.db";
CREATE DATABASE "statistics_dev.db";

DROP DATABASE IF EXISTS "managers_dev.db";
CREATE DATABASE "managers_dev.db";

\c users_dev.db

create table public.confirm_token
(
    id          int8 generated by default as identity,
    client_url  varchar(255) not null,
    create_date timestamp    not null,
    token       varchar(255) not null,
    user_id     int8         not null,
    primary key (id)
);

create table public.pass_reset_token
(
    id          int8 generated by default as identity,
    expire_time timestamp,
    new_pass    varchar(255),
    token       varchar(255),
    user_id     int8 not null,
    primary key (id)
);

create table public.refresh_session
(
    id            int8 generated by default as identity,
    expire_at     timestamp,
    expire_in     timestamp,
    fingerprint   varchar(255) not null,
    ip            varchar(255) not null,
    refresh_token text         not null,
    user_id       int8         not null,
    primary key (id)
);

create table public.role
(
    id   int8 generated by default as identity,
    role varchar(255) not null,
    primary key (id)
);

create table public.user
(
    id        int8 generated by default as identity,
    email     varchar(255)          not null,
    f_name    varchar(255)          not null,
    is_enable boolean default false not null,
    l_name    varchar(255)          not null,
    login     varchar(255)          not null,
    pass      varchar(255)          not null,
    primary key (id)
);

create index confirm_token_idx on public.confirm_token (token);

alter table if exists public.confirm_token
    drop constraint if exists UK_t6dikkyhbesb149pdavjv4y21;

alter table if exists public.confirm_token
    add constraint UK_t6dikkyhbesb149pdavjv4y21 unique (token);

create index reset_token_idx on public.pass_reset_token (token);

create index email_idx on public.user (email);

create index login_idx on public.user (login);

alter table if exists public.user
    drop constraint if exists UK_ob8kqyqqgmefl0aco34akdtpe;

alter table if exists public.user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table if exists public.user
    drop constraint if exists UK_ew1hvam8uwaknuaellwhqchhb;

alter table if exists public.user
    add constraint UK_ew1hvam8uwaknuaellwhqchhb unique (login);

create table users_roles
(
    user_id int8 not null,
    role_id int8 not null
);

alter table if exists public.confirm_token
    add constraint FK25rlycq258nhpffe8hn7c5id foreign key (user_id) references public.user;

alter table if exists public.pass_reset_token
    add constraint FKpnpedtvvatbratnudqis6i51q foreign key (user_id) references public.user;

alter table if exists users_roles
    add constraint FKt4v0rrweyk393bdgt107vdx0x foreign key (role_id) references public.role;

alter table if exists users_roles
    add constraint FKgd3iendaoyh04b95ykqise6qh foreign key (user_id) references public.user;

\c products_dev.db

create table public.categories
(
    id   int8 generated by default as identity,
    name varchar(50) not null,
    primary key (id)
);

create table public.comment
(
    id         int8 generated by default as identity,
    comment    varchar(255) not null,
    name       varchar(255) not null,
    comment_id int8,
    primary key (id)
);

create table public.product
(
    id            int8 generated by default as identity,
    create_date   date         not null,
    description   varchar(255) not null,
    name          varchar(255),
    preview_image varchar(255) not null,
    price         DECIMAL(8, 3),
    quantity      int4         not null,
    status        varchar(255),
    categories_id int8         not null,
    create_at     timestamp    not null,
    update_at     timestamp    not null,
    primary key (id)
);

create table public.specification
(
    id               int8 generated by default as identity,
    description      varchar(255) not null,
    name             varchar(50)  not null,
    specification_id int8,
    primary key (id)
);

create table product_images
(
    product_id int8 not null,
    images     varchar(255)
);

alter table if exists public.comment
    add constraint product_comments_fk foreign key (comment_id) references public.product;

alter table if exists public.product
    add constraint FK96ie9fvomwiok25wtg1qs6s1o foreign key (categories_id) references public.categories;

alter table if exists public.specification
    add constraint product_specification_fk foreign key (specification_id) references public.product;

alter table if exists product_images
    add constraint FKi8jnqq05sk5nkma3pfp3ylqrt foreign key (product_id) references public.product;

\c orders_dev.db

create table public.customers
(
    id               int8 generated by default as identity,
    customer_address varchar(255) not null,
    customer_email   varchar(128) not null,
    customer_name    varchar(255) not null,
    customer_phone   varchar(128) not null,
    primary key (id)
);

create table public.order_details
(
    id          int8 generated by default as identity,
    amount      DECIMAL(8, 3) not null,
    status      varchar(255),
    user_id     int8          not null,
    customer_id int8          not null,
    create_at   timestamp     not null,
    update_at   timestamp     not null,
    primary key (id)
);

create index order_user_idx on public.order_details (user_id);

create table order_detail_product_ids
(
    order_detail_id int8 not null,
    product_ids     int8
);

alter table if exists public.order_details
    add constraint FKad7jnqbuth2owkacxvj5nudt6 foreign key (customer_id) references public.customers;

alter table if exists order_detail_product_ids
    add constraint FK54gbd75d4hnf6ax1ph1nrc2hm foreign key (order_detail_id) references public.order_details;

\c managers_dev.db

create table "managers"
(
    "id"         serial,
    "created_at" timestamp with time zone,
    "updated_at" timestamp with time zone,
    "deleted_at" timestamp with time zone,
    "manager_id" integer,
    "first_name" text,
    "last_name"  text,
    primary key (id)
);

create index idx_managers_deleted_at ON "managers" (deleted_at);

create table "purchases"
(
    "id"         serial,
    "manager_id" integer,
    "order_id"   integer,
    "status"     text,
    primary key ("id")
);
