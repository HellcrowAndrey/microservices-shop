create table public.staffs
(
    id int8 generated by default as identity,
    avatar varchar(255),
    create_at timestamp not null,
    email varchar(100) not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    manager_id uuid not null,
    phone varchar(100) not null,
    status varchar(255),
    update_at timestamp not null,
    primary key (id)
);

create index email_idx on public.staffs (email);

alter table if exists public.staffs drop constraint if exists uk_staff_email;

alter table if exists public.staffs add constraint uk_staff_email unique (email);

alter table if exists public.staffs drop constraint if exists uk_staff_managerId;

alter table if exists public.staffs add constraint uk_staff_managerId unique (manager_id);