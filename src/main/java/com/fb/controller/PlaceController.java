package com.fb.controller;

import com.fb.service.FacebookService;
import com.restfb.types.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class PlaceController {

    private final FacebookService facebookService;
    private Logger logger = Logger.getLogger(PlaceController.class.getName());


    @Autowired
    public PlaceController(FacebookService facebookService) {
        this.facebookService = facebookService;
    }


    @RequestMapping("{country}/{city}/{name}")
    public List<Place> getPlaces(@PathVariable("country") String country, @PathVariable("city") String city, @PathVariable("name") String name) {
        List<Place> result = facebookService.searchPlaces(country, city, name);
        logger.log(Level.INFO, "Searching for places: " + country + ", " + city + ", " + name);

        return result;

    }

}
