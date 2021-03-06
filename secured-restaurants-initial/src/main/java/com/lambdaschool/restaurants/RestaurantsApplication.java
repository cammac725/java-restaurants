package com.lambdaschool.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start the application.
 */

@SpringBootApplication
public class RestaurantsApplication
{
    /**
     * Main method to start the application.
     *
     * @param args Not used in this application.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(RestaurantsApplication.class,
            args);
    }
}
