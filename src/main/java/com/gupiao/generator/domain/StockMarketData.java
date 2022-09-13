package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 股票交易数据
 * @TableName stock_market_data
 */
@Data
public class StockMarketData implements Serializable {

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
     * 开盘价
     */
    private BigDecimal openingPrice;

    /**
     * 收盘价
     */
    private BigDecimal closingPrice;

    /**
     * 最高价
     */
    private BigDecimal highestPrice;

    /**
     * 最低价
     */
    private BigDecimal lowestPrice;

    /**
     * 成交量
     */
    private BigDecimal volume;

    /**
     * 成交额
     */
    private BigDecimal turnover;

    /**
     * 振幅
     */
    private BigDecimal amplitude;

    /**
     * 涨跌幅
     */
    private BigDecimal quoteChange;

    /**
     * 涨跌额
     */
    private BigDecimal upsAndDowns;

    /**
     * 换手率
     */
    private BigDecimal turnoverRate;

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
        StockMarketData other = (StockMarketData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
            && (this.getOpeningPrice() == null ? other.getOpeningPrice() == null : this.getOpeningPrice().equals(other.getOpeningPrice()))
            && (this.getClosingPrice() == null ? other.getClosingPrice() == null : this.getClosingPrice().equals(other.getClosingPrice()))
            && (this.getHighestPrice() == null ? other.getHighestPrice() == null : this.getHighestPrice().equals(other.getHighestPrice()))
            && (this.getLowestPrice() == null ? other.getLowestPrice() == null : this.getLowestPrice().equals(other.getLowestPrice()))
            && (this.getVolume() == null ? other.getVolume() == null : this.getVolume().equals(other.getVolume()))
            && (this.getTurnover() == null ? other.getTurnover() == null : this.getTurnover().equals(other.getTurnover()))
            && (this.getAmplitude() == null ? other.getAmplitude() == null : this.getAmplitude().equals(other.getAmplitude()))
            && (this.getQuoteChange() == null ? other.getQuoteChange() == null : this.getQuoteChange().equals(other.getQuoteChange()))
            && (this.getUpsAndDowns() == null ? other.getUpsAndDowns() == null : this.getUpsAndDowns().equals(other.getUpsAndDowns()))
            && (this.getTurnoverRate() == null ? other.getTurnoverRate() == null : this.getTurnoverRate().equals(other.getTurnoverRate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getOpeningPrice() == null) ? 0 : getOpeningPrice().hashCode());
        result = prime * result + ((getClosingPrice() == null) ? 0 : getClosingPrice().hashCode());
        result = prime * result + ((getHighestPrice() == null) ? 0 : getHighestPrice().hashCode());
        result = prime * result + ((getLowestPrice() == null) ? 0 : getLowestPrice().hashCode());
        result = prime * result + ((getVolume() == null) ? 0 : getVolume().hashCode());
        result = prime * result + ((getTurnover() == null) ? 0 : getTurnover().hashCode());
        result = prime * result + ((getAmplitude() == null) ? 0 : getAmplitude().hashCode());
        result = prime * result + ((getQuoteChange() == null) ? 0 : getQuoteChange().hashCode());
        result = prime * result + ((getUpsAndDowns() == null) ? 0 : getUpsAndDowns().hashCode());
        result = prime * result + ((getTurnoverRate() == null) ? 0 : getTurnoverRate().hashCode());
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
        sb.append(", openingPrice=").append(openingPrice);
        sb.append(", closingPrice=").append(closingPrice);
        sb.append(", highestPrice=").append(highestPrice);
        sb.append(", lowestPrice=").append(lowestPrice);
        sb.append(", volume=").append(volume);
        sb.append(", turnover=").append(turnover);
        sb.append(", amplitude=").append(amplitude);
        sb.append(", quoteChange=").append(quoteChange);
        sb.append(", upsAndDowns=").append(upsAndDowns);
        sb.append(", turnoverRate=").append(turnoverRate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}