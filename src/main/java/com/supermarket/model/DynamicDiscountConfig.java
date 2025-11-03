package com.supermarket.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 动态折扣配置类
 * 支持基于商品ID的灵活折扣配置，完全解决扩展性问题
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
public class DynamicDiscountConfig {
    
    /** 商品折扣率映射表，键为商品ID，值为折扣率 */
    private final Map<String, BigDecimal> productDiscountRates;
    
    /** 配置描述 */
    private final String description;
    
    /**
     * 构造函数
     * 
     * @param description 折扣配置的描述
     */
    public DynamicDiscountConfig(final String description) {
        this.productDiscountRates = new HashMap<>();
        this.description = description;
    }
    
    /**
     * 添加商品折扣配置
     * 
     * @param productId 商品ID，不能为null或空字符串
     * @param discountRate 折扣率（如0.8表示8折），必须在0到1之间
     * @return 当前配置对象，支持链式调用
     * @throws IllegalArgumentException 如果参数无效
     */
    public DynamicDiscountConfig addProductDiscount(final String productId, final BigDecimal discountRate) {
        validateProductId(productId);
        validateDiscountRate(discountRate);
        
        productDiscountRates.put(productId, discountRate);
        return this;
    }
    
    /**
     * 添加商品折扣配置（使用double值）
     * 
     * @param productId 商品ID
     * @param discountRate 折扣率（如0.8表示8折）
     * @return 当前配置对象，支持链式调用
     */
    public DynamicDiscountConfig addProductDiscount(final String productId, final double discountRate) {
        return addProductDiscount(productId, BigDecimal.valueOf(discountRate));
    }
    
    /**
     * 批量添加商品折扣配置
     * 
     * @param discountRates 折扣率映射表
     * @return 当前配置对象，支持链式调用
     */
    public DynamicDiscountConfig addProductDiscounts(final Map<String, BigDecimal> discountRates) {
        if (discountRates != null) {
            for (final Map.Entry<String, BigDecimal> entry : discountRates.entrySet()) {
                addProductDiscount(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }
    
    /**
     * 获取指定商品的折扣率
     * 
     * @param productId 商品ID
     * @return 折扣率，如果没有配置则返回1.0（无折扣）
     */
    public BigDecimal getDiscountRate(final String productId) {
        return productDiscountRates.getOrDefault(productId, BigDecimal.ONE);
    }
    
    /**
     * 检查指定商品是否有折扣
     * 
     * @param productId 商品ID
     * @return 如果有折扣返回true，否则返回false
     */
    public boolean hasDiscount(final String productId) {
        return productDiscountRates.containsKey(productId);
    }
    
    /**
     * 获取所有有折扣的商品ID
     * 
     * @return 有折扣的商品ID集合
     */
    public Set<String> getDiscountedProductIds() {
        return productDiscountRates.keySet();
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
        productDiscountRates.clear();
    }
    
    /**
     * 移除指定商品的折扣配置
     * 
     * @param productId 要移除折扣的商品ID
     * @return 被移除的折扣率，如果不存在则返回null
     */
    public BigDecimal removeProductDiscount(final String productId) {
        return productDiscountRates.remove(productId);
    }
    
    /**
     * 获取所有折扣配置的副本
     * 
     * @return 折扣配置映射的不可变副本
     */
    public Map<String, BigDecimal> getAllDiscountRates() {
        return new HashMap<>(productDiscountRates);
    }
    
    /**
     * 检查是否有任何折扣配置
     * 
     * @return 如果有折扣配置返回true，否则返回false
     */
    public boolean hasAnyDiscounts() {
        return !productDiscountRates.isEmpty();
    }
    
    /**
     * 获取折扣商品数量
     * 
     * @return 有折扣的商品数量
     */
    public int getDiscountedProductCount() {
        return productDiscountRates.size();
    }
    
    /**
     * 验证商品ID的有效性
     */
    private void validateProductId(final String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
    }
    
    /**
     * 验证折扣率的有效性
     */
    private void validateDiscountRate(final BigDecimal discountRate) {
        if (discountRate == null || discountRate.compareTo(BigDecimal.ZERO) <= 0 || 
            discountRate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("折扣率必须在0到1之间: " + discountRate);
        }
    }
    
    /**
     * 创建预定义折扣配置的工厂方法
     */
    public static DynamicDiscountConfig createStrawberryPromotion() {
        return new DynamicDiscountConfig("草莓促销")
            .addProductDiscount("STRAWBERRY", new BigDecimal("0.8"));
    }
    
    /**
     * 创建多水果促销配置的工厂方法
     */
    public static DynamicDiscountConfig createMultiFruitPromotion() {
        return new DynamicDiscountConfig("多水果促销")
            .addProductDiscount("STRAWBERRY", new BigDecimal("0.8"))
            .addProductDiscount("MANGO", new BigDecimal("0.9"));
    }
    
    /**
     * 创建黑色星期五促销配置的工厂方法
     */
    public static DynamicDiscountConfig createBlackFridayPromotion() {
        return new DynamicDiscountConfig("黑色星期五大促销")
            .addProductDiscount("APPLE", new BigDecimal("0.5"))
            .addProductDiscount("STRAWBERRY", new BigDecimal("0.6"))
            .addProductDiscount("MANGO", new BigDecimal("0.7"))
            .addProductDiscount("ORANGE", new BigDecimal("0.55"))
            .addProductDiscount("BANANA", new BigDecimal("0.4"));
    }
    
    /**
     * 创建会员专享折扣配置的工厂方法
     */
    public static DynamicDiscountConfig createMemberExclusivePromotion() {
        return new DynamicDiscountConfig("会员专享折扣")
            .addProductDiscount("APPLE", new BigDecimal("0.85"))
            .addProductDiscount("STRAWBERRY", new BigDecimal("0.85"))
            .addProductDiscount("MANGO", new BigDecimal("0.85"));
    }
    
    @Override
    public String toString() {
        if (productDiscountRates.isEmpty()) {
            return description + " - 无折扣";
        }
        
        final StringBuilder sb = new StringBuilder(description + " - ");
        for (final Map.Entry<String, BigDecimal> entry : productDiscountRates.entrySet()) {
            final String productId = entry.getKey();
            final BigDecimal rate = entry.getValue();
            final int discountPercent = rate.multiply(BigDecimal.valueOf(10)).intValue();
            
            sb.append(productId)
              .append(":")
              .append(discountPercent)
              .append("折, ");
        }
        
        // 移除最后的逗号和空格
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        
        return sb.toString();
    }
}