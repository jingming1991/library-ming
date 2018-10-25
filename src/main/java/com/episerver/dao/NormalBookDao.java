package com.episerver.dao;

import com.episerver.entity.NormalBook;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NormalBookDao extends PagingAndSortingRepository<NormalBook, String> {

    List<NormalBook> findByNumberISBN(String numberISBN);

    List<NormalBook> findByAuthorIds(String authorId);

    List<NormalBook> findByAuthorIdsIn(List<String> authorId);
}
