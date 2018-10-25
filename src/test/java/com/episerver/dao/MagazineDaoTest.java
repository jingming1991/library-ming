package com.episerver.dao;

import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.fileReader.IMagazineReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MagazineDaoTest extends BaseDaoTest {

    @Before
    public void setUp() {
        cleanMagazineTable();
        initializeAuthor();
        initializeMagazine();
    }

    private void initializeMagazine() {
        List<Magazine> magazineList = magazineReader.convertMagazineFile("csv/zeitschriften.csv");
        magazineList.forEach(m -> m.setAuthorIds(authorDao.findByEmailIn(m.getAuthorMails()).stream().map(Author::getId).collect(Collectors.toList())));
        magazineDao.saveAll(magazineList);
    }

    @Test
    public void findAll() {
        Iterable<Magazine> all = magazineDao.findAll();
        List<Magazine> magazineList = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(6, magazineList.size());
        Magazine magazine = magazineList.get(2);
        Assert.assertEquals("Kochen für Genießer", magazine.getTitle());
        Assert.assertEquals("2007-05-01", magazine.getPublishDate().toString());
        Assert.assertEquals("2365-5632-7854", magazine.getNumberISBN());
        Assert.assertEquals(2, magazine.getAuthorIds().size());
        Assert.assertEquals(0, magazine.getAuthorMails().size());
    }

    @Test
    public void findByNumberISBN() {
        List<Magazine> haveISBN = magazineDao.findByNumberISBN("2547-8548-2541");
        Assert.assertEquals(1, haveISBN.size());
        Magazine magazine = haveISBN.get(0);
        Assert.assertEquals("Der Weinkenner", magazine.getTitle());
        Assert.assertEquals("2002-12-12", magazine.getPublishDate().toString());

        List<Magazine> withoutISBN = magazineDao.findByNumberISBN("123456");
        Assert.assertEquals(0, withoutISBN.size());
    }

    @Test
    public void findByAuthorIds() {
        List<Magazine> withoutAuthorId = magazineDao.findByAuthorIds("123456");
        Assert.assertEquals(0, withoutAuthorId.size());


        Author karl = authorDao.findByEmail("pr-gustafsson@optivo.de").get(0);
        List<Magazine> karlMagazines = magazineDao.findByAuthorIds(karl.getId());
        Assert.assertEquals(1, karlMagazines.size());
        Assert.assertEquals("1313-4545-8875", karlMagazines.get(0).getNumberISBN());


        Author paul = authorDao.findByEmail("pr-walter@optivo.de").get(0);
        List<Magazine> paulMagazines = magazineDao.findByAuthorIds(paul.getId());
        Assert.assertEquals(3, paulMagazines.size());
        List<String> isbns = paulMagazines.stream().map(Magazine::getNumberISBN).collect(Collectors.toList());
        Assert.assertTrue(isbns.contains("2365-5632-7854"));
        Assert.assertTrue(isbns.contains("2547-8548-2541"));
        Assert.assertTrue(isbns.contains("5454-5587-3210"));
    }
}