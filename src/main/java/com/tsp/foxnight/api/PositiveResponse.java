package com.tsp.foxnight.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.tsp.foxnight.utils.Views;
import lombok.Getter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@ToString
@JsonView(Views.AllView.class)
public class PositiveResponse<T> extends Response {

    @JsonInclude(NON_NULL)
    private final T data;

    public PositiveResponse(T data) {
        super(Boolean.TRUE);
        this.data = data;
    }
}
