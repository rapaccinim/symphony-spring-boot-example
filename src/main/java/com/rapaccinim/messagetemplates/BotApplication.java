package com.rapaccinim.messagetemplates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// here we have the SpringBootApplication annotation
@SpringBootApplication
public class BotApplication {

  public static void main(String[] args) throws Exception {

      // this is the default SpringBoot run
      SpringApplication.run(BotApplication.class, args);
  }

}
