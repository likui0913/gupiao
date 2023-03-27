package com.gupiao.generator.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 股票详细信息表
 * @TableName stock_detail_order_all
 */
@Data
public class StockDetailOrderAll implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 股票编码
     */
    private String stockCode;

    /**
     * 交易日期
     */
    private String tradeDate;

    /**
     * 交易时间
     */
    private String tradeTime;

    /**
     * 性质
     */
    private Integer properties;

    /**
     * 成交价格
     */
    private BigDecimal tradePrice;

    /**
     * 波动价格
     */
    private BigDecimal changePrice;

    /**
     * 成交量
     */
    private BigDecimal volume;

    /**
     * 成交额
     */
    private BigDecimal turnover;

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
        StockDetailOrderAll other = (StockDetailOrderAll) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
                && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
                && (this.getTradeTime() == null ? other.getTradeTime() == null : this.getTradeTime().equals(other.getTradeTime()))
                && (this.getProperties() == null ? other.getProperties() == null : this.getProperties().equals(other.getProperties()))
                && (this.getTradePrice() == null ? other.getTradePrice() == null : this.getTradePrice().equals(other.getTradePrice()))
                && (this.getChangePrice() == null ? other.getChangePrice() == null : this.getChangePrice().equals(other.getChangePrice()))
                && (this.getVolume() == null ? other.getVolume() == null : this.getVolume().equals(other.getVolume()))
                && (this.getTurnover() == null ? other.getTurnover() == null : this.getTurnover().equals(other.getTurnover()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getTradeTime() == null) ? 0 : getTradeTime().hashCode());
        result = prime * result + ((getProperties() == null) ? 0 : getProperties().hashCode());
        result = prime * result + ((getTradePrice() == null) ? 0 : getTradePrice().hashCode());
        result = prime * result + ((getChangePrice() == null) ? 0 : getChangePrice().hashCode());
        result = prime * result + ((getVolume() == null) ? 0 : getVolume().hashCode());
        result = prime * result + ((getTurnover() == null) ? 0 : getTurnover().hashCode());
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
        sb.append(", tradeTime=").append(tradeTime);
        sb.append(", properties=").append(properties);
        sb.append(", tradePrice=").append(tradePrice);
        sb.append(", changePrice=").append(changePrice);
        sb.append(", volume=").append(volume);
        sb.append(", turnover=").append(turnover);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}
