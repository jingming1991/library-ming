package com.episerver.dao;


import com.episerver.entity.Author;

import java.util.List;

public interface IAuthorDao {

    List<Author> findByEmailIn(List<String> mailList);
}
