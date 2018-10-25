package com.episerver.service;

import com.episerver.entity.Magazine;

import java.util.List;

public interface IMagazineService {

    List<Magazine> getFromFile(String fileUrl);

    boolean saveAll(List<Magazine> magazines);

    List<Magazine> findAll();

    List<Magazine> findByAuthorIds(String authorId);

    List<Magazine> findByNumberISBN(String numberISBN);
}
