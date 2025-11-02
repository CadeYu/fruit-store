package com.supermarket.model;

import com.supermarket.constants.EnhancedPricingConstants;
import java.math.BigDecimal;

/**
 * 增强的水果枚举类
 * 支持BigDecimal精确计算和灵活的价格配置
 * 
 * @author SupermarketPricingSystem
 * @version 2.0
 */
public enum EnhancedFruit {
    /**
     * 苹果 - Apple
     */
    APPLE(EnhancedPricingConstants.FruitPrices.APPLE_PRICE, "Apple", "苹果"),
    
    /**
     * 草莓 - Strawberry
     */
    STRAWBERRY(EnhancedPricingConstants.FruitPrices.STRAWBERRY_PRICE, "Strawberry", "草莓"),
    
    /**
     * 芒果 - Mango
     */
    MANGO(EnhancedPricingConstants.FruitPrices.MANGO_PRICE, "Mango", "芒果");
    
    /** 水果基础价格（元/斤） */
    private final BigDecimal basePrice;
    
    /** 水果英文名称 */
    private final String englishName;
    
    /** 水果中文名称 */
    private final String chineseName;
    
    /**
     * 构造函数
     * 
     * @param basePrice 水果的基础价格（元/斤）
     * @param englishName 水果的英文名称
     * @param chineseName 水果的中文名称
     */
    EnhancedFruit(final BigDecimal basePrice, final String englishName, final String chineseName) {
        this.basePrice = basePrice;
        this.englishName = englishName;
        this.chineseName = chineseName;
    }
    
    /**
     * 获取水果的基础价格
     * 
     * @return 价格（元/斤）
     */
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    
    /**
     * 获取水果的英文名称
     * 
     * @return 英文名称
     */
    public String getEnglishName() {
        return englishName;
    }
    
    /**
     * 获取水果的中文名称
     * 
     * @return 中文名称
     */
    public String getChineseName() {
        return chineseName;
    }
    
    /**
     * 获取水果的显示名称（英文名称）
     * 
     * @return 显示名称
     */
    public String getDisplayName() {
        return englishName;
    }
    
    /**
     * 计算指定数量的水果总价
     * 
     * @param quantity 数量（斤）
     * @return 总价
     */
    public BigDecimal calculateSubtotal(final int quantity) {
        return basePrice.multiply(BigDecimal.valueOf(quantity));
    }
    
    /**
     * 计算指定数量和折扣率的水果总价
     * 
     * @param quantity 数量（斤）
     * @param discountRate 折扣率（如0.8表示8折）
     * @return 折扣后总价
     */
    public BigDecimal calculateDiscountedSubtotal(final int quantity, final BigDecimal discountRate) {
        return basePrice.multiply(BigDecimal.valueOf(quantity))
                       .multiply(discountRate)
                       .setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES, 
                               EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
    }
}