package com.episerver.service;

import com.episerver.entity.Magazine;
import com.episerver.entity.NormalBook;
import com.episerver.entity.vo.BookVo;

import java.util.List;

public interface IMagazineService {

    List<Magazine> getFromFile(String fileUrl);

    boolean saveAll(List<Magazine> magazines);

    List<Magazine> findAll();

    List<Magazine> findByAuthorIds(String authorId);

    List<Magazine> findByAuthorIds(List<String> authorIds);

    List<Magazine> findByNumberISBN(String numberISBN);

    List<BookVo> convertToBookVo(List<Magazine> magazines);

    boolean deleteAll();
}
