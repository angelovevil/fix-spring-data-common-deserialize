package com.github.obiteaaron.common.data;

import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;

/**
 * Created by 刘一波 on 15/4/5.
 * E-Mail:obiteaaron@gmail.com
 * 此类用于修复PageRequest的构造函数在hessian反序列化时会抛出异常,所有pageable的接口全部用此类代替
 */
public class PageableRequest extends PageRequest {

    private static final long serialVersionUID = -499964834309543186L;

    private static final int PAGE_SIZE = 500;

    public PageableRequest() {
        super(0, 10);
    }

    public PageableRequest(int page, int size) {
        super(page, size < 1 ? 10 : size);
        verifyPageSize(size);
    }

    public PageableRequest(int page, int size, FieldsSort.Direction direction, String... properties) {
        super(page, size, new FieldsSort(direction, properties));
        verifyPageSize(size);
    }

    public PageableRequest(int page, int size, FieldsSort sort) {
        super(page, size, sort);
        verifyPageSize(size);
    }

    public PageableRequest(Pageable pageable) {
        this();
        verifyPageSize(pageable.getPageSize());
        try {
            Field pageField = AbstractPageRequest.class.getDeclaredField("page");
            Field sizeField = AbstractPageRequest.class.getDeclaredField("size");
            pageField.setAccessible(true);
            sizeField.setAccessible(true);
            pageField.set(this, pageField.get(pageable));
            sizeField.set(this, sizeField.get(pageable));

            Field sortField = PageRequest.class.getDeclaredField("sort");
            sortField.setAccessible(true);
            Sort sort = (Sort) sortField.get(pageable);
            if (sort != null) {
                FieldsSort fieldsSort = new FieldsSort(sort);
                sortField.set(this, fieldsSort);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void verifyPageSize(int size) {
        if (size > PAGE_SIZE) {
            throw new IllegalArgumentException("分页参数大小请小于500");
        }
    }
}
