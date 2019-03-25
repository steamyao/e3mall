package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.TbitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-23 10:18
 **/

@Controller
public class ItemControlle {

    @Autowired
    private TbitemService  tbitemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        //跟据商品id查询商品信息
        TbItem tbItem = tbitemService.getByid(itemId);
        //把TbItem转换成Item对象
        Item item = new Item(tbItem);
        //根据商品id查询商品描述
        TbItemDesc tbItemDesc = tbitemService.getItemDesc(itemId);
        //把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        return "item";
    }
}
