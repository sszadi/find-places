package com.fb.service;

import com.fb.exception.PlacesNotFoundException;
import com.restfb.*;
import com.restfb.types.Place;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Niki on 2017-05-12.
 */
@Service
@PropertySource("classpath:application.properties")
public class FacebookServiceImpl implements FacebookService {

    @Value("${facebook.accessToken}")
    private String accessToken;

    private FacebookClient facebookClient;

    public List<Place> searchPlaces(String country, String city, String name) {
        connect();

        Connection<Place> publicSearch =
                facebookClient.fetchConnection("search", Place.class,
                        Parameter.with("q", name), Parameter.with("fields", "name, location{city, country, latitude,longitude}"), Parameter.with("type", "place"));

        if (publicSearch == null) {
            throw new PlacesNotFoundException(country, city, name);
        }

        List<Place> result = findByCountryAndCity(country, city, publicSearch.getData());

        if (result.isEmpty()) {
            throw new PlacesNotFoundException(country, city, name);
        }

        return result;
    }

    public List<Place> findByCountryAndCity(String country, String city, List<Place> publicSearch) {
        List<Place> result = new ArrayList<>();

        if (!publicSearch.isEmpty()) {
            Predicate<Place> countryPredicate = place -> place.getLocation().getCountry() != null
                    && place.getLocation().getCountry().equals(country);

            Predicate<Place> cityPredicate = place -> place.getLocation().getCity() != null
                    && place.getLocation().getCity().equals(city);

            result = publicSearch.stream()
                    .filter(countryPredicate.and(cityPredicate))
                    .collect(Collectors.toList());
        }

        return result;
    }

    private void connect() {
        facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
    }


}
