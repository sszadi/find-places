package com.fb;

import com.fb.service.FacebookService;
import com.restfb.*;
import com.restfb.types.Place;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacebookPlacesApplicationTests {

    @Autowired
    FacebookService facebookService;
    private String country;
    private String city;


    @Test
    public void findByCountryAndCityTest() {
        country = "Poland";
        city = "Poznan";

        List<Place> result = facebookService.findByCountryAndCity(country, city, getConnectionResult());

        Assert.assertEquals(result.get(0).getLocation().getCountry(), country);
        Assert.assertEquals(result.get(0).getLocation().getCity(), city);

    }

    @Test
    public void findWithNoExistTest() {
        country = "United Kingdom";
        city = "London";
        List<Place> result = facebookService.findByCountryAndCity(country, city, getConnectionResult());
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void findWitOneCorrectTest() {
        country = "Poland";
        city = "London";
        List<Place> result = facebookService.findByCountryAndCity(country, city, getConnectionResult());
        Assert.assertTrue(result.isEmpty());
    }

    public List<Place> getConnectionResult() {

        FacebookClient facebookClient = new DefaultFacebookClient("accessToken",


                new DefaultWebRequestor() {
                    @Override
                    public Response executeGet(String url) throws IOException {
                        return new Response(HttpURLConnection.HTTP_OK,
                                "{\"id\": 123,\"name\": \"cafe\", \"location\": { \"country\": \"Poland\", \"city\": \"Poznan\"}}");

                    }
                }, new DefaultJsonMapper(), Version.LATEST);
        Place place = facebookClient.fetchObject("ignored", Place.class);

        return Arrays.asList(place);

    }

}
