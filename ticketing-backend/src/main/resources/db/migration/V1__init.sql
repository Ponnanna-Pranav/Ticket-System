create extension if not exists pgcrypto;
create table users (
  id uuid primary key default gen_random_uuid(),
  email text unique not null,
  password_hash text not null,
  name text not null,
  enabled boolean not null default true,
  created_at timestamptz not null default now()
);
create table roles (
  id serial primary key,
  name text unique not null
);
create table user_roles (
  user_id uuid references users(id) on delete cascade,
  role_id int references roles(id) on delete cascade,
  primary key (user_id, role_id)
);
create type ticket_status as enum ('OPEN','IN_PROGRESS','RESOLVED','CLOSED');
create type ticket_priority as enum ('LOW','MEDIUM','HIGH','URGENT');
create table tickets (
  id uuid primary key default gen_random_uuid(),
  subject text not null,
  description text not null,
  priority ticket_priority not null default 'MEDIUM',
  status ticket_status not null default 'OPEN',
  owner_id uuid not null references users(id),
  assignee_id uuid references users(id),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  resolved_at timestamptz,
  closed_at timestamptz
);
create index on tickets(owner_id);
create index on tickets(assignee_id);
create index on tickets(status);
create index on tickets(priority);
create table comments (
  id uuid primary key default gen_random_uuid(),
  ticket_id uuid not null references tickets(id) on delete cascade,
  author_id uuid not null references users(id),
  body text not null,
  created_at timestamptz not null default now()
);
create table ratings (
  id uuid primary key default gen_random_uuid(),
  ticket_id uuid not null unique references tickets(id) on delete cascade,
  rater_id uuid not null references users(id),
  score int not null check (score between 1 and 5),
  feedback text,
  created_at timestamptz not null default now()
);
insert into roles(name) values ('ADMIN'),('AGENT'),('USER');
