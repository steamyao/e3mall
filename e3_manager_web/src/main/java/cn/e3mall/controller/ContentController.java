package cn.e3mall.controller;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-02-08 19:38
 **/

@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result addContent(TbContent content) {
        E3Result result = contentService.addContent(content);
        return result;
    }
}
