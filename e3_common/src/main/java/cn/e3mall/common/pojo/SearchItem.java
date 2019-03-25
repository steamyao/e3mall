package cn.e3mall.common.pojo;

import java.io.Serializable;

/**
 * @program: e3mall
 * @description: 索引
 * @author: Mr.Yao
 * @create: 2019-02-17 08:49
 **/


public class SearchItem implements Serializable{
    private Long id;
    private String title;
    private String sell_point;
    private Long price;
    private String image;
    private String categrary_name;
    private String item_desc;

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }



    public String getCategrary_name() {
        return categrary_name;
    }

    public void setCategrary_name(String categrary_name) {
        this.categrary_name = categrary_name;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }
}
