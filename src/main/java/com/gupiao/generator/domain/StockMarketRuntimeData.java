package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * code实时交易数据
 * @TableName stock_market_runtime_data
 */
@Data
public class StockMarketRuntimeData implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * code编码
     */
    private String stockCode;

    /**
     * code名称
     */
    private String stockName;

    /**
     * 统计时间
     */
    private String tradeDate;

    /**
     * 统计时间
     */
    private String tradeTime;

    /**
     * 最新价
     */
    private BigDecimal newPrice;

    /**
     * 涨跌幅
     */
    private BigDecimal quoteChange;

    /**
     * 涨跌额
     */
    private BigDecimal upsAndDowns;

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
     * 最高价
     */
    private BigDecimal highestPrice;

    /**
     * 最低价
     */
    private BigDecimal lowestPrice;

    /**
     * 开盘价
     */
    private BigDecimal openingPrice;

    /**
     * 换手率
     */
    private BigDecimal turnoverRate;

    /**
     * 5分钟涨跌
     */
    private BigDecimal m5Amplitude;

    /**
     * 60日涨跌幅
     */
    private BigDecimal d60Amplitude;

    private Integer upCount;

    private Integer downCount;

    private static final long serialVersionUID = 1L;

    public StockMarketRuntimeData(){
        newPrice = BigDecimal.ZERO;
        quoteChange = BigDecimal.ZERO;
        upsAndDowns = BigDecimal.ZERO;
        volume = BigDecimal.ZERO;
        turnover = BigDecimal.ZERO;
        amplitude = BigDecimal.ZERO;
        highestPrice = BigDecimal.ZERO;
        lowestPrice = BigDecimal.ZERO;
        openingPrice = BigDecimal.ZERO;
        turnoverRate = BigDecimal.ZERO;
        m5Amplitude = BigDecimal.ZERO;
        d60Amplitude = BigDecimal.ZERO;
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
        StockMarketRuntimeData other = (StockMarketRuntimeData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getStockName() == null ? other.getStockName() == null : this.getStockName().equals(other.getStockName()))
            && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
            && (this.getTradeTime() == null ? other.getTradeTime() == null : this.getTradeTime().equals(other.getTradeTime()))
            && (this.getNewPrice() == null ? other.getNewPrice() == null : this.getNewPrice().equals(other.getNewPrice()))
            && (this.getQuoteChange() == null ? other.getQuoteChange() == null : this.getQuoteChange().equals(other.getQuoteChange()))
            && (this.getUpsAndDowns() == null ? other.getUpsAndDowns() == null : this.getUpsAndDowns().equals(other.getUpsAndDowns()))
            && (this.getVolume() == null ? other.getVolume() == null : this.getVolume().equals(other.getVolume()))
            && (this.getTurnover() == null ? other.getTurnover() == null : this.getTurnover().equals(other.getTurnover()))
            && (this.getAmplitude() == null ? other.getAmplitude() == null : this.getAmplitude().equals(other.getAmplitude()))
            && (this.getHighestPrice() == null ? other.getHighestPrice() == null : this.getHighestPrice().equals(other.getHighestPrice()))
            && (this.getLowestPrice() == null ? other.getLowestPrice() == null : this.getLowestPrice().equals(other.getLowestPrice()))
            && (this.getOpeningPrice() == null ? other.getOpeningPrice() == null : this.getOpeningPrice().equals(other.getOpeningPrice()))
            && (this.getTurnoverRate() == null ? other.getTurnoverRate() == null : this.getTurnoverRate().equals(other.getTurnoverRate()))
            && (this.getM5Amplitude() == null ? other.getM5Amplitude() == null : this.getM5Amplitude().equals(other.getM5Amplitude()))
            && (this.getD60Amplitude() == null ? other.getD60Amplitude() == null : this.getD60Amplitude().equals(other.getD60Amplitude()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getStockName() == null) ? 0 : getStockName().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getTradeTime() == null) ? 0 : getTradeTime().hashCode());
        result = prime * result + ((getNewPrice() == null) ? 0 : getNewPrice().hashCode());
        result = prime * result + ((getQuoteChange() == null) ? 0 : getQuoteChange().hashCode());
        result = prime * result + ((getUpsAndDowns() == null) ? 0 : getUpsAndDowns().hashCode());
        result = prime * result + ((getVolume() == null) ? 0 : getVolume().hashCode());
        result = prime * result + ((getTurnover() == null) ? 0 : getTurnover().hashCode());
        result = prime * result + ((getAmplitude() == null) ? 0 : getAmplitude().hashCode());
        result = prime * result + ((getHighestPrice() == null) ? 0 : getHighestPrice().hashCode());
        result = prime * result + ((getLowestPrice() == null) ? 0 : getLowestPrice().hashCode());
        result = prime * result + ((getOpeningPrice() == null) ? 0 : getOpeningPrice().hashCode());
        result = prime * result + ((getTurnoverRate() == null) ? 0 : getTurnoverRate().hashCode());
        result = prime * result + ((getM5Amplitude() == null) ? 0 : getM5Amplitude().hashCode());
        result = prime * result + ((getD60Amplitude() == null) ? 0 : getD60Amplitude().hashCode());
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
        sb.append(", stockName=").append(stockName);
        sb.append(", tradeDate=").append(tradeDate);
        sb.append(", tradeTime=").append(tradeTime);
        sb.append(", newPrice=").append(newPrice);
        sb.append(", quoteChange=").append(quoteChange);
        sb.append(", upsAndDowns=").append(upsAndDowns);
        sb.append(", volume=").append(volume);
        sb.append(", turnover=").append(turnover);
        sb.append(", amplitude=").append(amplitude);
        sb.append(", highestPrice=").append(highestPrice);
        sb.append(", lowestPrice=").append(lowestPrice);
        sb.append(", openingPrice=").append(openingPrice);
        sb.append(", turnoverRate=").append(turnoverRate);
        sb.append(", m5Amplitude=").append(m5Amplitude);
        sb.append(", d60Amplitude=").append(d60Amplitude);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(1.3333000000);
        System.out.println(a.toString());
    }

}