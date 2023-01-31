package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 日均线数据
 * @TableName stock_daily_average_data
 */
@Data
public class StockDailyAverageData implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 股票编码
     */
    private String stockCode;

    /**
     * 统计日期
     */
    private String statisticDate;

    /**
     * 统计天数
     */
    private Integer statisticDays;

    /**
     * N均价，过去N天的收盘平均价
     */
    private BigDecimal dailyPrice;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        StockDailyAverageData other = (StockDailyAverageData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getStatisticDate() == null ? other.getStatisticDate() == null : this.getStatisticDate().equals(other.getStatisticDate()))
            && (this.getStatisticDays() == null ? other.getStatisticDays() == null : this.getStatisticDays().equals(other.getStatisticDays()))
            && (this.getDailyPrice() == null ? other.getDailyPrice() == null : this.getDailyPrice().equals(other.getDailyPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getStatisticDate() == null) ? 0 : getStatisticDate().hashCode());
        result = prime * result + ((getStatisticDays() == null) ? 0 : getStatisticDays().hashCode());
        result = prime * result + ((getDailyPrice() == null) ? 0 : getDailyPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", stockCode=").append(stockCode);
        sb.append(", statisticDate=").append(statisticDate);
        sb.append(", statisticDays=").append(statisticDays);
        sb.append(", dailyPrice=").append(dailyPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}