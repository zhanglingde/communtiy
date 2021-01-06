package com.ling.other.common.constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公共请求参数模型
 */
@Data
public class CommonParams {

    @ApiModelProperty("页数")
    private Integer page = 1;

    @ApiModelProperty("每页显示数量,默认10")
    private Integer limit = 10;

    @ApiModelProperty("排序字段")
    private String sidx;

    @ApiModelProperty("排序方式，desc ，asc")
    private String order;

    public void setPage(Integer page) {
        this.page = page;
        if (page == null) {
             return;
            //this.page = 1;
        }
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
        if (limit == null) {
            // return;
            this.limit = 10;
        }
    }
}