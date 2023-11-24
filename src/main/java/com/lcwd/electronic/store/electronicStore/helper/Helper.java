package com.lcwd.electronic.store.electronicStore.helper;

import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> content = page.getContent();
        List<V> dtoList = content.stream()
                .map(object -> new ModelMapper().map(object, type))
                .collect(Collectors.toList());

        PageableResponse<V> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(dtoList);
        pageableResponse.setPageNumber(page.getNumber()+1);
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalElements(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setLastPage(page.isLast());

        return pageableResponse;
    }
}
