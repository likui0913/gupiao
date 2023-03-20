package com.gupiao.generator.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 配置表
 * @TableName sys_setting
 */
@Data
public class SysSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long id;

    /**
     * key
     */
    private String sysKey;

    /**
     * value
     */
    private String sysValue;

    /**
     * 备注
     */
    private String des;

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
        SysSetting other = (SysSetting) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysKey() == null ? other.getSysKey() == null : this.getSysKey().equals(other.getSysKey()))
            && (this.getSysValue() == null ? other.getSysValue() == null : this.getSysValue().equals(other.getSysValue()))
            && (this.getDes() == null ? other.getDes() == null : this.getDes().equals(other.getDes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysKey() == null) ? 0 : getSysKey().hashCode());
        result = prime * result + ((getSysValue() == null) ? 0 : getSysValue().hashCode());
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
        sb.append(", sysKey=").append(sysKey);
        sb.append(", sysValue=").append(sysValue);
        sb.append(", des=").append(des);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}