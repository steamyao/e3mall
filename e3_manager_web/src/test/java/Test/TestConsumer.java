package Test;

import cn.e3mall.service.ItemCatService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-01-29 20:49
 **/


public class TestConsumer {
   public static void main(String[]args)throws Exception{
         ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"resources/spring/springmvc.xml"});
        context.start();
       ItemCatService itemCatService = (ItemCatService) context.getBean("itemCatService");
                 itemCatService.getCatList(12);
       System.out.println(777);
        }
}
