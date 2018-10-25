package com.episerver.fileReader;

import com.episerver.entity.Magazine;
import com.episerver.fileReader.impl.MagazineCsvReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MagazineCsvReaderTest {

    private MagazineCsvReader magazineCsvReader;

    @Before
    public void setUp() {
        magazineCsvReader = new MagazineCsvReader();
    }

    @Test
    public void convertMagazineFile() {
        String file = "csv/zeitschriften.csv";
        List<Magazine> magazineList = magazineCsvReader.convertMagazineFile(file);
        Assert.assertEquals(6, magazineList.size());
        Magazine magazine = magazineList.get(0);
        Assert.assertEquals(2006, magazine.getPublishDate().getYear(), 0.001);
        Assert.assertEquals(5, magazine.getPublishDate().getMonthValue(), 0.001);
        Assert.assertEquals(21, magazine.getPublishDate().getDayOfMonth(), 0.001);
        Assert.assertEquals(1, magazine.getAuthorMails().size());
        Assert.assertEquals(0, magazine.getAuthorIds().size());
        Assert.assertEquals("pr-walter@optivo.de", magazine.getAuthorMails().get(0));
        Assert.assertEquals("Schöner kochen", magazine.getTitle());
        Assert.assertEquals("5454-5587-3210", magazine.getNumberISBN());
    }
}