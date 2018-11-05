
CREATE DATABASE business_core CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create table area
(
  id      int         not null
    primary key,
  name    varchar(32) null,
  area_id varchar(8)  null,
  city_id varchar(8)  null
);

create table area_price
(
  `_id`   int         not null
    primary key,
  name    varchar(32) null,
  area_id varchar(8)  null,
  city_id varchar(8)  null,
  price   bigint(10)  null
  comment '价格，基数'
);

create table calculation_item
(
  id        varchar(36)  not null,
  name      varchar(255) null,
  price     bigint(10)   null
  comment '单价',
  num_min   bigint(255)  not null
  comment '数量范围，最小',
  num_max   bigint(255)  not null
  comment '数量范围，最大，-1不限制',
  type_id   varchar(255) not null
  comment '分类id',
  type_name varchar(255) null
  comment '分类名称',
  primary key (id, type_id, num_max, num_min)
);

create table city
(
  id          int         not null
    primary key,
  city_id     varchar(8)  null,
  name        varchar(32) null,
  province_id varchar(8)  null
);

create table province
(
  id          int         not null
    primary key,
  name        varchar(16) null,
  province_id varchar(8)  null
);

