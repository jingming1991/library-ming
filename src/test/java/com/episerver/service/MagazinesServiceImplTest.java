package com.episerver.service;

import com.episerver.dao.AuthorDao;
import com.episerver.dao.MagazineDao;
import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.fileReader.IAuthorReader;
import com.episerver.fileReader.IMagazineReader;
import com.episerver.service.impl.MagazineServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MagazinesServiceImplTest {

    private MagazineServiceImpl mockMagazinesService;
    private AuthorDao mockAuthorDao;
    private MagazineDao mockMagazineDao;
    @Autowired
    private IMagazineReader magazineReader;
    @Autowired
    private IAuthorReader authorReader;
    @Autowired
    private MagazineServiceImpl magazinesService;

    @Before
    public void setUp() {

        List<Author> authorList = authorReader.convertAuthorFile("csv/autoren.csv");

        mockAuthorDao = Mockito.mock(AuthorDao.class);
        Mockito.when(mockAuthorDao.findByEmailIn(Mockito.anyList()))
                .thenReturn(authorList);
        mockMagazineDao = Mockito.mock(MagazineDao.class);
        mockMagazinesService = new MagazineServiceImpl(magazineReader, mockAuthorDao, mockMagazineDao);
    }


    @Test
    public void getFromFile() {
        List<Magazine> magazineList = mockMagazinesService.getFromFile("csv/zeitschriften.csv");
        magazineList.forEach(m -> Assert.assertTrue(m.getAuthorIds().size() > 0));
    }

    @Test
    public void saveAll() {
        List<Magazine> magazineList = mockMagazinesService.getFromFile("csv/zeitschriften.csv");
        magazinesService.saveAll(magazineList);
    }
}