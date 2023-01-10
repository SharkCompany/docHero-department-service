package com.dochero.departmentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
    private Integer pageIndex;
    private Integer itemsPerPage;
    private Integer currentItemCount;
    private Integer totalItems;
    private List<T> items;
}
