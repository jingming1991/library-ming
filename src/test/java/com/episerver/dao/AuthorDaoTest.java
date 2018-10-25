package com.episerver.dao;

import com.episerver.entity.Author;
import com.episerver.fileReader.IAuthorReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorDaoTest extends BaseDaoTest {


    @Before
    public void setUp() {
        cleanAuthorTable();
        initializeAuthor();
    }


    @Test
    public void findAll() {
        Iterable<Author> all = authorDao.findAll();
        List<Author> authorList = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(6, authorList.size());
        Author author = authorList.get(0);
        Assert.assertEquals("Walter", author.getFirstName());
        Assert.assertEquals("Paul", author.getLastName());
        Assert.assertEquals("pr-walter@optivo.de", author.getEmail());
    }

    @Test
    public void findByEmail() {
        List<Author> authorList = authorDao.findByEmail("pr-mueller@optivo.de");
        Assert.assertEquals(1, authorList.size());
        Author author = authorList.get(0);
        Assert.assertEquals("Müller", author.getFirstName());
        Assert.assertEquals("Max", author.getLastName());
        Assert.assertEquals("pr-mueller@optivo.de", author.getEmail());
    }

    @Test
    public void findByEmailIn() {
        List<Author> authorList = authorDao.findByEmailIn(Arrays.asList("pr-gustafsson@optivo.de", "pr-rabe@optivo.de"));
        Assert.assertEquals(2, authorList.size());
        Author author = authorList.get(1);
        Assert.assertEquals("Rabe", author.getFirstName());
        Assert.assertEquals("Harald", author.getLastName());
        Assert.assertEquals("pr-rabe@optivo.de", author.getEmail());
    }
}