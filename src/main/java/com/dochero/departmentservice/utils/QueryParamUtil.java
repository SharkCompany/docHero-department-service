package com.dochero.departmentservice.utils;

import com.dochero.departmentservice.search.SearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class QueryParamUtil {
    public static final String[] wildCardChars = {"\\", "%", "_"};

    public static Sort getSortOrder(String sortField, String sortOrder) {
        if (StringUtils.isNotBlank(sortField)) {
            if (StringUtils.isNotBlank(sortOrder)) {
                return Sort.by(Sort.Direction.valueOf(sortOrder.toUpperCase()), sortField.split(","));
            }
            return Sort.by(Sort.DEFAULT_DIRECTION, sortField.split(","));
        }
        return Sort.unsorted();
    }

    public static Pageable getPaginationOrder(Integer page, Integer records, Sort sort) {
        if (sort == null) {
            sort = Sort.unsorted();
        }
        return PageRequest.of(page, records, sort);
    }
}
