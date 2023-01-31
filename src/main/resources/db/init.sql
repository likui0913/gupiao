
-- truncate table stock_detail;
CREATE TABLE `stock_detail` (
                                `stock_code` varchar(60) NOT NULL COMMENT '股票编码',
                                `stock_name` varchar(60) NOT NULL COMMENT '股票名称',
                                `market_time` varchar(20) NOT NULL COMMENT '上市时间',
                                `industry` varchar(20) NOT NULL COMMENT '行业',
                                `total_capital` decimal(30,10) NOT NULL COMMENT '总股本',
                                `circulating_capital` decimal(30,10) NOT NULL COMMENT '流通股本',
                                `total_capitalization` decimal(30,10) NOT NULL COMMENT '总市值',
                                `circulating_capitalization` decimal(30,10) NOT NULL COMMENT '流通市值',
                                KEY `stock_detail_stock_code_index` (`stock_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='股票详细信息表';


CREATE TABLE `industry_transactions` (
                                         `id` bigint primary key not null auto_increment,
                                         `trade_date` varchar(10) NOT NULL COMMENT '统计时间',
                                         `project_name` varchar(60) NOT NULL COMMENT '项目名称',
                                         `project_name_english` varchar(128) NOT NULL COMMENT '项目名称-英文',
                                         `trading_days` int(11) NOT NULL COMMENT '交易天数',
                                         `turnover` decimal(30,10) NOT NULL COMMENT '成交金额-人民币元',
                                         `percentage_of_transaction_turnover` float NOT NULL COMMENT '成交金额-占总计',
                                         `number_of_traded` decimal(30,10) NOT NULL COMMENT '成交股数-股数',
                                         `percentage_of_traded` float NOT NULL COMMENT '成交股数-占总计',
                                         `Number_of_transactions` decimal(30,10) NOT NULL COMMENT '成交笔数-笔',
                                         `percentage_of_transactions` float NOT NULL COMMENT '成交笔数-占总计',
                                         KEY `idx_date_name` (`trade_date`,`project_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='行业交易信息';


CREATE TABLE `stock_market_data` (
                                     `id` bigint primary key not null auto_increment,
                                     `stock_code` varchar(60) NOT NULL COMMENT '股票编码',
                                     `trade_date` varchar(10) NOT NULL COMMENT '统计时间',
                                     `opening_price` decimal(30,10) NOT NULL COMMENT '开盘价',
                                     `closing_price` decimal(30,10) NOT NULL COMMENT '收盘价',
                                     `highest_price` decimal(30,10) NOT NULL COMMENT '最高价',
                                     `lowest_price` decimal(30,10) NOT NULL COMMENT '最低价',
                                     `volume` decimal(30,10) NOT NULL COMMENT '成交量',
                                     `turnover` decimal(30,10) NOT NULL COMMENT '成交额',
                                     `amplitude` decimal(30,10) NOT NULL COMMENT '振幅',
                                     `quote_change` decimal(30,10) NOT NULL COMMENT '涨跌幅',
                                     `ups_and_downs` decimal(30,10) NOT NULL COMMENT '涨跌额',
                                     `turnover_rate` decimal(30,10) NOT NULL COMMENT '换手率',
                                     KEY `idx_code_name` (`stock_code`,`trade_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='股票交易数据';


CREATE TABLE `stock_market_x_moving_average` (
                                                 `id` bigint primary key not null auto_increment,
                                                 `stock_code` varchar(60) NOT NULL COMMENT '股票编码',
                                                 `trade_date` varchar(10) NOT NULL COMMENT '统计时间',
                                                 `closing_price` decimal(30,10) NOT NULL COMMENT '统计当天收盘价 时间是trade_date + 1天',
                                                 `x3` decimal(30,10) NOT NULL DEFAULT 0.0 COMMENT '3天',
                                                 `x5` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '5天',
                                                 `x7` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '7天',
                                                 `x10` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '10天',
                                                 `x15` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '15天',
                                                 `x30` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '30天',
                                                 `x60` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '60天',
                                                 `x90` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '90天',
                                                 `x120` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '120天',
                                                 `x150` decimal(30,10) NOT NULL DEFAULT 0.0COMMENT '150天',
                                                 KEY `idx_code_name` (`stock_code`,`trade_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='股票n日线统计数据';


CREATE TABLE `sys_setting` (
                               `id` bigint primary key not null auto_increment,
                               `sys_key` varchar(128) NOT NULL COMMENT 'key',
                               `sys_value` varchar(128) NOT NULL COMMENT 'value',
                               `des` varchar(512) NOT NULL COMMENT '备注',
                               KEY `idx_sys_key` (`sys_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配置表';

select * from sys_user;

CREATE TABLE `stock_money_net_flow` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `flow_type` varchar(20) NOT NULL,
                                        `flow_date` varchar(20) NOT NULL,
                                        `count` decimal(30,10) NOT NULL DEFAULT '0.0000000000',
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `stock_money_net_flow_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


truncate table industry_transactions;
truncate table stock_detail;
truncate table stock_market_data;
truncate table sys_setting;

insert into sys_setting(sys_key, sys_value, des) values("updateAllStockMsgIsOn","1","是否开启更新股票元数据");
insert into sys_setting(sys_key, sys_value, des) values("updateStockSaleDataIsOn","1","是否开启更新股票T+1交易数据");