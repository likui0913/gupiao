package com.gupiao.generator.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 股票交易统计数据
 * @TableName stock_market_static_data
 */
@Data
public class StockMarketStaticData implements Serializable {
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
     * 计算天数
     */
    private Integer days;

    /**
     * 收盘价差异，当天收盘价-N天前收盘价
     */
    private BigDecimal closingPriceDiff;

    /**
     * 上涨天数
     */
    private Integer upDays;

    /**
     * 下跌天数
     */
    private Integer downDays;

    /**
     * n天交易笔数
     */
    private Integer tradeCount;

    /**
     * n天总成交额
     */
    private BigDecimal allTurnover;

    /**
     *
     */
    private BigDecimal turnoverRateAvg;

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
        StockMarketStaticData other = (StockMarketStaticData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
                && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
                && (this.getDays() == null ? other.getDays() == null : this.getDays().equals(other.getDays()))
                && (this.getClosingPriceDiff() == null ? other.getClosingPriceDiff() == null : this.getClosingPriceDiff().equals(other.getClosingPriceDiff()))
                && (this.getUpDays() == null ? other.getUpDays() == null : this.getUpDays().equals(other.getUpDays()))
                && (this.getDownDays() == null ? other.getDownDays() == null : this.getDownDays().equals(other.getDownDays()))
                && (this.getTradeCount() == null ? other.getTradeCount() == null : this.getTradeCount().equals(other.getTradeCount()))
                && (this.getAllTurnover() == null ? other.getAllTurnover() == null : this.getAllTurnover().equals(other.getAllTurnover()))
                && (this.getTurnoverRateAvg() == null ? other.getTurnoverRateAvg() == null : this.getTurnoverRateAvg().equals(other.getTurnoverRateAvg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getDays() == null) ? 0 : getDays().hashCode());
        result = prime * result + ((getClosingPriceDiff() == null) ? 0 : getClosingPriceDiff().hashCode());
        result = prime * result + ((getUpDays() == null) ? 0 : getUpDays().hashCode());
        result = prime * result + ((getDownDays() == null) ? 0 : getDownDays().hashCode());
        result = prime * result + ((getTradeCount() == null) ? 0 : getTradeCount().hashCode());
        result = prime * result + ((getAllTurnover() == null) ? 0 : getAllTurnover().hashCode());
        result = prime * result + ((getTurnoverRateAvg() == null) ? 0 : getTurnoverRateAvg().hashCode());
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
        sb.append(", days=").append(days);
        sb.append(", closingPriceDiff=").append(closingPriceDiff);
        sb.append(", upDays=").append(upDays);
        sb.append(", downDays=").append(downDays);
        sb.append(", tradeCount=").append(tradeCount);
        sb.append(", allTurnover=").append(allTurnover);
        sb.append(", turnoverRateAvg=").append(turnoverRateAvg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}