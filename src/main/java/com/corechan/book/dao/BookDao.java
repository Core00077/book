package com.corechan.book.dao;

import com.corechan.book.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookDao extends MongoRepository<Book,String> {

}
