package cn.e3mall.common.pojo;

import java.io.Serializable;

/**
 * @program: e3mall
 * @description: 新增商品时，商品类别的数据格式
 * @author: Mr.Yao
 * @create: 2019-01-28 07:15
 **/


public class EasyUiTreeNode implements Serializable {
    private long id;
    private String text;
    private String state;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
