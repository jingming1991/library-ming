package com.episerver.service;

import com.episerver.dao.IAuthorDao;
import com.episerver.dao.IMagazineDao;
import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.fileReader.IAuthorReader;
import com.episerver.fileReader.IMagazineReader;
import com.episerver.service.impl.MagazinesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MagazinesServiceImplTest {

    private MagazinesServiceImpl magazinesService;
    private IAuthorDao mockAuthorDao;
    private IMagazineDao mockMagazineDao;
    @Autowired
    private IMagazineReader magazineReader;
    @Autowired
    private IAuthorReader authorReader;

    @Before
    public void setUp() {

        List<Author> authorList = authorReader.convertAuthorFile("csv/autoren.csv");

        mockAuthorDao = Mockito.mock(IAuthorDao.class);
        Mockito.when(mockAuthorDao.findByEmailIn(Mockito.anyList()))
                .thenReturn(authorList);
        mockMagazineDao = Mockito.mock(IMagazineDao.class);
        magazinesService = new MagazinesServiceImpl(magazineReader, mockAuthorDao, mockMagazineDao);
    }


    @Test
    public void getFromFile() {
        List<Magazine> fromFile = magazinesService.getFromFile("csv/zeitschriften.csv");
        System.out.println(1);
    }


}