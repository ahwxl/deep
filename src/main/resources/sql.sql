CREATE TABLE `sk_transaction_record` (
	`id` VARCHAR(32) NOT NULL,
	`stock_id` VARCHAR(6) NULL DEFAULT NULL,
	`stock_name` VARCHAR(32) NULL DEFAULT NULL,
	`amount` BIGINT(20) NULL DEFAULT NULL,
	`price` DOUBLE NULL DEFAULT NULL,
	`transaction_type` VARCHAR(1) NULL DEFAULT NULL,
	`gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`transaction_date` VARCHAR(32) NOT NULL,
	`gmt_modify` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;



CREATE TABLE `sk_warehouse_positon` (
	`stock_id` VARCHAR(32) NOT NULL,
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
   status               varchar(2),
   gmt_create           timestamp,
   GMT_MODIFY           timestamp,
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

CREATE TABLE `sk_schedule_task` (
	`id` VARCHAR(32) NOT NULL,
	`group_id` VARCHAR(32) NULL DEFAULT NULL,
	`job_id` VARCHAR(32) NULL DEFAULT NULL,
	`trigger_name` VARCHAR(32) NULL DEFAULT NULL,
	`task_param` VARCHAR(200) NULL DEFAULT NULL,
	`gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`gmt_modify` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
	`status` VARCHAR(2) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

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
   gmt_modify           timestamp,
   status               varchar(1),
   primary key (sms_id)
);

create table Sk_Day_Swrich
(
   id                   varchar(32) not null,
   yester_day           varchar(32),
   today                varchar(32),
   tommory              varchar(32),
   primary key (id)
);




INSERT INTO `sk_day_swrich` (`id`, `yester_day`, `today`, `tommory`) VALUES ('1', '2017-3-1', '2017-03-05', '11');
INSERT INTO `sk_warehouse_positon` (`stock_id`, `stock_name`, `amount`, `yester_price`, `today_price`, `market_value`, `sort_by`, `gmt_create`, `gmt_modify`) VALUES ('sh600078', '澄星股份', 10000, 7.136, NULL, NULL, NULL, '2017-03-06 23:04:46', '2017-03-05 17:03:22');
