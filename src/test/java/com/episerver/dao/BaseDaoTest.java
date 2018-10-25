package com.episerver.dao;

import com.episerver.entity.Author;
import com.episerver.fileReader.IAuthorReader;
import com.episerver.fileReader.IMagazineReader;
import com.episerver.mongo.MongoMemoryTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class BaseDaoTest extends MongoMemoryTest {

    @Autowired
    private IAuthorReader authorReader;

    @Autowired
    IMagazineReader magazineReader;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    MagazineDao magazineDao;

    void initializeAuthor() {
        List<Author> authors = authorReader.convertAuthorFile("csv/autoren.csv");
        authorDao.saveAll(authors);
    }

    void cleanAuthorTable() {
        authorDao.deleteAll();
    }


    void cleanMagazineTable() {
        magazineDao.deleteAll();
    }

}
