package cn.e3mall.car.controller;

import cn.e3mall.common.Utils.CookieUtils;
import cn.e3mall.common.Utils.JsonUtils;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.TbitemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-24 20:27
 **/

@Controller
public class CartController {

    @Autowired
    private TbitemService tbitemService;

    @Value("${COOKIE_TT_CART}")
    private String COOKIE_TT_CART;

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response)
    {
        // 先从Cookie中查询购物车列表
        List<TbItem> cartList = getCartList(request);
        // 判断购物车列表中是否有此商品
        boolean flag = false;
        for (TbItem tbItem : cartList) {
            /*
             * 由于tbItem的ID与参数中的itemId都是包装类型的Long，要比较是否相等不要用==，
             * 因为那样比较的是对象的地址而不是值，为了让它们比较的是值，那么可以使用.longValue来获取值
                */
        if (tbItem.getId() == itemId.longValue()) {
            // 购物车列表中存在此商品，数量要相加
            // 如果有，则商品数量相加
            tbItem.setNum(tbItem.getNum() + num);
            // 可以用商品的库存字段来作为购物车商品数量
            flag = true; break;
        }
        }
        if (!flag) {
            // 如果没有，则根据商品id查询商品信息，
            // 调用商品服务实现
        TbItem tbItem = tbitemService.getByid(itemId);
        // 设置商品数量
        tbItem.setNum(num);
        // 取一张图片
        String image = tbItem.getImage();
        if (StringUtils.isNotBlank(image))
        {
            tbItem.setImage(image.split(",")[0]);
        }
        // 添加到商品列表
        cartList.add(tbItem); }
        // 把这个购物车写入Cookie
        CookieUtils.setCookie(request, response, COOKIE_TT_CART, JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        // 返回添加成功页面
        return "cartSuccess";
    }


    // 从Cookie中取出购物车列表
    private List<TbItem> getCartList(HttpServletRequest request) {
        // 使用CookieUtils取购物车列表
        String json = CookieUtils.getCookieValue(request, COOKIE_TT_CART, true);
        // 判断Cookie中是否有值
        if (StringUtils.isBlank(json))
        {
            // 没有值就返回一个空List
            return new ArrayList();
        }
        // 把json转换成List对象
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, Model model) {
        //取购物车商品列表
        List<TbItem> cartList = getCartList(request);
        //传递给页面
        model.addAttribute("cartList", cartList);
        return "cart";
    }


    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 1、接收两个参数
        // 2、从cookie中取商品列表
        List<TbItem> cartList = getCartList(request);
        // 3、遍历商品列表找到对应商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 4、更新商品数量
                tbItem.setNum(num);
            }
        }
        // 5、把商品列表写入cookie。
        CookieUtils.setCookie(request, response, COOKIE_TT_CART, JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        // 6、响应e3Result。Json数据。
        return E3Result.ok();
    }


    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response)
    {
        // 取购物车商品列表
        List<TbItem> cartList = getCartList(request);
        // 找到对应的商品
        for (TbItem tbItem : cartList)
        {
            if (tbItem.getId().longValue() == itemId)
            {
                // 删除商品
                cartList.remove(tbItem);
                // 退出循环
                break;
            }
        }
        // 写入Cookie
        CookieUtils.setCookie(request, response, COOKIE_TT_CART, JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        // 返回逻辑视图，需要做redirect跳转
        return "redirect:/cart/cart.html";
    }

}


