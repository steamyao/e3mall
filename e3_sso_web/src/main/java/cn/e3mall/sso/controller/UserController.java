package cn.e3mall.sso.controller;

import cn.e3mall.common.Utils.CookieUtils;
import cn.e3mall.common.Utils.JsonUtils;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.UserLoginService;
import cn.e3mall.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-24 09:58
 **/

@Controller
public class UserController {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserLoginService userLoginService;

    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;


    @RequestMapping(value="/user/check/{param}/{type}", method= RequestMethod.GET)
    @ResponseBody
    public E3Result checkUserInfo(@PathVariable String param, @PathVariable Integer type)
    {
        E3Result result = userRegisterService.checkUserInfo(param, type);
        return result;
    }

    @RequestMapping(value="/user/register", method=RequestMethod.POST)
    @ResponseBody
    public E3Result register(TbUser user) {
        E3Result result = userRegisterService.createUser(user);
        return result;
    }

    @RequestMapping(value="/user/login", method=RequestMethod.POST)
    @ResponseBody
    public E3Result userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response)
    {
        E3Result taotaoResult = userLoginService.login(username, password);
        if(taotaoResult.getStatus()==200) {
            // 取出token
            String token = taotaoResult.getData().toString();
            // 在返回结果之前，设置cookie(即将token写入cookie)
            // 1.cookie怎么跨域？ // 2.如何设置cookie的有效期？
            CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
        }
        // 返回结果
        return taotaoResult;
    }

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback) {
        E3Result result = userLoginService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            // 客户端为jsonp请求，需要返回js代码
            String jsonResult = callback + "(" + JsonUtils.objectToJson(result) + ");";
            return jsonResult;
            // 统一返回字符串
            }
            return JsonUtils.objectToJson(result); // 统一返回字符串
        }


    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public E3Result logOut(@PathVariable String token) {
        E3Result result = userLoginService.loginOut(token);
        return result;
    }

}
