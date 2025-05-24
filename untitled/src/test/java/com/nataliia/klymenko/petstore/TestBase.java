package com.nataliia.klymenko.petstore;

import io.qameta.allure.junit5.AllureJunit5;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = PetStoreApplication.class)
@ExtendWith(AllureJunit5.class)
public class TestBase {
}
