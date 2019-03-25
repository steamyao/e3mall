package cn.e3mall.controller;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: e3mall
 * @description: 内容分类管理
 * @author: Mr.Yao
 * @create: 2019-02-06 07:54
 **/

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUiTreeNode> getContentCatList(
            @RequestParam(value="id", defaultValue="0") Long parentId) {

        List<EasyUiTreeNode> list = contentCategoryService.getContentCategoryList(parentId);
        return list;
    }

    @RequestMapping("/create")
    @ResponseBody
    public E3Result createCategory(Long parentId, String name) {
        E3Result result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }
}
