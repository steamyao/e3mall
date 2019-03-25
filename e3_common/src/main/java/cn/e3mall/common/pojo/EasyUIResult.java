package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @program: e3mall
 * @description: 响应JSON的数据格式
 * @author: Mr.Yao
 * @create: 2019-01-27 22:24
 **/


public class EasyUIResult implements Serializable{
    private Integer total;

    private List<?> rows;

    public EasyUIResult(){

    }
    public EasyUIResult(Integer total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total.intValue();
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
