package com.it.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageInfo {
    private List<QuestionDTO> questions;
        private boolean showPrevious;//是否显示上一页
        private boolean showFirstPage;//是否显示第一页
        private boolean showNext;//是否显示下一页
        private boolean showEndPage;//是否显示最后一页
        private Integer page;//当前页
        private List<Integer> pages = new ArrayList<>();//当前页所包含的页码数组
        private Integer totalPage;//总页数


    public void setPageInfo(Integer totalCount, Integer page, Integer size) {
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }

        this.page=page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //是否有上一页，如果是第一页就没有
        if(page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        //是否有下一页，如果是最后一页就没有
        if(page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        //是否显示第一页，如果当前页的页码数组pages包含第一页就不用显示
        if(pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        //是否显示最后一页，如果当前页的页码数组pages包含最后一页就不用显示
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }

    }
}
