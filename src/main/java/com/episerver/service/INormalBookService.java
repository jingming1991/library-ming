package com.episerver.service;

import com.episerver.entity.NormalBook;
import com.episerver.entity.vo.BookVo;

import java.util.List;

public interface INormalBookService {

    List<NormalBook> getFromFile(String fileUrl);

    boolean saveAll(List<NormalBook> normalBooks);

    List<NormalBook> findAll();

    List<NormalBook> findAllBySort(String sort);

    List<NormalBook> findByAuthorIds(List<String> authorIds);

    List<NormalBook> findByNumberISBN(String numberISBN);

    List<BookVo> convertToBookVo(List<NormalBook> normalBooks);

    boolean deleteAll();
}
