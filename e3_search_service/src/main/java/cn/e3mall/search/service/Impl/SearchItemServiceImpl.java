package cn.e3mall.search.service.Impl;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-02-17 09:23
 **/

@Service
public class SearchItemServiceImpl implements SearchItemService {


    @Autowired
    private SolrServer solrServer;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public E3Result importAllItemToIndex() {
        try {
            //查询商品列表
            List<SearchItem> itemList = itemMapper.getItemList();
            //导入索引库
            for (SearchItem searchItem : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategrary_name());
                document.addField("item_desc", searchItem.getItem_desc());
                //写入索引库
                solrServer.add(document);
            }
            //提交
            solrServer.commit();
            //返回成功
            return E3Result.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, "商品导入失败");
        }

    }


    public E3Result addDocument(long itemId) throws Exception {
        // 1、根据商品id查询商品信息。
        SearchItem searchItem = itemMapper.getItemById(itemId);
        // 2、创建一SolrInputDocument对象。
        SolrInputDocument document = new SolrInputDocument();
        // 3、使用SolrServer对象写入索引库。
        document.addField("id", searchItem.getId());
        document.addField("item_title", searchItem.getTitle());
        document.addField("item_sell_point", searchItem.getSell_point());
        document.addField("item_price", searchItem.getPrice());
        document.addField("item_image", searchItem.getImage());
        document.addField("item_category_name", searchItem.getCategrary_name());
        document.addField("item_desc", searchItem.getItem_desc());
        // 5、向索引库中添加文档。
        solrServer.add(document);
        solrServer.commit();
        // 4、返回成功，返回e3Result。
        return E3Result.ok();
    }

}


