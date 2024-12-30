package org.example.lab3.services;

import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.lab3.models.History;

import java.io.Serializable;
import java.time.LocalDateTime;

@SessionScoped
public class HistoryService implements Serializable {

    private final EntityManagerFactory entityManagerFactory;

    public HistoryService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lab3");
    }

    public void addHistoryRecord(double x, double y, double radius, Boolean hitStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        History history = new History(x, y, radius, hitStatus, LocalDateTime.now());
        entityManager.getTransaction().begin();
        entityManager.persist(history);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void close() {
        entityManagerFactory.close();
    }
}
