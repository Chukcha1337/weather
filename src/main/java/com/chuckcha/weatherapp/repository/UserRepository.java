package com.chuckcha.weatherapp.repository;

import com.chuckcha.weatherapp.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(entityManager
                .createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult());
    }
}
