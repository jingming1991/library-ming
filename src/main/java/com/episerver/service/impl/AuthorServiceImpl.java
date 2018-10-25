package com.episerver.service.impl;


import com.episerver.dao.AuthorDao;
import com.episerver.entity.Author;
import com.episerver.fileReader.IAuthorReader;
import com.episerver.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    @Qualifier("AuthorCsv")
    private IAuthorReader authorReader;

    @Autowired
    private AuthorDao dao;


    @Override
    public List<Author> getFromFile(String csvUrl) {
        return authorReader.convertAuthorFile(csvUrl);
    }
}
