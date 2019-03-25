package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.E3Result;

public interface UserLoginService {

    E3Result login(String username, String password);

    E3Result getUserByToken(String token);

    E3Result loginOut(String token);
}
