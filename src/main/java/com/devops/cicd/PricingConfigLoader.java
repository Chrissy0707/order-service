package com.devops.cicd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricingConfigLoader {

    public PricingConfig load() {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (in == null) {
                throw new IllegalStateException("Fichier app.properties introuvable");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de app.properties", e);
        }

        String vatRateStr = required(props, "vatRate");
        String thresholdStr = required(props, "freeShippingThreshold");

        double vatRate = Double.parseDouble(vatRateStr);
        double threshold = Double.parseDouble(thresholdStr);

        return new PricingConfig(vatRate, threshold);
    }

    private String required(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Propriété manquante : " + key);
        }
        return value.trim();
    }
}