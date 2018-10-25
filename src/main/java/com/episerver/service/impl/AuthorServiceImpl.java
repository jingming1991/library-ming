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

    private IAuthorReader authorReader;

    private AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(@Qualifier("AuthorCsv") IAuthorReader authorReader, AuthorDao authorDao) {
        this.authorReader = authorReader;
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getFromFile(String csvUrl) {
        return authorReader.convertAuthorFile(csvUrl);
    }

    @Override
    public boolean saveAll(List<Author> authors) {
        try {
            authorDao.saveAll(authors);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Author> findByEmail(String email) {
        return authorDao.findByEmail(email);
    }

    @Override
    public boolean deleteAll() {
        try {
            authorDao.deleteAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
