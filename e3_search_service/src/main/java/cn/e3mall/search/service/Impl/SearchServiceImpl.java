package cn.e3mall.search.service.Impl;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.ItemSearchDao;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-21 14:16
 **/

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ItemSearchDao searchDao;

    @Value("${DEFAULT_FIELD}")
    private String DEFAULT_FIELD;

    @Override
    public SearchResult search(String keyWord, int page, int rows) throws Exception {
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(keyWord);
        if (page < 1) {
            // page为当前页
            page = 1;
        }
        query.setStart((page - 1) * rows);
        if (rows < 1) {
            rows = 10;
        }
            query.setRows(rows);

        //设置默认搜索域
        query.set("df", DEFAULT_FIELD);
        //设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算总页数
        int totalNumber = searchResult.getTotalNumber();
        int pages = totalNumber / rows;
        if (totalNumber % rows > 0) pages++;
        //设置到返回结果
        searchResult.setTotalPage(pages);
        return searchResult;
    }
}
