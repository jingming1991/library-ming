package com.episerver.controller;

import com.episerver.dao.BaseDaoTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class BookControllerTest extends BaseDaoTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        cleanAuthorTable();
        cleanMagazineTable();
        cleanNormalBookTable();
        initializeAuthor();
        initializeMagazine();
        initializeNormalBook();
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/v1/books")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String responseString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(responseString.contains("pr-walter@optivo.de"));
    }

    @Test
    public void findByEmail() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/v1/books/authors/email/pr-walter@optivo.de")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String responseString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(responseString.contains("5454-5587-3210"));
        Assert.assertTrue(responseString.contains("2365-5632-7854"));
    }


    @Test
    public void findByNumberISBN() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/v1/books/isbn/5454-5587-3210")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String responseString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(responseString.contains("Schöner kochen"));
        Assert.assertTrue(responseString.contains("MAGAZINE"));
    }

    @Test
    public void findBySort() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/v1/books/sort/title")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String responseString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(responseString.contains("MAGAZINE"));
        int das_perfekte = responseString.indexOf("Das Perfekte");
        int das_piratenkochbuch = responseString.indexOf("Das Piratenkochbuch");
        Assert.assertTrue(das_piratenkochbuch > das_perfekte);
    }
}