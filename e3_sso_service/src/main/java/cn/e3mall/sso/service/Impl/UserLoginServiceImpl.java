package cn.e3mall.sso.service.Impl;

import cn.e3mall.common.Utils.JsonUtils;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.redis.JedisClient;
import cn.e3mall.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-24 16:43
 **/

@Service
public class UserLoginServiceImpl implements UserLoginService{

    /**
     * @program: e3mall
     * @description:
     * @author: Mr.Yao
     * @create: 2019-03-24 15:52
     **/


        @Autowired
        private TbUserMapper userMapper;

        @Autowired
        private JedisClient jedisClient;

        @Value("${SESSION_PRE}")
        private String SESSION_PRE;

        @Value("${SESSION_EXPIRE}")
        private Integer SESSION_EXPIRE;


        @Override
        public E3Result login(String username, String password) {
            // 1、判断用户名密码是否正确。
            TbUserExample example = new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(username);
            //查询用户信息
            List<TbUser> list = userMapper.selectByExample(example);
            if (list == null || list.size() == 0) {
                return E3Result.build(400, "用户名或密码错误");
            }
            TbUser user = list.get(0);
            //校验密码
            if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
                return E3Result.build(400, "用户名或密码错误");
            }
            // 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
            String token = UUID.randomUUID().toString();
            // 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
            // 4、使用String类型保存Session信息。可以使用“前缀:token”为key
            user.setPassword(null);
            jedisClient.set(SESSION_PRE + ":" + token, JsonUtils.objectToJson(user));
            // 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
            jedisClient.expire(SESSION_PRE + ":" + token, SESSION_EXPIRE);
            // 6、返回e3Result包装token。
            return E3Result.ok(token);

        }

        @Override
        public E3Result getUserByToken(String token) {
            // 2、根据token查询redis。
            String json = jedisClient.get(SESSION_PRE + ":" + token);
            if (StringUtils.isBlank(json)) {
                // 3、如果查询不到数据。返回用户已经过期。
                return E3Result.build(400, "用户登录已经过期，请重新登录。");
            }
            // 4、如果查询到数据，说明用户已经登录。
            // 5、需要重置key的过期时间。
            jedisClient.expire(SESSION_PRE + ":" + token, SESSION_EXPIRE);
            // 6、把json数据转换成TbUser对象，然后使用e3Result包装并返回。
            TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
            return E3Result.ok(user);
        }

        @Override
        public E3Result loginOut(String token) {
            jedisClient.expire(SESSION_PRE + ":" + token, 0);
            return E3Result.ok();
        }




}
