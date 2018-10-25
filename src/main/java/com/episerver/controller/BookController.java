package com.episerver.controller;

import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.entity.NormalBook;
import com.episerver.entity.vo.BookVo;
import com.episerver.service.IAuthorService;
import com.episerver.service.IMagazineService;
import com.episerver.service.INormalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "v1/books")
public class BookController {

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IMagazineService magazineService;

    @Autowired
    private INormalBookService normalBookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookVo> findAll() {
        List<Magazine> magazines = magazineService.findAll();
        List<NormalBook> normalBooks = normalBookService.findAll();
        return combineBooks(magazines, normalBooks);
    }

    @GetMapping(value = "/authors/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookVo> findByEmail(@PathVariable String email) {
        List<Author> authorList = authorService.findByEmail(email);
        List<String> authorIds = authorList.stream().map(Author::getId).collect(Collectors.toList());
        List<Magazine> magazines = magazineService.findByAuthorIds(authorIds);
        List<NormalBook> normalBooks = normalBookService.findByAuthorIds(authorIds);
        return combineBooks(magazines, normalBooks);
    }

    @GetMapping(value = "/isbn/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookVo> findByNumberISBN(@PathVariable(value = "isbn") String numberISBN) {
        List<Magazine> magazines = magazineService.findByNumberISBN(numberISBN);
        List<NormalBook> normalBooks = normalBookService.findByNumberISBN(numberISBN);
        return combineBooks(magazines, normalBooks);
    }

    private List<BookVo> combineBooks(List<Magazine> magazines, List<NormalBook> normalBooks) {
        List<BookVo> bookVoList = magazineService.convertToBookVo(magazines);
        bookVoList.addAll(normalBookService.convertToBookVo(normalBooks));
        return bookVoList;
    }
}
