package com.episerver.dao;

import com.episerver.entity.Magazine;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MagazineDao extends PagingAndSortingRepository<Magazine, String> {

    List<Magazine> findByNumberISBN(String numberISBN);

    List<Magazine> findByAutorIds(String authorId);
}
