package com.episerver.fileReader;

import com.episerver.entity.Author;
import com.episerver.fileReader.impl.AuthorCsvReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AuthorCsvReaderTest {

    private AuthorCsvReader authorCsvReader;

    @Before
    public void setUp() {
        authorCsvReader = new AuthorCsvReader();
    }

    @Test
    public void convertAuthorCsv() {
        String file = "csv/autoren.csv";
        List<Author> authors = authorCsvReader.convertAuthorFile(file);
        Assert.assertEquals(6, authors.size());
    }

    @Test
    public void mapFull() {
    }

    @Test
    public void mapOne() {
    }
}