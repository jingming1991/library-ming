package com.episerver.dao;

import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.entity.NormalBook;
import com.episerver.fileReader.IAuthorReader;
import com.episerver.fileReader.IMagazineReader;
import com.episerver.fileReader.INormalBookReader;
import com.episerver.mongo.MongoMemoryTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


public class BaseDaoTest extends MongoMemoryTest {

    @Autowired
    IAuthorReader authorReader;

    @Autowired
    IMagazineReader magazineReader;

    @Autowired
    INormalBookReader normalBookReader;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    MagazineDao magazineDao;

    @Autowired
    NormalBookDao normalBookDao;

    public void initializeAuthor() {
        List<Author> authors = authorReader.convertAuthorFile("csv/autoren.csv");
        authorDao.saveAll(authors);
    }

    public void cleanAuthorTable() {
        authorDao.deleteAll();
    }

    public void cleanMagazineTable() {
        magazineDao.deleteAll();
    }

    public void cleanNormalBookTable() {
        normalBookDao.deleteAll();
    }

    public void initializeMagazine() {
        List<Magazine> magazineList = magazineReader.convertMagazineFile("csv/zeitschriften.csv");
        magazineList.forEach(m -> m.setAuthorIds(authorDao.findByEmailIn(m.getAuthorMails()).stream().map(Author::getId).collect(Collectors.toList())));
        magazineDao.saveAll(magazineList);
    }

    public void initializeNormalBook() {
        List<NormalBook> normalBooks = normalBookReader.convertNormalBookFile("csv/buecher.csv");
        normalBooks.forEach(m -> m.setAuthorIds(authorDao.findByEmailIn(m.getAuthorMails()).stream().map(Author::getId).collect(Collectors.toList())));
        normalBookDao.saveAll(normalBooks);
    }
}
