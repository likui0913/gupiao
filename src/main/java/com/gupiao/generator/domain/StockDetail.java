package com.gupiao.generator.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 股票详细信息表
 * @TableName stock_detail
 */
@Data
public class StockDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 股票编码
     */
    private String stockCode;

    private Integer stockType;

    private Integer focus;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 上市时间
     */
    private String marketTime;

    /**
     * 行业
     */
    private String industry;

    /**
     * 总股本
     */
    private Double totalCapital;

    /**
     * 流通股本
     */
    private Double circulatingCapital;

    /**
     * 总市值
     */
    private Double totalCapitalization;

    /**
     * 流通市值
     */
    private Double circulatingCapitalization;

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
        StockDetail other = (StockDetail) that;
        return (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getStockName() == null ? other.getStockName() == null : this.getStockName().equals(other.getStockName()))
            && (this.getMarketTime() == null ? other.getMarketTime() == null : this.getMarketTime().equals(other.getMarketTime()))
            && (this.getIndustry() == null ? other.getIndustry() == null : this.getIndustry().equals(other.getIndustry()))
            && (this.getTotalCapital() == null ? other.getTotalCapital() == null : this.getTotalCapital().equals(other.getTotalCapital()))
            && (this.getCirculatingCapital() == null ? other.getCirculatingCapital() == null : this.getCirculatingCapital().equals(other.getCirculatingCapital()))
            && (this.getTotalCapitalization() == null ? other.getTotalCapitalization() == null : this.getTotalCapitalization().equals(other.getTotalCapitalization()))
            && (this.getCirculatingCapitalization() == null ? other.getCirculatingCapitalization() == null : this.getCirculatingCapitalization().equals(other.getCirculatingCapitalization()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getStockName() == null) ? 0 : getStockName().hashCode());
        result = prime * result + ((getMarketTime() == null) ? 0 : getMarketTime().hashCode());
        result = prime * result + ((getIndustry() == null) ? 0 : getIndustry().hashCode());
        result = prime * result + ((getTotalCapital() == null) ? 0 : getTotalCapital().hashCode());
        result = prime * result + ((getCirculatingCapital() == null) ? 0 : getCirculatingCapital().hashCode());
        result = prime * result + ((getTotalCapitalization() == null) ? 0 : getTotalCapitalization().hashCode());
        result = prime * result + ((getCirculatingCapitalization() == null) ? 0 : getCirculatingCapitalization().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stockCode=").append(stockCode);
        sb.append(", stockName=").append(stockName);
        sb.append(", marketTime=").append(marketTime);
        sb.append(", industry=").append(industry);
        sb.append(", totalCapital=").append(totalCapital);
        sb.append(", circulatingCapital=").append(circulatingCapital);
        sb.append(", totalCapitalization=").append(totalCapitalization);
        sb.append(", circulatingCapitalization=").append(circulatingCapitalization);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}