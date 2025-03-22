package com.hanshul.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationMetaDto {
    private Integer currentPageNumber;
    private Integer currentPageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
}
