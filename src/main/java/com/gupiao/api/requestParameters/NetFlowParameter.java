package com.gupiao.api.requestParameters;

import lombok.Data;

@Data
public class NetFlowParameter {

    private String flowType;

    private String flowStartDate;

    private String flowEndDate;

}
