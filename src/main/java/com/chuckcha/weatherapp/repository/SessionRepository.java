package com.chuckcha.weatherapp.repository;

import com.chuckcha.weatherapp.model.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository extends BaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Session session) {
        entityManager.persist(session);
    }
}
