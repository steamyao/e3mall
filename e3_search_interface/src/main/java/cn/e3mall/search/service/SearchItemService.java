package cn.e3mall.search.service;

import cn.e3mall.common.pojo.E3Result;

public interface SearchItemService {

    E3Result importAllItemToIndex() throws Exception;

    E3Result addDocument(long itemId) throws Exception;
}
