package com.episerver.fileReader;

import com.episerver.entity.NormalBook;
import com.episerver.fileReader.impl.NormalBookCsvReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NormalBookCsvReaderTest {

    private NormalBookCsvReader normalBookCsvReader;

    @Before
    public void setUp() {
        normalBookCsvReader = new NormalBookCsvReader();
    }

    @Test
    public void convertMagazineFile() {
        String file = "csv/buecher.csv";
        List<NormalBook> normalBooks = normalBookCsvReader.convertNormalBookFile(file);
        Assert.assertEquals(8, normalBooks.size());
        NormalBook normalBook = normalBooks.get(0);
        Assert.assertEquals("Ich helf dir kochen. Das erfolgreiche Universalkochbuch mit großem Backteil", normalBook.getTitle());
        Assert.assertEquals("5554-5545-4518", normalBook.getNumberISBN());
        Assert.assertNotNull(normalBook.getSummary());
        Assert.assertEquals(1, normalBook.getAuthorMails().size());
        Assert.assertEquals(1, normalBook.getAuthorMails().size());
    }
}