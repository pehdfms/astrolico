package com.makers.astrolico.config;

import com.makers.astrolico.entity.Planet;
import com.makers.astrolico.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {
    private PlanetRepository repository;

    @Autowired
    public DataInitializer(PlanetRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Initializes 100 planets if there aren't enough saved
        final int maxCount = 100;

        List<Planet> planets = repository.findAll();

        int planetCount = planets.size();
        if (planetCount >= maxCount) return;

        List<Planet> newPlanets = getPlanets(maxCount - planetCount);

        if (newPlanets == null) return;

        planets.addAll(newPlanets);

        try {
            repository.saveAll(planets);
        } catch (Exception ignored) {}
    }

    private List<Planet> getPlanets(int count) {
        List<Planet> newPlanets = new ArrayList<>();
        if (count <= 0) return newPlanets;

        final String baseUrl = "https://api.nasa.gov/planetary/apod";
        final String key = "";

        if (key == "") return newPlanets;

        RestTemplate restTemplate = new RestTemplate();
        while (count > 0) {
            int requestCount = Math.min(count, 100);
            String requestUrl = baseUrl + "?api_key=" + key + "&count=" + requestCount;

            try {
                for (Planet newPlanet : restTemplate.getForObject(requestUrl, Planet[].class)) {
                    newPlanets.add(newPlanet);
                }
            } catch (Exception e) {
                System.out.println(e);
                return newPlanets;
            }
            count -= requestCount;
        }

        return newPlanets;
    }
}
