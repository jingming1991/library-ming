package com.episerver.controller;

import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.entity.NormalBook;
import com.episerver.entity.vo.BookVo;
import com.episerver.service.IAuthorService;
import com.episerver.service.IMagazineService;
import com.episerver.service.INormalBookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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

    @ApiOperation(value = "findAll", notes = "find all normal books and magazines")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookVo> findAll() {
        List<Magazine> magazines = magazineService.findAll();
        List<NormalBook> normalBooks = normalBookService.findAll();
        return combineBooks(magazines, normalBooks);
    }

    @ApiOperation(value = "findByEmail", notes = "show all books according to author's email")
    @ApiImplicitParam(name = "email", value = "email", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/authors/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookVo> findByEmail(@PathVariable String email) {
        List<Author> authorList = authorService.findByEmail(email);
        List<String> authorIds = authorList.stream().map(Author::getId).collect(Collectors.toList());
        List<Magazine> magazines = magazineService.findByAuthorIds(authorIds);
        List<NormalBook> normalBooks = normalBookService.findByAuthorIds(authorIds);
        List<BookVo> bookVoList = combineBooks(magazines, normalBooks);
        return bookVoList.stream().filter(b -> email.equals(b.getEmail())).collect(Collectors.toList());
    }


    @ApiOperation(value = "findByNumberISBN", notes = "display the book by given ISBN number")
    @ApiImplicitParam(name = "isbn", value = "isbn number", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/isbn/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookVo> findByNumberISBN(@PathVariable(value = "isbn") String numberISBN) {
        List<Magazine> magazines = magazineService.findByNumberISBN(numberISBN);
        List<NormalBook> normalBooks = normalBookService.findByNumberISBN(numberISBN);
        return combineBooks(magazines, normalBooks);
    }

    @ApiOperation(value = "findBySort", notes = "show all books sorted by given sortType")
    @ApiImplicitParam(name = "sort", value = "sortType", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/sort/{sort}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookVo> findBySort(@PathVariable String sort) {
        List<Magazine> magazines = magazineService.findAllBySort(sort);
        List<NormalBook> normalBooks = normalBookService.findAllBySort(sort);
        List<BookVo> bookVos = combineBooks(magazines, normalBooks);
        return bookVos.stream().sorted(Comparator.comparing(BookVo::getTitle)).collect(Collectors.toList());
    }

    private List<BookVo> combineBooks(List<Magazine> magazines, List<NormalBook> normalBooks) {
        List<BookVo> bookVoList = magazineService.convertToBookVo(magazines);
        bookVoList.addAll(normalBookService.convertToBookVo(normalBooks));
        return bookVoList;
    }
}
