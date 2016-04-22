package com.github.obiteaaron.common.data;

import java.util.Collections;
import java.util.List;

/**
 * Created by 刘一波 on 15/4/6.
 * E-Mail:obiteaaron@gmail.com
 * 此类用于修复PageImp的构造函数在hessian反序列化时会抛出异常,检测content不能为null,所有page的接口全部用此类代替
 */
public class PageableResponse<T> extends FixPageImpl<T> {

    private static final long serialVersionUID = 8099872385988073401L;

    public PageableResponse() {
        super(Collections.<T>emptyList(), null, 0L);
    }

    public PageableResponse(List<T> content, PageableRequest pageable, long total) {
        super(content == null ? Collections.<T>emptyList() : content, pageable, total);
    }

    public PageableResponse(List<T> content) {
        super(content);
    }
}
