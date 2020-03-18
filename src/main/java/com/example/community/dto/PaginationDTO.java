package com.example.community.dto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 2019/5/14.
 */
@Data
public class PaginationDTO<T> {
    private List<T> list;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    //开始页数
    private Integer page;

    private List<Integer> pages = new ArrayList<>();
    //总页数
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {//第一页,25数据/5=5页.第四页，40数据/5=7页
        this.totalPage = totalPage;
        this.page = page;

        pages.add(page);//1，2，3，4    1，2，3， 4， 5，6，7
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);//在索引位置为0的地方插入数据page-1
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        // 是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        // 是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // 是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}