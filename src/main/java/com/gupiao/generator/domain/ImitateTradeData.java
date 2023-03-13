package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 模拟交易数据
 * @TableName imitate_trade_data
 */
@Data
public class ImitateTradeData implements Serializable {

    /**
     * 
     */
    private Long id;

    /**
     * 上市时间
     */
    private String userName;

    /**
     * 股票编码
     */
    private String stockCode;

    /**
     * 买入时间
     */
    private String tradeInDate;

    /**
     * 卖出时间
     */
    private String tradeOutDate;

    /**
     * 买入价格
     */
    private BigDecimal tradeInPrice;

    /**
     * 卖出价格
     */
    private BigDecimal tradeOutPrice;

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
        ImitateTradeData other = (ImitateTradeData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getTradeInDate() == null ? other.getTradeInDate() == null : this.getTradeInDate().equals(other.getTradeInDate()))
            && (this.getTradeOutDate() == null ? other.getTradeOutDate() == null : this.getTradeOutDate().equals(other.getTradeOutDate()))
            && (this.getTradeInPrice() == null ? other.getTradeInPrice() == null : this.getTradeInPrice().equals(other.getTradeInPrice()))
            && (this.getTradeOutPrice() == null ? other.getTradeOutPrice() == null : this.getTradeOutPrice().equals(other.getTradeOutPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getTradeInDate() == null) ? 0 : getTradeInDate().hashCode());
        result = prime * result + ((getTradeOutDate() == null) ? 0 : getTradeOutDate().hashCode());
        result = prime * result + ((getTradeInPrice() == null) ? 0 : getTradeInPrice().hashCode());
        result = prime * result + ((getTradeOutPrice() == null) ? 0 : getTradeOutPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", stockCode=").append(stockCode);
        sb.append(", tradeInDate=").append(tradeInDate);
        sb.append(", tradeOutDate=").append(tradeOutDate);
        sb.append(", tradeInPrice=").append(tradeInPrice);
        sb.append(", tradeOutPrice=").append(tradeOutPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}