package cn.e3mall.controller;



import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.TbitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2018-12-15 10:02
 **/

@Controller
@RequestMapping(value = "/item")
public class TbitemController  {

    @Autowired
    private TbitemService tbitemSerice;

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public TbItem getByid(@PathVariable Long id){
        System.out.println("cont");
        return tbitemSerice.getByid(id);
    }

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIResult getItemList(int page,int rows){
        return tbitemSerice.getItemList(page,rows);
    }


    @RequestMapping("/save")
    @ResponseBody
    public E3Result saveItem(TbItem item, String desc) {
        E3Result result = tbitemSerice.addItem(item, desc);
        return result;
    }
}
