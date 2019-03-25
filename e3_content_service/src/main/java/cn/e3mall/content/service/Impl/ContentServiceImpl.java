package cn.e3mall.content.service.Impl;

import cn.e3mall.common.Utils.JsonUtils;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.redis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-02-08 19:34
 **/

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    Logger logger = Logger.getLogger(this.getClass());
    @Override
    public E3Result addContent(TbContent content) {
        //补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据
        contentMapper.insert(content);
        //缓存同步
        jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentList(long cid) {
        //查询缓存
        try {
            String json = jedisClient.hget(CONTENT_KEY, cid + "");
            //判断json是否为空
            if (StringUtils.isNotBlank(json)) {
                //把json转换成list
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                logger.info("使用redis缓存");
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据分类id查询内容列表
        //设置查询条件
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);
        logger.warn("向数据库查询");
        //向缓存中添加数据
        try {
            jedisClient.hset(CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
