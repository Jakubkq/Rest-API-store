package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testUsersRepositoryPost() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        User user3 = new User("Atomek");
        User user4 = new User("Bolek");
        User user5 = new User("Olek");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        //When
        long usersRepositorySize = userRepository.count();

        //  Then
        assertEquals(5, usersRepositorySize);

        //CleanUp
        userRepository.deleteAll();
    }

    @Test
    public void testUsersRepositoryGet() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        User user3 = new User("Atomek");
        User user4 = new User("Bolek");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        //When
        List<User> testUser = userRepository.findAll();

        //Then
        assertEquals(4, testUser.size());

        //CleanUp
        userRepository.deleteAll();
    }

    @Test
    public void testUsersRepositoryGetById() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        User user3 = new User("Atomek");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //When
        Long userId = user1.getUserId();

        //Then
        assertEquals(user1.getUserId(), userId);

        //CleanUp
        userRepository.deleteAll();
    }

    @Test
    public void testUsersRepositoryPut() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        userRepository.save(user1);
        userRepository.save(user2);

        //When
        User editedUser = userRepository.findById(user2.getUserId()).get();
        editedUser.setName("Matolek");
        userRepository.save(editedUser);
        String userNAmeAfterEdit = userRepository.findById(user2.getUserId()).get().getName();

        //Then
        assertEquals("Matolek",userNAmeAfterEdit );
        assertEquals(2, userRepository.count());

        //CleanUp
        userRepository.deleteAll();
    }

    @Test
    public void testUsersRepositoryDeleteById() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        userRepository.save(user1);
        userRepository.save(user2);

        //When
        userRepository.deleteById(user1.getUserId());
        long userRepositorySize = userRepository.count();

        //Then
        assertEquals(1, userRepositorySize);
        assertEquals(Optional.empty(), userRepository.findById(user1.getUserId()));

        //CleanUp
        userRepository.deleteAll();
    }

    @Test
    public void testUserCascadeWhenRemoveCart() {
        //Given
        User user1 = new User("Tomek");
        Cart cart1 = new Cart(user1);
        Cart cart2 = new Cart(user1);
        user1.getCarts().add(cart1);
        user1.getCarts().add(cart2);

        userRepository.save(user1);
        cartRepository.save(cart1);
        cartRepository.save(cart2);

        //When
        long userSizeBeforeDelete = userRepository.count();
        cartRepository.deleteAll();
        long usersSizeAfterDelete = userRepository.count();

        // Then
        Assert.assertEquals(1, usersSizeAfterDelete);
        Assert.assertEquals(1, userSizeBeforeDelete);

        //CleanUp
        userRepository.deleteAll();
    }
}