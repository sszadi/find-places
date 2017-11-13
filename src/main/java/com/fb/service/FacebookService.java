package com.fb.service;

import com.restfb.Connection;
import com.restfb.types.Place;

import java.util.List;


public interface FacebookService {

    List<Place> searchPlaces(String country, String city, String name);

    List<Place> findByCountryAndCity(String country, String city, List<Place> publicSearch);
}
