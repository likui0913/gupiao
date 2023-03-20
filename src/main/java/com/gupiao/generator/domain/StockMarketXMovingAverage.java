package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 股票n日线统计数据
 * @TableName stock_market_x_moving_average
 */
@Data
public class StockMarketXMovingAverage implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 统计当天收盘价 时间是trade_date + 1天
     */
    private BigDecimal closingPrice;

    /**
     * 3天
     */
    private BigDecimal x3;

    /**
     * 5天
     */
    private BigDecimal x5;

    /**
     * 7天
     */
    private BigDecimal x7;

    /**
     * 10天
     */
    private BigDecimal x10;

    /**
     * 15天
     */
    private BigDecimal x15;

    /**
     * 30天
     */
    private BigDecimal x30;

    /**
     * 60天
     */
    private BigDecimal x60;

    /**
     * 90天
     */
    private BigDecimal x90;

    /**
     * 120天
     */
    private BigDecimal x120;

    /**
     * 150天
     */
    private BigDecimal x150;

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
        StockMarketXMovingAverage other = (StockMarketXMovingAverage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
            && (this.getClosingPrice() == null ? other.getClosingPrice() == null : this.getClosingPrice().equals(other.getClosingPrice()))
            && (this.getX3() == null ? other.getX3() == null : this.getX3().equals(other.getX3()))
            && (this.getX5() == null ? other.getX5() == null : this.getX5().equals(other.getX5()))
            && (this.getX7() == null ? other.getX7() == null : this.getX7().equals(other.getX7()))
            && (this.getX10() == null ? other.getX10() == null : this.getX10().equals(other.getX10()))
            && (this.getX15() == null ? other.getX15() == null : this.getX15().equals(other.getX15()))
            && (this.getX30() == null ? other.getX30() == null : this.getX30().equals(other.getX30()))
            && (this.getX60() == null ? other.getX60() == null : this.getX60().equals(other.getX60()))
            && (this.getX90() == null ? other.getX90() == null : this.getX90().equals(other.getX90()))
            && (this.getX120() == null ? other.getX120() == null : this.getX120().equals(other.getX120()))
            && (this.getX150() == null ? other.getX150() == null : this.getX150().equals(other.getX150()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getClosingPrice() == null) ? 0 : getClosingPrice().hashCode());
        result = prime * result + ((getX3() == null) ? 0 : getX3().hashCode());
        result = prime * result + ((getX5() == null) ? 0 : getX5().hashCode());
        result = prime * result + ((getX7() == null) ? 0 : getX7().hashCode());
        result = prime * result + ((getX10() == null) ? 0 : getX10().hashCode());
        result = prime * result + ((getX15() == null) ? 0 : getX15().hashCode());
        result = prime * result + ((getX30() == null) ? 0 : getX30().hashCode());
        result = prime * result + ((getX60() == null) ? 0 : getX60().hashCode());
        result = prime * result + ((getX90() == null) ? 0 : getX90().hashCode());
        result = prime * result + ((getX120() == null) ? 0 : getX120().hashCode());
        result = prime * result + ((getX150() == null) ? 0 : getX150().hashCode());
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
        sb.append(", closingPrice=").append(closingPrice);
        sb.append(", x3=").append(x3);
        sb.append(", x5=").append(x5);
        sb.append(", x7=").append(x7);
        sb.append(", x10=").append(x10);
        sb.append(", x15=").append(x15);
        sb.append(", x30=").append(x30);
        sb.append(", x60=").append(x60);
        sb.append(", x90=").append(x90);
        sb.append(", x120=").append(x120);
        sb.append(", x150=").append(x150);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}