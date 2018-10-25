package com.episerver.service;

import com.episerver.entity.Author;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceImplTest {

    @Autowired
    private IAuthorService authorService;

    @Test
    public void getFromFile() {
        String file = getClass().getClassLoader().getResource("csv/autoren.csv").getFile();
        List<Author> authors = authorService.getFromFile(file);
        Assert.assertEquals(6, authors.size());
    }
}