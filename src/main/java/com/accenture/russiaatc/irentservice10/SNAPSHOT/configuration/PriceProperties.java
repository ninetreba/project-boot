package com.accenture.russiaatc.irentservice10.SNAPSHOT.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@Getter
@ConfigurationProperties(prefix = "price")
public class PriceProperties {
    private  BigDecimal startingPriceBicycle;
    private  BigDecimal startingPriceElectricScooter;
    private  BigDecimal pricePerMinBicycle;
    private  BigDecimal pricePerMinElectricScooter;

//    public PriceProperties(BigDecimal startingPriceBicycle, BigDecimal startingPriceElectricScooter, BigDecimal pricePerMinBicycle, BigDecimal pricePerMinElectricScooter) {
//        this.startingPriceBicycle = startingPriceBicycle;
//        this.startingPriceElectricScooter = startingPriceElectricScooter;
//        this.pricePerMinBicycle = pricePerMinBicycle;
//        this.pricePerMinElectricScooter = pricePerMinElectricScooter;
//    }
}
