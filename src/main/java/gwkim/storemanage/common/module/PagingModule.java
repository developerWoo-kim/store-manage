package gwkim.storemanage.common.module;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class PagingModule<T> {

    public Page<T> getPage(List<T> content, Pageable pageable,Long totalCount) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<T> pageContent;
//        int totalItems = content.size();

        if (startItem >= totalCount) {
            pageContent = List.of();
        } else {
            long endItem = Math.min(startItem + pageSize, totalCount);
//            pageContent = content.subList(startItem, endItem);
            pageContent = content.stream()
                    .skip(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(pageContent, pageable, totalCount);
    }
}
