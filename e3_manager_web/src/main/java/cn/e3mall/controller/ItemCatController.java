package cn.e3mall.controller;


import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: e3mall
 * @description:      获取商品分类信息
 * @author: Mr.Yao
 * @create: 2019-01-28 07:34
 **/

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUiTreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
        return itemCatService.getCatList(parentId);
    }
}