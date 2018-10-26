package com.episerver.service.impl;


import com.episerver.dao.AuthorDao;
import com.episerver.dao.NormalBookDao;
import com.episerver.entity.Author;
import com.episerver.entity.NormalBook;
import com.episerver.entity.SortType;
import com.episerver.entity.vo.BookVo;
import com.episerver.entity.vo.BookeType;
import com.episerver.fileReader.INormalBookReader;
import com.episerver.service.INormalBookService;
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
public class NormalBookServiceImpl implements INormalBookService {

    private INormalBookReader normalBookReader;

    private AuthorDao authorDao;

    private NormalBookDao normalBookDao;

    @Autowired
    public NormalBookServiceImpl(@Qualifier("NormalBookCsv") INormalBookReader normalBookReader, AuthorDao authorDao, NormalBookDao normalBookDao) {
        this.normalBookReader = normalBookReader;
        this.authorDao = authorDao;
        this.normalBookDao = normalBookDao;
    }

    @Override
    public List<NormalBook> getFromFile(String fileUrl) {
        List<NormalBook> normalBooks = normalBookReader.convertNormalBookFile(fileUrl);

        List<String> emails = normalBooks.stream()
                .flatMap(m -> m.getAuthorMails().stream()).collect(Collectors.toList());

        List<Author> AuthorByEmailIn = authorDao.findByEmailIn(emails);

        Map<String, List<Author>> mailMap = AuthorByEmailIn.stream()
                .collect(Collectors.groupingBy(Author::getEmail));

        normalBooks.forEach(m ->
                m.setAuthorIds(
                        m.getAuthorMails().stream()
                                .flatMap(mail -> mailMap.getOrDefault(mail, new ArrayList<>()).stream().map(Author::getId))
                                .collect(Collectors.toList())
                )
        );

        return normalBooks;
    }

    @Override
    public boolean saveAll(List<NormalBook> normalBooks) {
        try {
            normalBookDao.saveAll(normalBooks);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<NormalBook> findAll() {
        Iterable<NormalBook> title = normalBookDao.findAll(Sort.by(Sort.Direction.ASC, "title"));
        return StreamSupport.stream(title.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<NormalBook> findAllBySort(String sort) {
        SortType sortType = findSortType(sort);
        if (sortType == null) {
            return findAll();
        }
        Iterable<NormalBook> all = normalBookDao.findAll(Sort.by(Sort.Direction.ASC, sort));
        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    public SortType findSortType(String sort) {
        try {
            return SortType.valueOf(sort.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<NormalBook> findByAuthorIds(List<String> authorIds) {
        return normalBookDao.findByAuthorIdsIn(authorIds);
    }


    @Override
    public List<NormalBook> findByNumberISBN(String numberISBN) {
        return normalBookDao.findByNumberISBN(numberISBN);
    }

    @Override
    public List<BookVo> convertToBookVo(List<NormalBook> normalBooks) {
        List<BookVo> bookVoList = new ArrayList<>();

        List<String> authorIds = normalBooks.stream().flatMap(n -> n.getAuthorIds().stream())
                .distinct().collect(Collectors.toList());

        List<Author> authors = authorDao.findByIdIn(authorIds);

        Map<String, Author> authorsMap = authors.stream().collect(Collectors.toMap(Author::getId, Function.identity()));

        for (NormalBook normalBook : normalBooks) {
            String title = normalBook.getTitle();
            String numberISBN = normalBook.getNumberISBN();
            String summary = normalBook.getSummary();
            List<String> bookAuthorIds = normalBook.getAuthorIds();

            bookAuthorIds.forEach(id -> {
                BookVo bookVo = new BookVo();
                bookVo.setBookeType(BookeType.BOOK);
                bookVo.setTitle(title);
                bookVo.setNumberISBN(numberISBN);
                bookVo.setSummary(summary);
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
            normalBookDao.deleteAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
