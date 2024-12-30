package by.yaroslavesev.backend.repositories;
import by.yaroslavesev.backend.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserRepository {

    @Inject
    private EntityManager entityManager;

    public void registerUser(User user) {
        entityManager.getTransaction().begin();

        try {
            if (userExists(user.getUsername())) {
                throw new IllegalArgumentException("User with username " + user.getUsername() + " already exists");
            }
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public Optional<User> findUser(String username) {
        try {
            entityManager.getTransaction().begin();

            User user = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            entityManager.getTransaction().commit();
            return Optional.of(user);

        } catch (jakarta.persistence.NoResultException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return Optional.empty();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public boolean userExists(String username) {
        return entityManager
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult() > 0;
    }

}

