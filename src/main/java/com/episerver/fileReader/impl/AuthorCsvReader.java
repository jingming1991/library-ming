package com.episerver.fileReader.impl;

import com.episerver.entity.Author;
import com.episerver.fileReader.IAuthorReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("AuthorCsv")
public class AuthorCsvReader extends BaseReader implements IAuthorReader {

    @Override
    public List<Author> convertAuthorFile(String fileUrl) {
        List<Author> results;
        try {
            List<String> stringList = getLines(fileUrl);
            results = mapFull(stringList);
        } catch (IOException e) {
            results = new ArrayList<>();
        }
        return results;
    }


    List<Author> mapFull(List<String> stringList) {
        return stringList.subList(1, stringList.size())
                .stream()
                .map(this::mapOne)
                .collect(Collectors.toList());
    }

    Author mapOne(String line) {
        String[] split = line.split(";",4);
        return new Author(split[2], split[1], split[0]);
    }
}
