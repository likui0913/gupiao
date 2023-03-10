package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 展示明细批次表
 * @TableName stock_show
 */
@Data
public class StockShow implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 股票编码
     */
    private String stockCode;

    /**
     * 批次
     */
    private Integer batch;

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

    private String exchange;

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
        StockShow other = (StockShow) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getBatch() == null ? other.getBatch() == null : this.getBatch().equals(other.getBatch()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getBatch() == null) ? 0 : getBatch().hashCode());
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
        sb.append(", batch=").append(batch);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}