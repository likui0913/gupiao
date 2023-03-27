package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 股票详细信息表
 * @TableName stock_detail_order_properties_static
 */
@Data
public class StockDetailOrderPropertiesStatic implements Serializable {
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
     * 买盘
     */
    private Integer propertiesUp = 0;

    /**
     * 卖盘
     */
    private Integer propertiesDown = 0;

    /**
     * 持平
     */
    private Integer propertiesUnbiased = 0;

    private static final long serialVersionUID = 1L;

    public StockDetailOrderPropertiesStatic(){

        propertiesUp = 0;
        propertiesDown = 0;
        propertiesUnbiased = 0;

    }

    public void propertiesUpAdd1(){
        propertiesUp++;
    }

    public void propertiesDownAdd1(){
        propertiesDown++;
    }

    public void propertiesUnbiasedAdd1(){
        propertiesUnbiased++;
    }

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
        StockDetailOrderPropertiesStatic other = (StockDetailOrderPropertiesStatic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
            && (this.getTradeTime() == null ? other.getTradeTime() == null : this.getTradeTime().equals(other.getTradeTime()))
            && (this.getPropertiesUp() == null ? other.getPropertiesUp() == null : this.getPropertiesUp().equals(other.getPropertiesUp()))
            && (this.getPropertiesDown() == null ? other.getPropertiesDown() == null : this.getPropertiesDown().equals(other.getPropertiesDown()))
            && (this.getPropertiesUnbiased() == null ? other.getPropertiesUnbiased() == null : this.getPropertiesUnbiased().equals(other.getPropertiesUnbiased()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getTradeTime() == null) ? 0 : getTradeTime().hashCode());
        result = prime * result + ((getPropertiesUp() == null) ? 0 : getPropertiesUp().hashCode());
        result = prime * result + ((getPropertiesDown() == null) ? 0 : getPropertiesDown().hashCode());
        result = prime * result + ((getPropertiesUnbiased() == null) ? 0 : getPropertiesUnbiased().hashCode());
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
        sb.append(", propertiesUp=").append(propertiesUp);
        sb.append(", propertiesDown=").append(propertiesDown);
        sb.append(", propertiesUnbiased=").append(propertiesUnbiased);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}