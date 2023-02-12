package com.group19.bookstore.controller.impl;

import com.group19.bookstore.controller.BookDetailsController;
import com.group19.bookstore.models.Author;
import com.group19.bookstore.models.Book;
import com.group19.bookstore.repository.DummyAuthorRepository;
import com.group19.bookstore.repository.DummyBookStoreRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class BookDetailsControllerImpl implements BookDetailsController {
    @Override
    public List<Book> getBooksByAuthorId(String authorId) {
        List<Author> authors = DummyAuthorRepository.getInstance().getAuthorList();
        List<Book> books = DummyBookStoreRepository.getInstance().getBooks();

        Author requestedAuthor = authors.stream()
                .filter(author -> StringUtils.equalsAnyIgnoreCase(String.valueOf(author.getId()), authorId))
                .findFirst()
                .orElse(null);

        if (requestedAuthor == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Count not find author for given id: " + authorId
            );
        }

        List<Book> authorBooks = books.stream()
                .filter(book -> StringUtils.equalsAnyIgnoreCase(book.getAuthor(), requestedAuthor.getName()))
                .findAny().stream().toList();

        return authorBooks;
    }
}
