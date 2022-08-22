package com.codeblue.montreISTA.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {
    private static Pageable pageable;

    public static Pageable paginate(Integer page, String sort, boolean descending){

        int size = 10;

        if (sort == null){sort = "id";}
        if (page == null || page < 0){page = 0;}
        page = page == 0 ? 0 : --page;

        return pageable = !descending ? PageRequest.of(page, size, Sort.by(sort)) : PageRequest.of(page, size, Sort.by(sort).descending());

    }
}




//        if (page == 0){page  = 0;} else {page--;}
//        if (!descending){
//        pageable = PageRequest.of(page,size, Sort.by(sort));
//    } else {
//        pageable = PageRequest.of(page,size, Sort.by(sort).descending());
//    }