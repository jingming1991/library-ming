package com.episerver.dao;


import com.episerver.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AuthorDao extends PagingAndSortingRepository<Author, String> {

    List<Author> findByEmailIn(List<String> mailList);


    List<Author> findByEmail(String mail);


    List<Author> findByFirstNameAndLastName(String firstName, String lastName);


    List<Author> findByIdIn(List<String> authorIds);

}
