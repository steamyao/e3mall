package cn.e3mall.content.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeNode;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 5.1.1.展示内容分类
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getContentCategoryList(long parentId);

    /**
     * 新增节点
     * @param parentId
     * @param name
     * @return
     */
    E3Result addContentCategory(long parentId, String name);
}
