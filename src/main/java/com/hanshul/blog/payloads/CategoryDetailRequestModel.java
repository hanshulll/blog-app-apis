package com.hanshul.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDetailRequestModel {
    private String categoryTitle;
    private String categoryDescription;
}
