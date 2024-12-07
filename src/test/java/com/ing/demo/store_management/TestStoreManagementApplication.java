package com.ing.demo.store_management;

import org.springframework.boot.SpringApplication;

public class TestStoreManagementApplication {

    public static void main(String[] args) {
        SpringApplication.from(StoreManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
