package com.episerver.service;

import com.episerver.entity.Author;

import java.util.List;

public interface IAuthorService {

    List<Author> getFromFile(String csvUrl);

    boolean saveAll(List<Author> authors);

    List<Author> findByEmail(String email);

    boolean deleteAll();
}
