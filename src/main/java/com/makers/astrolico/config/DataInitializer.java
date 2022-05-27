package com.makers.astrolico.config;

import com.makers.astrolico.entity.Planet;
import com.makers.astrolico.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
        final int initialCount = 100;

        List<Planet> planets = repository.findAll();

        int planetCount = planets.size();
        if (planetCount >= initialCount) return;

        List<Planet> newPlanets = getPlanets(initialCount - planetCount);

        if (newPlanets == null) return;

        planets.addAll(newPlanets);

        repository.saveAll(planets);
    }

    private List<Planet> getPlanets(int count) {
        String baseUrl = "https://api.nasa.gov/planetary/apod";
        String key = "";

        if (key == "") return null;

        String requestUrl = baseUrl + "?api_key=" + key + "&count=" + count;

        RestTemplate restTemplate = new RestTemplate();

        return Arrays.asList(restTemplate.getForObject(requestUrl, Planet[].class));
    }
}
