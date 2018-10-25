package com.episerver.service.impl;


import com.episerver.dao.AuthorDao;
import com.episerver.dao.MagazineDao;
import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.entity.vo.BookVo;
import com.episerver.entity.vo.BookeType;
import com.episerver.fileReader.IMagazineReader;
import com.episerver.service.IMagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MagazineServiceImpl implements IMagazineService {

    private IMagazineReader magazineReader;

    private AuthorDao authorDao;

    private MagazineDao magazineDao;

    @Autowired
    public MagazineServiceImpl(@Qualifier("MagazineCsv") IMagazineReader magazineReader, AuthorDao authorDao, MagazineDao magazineDao) {
        this.magazineReader = magazineReader;
        this.authorDao = authorDao;
        this.magazineDao = magazineDao;
    }

    @Override
    public List<Magazine> getFromFile(String fileUrl) {
        List<Magazine> magazines = magazineReader.convertMagazineFile(fileUrl);

        List<String> emails = magazines.stream()
                .flatMap(m -> m.getAuthorMails().stream()).collect(Collectors.toList());

        List<Author> AuthorByEmailIn = authorDao.findByEmailIn(emails);

        Map<String, List<Author>> mailMap = AuthorByEmailIn.stream()
                .collect(Collectors.groupingBy(Author::getEmail));

        magazines.forEach(m ->
                m.setAuthorIds(
                        m.getAuthorMails().stream()
                                .flatMap(mail -> mailMap.getOrDefault(mail, new ArrayList<>()).stream().map(Author::getId))
                                .collect(Collectors.toList())
                )
        );

        return magazines;
    }

    @Override
    public boolean saveAll(List<Magazine> magazines) {
        try {
            magazineDao.saveAll(magazines);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Magazine> findAll() {
        Iterable<Magazine> all = magazineDao.findAll(Sort.by(Sort.Direction.ASC, "title"));
        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Magazine> findByAuthorIds(String authorId) {
        return magazineDao.findByAuthorIds(authorId);
    }

    @Override
    public List<Magazine> findByAuthorIds(List<String> authorIds) {
        return magazineDao.findByAuthorIdsIn(authorIds);
    }


    @Override
    public List<Magazine> findByNumberISBN(String numberISBN) {
        return magazineDao.findByNumberISBN(numberISBN);
    }

    @Override
    public List<BookVo> convertToBookVo(List<Magazine> magazines) {
        List<BookVo> bookVoList = new ArrayList<>();

        List<String> authorIds = magazines.stream().flatMap(n -> n.getAuthorIds().stream())
                .distinct().collect(Collectors.toList());

        List<Author> authors = authorDao.findByIdIn(authorIds);

        Map<String, Author> authorsMap = authors.stream().collect(Collectors.toMap(Author::getId, Function.identity()));

        for (Magazine magazine : magazines) {
            String title = magazine.getTitle();
            String numberISBN = magazine.getNumberISBN();
            String publishDate = magazine.getPublishDate().toString();
            List<String> bookAuthorIds = magazine.getAuthorIds();

            bookAuthorIds.forEach(id -> {
                BookVo bookVo = new BookVo();
                bookVo.setBookeType(BookeType.MAGAZINE);
                bookVo.setTitle(title);
                bookVo.setNumberISBN(numberISBN);
                bookVo.setPublishDate(publishDate);
                Author author = authorsMap.get(id);
                String email = author.getEmail();
                bookVo.setEmail(email);
                String name = author.getFirstName() + "." + author.getLastName();
                bookVo.setAuthorName(name);
                bookVoList.add(bookVo);
            });
        }
        return bookVoList;
    }

    @Override
    public boolean deleteAll() {
        try {
            magazineDao.deleteAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
