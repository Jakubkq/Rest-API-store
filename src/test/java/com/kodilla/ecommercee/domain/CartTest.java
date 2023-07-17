package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void tesCartRepositoryPost() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        User user3 = new User("Atomek");
        Cart cart1 = new Cart(user1);
        Cart cart2 = new Cart(user1);
        Cart cart3 = new Cart(user2);
        Cart cart4 = new Cart(user2);
        Cart cart5 = new Cart(user3);

        user1.getCarts().add(cart1);
        user1.getCarts().add(cart2);
        user2.getCarts().add(cart3);
        user2.getCarts().add(cart4);
        user3.getCarts().add(cart5);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cart3);
        cartRepository.save(cart4);
        cartRepository.save(cart5);

        //When
        long cartRepositorySize = cartRepository.count();

        //Then
        assertEquals(5, cartRepositorySize);

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void testCartRepositoryGet() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        User user3 = new User("Atomek");
        Cart cart1 = new Cart(user1);
        Cart cart2 = new Cart(user1);
        Cart cart3 = new Cart(user2);
        Cart cart4 = new Cart(user2);
        Cart cart5 = new Cart(user3);

        user1.getCarts().add(cart1);
        user1.getCarts().add(cart2);
        user2.getCarts().add(cart3);
        user2.getCarts().add(cart4);
        user3.getCarts().add(cart5);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cart3);
        cartRepository.save(cart4);
        cartRepository.save(cart5);

        //When
        List<Cart> testCart = cartRepository.findAll();

        //Then
        assertEquals(5, testCart.size());

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void testCartRepositoryGetById() {
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
        Long cartId = cart1.getId();

        //Then
        assertEquals(cart1.getId(), cartId);

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void testCartRepositoryPut() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        Cart cart1 = new Cart(user1);
        Cart cart2 = new Cart(user1);
        Cart cart3 = new Cart(user2);

        user1.getCarts().add(cart1);
        user1.getCarts().add(cart2);
        user2.getCarts().add(cart3);

        userRepository.save(user1);
        userRepository.save(user2);
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cart3);

        //When
        Cart editCart = cartRepository.findById(cart3.getId()).orElse(null);
        editCart.setUser(user1);
        cartRepository.save(editCart);
        String nameOfNewUser = cartRepository.findById(cart3.getId()).get().getUser().getName();

        //Then
        assertEquals(nameOfNewUser, "Tomek");

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void tesCartDeleteById() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        Cart cart1 = new Cart(user1);
        Cart cart2 = new Cart(user1);
        Cart cart3 = new Cart(user2);


        user1.getCarts().add(cart1);
        user1.getCarts().add(cart2);
        user2.getCarts().add(cart3);

        userRepository.save(user1);
        userRepository.save(user2);
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cart3);

        //When
        long cartSizeBeforeDelete = cartRepository.count();
        cartRepository.deleteById(cart1.getId());
        long cartSizeAfterDelete = cartRepository.count();

        //Then
        assertEquals(Optional.empty(), cartRepository.findById(cart1.getId()));
        assertEquals(3, cartSizeBeforeDelete);
        assertEquals(2, cartSizeAfterDelete);

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void testCartCascadeWhenRemoveUser() {
        //Given
        User user1 = new User("Tomek");
        User user2 = new User("Romek");
        Cart cart1 = new Cart(user1);
        Cart cart2 = new Cart(user1);
        Cart cart3 = new Cart(user2);


        user1.getCarts().add(cart1);
        user1.getCarts().add(cart2);
        user2.getCarts().add(cart3);

        userRepository.save(user1);
        userRepository.save(user2);
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cart3);

        //When
        long cartSizeBeforeDelete = cartRepository.count();
        userRepository.deleteAll();
        long cartSizeAfterDelete = cartRepository.count();

        //Then
        assertEquals(3, cartSizeBeforeDelete);
        assertEquals(0, cartSizeAfterDelete);
        assertEquals(0, cartRepository.count());

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void testCartCascadeWhenRemoveProduct() {
        //Given
        User user1 = new User("Tomek");
        Cart cart1 = new Cart(user1);
        Group nabial = new Group("nabial");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));

        nabial.getProducts().add(product1);
        user1.getCarts().add(cart1);

        userRepository.save(user1);
        groupRepository.save(nabial);
        productRepository.save(product1);
        cartRepository.save(cart1);

        //When
        long cartSizeBeforeDelete = cartRepository.count();
        productRepository.deleteById(product1.getId());
        long cartSizeAfterDelete = cartRepository.count();

        //Then
        assertEquals(1, cartSizeBeforeDelete);
        assertEquals(1, cartSizeAfterDelete);

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void testCartCascadeWhenRemoveOrder() {
        //Given
        User user1 = new User("Tomek");
        Cart cart1 = new Cart(user1);
        Order order1 = new Order(cart1) ;

        user1.getCarts().add(cart1);
        userRepository.save(user1);
        cartRepository.save(cart1);
        orderRepository.save(order1);

        //When
        long cartSizeBeforeDelete = cartRepository.count();
        orderRepository.deleteById(order1.getId());
        long cartSizeAfterDelete = cartRepository.count();

        //Then
        assertEquals(1, cartSizeBeforeDelete);
        assertEquals(1, cartSizeAfterDelete);

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
        orderRepository.deleteAll();
    }
}