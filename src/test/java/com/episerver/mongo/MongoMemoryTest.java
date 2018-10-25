package com.episerver.mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;

public class MongoMemoryTest {

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static MongodProcess mongod;
    private static MongodExecutable mongodExecutable;
    protected static int port = 12345;
    protected String mongoDb = "librarySystem_test";

    @BeforeClass
    public static void setUpMongo() throws IOException {

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        mongodExecutable = starter.prepare(mongodConfig);
        mongod = mongodExecutable.start();
    }

    @AfterClass
    public static void tearDownMongo() throws InterruptedException {
        mongod.stop();
        Thread.sleep(1000);
    }
}
