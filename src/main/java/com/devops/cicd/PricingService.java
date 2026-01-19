package com.devops.cicd;

public final class PricingService {

    private final PricingConfig config;

    public PricingService(PricingConfig config) {
        this.config = config;
    }

    public double applyVat(double amountExclVat) {
        return amountExclVat * (1 + config.getVatRate() / 100.0);
    }

    public double applyVipDiscount(double amount, boolean vip) {
        if (vip) {
            return amount * 0.9; // 10% de remise
        }
        return amount;
    }

    public double shippingCost(double amount) {
        if (amount >= config.getFreeShippingThreshold()) {
            return 0.0;
        }
        return 4.99;
    }

    /**
     * - TVA appliquée d'abord : HT -> TTC
     * - remise VIP appliquée sur TTC
     * - frais de livraison ajoutés ensuite (calculés sur TTC)
     */
    public double finalTotal(double amountExclVat, boolean vip) {
        double withVat = applyVat(amountExclVat);
        double withDiscount = applyVipDiscount(withVat, vip);
        double shipping = shippingCost(withDiscount);
        return withDiscount + shipping;
    }
}