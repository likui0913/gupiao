package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 统计两个时间间隔内价格差异和百分比，判断走势
 * @TableName stock_market_tow_day_diff_data
 */
@Data
public class StockMarketTowDayDiffData implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 股票编码
     */
    private String stockCode;

    /**
     * 统计时间
     */
    private String tradeDate;

    /**
     * 第一个时间差值
     */
    private Integer daysX;

    /**
     * 第二个时间差值
     */
    private Integer daysY;

    /**
     * 第一个时间差值,收盘价差异，当天收盘价-N天前收盘价
     */
    private BigDecimal closingPriceDiffX;

    /**
     * 第二个时间差值,收盘价差异，当天收盘价-N天前收盘价
     */
    private BigDecimal closingPriceDiffY;

    /**
     * 第二个时间差值,收盘价差异，当天收盘价-N天前收盘价
     */
    private BigDecimal closingPriceDiffPercent;

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
        StockMarketTowDayDiffData other = (StockMarketTowDayDiffData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
            && (this.getDaysX() == null ? other.getDaysX() == null : this.getDaysX().equals(other.getDaysX()))
            && (this.getDaysY() == null ? other.getDaysY() == null : this.getDaysY().equals(other.getDaysY()))
            && (this.getClosingPriceDiffX() == null ? other.getClosingPriceDiffX() == null : this.getClosingPriceDiffX().equals(other.getClosingPriceDiffX()))
            && (this.getClosingPriceDiffY() == null ? other.getClosingPriceDiffY() == null : this.getClosingPriceDiffY().equals(other.getClosingPriceDiffY()))
            && (this.getClosingPriceDiffPercent() == null ? other.getClosingPriceDiffPercent() == null : this.getClosingPriceDiffPercent().equals(other.getClosingPriceDiffPercent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getDaysX() == null) ? 0 : getDaysX().hashCode());
        result = prime * result + ((getDaysY() == null) ? 0 : getDaysY().hashCode());
        result = prime * result + ((getClosingPriceDiffX() == null) ? 0 : getClosingPriceDiffX().hashCode());
        result = prime * result + ((getClosingPriceDiffY() == null) ? 0 : getClosingPriceDiffY().hashCode());
        result = prime * result + ((getClosingPriceDiffPercent() == null) ? 0 : getClosingPriceDiffPercent().hashCode());
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
        sb.append(", tradeDate=").append(tradeDate);
        sb.append(", daysX=").append(daysX);
        sb.append(", daysY=").append(daysY);
        sb.append(", closingPriceDiffX=").append(closingPriceDiffX);
        sb.append(", closingPriceDiffY=").append(closingPriceDiffY);
        sb.append(", closingPriceDiffPercent=").append(closingPriceDiffPercent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}