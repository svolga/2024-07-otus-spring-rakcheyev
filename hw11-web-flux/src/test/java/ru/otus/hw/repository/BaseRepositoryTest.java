package ru.otus.hw.repository;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@DataMongoTest
@ComponentScan({"ru/otus/hw/repositories"})
@TestPropertySource(properties = """
    mongock.enabled=true""")

public abstract class BaseRepositoryTest {
}
