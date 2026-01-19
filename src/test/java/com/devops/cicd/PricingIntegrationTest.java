package com.devops.cicd;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingIntegrationTest {

    @Test
    void fullPricingFlow_withRealConfigFile() {
        // Chargement de la configuration réelle depuis le fichier
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Vérification que la configuration a été chargée correctement
        assertEquals(20.0, config.getVatRate(), 0.01);
        assertEquals(50.0, config.getFreeShippingThreshold(), 0.01);

        // Instanciation du service avec la configuration réelle
        PricingService service = new PricingService(config);

        // Scénario métier complet :
        // montant HT = 100
        // TVA = 20% -> 120 TTC
        // client VIP -> remise 10% -> 108
        // livraison gratuite (>= 50) -> 0
        // Total final = 108
        double result = service.finalTotal(100.0, true);
        assertEquals(108.0, result, 0.01);
    }

    @Test
    void fullPricingFlow_withShippingCost() {
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();
        PricingService service = new PricingService(config);

        // Scénario avec frais de livraison :
        // montant HT = 30
        // TVA = 20% -> 36 TTC
        // pas VIP -> pas de remise -> 36
        // frais de livraison (< 50) -> 4.99
        // Total final = 40.99
        double result = service.finalTotal(30.0, false);
        assertEquals(40.99, result, 0.01);
    }
}