package com.assessment.user.management.app.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaginationUtils {
    public static <T>Map<String,Object> buildPaginatedResponse(Page<?> object, Pageable pageable, Long total, String key){
        Map<String,Object> pagination = new HashMap<>();
        pagination.put("total",total);
        pagination.put("size",object.getTotalElements());
        pagination.put("page",pageable.getPageNumber() +1);
        pagination.put("totalPages",(long) Math.ceil(total.doubleValue()/ pageable.getPageSize()));

        Map<String,Object> data = new HashMap<>();
        data.put(key,object.getContent());
        data.put("pagination",pagination);
        return data;
    }
}
