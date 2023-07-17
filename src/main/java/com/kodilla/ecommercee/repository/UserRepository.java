package com.kodilla.ecommercee.repository;


import com.kodilla.ecommercee.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    @Override
    void deleteById(Long id);

    @Override
    User save (User user);


    @Override
    Optional<User> findById(Long id);

}
