package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-21 14:40
 **/

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${PAGE_ROWS}")
    private Integer PAGE_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("keywords") String queryString, @RequestParam(defaultValue="1") Integer page, Model model) throws Exception {
        //需要转码
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        //调用Service查询商品信息
        SearchResult result = searchService.search(queryString, page, PAGE_ROWS);
        //把结果传递给jsp页面
        model.addAttribute("query", queryString);
        model.addAttribute("totalPage", result.getTotalPage());
        model.addAttribute("totalNumber", result.getTotalNumber());
        model.addAttribute("page", page);
        model.addAttribute("itemList", result.getItemList());
        //返回逻辑视图
        return "search";
    }
}
