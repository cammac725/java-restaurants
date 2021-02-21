package com.lambdaschool.restaurants.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import com.lambdaschool.restaurants.services.RestaurantService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestaurantControllerUnitTest {

    // controller goes to and endpoint and MockMvc let's me go there
    @Autowired
    private MockMvc mockMvc;

    // we never call the RestaurantService directly, we mock it
    @MockBean
    private RestaurantService restaurantService;

    // bypass the database
    private List<Restaurant> restaurantList;

    @Before
    public void setUp() throws Exception {
        restaurantList = new ArrayList<>();

        Payment payType1 = new Payment("Credit Card");
        Payment payType2 = new Payment("Cash");
        Payment payType3 = new Payment("Bitcoin");

        payType1.setPaymentid(1);
        payType2.setPaymentid(2);
        payType3.setPaymentid(3);

        // Restaurant String name, String address, String city, String state, String telephone
        String rest1Name = "Test Apple";
        Restaurant rest1 = new Restaurant(rest1Name,
                "123 Main Street",
                "City",
                "ST",
                "555-555-1234");
        rest1.setRestaurantid(10);

        rest1.getPayments().add(new RestaurantPayments(rest1, payType1));
        rest1.getPayments().add(new RestaurantPayments(rest1, payType2));
        rest1.getPayments().add(new RestaurantPayments(rest1, payType3));

        rest1.getMenus().add(new Menu("Mac and Cheese", 6.95, rest1));
        rest1.getMenus().get(0).setMenuId(11);
        rest1.getMenus().add(new Menu("Lasagna", 8.50, rest1));
        rest1.getMenus().get(1).setMenuId(12);
        rest1.getMenus().add(new Menu("Meatloaf", 7.77, rest1));
        rest1.getMenus().get(2).setMenuId(13);
        rest1.getMenus().add(new Menu("Tacos", 8.49, rest1));
        rest1.getMenus().get(3).setMenuId(14);
        rest1.getMenus().add(new Menu("Chef Salad", 12.50, rest1));
        rest1.getMenus().get(4).setMenuId(15);

        restaurantList.add(rest1);

        String rest2Name = "Test Eagle Cafe";
        Restaurant rest2 = new Restaurant(rest2Name,
                "321 Uptown Drive",
                "Town",
                "ST",
                "555-555-5555");
        rest2.setRestaurantid(20);
        rest2.getPayments().add(new RestaurantPayments(rest2, payType2));

        rest2.getMenus().add(new Menu("Tacos", 10.49, rest2));
        rest1.getMenus().get(0).setMenuId(21);
        rest2.getMenus().add(new Menu("Barbacoa", 12.75, rest2));
        rest1.getMenus().get(1).setMenuId(22);

        restaurantList.add(rest2);

        String rest3Name = "Test Number 1 Eats";
        Restaurant rest3 = new Restaurant(rest3Name,
                "565 Side Avenue",
                "Village",
                "ST",
                "555-123-1555");
        rest3.setRestaurantid(30);
        rest3.getPayments().add(new RestaurantPayments(rest3, payType1));
        rest3.getPayments().add(new RestaurantPayments(rest3, payType3));
        rest3.getMenus().add(new Menu("Pizza", 15.15, rest3));
        rest1.getMenus().get(0).setMenuId(31);

        restaurantList.add(rest3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllRestaurants() throws Exception {
        String apiUrl = "/restaurants/restaurants";
        Mockito.when(restaurantService.findAll()).thenReturn(restaurantList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList);

        System.out.println(er);
        assertEquals(er, tr);
    }

    @Test
    public void getRestaurantById() {
    }

    @Test
    public void getRestaurantByIdNotFound() throws Exception {
        String apiUrl = "/restaurants/restaurant/100";
        Mockito.when(restaurantService.findRestaurantById(100)).thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        String er = "";

        System.out.println(er);
        assertEquals(er, tr);
    }

    @Test
    public void getRestaurantByName() {
    }

    @Test
    public void listRestaurantNameCity() {
    }

    @Test
    public void listRestaurantNameLike() {
    }

    @Test
    public void addNewRestaurant() throws Exception {
        String apiUrl = "/restaurants/restaurant";

        // create new restaurant to add for test
        String rest3Name = "Test Good Eats";
        Restaurant rest3 = new Restaurant(rest3Name,
                "565 Side Avenue",
                "Village",
                "ST",
                "555-123-1555");
        rest3.setRestaurantid(40);

        Payment payType1 = new Payment("Pay1");
        payType1.setPaymentid(9);

        rest3.getPayments().add(new RestaurantPayments(rest3, payType1));
        rest3.getMenus().add(new Menu("Pizza", 15.15, rest3));
        rest3.getMenus().get(0).setMenuId(41);

        ObjectMapper mapper = new ObjectMapper();
        String restaurantString = mapper.writeValueAsString(rest3);

        Mockito.when(restaurantService.save(any(Restaurant.class)))
                .thenReturn(rest3);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantString);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateRestaurant() {
    }

    @Test
    public void deleteRestaurantById() {
    }
}