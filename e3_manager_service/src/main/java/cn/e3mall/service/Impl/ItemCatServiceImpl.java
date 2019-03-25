package cn.e3mall.service.Impl;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-01-28 07:21
 **/

@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private TbItemCatMapper itemCatMapper;



    @Override
    public List<EasyUiTreeNode> getCatList(long parentId) {
        // 1、根据parentId查询节点列表
        TbItemCatExample example = new TbItemCatExample();
        //设置查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        // 2、转换成EasyUITreeNode列表。
        List<EasyUiTreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加到列表
            resultList.add(node);
        }
        // 3、返回。
        return resultList;

    }
}
