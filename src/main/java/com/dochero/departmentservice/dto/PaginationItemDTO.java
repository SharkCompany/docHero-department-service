package com.dochero.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationItemDTO {
    private Integer pageIndex;
    private Integer itemsPerPage;
    private Integer currentItemCount;
    private Integer totalItems;
    private List<ItemDTO> items;
}
