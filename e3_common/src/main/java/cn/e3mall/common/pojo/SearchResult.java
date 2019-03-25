package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @program: e3mall
 * @description: 搜索结果
 * @author: Mr.Yao
 * @create: 2019-02-17 22:19
 **/


public class SearchResult implements Serializable {
    // 总页数
   private int totalPage;
   // 总数量
   private int totalNumber;
   // 查询出的商品列表
   private List<SearchItem> itemList;

   public int getTotalPage() { return totalPage; }

   public void setTotalPage(int totalPage) { this.totalPage = totalPage; }

   public int getTotalNumber() { return totalNumber; }

   public void setTotalNumber(int totalNumber) { this.totalNumber = totalNumber; }

   public List<SearchItem> getItemList() { return itemList; }

   public void setItemList(List<SearchItem> itemList) { this.itemList = itemList; } }

