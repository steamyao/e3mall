package cn.e3mall.controller;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: e3mall
 * @description: solr维护
 * @author: Mr.Yao
 * @create: 2019-02-17 10:01
 **/

@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result impotItemIndex() throws Exception {
        E3Result result = null;
        result = searchItemService.importAllItemToIndex();
        return result;
    }

}
