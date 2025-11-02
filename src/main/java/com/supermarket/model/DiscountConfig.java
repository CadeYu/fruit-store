package com.supermarket.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 折扣配置类
 * 支持灵活配置不同水果的折扣率，支持多种水果同时打折
 * 
 * @author SupermarketPricingSystem
 * @version 2.0
 */
public class DiscountConfig {
    
    /** 水果折扣率映射表 */
    private final Map<EnhancedFruit, BigDecimal> fruitDiscountRates;
    
    /** 配置描述 */
    private final String description;
    
    /**
     * 构造函数
     * 
     * @param description 折扣配置的描述
     */
    public DiscountConfig(final String description) {
        this.fruitDiscountRates = new HashMap<>();
        this.description = description;
    }
    
    /**
     * 添加水果折扣配置
     * 
     * @param fruit 水果类型
     * @param discountRate 折扣率（如0.8表示8折）
     * @return 当前配置对象，支持链式调用
     */
    public DiscountConfig addFruitDiscount(final EnhancedFruit fruit, final BigDecimal discountRate) {
        if (fruit == null) {
            throw new IllegalArgumentException("水果类型不能为空");
        }
        if (discountRate == null || discountRate.compareTo(BigDecimal.ZERO) <= 0 || 
            discountRate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("折扣率必须在0到1之间");
        }
        
        fruitDiscountRates.put(fruit, discountRate);
        return this;
    }
    
    /**
     * 获取指定水果的折扣率
     * 
     * @param fruit 水果类型
     * @return 折扣率，如果没有配置则返回1.0（无折扣）
     */
    public BigDecimal getDiscountRate(final EnhancedFruit fruit) {
        return fruitDiscountRates.getOrDefault(fruit, BigDecimal.ONE);
    }
    
    /**
     * 检查指定水果是否有折扣
     * 
     * @param fruit 水果类型
     * @return 如果有折扣返回true，否则返回false
     */
    public boolean hasDiscount(final EnhancedFruit fruit) {
        return fruitDiscountRates.containsKey(fruit);
    }
    
    /**
     * 获取所有有折扣的水果类型
     * 
     * @return 有折扣的水果类型集合
     */
    public Set<EnhancedFruit> getDiscountedFruits() {
        return fruitDiscountRates.keySet();
    }
    
    /**
     * 获取配置描述
     * 
     * @return 配置描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 清除所有折扣配置
     */
    public void clearAllDiscounts() {
        fruitDiscountRates.clear();
    }
    
    /**
     * 移除指定水果的折扣配置
     * 
     * @param fruit 要移除折扣的水果类型
     */
    public void removeFruitDiscount(final EnhancedFruit fruit) {
        fruitDiscountRates.remove(fruit);
    }
    
    @Override
    public String toString() {
        if (fruitDiscountRates.isEmpty()) {
            return description + " - 无折扣";
        }
        
        StringBuilder sb = new StringBuilder(description + " - ");
        for (Map.Entry<EnhancedFruit, BigDecimal> entry : fruitDiscountRates.entrySet()) {
            sb.append(entry.getKey().getChineseName())
              .append(":")
              .append(entry.getValue().multiply(BigDecimal.valueOf(10)).intValue())
              .append("折, ");
        }
        
        // 移除最后的逗号和空格
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        
        return sb.toString();
    }
}