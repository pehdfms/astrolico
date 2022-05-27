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
    @Autowired
    private PlanetRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Initializes 100 planets if there aren't enough saved
        final int initialCount = 5;

        List<Planet> planets = repository.findAll();

        int planetCount = planets.size();
        if (planetCount >= initialCount) return;

        List<Planet> newPlanets = getPlanets(initialCount - planetCount);
        System.out.println(newPlanets);

        planets.addAll(newPlanets);

        repository.saveAll(planets);
    }

    private List<Planet> getPlanets(int count) {
        final String url =
                "https://api.nasa.gov/planetary/apod?api_key=ruS8JIATjaE4pnYQbcA0sm2z45cpN2bchaEsV4GP&count=" + count;

        RestTemplate restTemplate = new RestTemplate();

        return Arrays.asList(restTemplate.getForObject(url, Planet[].class));
    }
}
