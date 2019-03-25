package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUiTreeNode;

import java.util.List;


/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-01-28 07:21
 **/
public interface ItemCatService {
    /**
     * 获取商品分类信息
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getCatList(long parentId);
}
