package com.episerver;

import com.episerver.entity.Author;
import com.episerver.entity.Magazine;
import com.episerver.entity.NormalBook;
import com.episerver.service.IAuthorService;
import com.episerver.service.IMagazineService;
import com.episerver.service.INormalBookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

public class InitializationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        IAuthorService authorService = applicationContext.getBean(IAuthorService.class);
        IMagazineService magazineService = applicationContext.getBean(IMagazineService.class);
        INormalBookService normalBookService = applicationContext.getBean(INormalBookService.class);

        authorService.deleteAll();
        List<Author> authors = authorService.getFromFile("csv/autoren.csv");
        authorService.saveAll(authors);

        magazineService.deleteAll();
        List<Magazine> magazines = magazineService.getFromFile("csv/zeitschriften.csv");
        magazineService.saveAll(magazines);


        normalBookService.deleteAll();
        List<NormalBook> normalBooks = normalBookService.getFromFile("csv/buecher.csv");
        normalBookService.saveAll(normalBooks);

    }
}
