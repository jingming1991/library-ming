package com.episerver.service;

import com.episerver.dao.AuthorDao;
import com.episerver.dao.MagazineDao;
import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.entity.SortType;
import com.episerver.fileReader.IAuthorReader;
import com.episerver.fileReader.IMagazineReader;
import com.episerver.fileReader.impl.AuthorCsvReader;
import com.episerver.fileReader.impl.MagazineCsvReader;
import com.episerver.service.impl.MagazineServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class MagazinesServiceImplTest {

    private MagazineServiceImpl mockMagazinesService;

    @Before
    public void setUp() {
        IAuthorReader authorReader = new AuthorCsvReader();
        List<Author> authorList = authorReader.convertAuthorFile("csv/autoren.csv");

        AuthorDao mockAuthorDao = Mockito.mock(AuthorDao.class);
        Mockito.when(mockAuthorDao.findByEmailIn(Mockito.anyList()))
                .thenReturn(authorList);

        MagazineDao mockMagazineDao = Mockito.mock(MagazineDao.class);
        IMagazineReader magazineReader = new MagazineCsvReader();
        mockMagazinesService = new MagazineServiceImpl(magazineReader, mockAuthorDao, mockMagazineDao);
    }


    @Test
    public void getFromFile() {
        List<Magazine> magazineList = mockMagazinesService.getFromFile("csv/zeitschriften.csv");
        magazineList.forEach(m -> Assert.assertTrue(m.getAuthorIds().size() > 0));
    }


    @Test
    public void findSortType() {
        SortType haha = mockMagazinesService.findSortType("haha");
        Assert.assertNull(haha);

        SortType titleLower = mockMagazinesService.findSortType("title");
        Assert.assertEquals(SortType.TITLE, titleLower);

        SortType titleUpper = mockMagazinesService.findSortType("TITLE");
        Assert.assertEquals(SortType.TITLE, titleUpper);
    }
}