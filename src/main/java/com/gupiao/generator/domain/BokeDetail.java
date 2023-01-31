package com.gupiao.generator.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 博客明细表
 * @TableName boke_detail
 */
@Data
public class BokeDetail implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 分类
     */
    private Integer titleType;

    /**
     * 博客ID
     */
    private String titleId;

    /**
     * 博客标题
     */
    private String titleName;

    /**
     * 博客主体
     */
    private String titleDetail;

    /**
     * 备注
     */
    private String des;

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
        BokeDetail other = (BokeDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitleType() == null ? other.getTitleType() == null : this.getTitleType().equals(other.getTitleType()))
            && (this.getTitleId() == null ? other.getTitleId() == null : this.getTitleId().equals(other.getTitleId()))
            && (this.getTitleName() == null ? other.getTitleName() == null : this.getTitleName().equals(other.getTitleName()))
            && (this.getTitleDetail() == null ? other.getTitleDetail() == null : this.getTitleDetail().equals(other.getTitleDetail()))
            && (this.getDes() == null ? other.getDes() == null : this.getDes().equals(other.getDes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitleType() == null) ? 0 : getTitleType().hashCode());
        result = prime * result + ((getTitleId() == null) ? 0 : getTitleId().hashCode());
        result = prime * result + ((getTitleName() == null) ? 0 : getTitleName().hashCode());
        result = prime * result + ((getTitleDetail() == null) ? 0 : getTitleDetail().hashCode());
        result = prime * result + ((getDes() == null) ? 0 : getDes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", titleType=").append(titleType);
        sb.append(", titleId=").append(titleId);
        sb.append(", titleName=").append(titleName);
        sb.append(", titleDetail=").append(titleDetail);
        sb.append(", des=").append(des);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}