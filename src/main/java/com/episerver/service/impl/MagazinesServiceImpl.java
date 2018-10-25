package com.episerver.service.impl;


import com.episerver.dao.IAuthorDao;
import com.episerver.dao.IMagazineDao;
import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.fileReader.IMagazineReader;
import com.episerver.service.IMagazinesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MagazinesServiceImpl implements IMagazinesService {

    private IMagazineReader magazineReader;

    private IAuthorDao authorDao;

    private IMagazineDao magazineDao;


    public MagazinesServiceImpl(@Qualifier("MagazineCsv") IMagazineReader magazineReader, IAuthorDao authorDao, IMagazineDao magazineDao) {
        this.magazineReader = magazineReader;
        this.authorDao = authorDao;
        this.magazineDao = magazineDao;
    }

    @Override
    public List<Magazine> getFromFile(String fileUrl) {
        List<Magazine> magazines = magazineReader.convertMagazineFile(fileUrl);

        List<String> emails = magazines.stream()
                .flatMap(m -> m.getAutorMails().stream()).collect(Collectors.toList());

        List<Author> AuthorByEmailIn = authorDao.findByEmailIn(emails);

        Map<String, List<Author>> mailMap = AuthorByEmailIn.stream()
                .collect(Collectors.groupingBy(Author::getEmail));

        magazines.forEach(m ->
                m.setAutorIds(
                        m.getAutorMails().stream()
                                .flatMap(mail -> mailMap.getOrDefault(mail, new ArrayList<>()).stream().map(Author::getId))
                                .collect(Collectors.toList())
                )
        );

        return magazines;
    }
}
