package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> listAuthorByLastNameLike(String lastName);

    List<Author> findAll();

    Author findAuthorByNameCriteria(String craig, String walls);

    Author findAuthorByNameNative(String craig, String walls);
}
