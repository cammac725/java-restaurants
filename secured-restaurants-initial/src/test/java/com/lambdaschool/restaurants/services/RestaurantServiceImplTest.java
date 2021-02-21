package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.RestaurantsApplication;
import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// use the database

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantsApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestaurantServiceImplTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private PaymentService paymentService;

    @Before
    public void setUp() throws Exception {
        // mocks => fake data
        // stubs -> fake methods
        // Java -> both are called mocks
        MockitoAnnotations.initMocks(this);

//        List<Restaurant> myList = restaurantService.findAll();
//        for (Restaurant r : myList) {
//            System.out.println(r.getRestaurantid() + " " + r.getName());
//        }

//        List<Payment> myList = paymentService.findAll();
//        for (Payment p : myList) {
//            System.out.println(p.getPaymentid());
//        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findRestaurantByNameLike() {
        assertEquals(1, restaurantService.findRestaurantByNameLike("eat").size());
    }

    @Test
    public void b_findNameCity() {
        assertEquals(1, restaurantService.findNameCity("test", "city").size());
    }

    @Test
    public void c_findAll() {
        assertEquals(3, restaurantService.findAll().size());
    }

    @Test
    public void d_findRestaurantById() {
        assertEquals("Test Apple", restaurantService.findRestaurantById(4).getName());
    }

    @Test (expected = EntityNotFoundException.class)
    public void e_findRestaurantByIdNotFound() {
        assertEquals("", restaurantService.findRestaurantById(166).getName());
    }

    @Test
    public void f_findRestaurantByName() {
    }

    @Test
    public void g_delete() {
        restaurantService.delete(10);
        assertEquals(2, restaurantService.findAll().size());
    }

    @Test
    public void h_save() {
        // create a restaurant to save
        String rest3Name = "Test Good Eats";
        Restaurant rest3 = new Restaurant(rest3Name,
                "565 Side Avenue",
                "Village",
                "ST",
                "555-123-1555");

        Payment payType1 = new Payment("Pay1");
        payType1.setPaymentid(1);

        rest3.getPayments().add(new RestaurantPayments(rest3, payType1));
        rest3.getMenus().add(new Menu("Pizza", 15.15, rest3));

        Restaurant addRest = restaurantService.save(rest3);

        assertNotNull(addRest);
        assertEquals(rest3Name, addRest.getName());

    }

    @Test
    public void hh_saveput() {

    }

    @Test
    public void i_update() {
    }
}