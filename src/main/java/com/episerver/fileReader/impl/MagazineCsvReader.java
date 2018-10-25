package com.episerver.fileReader.impl;

import com.episerver.entity.Magazine;
import com.episerver.fileReader.IMagazineReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("MagazineCsv")
public class MagazineCsvReader extends BaseReader implements IMagazineReader {

    @Override
    public List<Magazine> convertMagazineFile(String fileUrl) {
        List<Magazine> results;
        try {
            List<String> stringList = getLines(fileUrl);
            results = mapFull(stringList);
        } catch (IOException e) {
            results = new ArrayList<>();
        }
        return results;
    }


    public List<Magazine> mapFull(List<String> stringList) {
        return stringList.subList(1, stringList.size())
                .stream()
                .map(this::mapOne)
                .collect(Collectors.toList());
    }

    public Magazine mapOne(String line) {
        String[] split = line.split(";", 4);
        Magazine magazine = new Magazine();
        magazine.setTitle(split[0]);
        magazine.setNumberISBN(split[1]);
        magazine.setPublishDate(parseDate(split[3]));
        String userMails = split[2];
        String[] split1 = userMails.split(",");
        magazine.setAuthorMails(Arrays.stream(split1).collect(Collectors.toList()));
        return magazine;
    }
}
