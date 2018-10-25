package com.episerver.mongo;


import com.episerver.dao.AuthorDao;
import com.episerver.dao.MagazineDao;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

@Configuration
@EnableAutoConfiguration
@Lazy
public class MongoFactory {

    @Value("${mongo.host}")
    private String mongoHostId;

    @Value("${mongo.dbName}")
    private String mongoDBName;


    @Bean(name = "mongoClient")
    public MongoClient createMongoClient() {
        MongoClientOptions build = MongoClientOptions.builder()
                .connectionsPerHost(500)
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .maxConnectionIdleTime(20 * 60 * 1000)
                .socketTimeout(20 * 60 * 1000).build();
        return new MongoClient(mongoHostId, build);
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate getMongoTemplate(@Qualifier("mongoClient") MongoClient client) {
        return new MongoTemplate(client, mongoDBName);
    }

    @Bean(name = "MongoRepositoryFactory")
    public MongoRepositoryFactory getMongoRepositoryFactory(@Qualifier("mongoTemplate") MongoTemplate mongoTemplate) {
        return new MongoRepositoryFactory(mongoTemplate);
    }

    @Bean
    public AuthorDao authorDao(@Qualifier("MongoRepositoryFactory") MongoRepositoryFactory factory) {
        return factory.getRepository(AuthorDao.class);
    }

    @Bean
    public MagazineDao magazineDao(@Qualifier("MongoRepositoryFactory") MongoRepositoryFactory factory) {
        return factory.getRepository(MagazineDao.class);
    }
}
