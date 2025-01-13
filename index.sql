create table authors
(
    id           int unsigned auto_increment
        primary key,
    author_name  varchar(255) not null,
    author_info  mediumtext   null,
    author_image varchar(255) null
);

create table carts
(
    id      int auto_increment
        primary key,
    user_id varchar(255) not null
);

create table cart_items
(
    id          int auto_increment
        primary key,
    book_id     int          not null,
    cart_id     int          not null,
    name        varchar(255) null,
    unit_price  double       null,
    quantity    int          null,
    total_price double       null,
    constraint cart_items_ibfk_1
        foreign key (cart_id) references carts (id)
);

create index cart_id
    on cart_items (cart_id);

create table categories
(
    id            int unsigned auto_increment
        primary key,
    category_name varchar(191) not null,
    parent_id     tinyint      null,
    `order`       tinyint      null
);

create index categories_category_name_index
    on categories (category_name);

create table comments
(
    id        int auto_increment
        primary key,
    content   varchar(255) null,
    createdAt datetime(6)  null,
    rate      int          null,
    title     varchar(255) null,
    updatedAt int          null,
    bookId    int          null,
    userId    int          null,
    constraint UKb86g3rc9y2t7w2v91lc7tjc9i
        unique (bookId, userId)
)
    collate = utf8mb3_general_ci;

create table companies
(
    id            int unsigned auto_increment
        primary key,
    company_name  varchar(191) not null,
    company_info  text         null,
    company_image varchar(191) null,
    companyImage  varchar(255) null,
    companyInfo   varchar(255) null,
    companyName   varchar(255) null
);

create table books
(
    id               int unsigned auto_increment
        primary key,
    book_name        varchar(191)  not null,
    description      text          null,
    publish_date     date          null,
    suggest          int           null,
    author_id        int unsigned  null,
    company_id       int unsigned  null,
    category_id      int unsigned  null,
    publishing_house varchar(191)  null,
    translator       varchar(191)  null,
    number_of_pages  int           null,
    quality          int default 0 null,
    price            bigint        null,
    cover_price      bigint        null,
    book_image       varchar(191)  null,
    images           text          null,
    created_at       timestamp     null,
    updated_at       timestamp     null,
    favorite         tinyint       not null,
    constraint FKfjixh2vym2cvfj3ufxj91jem7
        foreign key (author_id) references authors (id),
    constraint FKleqa3hhc0uhfvurq6mil47xk0
        foreign key (category_id) references categories (id),
    constraint FKmm6ik7jippnnqrn3k51kdngb2
        foreign key (company_id) references companies (id),
    constraint books_author_id_foreign
        foreign key (author_id) references authors (id),
    constraint books_category_id_foreign
        foreign key (category_id) references categories (id),
    constraint books_company_id_foreign
        foreign key (company_id) references companies (id)
);

create table customers
(
    customerId varchar(255) not null
        primary key,
    email      varchar(255) null,
    enabled    bit          null,
    fullname   varchar(255) null,
    isUsing2FA bit          not null,
    password   varchar(255) null,
    photo      varchar(255) null,
    roleId     varchar(255) null,
    secret     varchar(255) null
)
    collate = utf8mb3_general_ci;

create table hibernate_sequence
(
    next_val bigint null
)
    collate = utf8mb3_general_ci;

create table migrations
(
    id        int auto_increment
        primary key,
    batch     int          null,
    migration varchar(255) null
)
    collate = utf8mb3_general_ci;

create table password_resets
(
    email      varchar(191) not null,
    token      varchar(191) not null,
    created_at timestamp    null
);

create index password_resets_email_index
    on password_resets (email);

create index password_resets_token_index
    on password_resets (token);

create table payments
(
    id            int auto_increment
        primary key,
    amount        int          null,
    createdAT     datetime(6)  null,
    paymentStatus varchar(255) null,
    paymentType   varchar(255) null,
    rememberToken varchar(255) null,
    updatedAt     datetime(6)  null
)
    collate = utf8mb3_general_ci;

create table orders
(
    id               int auto_increment
        primary key,
    created_at       datetime(6)  null,
    nameReceiver     varchar(255) null,
    order_status     varchar(255) null,
    phoneReceiver    varchar(255) null,
    shipping_address varchar(255) null,
    shipping_fee     int          null,
    updated_at       datetime(6)  null,
    customerId       varchar(255) null,
    payment_id       int          null,
    constraint FK1bpj2iini89gbon333nm7tvht
        foreign key (customerId) references customers (customerId),
    constraint FK8aol9f99s97mtyhij0tvfj41f
        foreign key (payment_id) references payments (id)
)
    collate = utf8mb3_general_ci;

create table order_details
(
    id       int auto_increment
        primary key,
    price    double null,
    quality  int    null,
    book_id  int    null,
    order_id int    null,
    constraint FKjyu2qbqt8gnvno9oe9j2s2ldk
        foreign key (order_id) references orders (id)
)
    collate = utf8mb3_general_ci;

create table roles
(
    id         int          not null
        primary key,
    roleName   varchar(255) null,
    customerId varchar(255) null,
    constraint FKcotftqap7by5m4ibph3ss3xvo
        foreign key (customerId) references customers (customerId)
)
    collate = utf8mb3_general_ci;

create table saves
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    book_id    int          null,
    customerId varchar(255) null,
    constraint FKljl5fi81fce2vma624wnylid1
        foreign key (customerId) references customers (customerId)
)
    collate = utf8mb3_general_ci;

create table verificationtoken
(
    id         bigint       not null
        primary key,
    expiryDate datetime(6)  null,
    token      varchar(255) null,
    customerId varchar(255) not null,
    constraint FK_VERIFY_CUSTOMER
        foreign key (customerId) references customers (customerId)
)
    collate = utf8mb3_general_ci;

