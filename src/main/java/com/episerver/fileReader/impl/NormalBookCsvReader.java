package com.episerver.fileReader.impl;

import com.episerver.entity.NormalBook;
import com.episerver.fileReader.INormalBookReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("NormalBookCsv")
public class NormalBookCsvReader extends BaseReader implements INormalBookReader {

    @Override
    public List<NormalBook> convertMagazineFile(String fileUrl) {
        List<NormalBook> results;
        try {
            List<String> stringList = getLines(fileUrl);
            results = mapFull(stringList);
        } catch (IOException e) {
            results = new ArrayList<>();
        }
        return results;
    }


    public List<NormalBook> mapFull(List<String> stringList) {
        return stringList.subList(1, stringList.size())
                .stream()
                .map(this::mapOne)
                .collect(Collectors.toList());
    }

    public NormalBook mapOne(String line) {
        String[] split = line.split(";", 4);
        NormalBook normalBook = new NormalBook();
        normalBook.setTitle(split[0]);
        normalBook.setNumberISBN(split[1]);
        normalBook.setSummary(split[3]);
        String userMails = split[2];
        String[] split1 = userMails.split(",");
        normalBook.setAuthorMails(Arrays.stream(split1).collect(Collectors.toList()));
        return normalBook;
    }
}
