package com.gupiao.api.requestParameters;

import lombok.Data;

@Data
public class BokeParameter {

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

}
