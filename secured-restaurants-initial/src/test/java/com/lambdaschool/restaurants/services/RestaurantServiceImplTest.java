package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.RestaurantsApplication;
import com.lambdaschool.restaurants.exceptions.ResourceNotFoundException;
import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// use the database

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantsApplication.class)
public class RestaurantServiceImplTest {

    @Autowired
    private RestaurantService restaurantService;

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
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findRestaurantByNameLike() {
        assertEquals(1, restaurantService.findRestaurantByNameLike("eat").size());
    }

    @Test
    public void findNameCity() {
        assertEquals(1, restaurantService.findNameCity("test", "city").size());
    }

    @Test
    public void findAll() {
        assertEquals(3, restaurantService.findAll().size());
    }

    @Test
    public void findRestaurantById() {
        assertEquals("Test Apple", restaurantService.findRestaurantById(12).getName());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void findRestaurantByIdNotFound() {
        assertEquals("", restaurantService.findRestaurantById(166).getName());
    }

    @Test
    public void findRestaurantByName() {
    }

    @Test
    public void delete() {
        restaurantService.delete(12);
        assertEquals(2, restaurantService.findAll().size());
    }

    @Test
    public void save() {
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
    public void update() {
    }
}