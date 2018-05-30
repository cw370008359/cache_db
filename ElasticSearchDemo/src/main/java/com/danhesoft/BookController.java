package com.danhesoft;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS.required;

/**
 * Created by caowei on 2018/5/29.
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private TransportClient client;

    /**
     * 根据id查询
     * @param id 编号
     * @return
     */
    @GetMapping("/get/novel/{id}")
    @ResponseBody
    public ResponseEntity get(@PathVariable String id){
        if(id.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        GetResponse response = client.prepareGet("book", "novel", id).get();
        if(!response.isExists()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }

    /**
     * 添加新的book数据
     * @param title
     * @param author
     * @param wordCount
     * @param publisDate
     * @return 返回id
     */
    @PostMapping("/add/novel")
    @ResponseBody
    public ResponseEntity add(@RequestParam(name = "title")String title, @RequestParam(name = "author")String author,
                              @RequestParam(name = "work_count")int wordCount, @RequestParam(name = "publis_date")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publisDate){
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject().field("title", title)
                    .field("author", author).field("work_count", wordCount).field("publis_date", publisDate.getTime()).endObject();
            IndexResponse response = client.prepareIndex("book", "novel").setSource(contentBuilder).get();
            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id删除book
     * @param id
     * @return DELETED
     */
    @DeleteMapping("/delete/novel/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable String id) {
        if(id.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        DeleteResponse response = client.prepareDelete("book", "novel", id).get();
        if(response!=null){
            return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 根据id修改book信息
     * @param id
     * @param title
     * @param author
     * @param wordCount
     * @param publisDate
     * @return UPDATED
     */
    @PutMapping("/update/novel/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable String id, @RequestParam(name = "title", required = false)String title,
                                 @RequestParam(name = "author", required = false)String author,
                                 @RequestParam(name = "work_count",  required = false)Integer wordCount,
                                 @RequestParam(name = "publis_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publisDate){
        UpdateRequest updateRequest = new UpdateRequest("book", "novel", id);
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
            if(title!=null){
                contentBuilder.field("title", title);
            }
            if(author!=null){
                contentBuilder.field("author", author);
            }
            if(wordCount!=null){
                contentBuilder.field("work_count", wordCount);
            }
            if(publisDate!=null){
                contentBuilder.field("publis_date", publisDate);
            }
            contentBuilder.endObject();
            updateRequest.doc(contentBuilder);
            try {
                UpdateResponse updateResponse = this.client.update(updateRequest).get();
                if(updateResponse!=null){
                    return new ResponseEntity(updateResponse.getResult().toString(), HttpStatus.OK);
                }else{
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/query/novel")
    @ResponseBody
    public ResponseEntity query(
            @RequestParam(name = "author", required = false)String author,
            @RequestParam(name = "title", required = false)String title,
            @RequestParam(name = "gt_word_count", defaultValue = "0")int gtWordCount,
            @RequestParam(name = "lt_word_count", required = false)Integer ltWordCount){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(author!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("author", author));
        }
        if(title!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
        }
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("work_count").from(gtWordCount);
        if(ltWordCount!=null && ltWordCount>0)
            rangeQueryBuilder.to(ltWordCount);
        boolQueryBuilder.filter(rangeQueryBuilder);
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch("book").setTypes("novel").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder).setFrom(0).setSize(10);
        System.out.println(searchRequestBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for(SearchHit hit: searchResponse.getHits()){
            result.add(hit.getSourceAsMap());
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
