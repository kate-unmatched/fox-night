package com.tsp.foxnight.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.tsp.foxnight.utils.Views;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


@JsonView(Views.AllView.class)
public class CustomPage<T> extends PageImpl<T> {
    private CustomPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public static <T> CustomPage<T> of(Page<T> page) {
        return new CustomPage<>(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}