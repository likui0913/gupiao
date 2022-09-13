package com.gupiao.generator.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 行业交易信息
 * @TableName industry_transactions
 */
@Data
public class IndustryTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long id;

    /**
     * 统计时间
     */
    private String tradeDate;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目名称-英文
     */
    private String projectNameEnglish;

    /**
     * 交易天数
     */
    private Integer tradingDays;

    /**
     * 成交金额-人民币元
     */
    private BigDecimal turnover;

    /**
     * 成交金额-占总计
     */
    private Double percentageOfTransactionTurnover;

    /**
     * 成交股数-股数
     */
    private BigDecimal numberOfTraded;

    /**
     * 成交股数-占总计
     */
    private Double percentageOfTraded;

    /**
     * 成交笔数-笔
     */
    private BigDecimal numberOfTransactions;

    /**
     * 成交笔数-占总计
     */
    private Double percentageOfTransactions;

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
        IndustryTransactions other = (IndustryTransactions) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTradeDate() == null ? other.getTradeDate() == null : this.getTradeDate().equals(other.getTradeDate()))
            && (this.getProjectName() == null ? other.getProjectName() == null : this.getProjectName().equals(other.getProjectName()))
            && (this.getProjectNameEnglish() == null ? other.getProjectNameEnglish() == null : this.getProjectNameEnglish().equals(other.getProjectNameEnglish()))
            && (this.getTradingDays() == null ? other.getTradingDays() == null : this.getTradingDays().equals(other.getTradingDays()))
            && (this.getTurnover() == null ? other.getTurnover() == null : this.getTurnover().equals(other.getTurnover()))
            && (this.getPercentageOfTransactionTurnover() == null ? other.getPercentageOfTransactionTurnover() == null : this.getPercentageOfTransactionTurnover().equals(other.getPercentageOfTransactionTurnover()))
            && (this.getNumberOfTraded() == null ? other.getNumberOfTraded() == null : this.getNumberOfTraded().equals(other.getNumberOfTraded()))
            && (this.getPercentageOfTraded() == null ? other.getPercentageOfTraded() == null : this.getPercentageOfTraded().equals(other.getPercentageOfTraded()))
            && (this.getNumberOfTransactions() == null ? other.getNumberOfTransactions() == null : this.getNumberOfTransactions().equals(other.getNumberOfTransactions()))
            && (this.getPercentageOfTransactions() == null ? other.getPercentageOfTransactions() == null : this.getPercentageOfTransactions().equals(other.getPercentageOfTransactions()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
        result = prime * result + ((getProjectName() == null) ? 0 : getProjectName().hashCode());
        result = prime * result + ((getProjectNameEnglish() == null) ? 0 : getProjectNameEnglish().hashCode());
        result = prime * result + ((getTradingDays() == null) ? 0 : getTradingDays().hashCode());
        result = prime * result + ((getTurnover() == null) ? 0 : getTurnover().hashCode());
        result = prime * result + ((getPercentageOfTransactionTurnover() == null) ? 0 : getPercentageOfTransactionTurnover().hashCode());
        result = prime * result + ((getNumberOfTraded() == null) ? 0 : getNumberOfTraded().hashCode());
        result = prime * result + ((getPercentageOfTraded() == null) ? 0 : getPercentageOfTraded().hashCode());
        result = prime * result + ((getNumberOfTransactions() == null) ? 0 : getNumberOfTransactions().hashCode());
        result = prime * result + ((getPercentageOfTransactions() == null) ? 0 : getPercentageOfTransactions().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tradeDate=").append(tradeDate);
        sb.append(", projectName=").append(projectName);
        sb.append(", projectNameEnglish=").append(projectNameEnglish);
        sb.append(", tradingDays=").append(tradingDays);
        sb.append(", turnover=").append(turnover);
        sb.append(", percentageOfTransactionTurnover=").append(percentageOfTransactionTurnover);
        sb.append(", numberOfTraded=").append(numberOfTraded);
        sb.append(", percentageOfTraded=").append(percentageOfTraded);
        sb.append(", numberOfTransactions=").append(numberOfTransactions);
        sb.append(", percentageOfTransactions=").append(percentageOfTransactions);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}