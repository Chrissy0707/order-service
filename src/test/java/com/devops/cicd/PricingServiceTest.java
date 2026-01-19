package com.devops.cicd;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private final PricingConfig fakeConfig = new PricingConfig(20.0, 50.0);
    private final PricingService service = new PricingService(fakeConfig);

    @Test
    void applyVat_shouldAdd20Percent() {
        double result = service.applyVat(100.0);
        assertEquals(120.0, result, 0.01);
    }

    @Test
    void applyVipDiscount_shouldApply10PercentForVip() {
        double result = service.applyVipDiscount(100.0, true);
        assertEquals(90.0, result, 0.01);
    }

    @Test
    void applyVipDiscount_shouldNotApplyDiscountForNonVip() {
        double result = service.applyVipDiscount(100.0, false);
        assertEquals(100.0, result, 0.01);
    }

    @Test
    void shippingCost_shouldBeFreeAboveThreshold() {
        double result = service.shippingCost(50.0);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void shippingCost_shouldBe499BelowThreshold() {
        double result = service.shippingCost(49.99);
        assertEquals(4.99, result, 0.01);
    }

    @Test
    void finalTotal_withVipAndFreeShipping() {
        // 100 HT -> 120 TTC -> 108 avec remise VIP -> 108 + 0 livraison = 108
        double result = service.finalTotal(100.0, true);
        assertEquals(108.0, result, 0.01);
    }

    @Test
    void finalTotal_withoutVipAndWithShipping() {
        // 30 HT -> 36 TTC -> 36 sans remise -> 36 + 4.99 livraison = 40.99
        double result = service.finalTotal(30.0, false);
        assertEquals(40.99, result, 0.01);
    }

    @Test
    void finalTotal_withVipAndShipping() {
        // 30 HT -> 36 TTC -> 32.4 avec remise VIP -> 32.4 + 4.99 livraison = 37.39
        double result = service.finalTotal(30.0, true);
        assertEquals(37.39, result, 0.01);
    }
}