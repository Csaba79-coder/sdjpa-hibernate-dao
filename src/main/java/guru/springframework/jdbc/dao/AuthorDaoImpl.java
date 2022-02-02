package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Author> findAll() {
        EntityManager entityManager = getEntityManager();

        try{
            TypedQuery<Author> typedQuery = entityManager.createNamedQuery("author_find_all", Author.class);

            return typedQuery.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    /* @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = getEntityManager().createQuery("SELECT a FROM Author a " +
                "WHERE a.firstName = :first_name and a.lastName = :last_name", Author.class);

        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();
    }*/

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        EntityManager entityManager = getEntityManager();

        TypedQuery<Author> query = entityManager.createNamedQuery("find_by_name", Author.class);

        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        Author author = query.getSingleResult();
        entityManager.close();
        return author;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.close();

        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();

        entityManager.close();

        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Author author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager entityManager = getEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT a from Author a where a.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
            List<Author> authors = query.getResultList();

            return authors;
        } finally {
            entityManager.close();
        }
    }


    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
