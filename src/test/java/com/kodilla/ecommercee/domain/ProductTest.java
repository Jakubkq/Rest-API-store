package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.Assert;
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
public class ProductTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testProductRepositoryPost() {
        //Given
        Group nabial = new Group("nabial");
        Group mieso = new Group("mieso");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));
        Product product2 = new Product(nabial, "ser", new BigDecimal(32));
        Product product3 = new Product(mieso, "kurczak", new BigDecimal(22));
        Product product4 = new Product(mieso, "wolowina", new BigDecimal(52));

        nabial.getProducts().add(product1);
        nabial.getProducts().add(product2);
        mieso.getProducts().add(product3);
        mieso.getProducts().add(product4);

        //When
        groupRepository.save(nabial);
        groupRepository.save(mieso);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);

        //Then
        assertEquals(4, productRepository.count());
        assertEquals(2, groupRepository.count());
        assertEquals(2, nabial.getProducts().size());
        assertEquals(2, mieso.getProducts().size());

        //CleanUp
        groupRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testProductRepositoryGetAll() {
        //Given
        Group nabial = new Group("nabial");
        Group mieso = new Group("mieso");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));
        Product product2 = new Product(nabial, "ser", new BigDecimal(32));
        Product product3 = new Product(mieso, "kurczak", new BigDecimal(22));

        nabial.getProducts().add(product1);
        nabial.getProducts().add(product2);
        mieso.getProducts().add(product3);

        groupRepository.save(nabial);
        groupRepository.save(mieso);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        //When
        List<Product> testProduct = productRepository.findAll();

        //Then
        assertEquals(3, testProduct.size());

        //CleanUp
        groupRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testProductRepositoryGetById() {
        //Given
        Group nabial = new Group("nabial");
        Group mieso = new Group("mieso");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));
        Product product2 = new Product(nabial, "ser", new BigDecimal(32));

        nabial.getProducts().add(product1);
        nabial.getProducts().add(product2);

        groupRepository.save(nabial);
        groupRepository.save(mieso);
        productRepository.save(product1);
        productRepository.save(product2);

        //When
        Optional<Product> testProduct1 = productRepository.findById(product1.getId());
        Optional<Product> testProduct2 = productRepository.findById(product2.getId());

        // Then
        assertEquals(product1.getId(), testProduct1.get().getId());
        assertEquals(product2.getId(), testProduct2.get().getId());

        //CleanUp
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void testProductRepositoryPut() {
        //Given
        Group nabial = new Group("nabial");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));
        Product product2 = new Product(nabial, "ser", new BigDecimal(32));

        nabial.getProducts().add(product1);
        nabial.getProducts().add(product2);

        groupRepository.save(nabial);
        productRepository.save(product1);
        productRepository.save(product2);

        //When
        Product editedProduct = productRepository.findById(product1.getId()).get();
        editedProduct.setName("kefir");
        productRepository.save(editedProduct);

        //Then
        assertEquals(productRepository.findById(product1.getId()).get().getName(), "kefir");

        //CleanUp
        groupRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testProductRepositoryPutGroup() {
        //Given
        Group nabial = new Group("nabial");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));
        Product product2 = new Product(nabial, "ser", new BigDecimal(32));

        nabial.getProducts().add(product1);
        nabial.getProducts().add(product2);

        groupRepository.save(nabial);
        productRepository.save(product1);
        productRepository.save(product2);

        //When
        Group editGroup = productRepository.findById(product1.getId()).get().getGroup();
        editGroup.setName("zepsutynabial");
        groupRepository.save(editGroup);
        String productGroupAfterEdit = productRepository.findById(product2.getId()).get().getGroup().getName();

        //Then
        assertEquals(productGroupAfterEdit, "zepsutynabial");

        //CleanUp
        groupRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testDeleteProductById() {
        //Given
        Group nabial = new Group("nabial");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));
        Product product2 = new Product(nabial, "ser", new BigDecimal(32));

        nabial.getProducts().add(product1);
        nabial.getProducts().add(product2);

        groupRepository.save(nabial);
        productRepository.save(product1);
        productRepository.save(product2);

        //When
        long productSizeBeforeDelete = productRepository.count();
        productRepository.deleteById(product1.getId());
        long productSizeAfterDelete = productRepository.count();

        // Then
        assertEquals(Optional.empty(), productRepository.findById(product1.getId()));
        assertEquals(2, productSizeBeforeDelete);
        assertEquals(1, productSizeAfterDelete);

        //CleanUp
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void testProductCascadeWhenRemoveGroup() {
        //Given
        Group nabial = new Group("nabial");
        Group mieso = new Group("mieso");
        Product product1 = new Product(nabial, "Maslo", new BigDecimal(4));
        Product product2 = new Product(nabial, "ser", new BigDecimal(32));
        Product product3 = new Product(mieso, "kurczak", new BigDecimal(22));

        nabial.getProducts().add(product1);
        nabial.getProducts().add(product2);
        mieso.getProducts().add(product3);

        groupRepository.save(nabial);
        groupRepository.save(mieso);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        //When
        long productSizeBeforeDelete = productRepository.count();
        long groupSizeBeforeDelete = groupRepository.count();

        groupRepository.deleteById(nabial.getId());
        groupRepository.deleteById(mieso.getId());

        long productSizeAfterDelete = groupRepository.count();
        long groupSizeAfterDelete = groupRepository.count();

        // Then
        assertEquals(2 ,groupSizeBeforeDelete);
        assertEquals(0, groupSizeAfterDelete);
        assertEquals(3, productSizeBeforeDelete);
        assertEquals(0, productSizeAfterDelete);

        //CleanUp
        groupRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testProductCascadeWhenRemoveCart() {
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
        long productBeforeDelete = productRepository.count();
        cartRepository.deleteById(cart1.getId());
        long productAfterDelete = productRepository.count();

        //Then
        assertEquals(1, productBeforeDelete);
        assertEquals(1, productAfterDelete);

        //CleanUp
        userRepository.deleteAll();
        cartRepository.deleteAll();
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }
}