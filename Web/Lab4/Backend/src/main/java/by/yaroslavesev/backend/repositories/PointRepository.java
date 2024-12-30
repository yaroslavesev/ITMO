package by.yaroslavesev.backend.repositories;
import by.yaroslavesev.backend.models.Point;
import by.yaroslavesev.backend.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

import java.util.List;


@ApplicationScoped
@NoArgsConstructor
public class PointRepository {

    @Inject
    private EntityManager entityManager;

    public Point savePoint(Point point) {
        entityManager.getTransaction().begin();
        entityManager.persist(point);
        entityManager.getTransaction().commit();
        return point;
    }

    public List<Point> findAllPointsByUser(User user) {
        return entityManager.createQuery("SELECT p FROM Point p WHERE p.user = :user", Point.class)
                .setParameter("user", user)
                .getResultList();
    }

}

