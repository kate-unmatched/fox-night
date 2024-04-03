package com.tsp.foxnight.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.tsp.foxnight.utils.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@JsonView(Views.AllView.class)
public abstract class Response implements Serializable {
    private final Boolean result;
}
