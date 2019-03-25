package cn.e3mall.content.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

public interface ContentService {
    /**
     * 5.2.3.新增内容
     * @param content
     * @return
     */
    E3Result addContent(TbContent content);

    /**
     * 根据分类id查询内容列表
     * @param cid
     * @return
     */
    List<TbContent> getContentList(long cid);
}
