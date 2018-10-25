package com.episerver.service;

import com.episerver.dao.AuthorDao;
import com.episerver.entity.Author;
import com.episerver.fileReader.IAuthorReader;
import com.episerver.fileReader.impl.AuthorCsvReader;
import com.episerver.service.impl.AuthorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class AuthorServiceImplTest {

    private IAuthorReader authorReader;
    private AuthorDao mockAuthorDao;
    private AuthorServiceImpl authorService;

    @Before
    public void setUp() {
        authorReader = new AuthorCsvReader();
        mockAuthorDao = Mockito.mock(AuthorDao.class);

    }

    @Test
    public void getFromFile() {
        authorService = new AuthorServiceImpl(authorReader, mockAuthorDao);
        String file = "csv/autoren.csv";
        List<Author> authors = authorService.getFromFile(file);
        Assert.assertEquals(6, authors.size());
    }

    @Test
    public void saveAllException() {
        List<Author> authorList = authorReader.convertAuthorFile("csv/autoren.csv");
        Mockito.doThrow(new RuntimeException()).when(mockAuthorDao).saveAll(Mockito.anyList());
        authorService = new AuthorServiceImpl(authorReader, mockAuthorDao);
        Assert.assertFalse(authorService.saveAll(authorList));
    }

    @Test
    public void saveAllNice() {
        List<Author> authorList = authorReader.convertAuthorFile("csv/autoren.csv");
        authorService = new AuthorServiceImpl(authorReader, mockAuthorDao);
        Assert.assertTrue(authorService.saveAll(authorList));
        Mockito.verify(mockAuthorDao, Mockito.times(1)).saveAll(authorList);
    }

    @Test
    public void findByEmail() {
        List<Author> authorList = authorReader.convertAuthorFile("csv/autoren.csv");
        Mockito.when(mockAuthorDao.findByEmail(Mockito.anyString())).thenReturn(authorList);
        authorService = new AuthorServiceImpl(authorReader, mockAuthorDao);
        List<Author> authorFound = authorService.findByEmail("jingming1991@gmail.com");
        Assert.assertEquals(authorList.size(), authorFound.size());
    }
}