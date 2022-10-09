package com.gupiao.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 */
@Data
public class ComputeDailyBean {

    private String date;

    private String quoteChange;

    private String transactionResults;

    public static ComputeDailyBean createBean(String d,BigDecimal q,BigDecimal t){

        ComputeDailyBean bean = new ComputeDailyBean();
        bean.setDate(d);
        bean.setQuoteChange(q.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        bean.setTransactionResults(t.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        return bean;

    }

    public static ComputeDailyBean createBean(String d,String q,String t){

        ComputeDailyBean bean = new ComputeDailyBean();
        bean.setDate(d);
        bean.setQuoteChange(q);
        bean.setTransactionResults(t);
        return bean;

    }

}
