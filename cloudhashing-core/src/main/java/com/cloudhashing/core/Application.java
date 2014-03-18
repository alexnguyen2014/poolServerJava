package com.cloudhashing.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloudhashing.core.bin.DBService;

public class Application {

   public static void main(String[] args) {
     ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
     DBService dbservice = applicationContext.getBean(DBService.class);
     Long key = dbservice.getJdbcTemplate().queryForObject("INSERT INTO epictable(moobars) VALUES('abc') RETURNING mytable_key", Long.class);
     System.out.println(key);
   }
}
