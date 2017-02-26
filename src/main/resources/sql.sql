CREATE TABLE `sk_transaction_record` (
	`id` VARCHAR(32) NOT NULL,
	`stock_id` VARCHAR(6) NULL DEFAULT NULL,
	`stock_name` VARCHAR(32) NULL DEFAULT NULL,
	`amount` BIGINT(20) NULL DEFAULT NULL,
	`price` DOUBLE NULL DEFAULT NULL,
	`transaction_type` VARCHAR(1) NULL DEFAULT NULL,
	`gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`gmt_modify` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


CREATE TABLE `sk_warehouse_positon` (
	`stock_id` VARCHAR(6) NOT NULL,
	`stock_name` VARCHAR(32) NULL DEFAULT NULL,
	`amount` BIGINT(20) NULL DEFAULT NULL,
	`yester_price` DOUBLE NULL DEFAULT NULL,
	`today_price` DOUBLE NULL DEFAULT NULL,
	`market_value` DOUBLE NULL DEFAULT NULL,
	`sort_by` BIGINT(20) NULL DEFAULT NULL,
	`gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`gmt_modify` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
	PRIMARY KEY (`stock_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

create table sk_warn_log
(
   id                   varchar(32) not null,
   rule_id              varchar(32),
   user_id              varchar(32),
   stock_id             varchar(32),
   warn_msg             varchar(200),
   gmt_create           timestamp,
   primary key (id)
);

create table sk_customer_warn
(
   id                   varchar(32) not null,
   user_id              varchar(32),
   rule_id              varchar(32),
   stock_id             varchar(32),
   gmt_create           timestamp,
   primary key (id)
);

create table sk_warn_rule
(
   rule_id              varchar(32) not null,
   scripte              varchar(200),
   rule_msg             varchar(200),
   status               varchar(1),
   gmt_create           timestamp,
   gmt_modify           timestamp,
   primary key (rule_id)
);

create table sk_schedule_task
(
   id                   varchar(32) not null,
   group_id             varchar(32),
   job_id               varchar(32),
   trigger_name         varchar(32),
   task_param           varchar(200),
   gmt_create           timestamp,
   gmt_modify           timestamp,
   status               varchar(2),
   primary key (id)
);

create table sys_user
(
   user_id              varchar(32) not null,
   user_name            varchar(32),
   password             varchar(64),
   sex                  char(1),
   email                varchar(32),
   mobile               varchar(11),
   status               char(1),
   remark               varchar(200),
   gmt_create           timestamp,
   gmt_modify           timestamp,
   primary key (user_id)
);

create table sk_send_sms_log
(
   sms_id               varchar(32) not null,
   send_mobile          varchar(11),
   send_cnt             varchar(200),
   parament             varchar(200),
   gmt_create           timestamp,
   status               varchar(1),
   primary key (sms_id)
);