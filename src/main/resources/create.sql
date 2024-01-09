-- create.sql




create table admin_role_users (
                                  user_id bigint not null,
                                  role_id bigint not null,
                                  primary key (user_id, role_id)
) ENGINE=InnoDB;

create table admin_roles (
                             id bigint not null auto_increment,
                             name varchar(60),
                             primary key (id)
) ENGINE=InnoDB;

create table admin_users (
                             id bigint not null auto_increment,
                             createdAt datetime not null,
                             updatedAt datetime not null,
                             email varchar(40),
                             name varchar(40),
                             password varchar(100),
                             username varchar(15),
                             primary key (id)
) ENGINE=InnoDB;

create table cmy_choices (
                             id bigint not null auto_increment,
                             text varchar(40),
                             cmy_poll_id bigint not null,
                             primary key (id)
) ENGINE=InnoDB;

create table cmy_employees (
                               id bigint not null auto_increment,
                               createdAt datetime not null,
                               updatedAt datetime not null,
                               email_id varchar(255) not null,
                               first_name varchar(255),
                               last_name varchar(255),
                               primary key (id)
) ENGINE=InnoDB;

create table cmy_polls (
                           id bigint not null auto_increment,
                           createdAt datetime not null,
                           updatedAt datetime not null,
                           createdBy bigint,
                           updatedBy bigint,
                           expirationDateTime datetime not null,
                           question varchar(140),
                           primary key (id)
) ENGINE=InnoDB;

create table cmy_users (
                           id bigint not null auto_increment,
                           createdAt datetime not null,
                           updatedAt datetime not null,
                           email varchar(255),
                           firstName varchar(255),
                           lastName varchar(255),
                           primary key (id)
) ENGINE=InnoDB;

create table cmy_votes (
                           id bigint not null auto_increment,
                           createdAt datetime not null,
                           updatedAt datetime not null,
                           cmy_choice_id bigint not null,
                           cmy_poll_id bigint not null,
                           cmy_user_id bigint not null,
                           primary key (id)
) ENGINE=InnoDB;

# alter table admin_roles
#     drop index UK_bwtldft72gjvgdspfymt0p7oa;
#
# alter table admin_roles
#     add constraint UK_bwtldft72gjvgdspfymt0p7oa unique (name);
#
# alter table admin_users
#     drop index UK3fgxk4ewgaxgtgvqwb1jjudj6;
#
# alter table admin_users
#     add constraint UK3fgxk4ewgaxgtgvqwb1jjudj6 unique (username);
#
# alter table admin_users
#     drop index UKcp8822350s9vtyww7xdbgeuvu;
#
# alter table admin_users
#     add constraint UKcp8822350s9vtyww7xdbgeuvu unique (email);
#
# alter table cmy_employees
#     drop index UK_4bpebw1qn5nad20k4n8swkou9;
#
# alter table cmy_employees
#     add constraint UK_4bpebw1qn5nad20k4n8swkou9 unique (email_id);
#
# alter table cmy_votes
#     drop index UKlrbktbt1pqu259n0s3shn4vix;
#
# alter table cmy_votes
#     add constraint UKlrbktbt1pqu259n0s3shn4vix unique (cmy_poll_id, cmy_user_id);
#
# alter table admin_role_users
#     add constraint FKr0mcn0re2opqx098toifo3vg2
#         foreign key (role_id)
#             references admin_roles (id);
#
# alter table admin_role_users
#     add constraint FKe0wobw681st5pe8o8tu9m6t15
#         foreign key (user_id)
#             references admin_users (id);
#
# alter table cmy_choices
#     add constraint FK245xqd1v3ucms6tr1irqarh6g
#         foreign key (cmy_poll_id)
#             references cmy_polls (id);
#
# alter table cmy_votes
#     add constraint FKql3hqtgoapf8lna30f3opd9xe
#         foreign key (cmy_choice_id)
#             references cmy_choices (id);
#
# alter table cmy_votes
#     add constraint FKmssphp0jsddcspj2v34fglefc
#         foreign key (cmy_poll_id)
#             references cmy_polls (id);
#
# alter table cmy_votes
#     add constraint FKq0uixmvfnke6v4njui2ve305x
#         foreign key (cmy_user_id)
#             references cmy_users (id);
