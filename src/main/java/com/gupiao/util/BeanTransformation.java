package com.gupiao.util;

import com.gupiao.bean.api.StockCode;
import com.gupiao.bean.api.StockMsg;
import com.gupiao.generator.domain.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 将抓取数据转成对应数据库bean
 */
public class BeanTransformation {

    public static StockDetail createStockDetailFromList(List<StockMsg> stockMsgList){

        StockDetail detail = new StockDetail();
        detail.setExchange("null");
        detail.setIsFocus(0);
        for ( StockMsg m : stockMsgList) {
            switch(m.getItem()){
                case "总市值" :
                    detail.setTotalCapitalization(Double.valueOf(m.getValue().toString()));
                    break;
                case "流通市值" :
                    detail.setCirculatingCapitalization(Double.valueOf(m.getValue().toString()));
                    break;
                case "行业" :
                    detail.setIndustry(m.getValue().toString());
                    break;
                case "上市时间" :
                    Integer time = Double.valueOf(m.getValue().toString()).intValue();
                    detail.setMarketTime(time.toString());
                    break;
                case "股票代码" :
                    detail.setStockCode(m.getValue().toString());
                    break;
                case "股票简称" :
                    detail.setStockName(m.getValue().toString());
                    break;
                case "总股本" :
                    detail.setTotalCapital(Double.valueOf(m.getValue().toString()));
                    break;
                case "流通股" :
                    detail.setCirculatingCapital(Double.valueOf(m.getValue().toString()));
                    break;
                default :
                    detail.setStockCode(null);
            }
        }
        detail.setExchange(Util.codeToExchange(detail.getStockCode()));
        return detail;
    }

    public static StockCode createUSAStockCodeFromList(Map<String,String> stockMsgList){

        StockCode code = new StockCode();
        code.setName(stockMsgList.get("名称"));
        code.setCode(stockMsgList.get("代码"));
        return code;
    }

    public static IndustryTransactions createIndustryTransactionsFromList(Map<String,String> params){

        IndustryTransactions bean = new IndustryTransactions();
        for (Map.Entry<String,String> entry: params.entrySet()) {

            /*
            "项目名称": "合计",
            "项目名称-英文": "Total",
            "交易天数": 19,
            "成交金额-人民币元": 11786437756873,
            "成交金额-占总计": 100.0,
            "成交股数-股数": 904978116344,
            "成交股数-占总计": 100.0,
            "成交笔数-笔": 982714260,
            "成交笔数-占总计": 100.0
             */

            switch(entry.getKey()){
                case "项目名称" :
                    bean.setProjectName(entry.getValue());
                    break;
                case "项目名称-英文" :
                    bean.setProjectNameEnglish(entry.getValue());
                    break;
                case "交易天数" :
                    bean.setTradingDays(Integer.valueOf(entry.getValue()));
                    break;
                case "成交金额-人民币元" :
                    BigDecimal time = BigDecimal.valueOf(Long.parseLong(entry.getValue()));
                    bean.setTurnover(time);
                    break;
                case "成交金额-占总计" :
                    Double r = Double.valueOf(entry.getValue());
                    bean.setPercentageOfTransactionTurnover(r);
                    break;
                case "成交股数-股数" :
                    BigDecimal time2 = BigDecimal.valueOf(Long.parseLong(entry.getValue()));
                    bean.setNumberOfTraded(time2);
                    break;
                case "成交股数-占总计" :
                    Double r2 = Double.valueOf(entry.getValue());
                    bean.setPercentageOfTraded(r2);
                    break;
                case "成交笔数-笔" :
                    BigDecimal time3 = BigDecimal.valueOf(Long.parseLong(entry.getValue()));
                    bean.setNumberOfTransactions(time3);
                    break;
                case "成交笔数-占总计" :
                    Double r3 = Double.valueOf(entry.getValue());
                    bean.setPercentageOfTransactions(r3);
                    break;
                default :
                    return null;
            }
        }
        return bean;
    }

    public static StockMarketData createStockMarketDataFromList(Map<String,String> params){

        StockMarketData marketData = new StockMarketData();

        for ( Map.Entry<String,String> entry : params.entrySet()) {
            switch(entry.getKey()){

                /*
                  [{"日期":"2022-09-01","开盘":22.19,"收盘":22.38,"最高":22.54,"最低":21.91,"成交量":18135,"成交额":28917133.3,"振幅":2.85,"涨跌幅":1.36,"涨跌额":0.3,"换手率":2.21}]
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
                 */

                case "日期" :
                    marketData.setTradeDate(entry.getValue());
                    break;
                case "开盘" :
                    BigDecimal time1 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setOpeningPrice(time1);
                    break;
                case "收盘" :
                    BigDecimal time2 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setClosingPrice(time2);
                    break;
                case "最高" :
                    BigDecimal time3 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setHighestPrice(time3);
                    break;
                case "最低" :
                    BigDecimal time4 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setLowestPrice(time4);
                    break;
                case "成交量" :
                    BigDecimal time5 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setVolume(time5);
                    break;
                case "成交额" :
                    BigDecimal time6 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setTurnover(time6);
                    break;
                case "振幅" :
                    BigDecimal time7 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setAmplitude(time7);
                    break;
                case "涨跌幅" :
                    BigDecimal time8 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setQuoteChange(time8);
                    break;
                case "涨跌额" :
                    BigDecimal time9 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setUpsAndDowns(time9);
                    break;
                case "换手率" :
                    BigDecimal time10 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    marketData.setTurnoverRate(time10);
                    break;
                default :
                    return null;
            }
        }

        return marketData;
    }

    public static StockMarketRuntimeData createStockMarketRuntimeDataFromList(Map<String,String> params){

        StockMarketRuntimeData runtimeData = new StockMarketRuntimeData();

        for ( Map.Entry<String,String> entry : params.entrySet()) {
            switch(entry.getKey()){

                /*
                    CREATE TABLE `stock_market_runtime_data` (
                    `trade_date` varchar(10) NOT NULL COMMENT '统计时间',
                    `trade_time` varchar(20) NOT NULL COMMENT '统计时间',
                    `id` bigint primary key not null auto_increment,
                    `stock_code` varchar(60) NOT NULL COMMENT 'code编码',
                    `stock_name` varchar(60) NOT NULL COMMENT 'code名称',
                    `new_price` decimal(30,10) NOT NULL COMMENT '最新价',
                    `quote_change` decimal(30,10) NOT NULL COMMENT '涨跌幅',
                    `ups_and_downs` decimal(30,10) NOT NULL COMMENT '涨跌额',
                    `volume` decimal(30,10) NOT NULL COMMENT '成交量',
                    `turnover` decimal(30,10) NOT NULL COMMENT '成交额',
                    `amplitude` decimal(30,10) NOT NULL COMMENT '振幅',
                    `highest_price` decimal(30,10) NOT NULL COMMENT '最高价',
                    `lowest_price` decimal(30,10) NOT NULL COMMENT '最低价',
                    `opening_price` decimal(30,10) NOT NULL COMMENT '开盘价',
                    `turnover_rate` decimal(30,10) NOT NULL COMMENT '换手率',
                    `m_5_amplitude` decimal(30,10) NOT NULL COMMENT '5分钟涨跌',
                    `d_60_amplitude` decimal(30,10) NOT NULL COMMENT '60日涨跌幅',
                    KEY `idx_code_name` (`stock_code`,`trade_date`),
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='code实时交易数据';
                 */

                case "代码" :
                    runtimeData.setStockCode(entry.getValue());
                    break;
                case "名称" :
                    runtimeData.setStockName(entry.getValue());
                    break;
                case "最新价" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time2 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setNewPrice(time2);
                    break;
                case "涨跌幅" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time3 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setQuoteChange(time3);
                    break;
                case "涨跌额" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time4 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setUpsAndDowns(time4);
                    break;
                case "成交量" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time5 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setVolume(time5);
                    break;
                case "成交额" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time6 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setTurnover(time6);
                    break;
                case "振幅" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time7 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setAmplitude(time7);
                    break;
                case "最高" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time8 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setHighestPrice(time8);
                    break;
                case "最低" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time9 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setLowestPrice(time9);
                    break;
                case "今开" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time10 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setOpeningPrice(time10);
                    break;
                case "换手率" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time11 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setTurnoverRate(time11);
                    break;
                case "5分钟涨跌" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time12 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setM5Amplitude(time12);
                    break;
                case "60日涨跌幅" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time13 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    runtimeData.setD60Amplitude(time13);
                    break;
            }
        }

        return runtimeData;
    }

    public static StockDetailOrderAll createStockDetailOrderAllDataFromList(Map<String,String> params){

        StockDetailOrderAll stockDetailOrderAll = new StockDetailOrderAll();

        for ( Map.Entry<String,String> entry : params.entrySet()) {
            switch(entry.getKey()){

                /*
                * "成交时间": "09:30:03",
                "成交价格": 12.77,
                "价格变动": -0.02,
                "成交量": 2272,
                "成交金额": 2904887,
                "性质": "卖盘"
                */
                case "成交时间" :
                    stockDetailOrderAll.setTradeTime(entry.getValue());
                    break;
                case "成交价格" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time1 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    stockDetailOrderAll.setTradePrice(time1);                    break;
                case "价格变动" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time2 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    stockDetailOrderAll.setChangePrice(time2);
                    break;
                case "成交量" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time3 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    stockDetailOrderAll.setVolume(time3);
                    break;
                case "成交金额" :
                    if(null == entry.getValue() || "null".equals(entry.getValue())){
                        continue;
                    }
                    BigDecimal time4 = BigDecimal.valueOf(Double.parseDouble(entry.getValue()));
                    stockDetailOrderAll.setTurnover(time4);
                    break;
                case "性质" :
                    if("买盘".equals(entry.getValue())){
                        stockDetailOrderAll.setProperties(1);
                    }else if("卖盘".equals(entry.getValue())){
                        stockDetailOrderAll.setProperties(2);
                    }else{
                        stockDetailOrderAll.setProperties(0);
                    }
                    break;
            }
        }

        return stockDetailOrderAll;
    }


}
