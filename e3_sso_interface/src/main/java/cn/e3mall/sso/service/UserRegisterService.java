package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbUser;

public interface UserRegisterService {

    E3Result checkUserInfo(String param, int type);

     E3Result createUser(TbUser user);
}
