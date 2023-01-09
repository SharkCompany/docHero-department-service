package com.dochero.departmentservice.search;

import com.dochero.departmentservice.constant.SearchOperation;
import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.utils.QueryParamUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSpecification<T> implements Specification<Document> {
    private SearchCriteria criteria;

    public static <T> Specification<Document> getSearchSpec(String searchField, String operation, Object searchValue) {
        if (!StringUtils.isNoneBlank(searchField, operation)) {
            return null;
        }

        if (searchValue != null && searchValue.getClass().equals(String.class)) {
            String value = String.valueOf(searchValue);
            for (String wcc : QueryParamUtil.wildCardChars) {
                value = StringUtils.replace(value, wcc, "\\" + wcc);
            }
            return DocumentSpecification.builder()
                    .criteria(new SearchCriteria(searchField, operation, value)).build();
        }
        return DocumentSpecification.builder()
                .criteria(new SearchCriteria(searchField, operation, searchValue)).build();
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Document> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (criteria.getOperation().equalsIgnoreCase(SearchOperation.EQUAL)) {
            return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase(SearchOperation.EQUAL_OR_GREATER_THAN)) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(SearchOperation.EQUAL_OR_LESS_THAN)) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(SearchOperation.LIKE)) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(criteriaBuilder.upper(root.<String>get(criteria.getKey())),
                        "%" + String.valueOf(criteria.getValue()).toUpperCase() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
                return criteriaBuilder.equal(root.get(criteria.getKey()), Boolean.valueOf(criteria.getValue().toString()));
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase(SearchOperation.IS_NULL)) {
            return criteriaBuilder.isNull(root.get(criteria.getKey()));
        }
        return null;
    }
}
