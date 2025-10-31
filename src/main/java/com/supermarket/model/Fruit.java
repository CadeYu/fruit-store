package com.supermarket.model;

import com.supermarket.constants.PricingConstants;

/**
 * 水果枚举类
 * 定义超市销售的水果类型及其基础价格
 * 使用常量类避免魔法数字，提供英文和中文名称映射
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public enum Fruit {
    /**
     * 苹果 - Apple
     */
    APPLE(PricingConstants.FruitPrices.APPLE_PRICE, "Apple", "苹果"),
    
    /**
     * 草莓 - Strawberry
     */
    STRAWBERRY(PricingConstants.FruitPrices.STRAWBERRY_PRICE, "Strawberry", "草莓"),
    
    /**
     * 芒果 - Mango
     */
    MANGO(PricingConstants.FruitPrices.MANGO_PRICE, "Mango", "芒果");
    
    /** 水果基础价格（元/斤） */
    private final double basePrice;
    
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
    Fruit(final double basePrice, final String englishName, final String chineseName) {
        this.basePrice = basePrice;
        this.englishName = englishName;
        this.chineseName = chineseName;
    }
    
    /**
     * 获取水果的基础价格
     * 
     * @return 价格（元/斤）
     */
    public double getBasePrice() {
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
}