package cn.e3mall.service.Impl;

import cn.e3mall.common.Utils.IDUtils;
import cn.e3mall.common.Utils.JsonUtils;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIResult;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.redis.JedisClient;
import cn.e3mall.service.TbitemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2018-12-15 10:08
 **/

@Service
public class TbitemSericeImpl implements TbitemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;


    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;


    @Override
    public TbItem getByid(Long itemId) {

        try {
            //查询缓存
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                //把json转换为java对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据商品id查询商品信息
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);

            try {
                //把数据保存到缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
                //设置缓存的有效期
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":BASE", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return item;

    }

    @Override
    public EasyUIResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        //创建返回结果对象
        EasyUIResult result = new EasyUIResult();
        result.setTotal((int)pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
        // 1、生成商品id
        long itemId = IDUtils.genItemId();
        // 2、补全TbItem对象的属性
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 3、向商品表插入数据
        tbItemMapper.insert(item);
        // 4、创建一个TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        // 5、补全TbItemDesc的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 6、向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);

        //商品添加完成后，发生消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage textMessage = session.createTextMessage(itemId + "");
                        return textMessage;
                    }

                });
                // 7、E3Result.ok()
                return E3Result.ok();
    }



    @Override
    public TbItemDesc getItemDesc(Long itemId) {
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":DESC");
            //判断缓存是否命中
            if (StringUtils.isNotBlank(json) ) {
                //转换为java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
            //设置过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":DESC", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;

    }


}
