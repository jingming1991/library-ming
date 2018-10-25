package com.episerver.fileReader.impl;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BaseReader {

    private Charset charset = Charset.forName("WINDOWS-1252");

    public List<String> getLines(String fileName) throws IOException {
        String path = getClass().getClassLoader().getResource(fileName).getFile();
        return FileUtils.readLines(new File(path), charset);
    }

    public LocalDate parseDate(String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dateString, dateTimeFormatter);
    }
}
