package cn.e3mall.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface TbitemService {
    /**
     * 根据商品ID获取商品信息
     * @param id
     * @return
     */
    TbItem getByid(Long id);

    /**
     * 获取商品列表
     * @param page
     * @param rows
     * @return
     */
    EasyUIResult getItemList(int page, int rows);

    /**
     *添加商品
     * @param item
     * @param desc
     * @return
     */
     E3Result addItem(TbItem item, String desc);

    /**
     * 根据商品ID 查询商品描述
     * @param itemId
     * @return
     */
     TbItemDesc getItemDesc(Long itemId);
}
