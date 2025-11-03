package com.supermarket.model;

import com.supermarket.constants.EnhancedPricingConstants;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 动态商品类
 * 支持运行时动态创建商品，完全解决扩展性问题
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
public class Product {
    
    /** 商品唯一标识符 */
    private final String productId;
    
    /** 商品英文名称 */
    private final String englishName;
    
    /** 商品中文名称 */
    private final String chineseName;
    
    /** 商品基础价格（元/斤） */
    private final BigDecimal basePrice;
    
    /** 商品类别（可选） */
    private final String category;
    
    /**
     * 构造函数
     * 
     * @param productId 商品唯一标识符，不能为null或空字符串
     * @param englishName 商品英文名称，不能为null或空字符串
     * @param chineseName 商品中文名称，不能为null或空字符串
     * @param basePrice 商品基础价格，不能为null且必须大于0
     * @param category 商品类别，可以为null
     * @throws IllegalArgumentException 如果参数无效
     */
    public Product(final String productId, 
                   final String englishName, 
                   final String chineseName, 
                   final BigDecimal basePrice, 
                   final String category) {
        validateProductId(productId);
        validateEnglishName(englishName);
        validateChineseName(chineseName);
        validateBasePrice(basePrice);
        
        this.productId = productId;
        this.englishName = englishName;
        this.chineseName = chineseName;
        this.basePrice = basePrice.setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES,
                                          EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
        this.category = category;
    }
    
    /**
     * 简化构造函数（无类别）
     */
    public Product(final String productId, 
                   final String englishName, 
                   final String chineseName, 
                   final BigDecimal basePrice) {
        this(productId, englishName, chineseName, basePrice, null);
    }
    
    /**
     * 获取商品ID
     */
    public String getProductId() {
        return productId;
    }
    
    /**
     * 获取英文名称
     */
    public String getEnglishName() {
        return englishName;
    }
    
    /**
     * 获取中文名称
     */
    public String getChineseName() {
        return chineseName;
    }
    
    /**
     * 获取基础价格
     */
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    
    /**
     * 获取商品类别
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * 计算指定数量的商品总价
     * 
     * @param quantity 数量（斤）
     * @return 总价
     */
    public BigDecimal calculateSubtotal(final int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("商品数量不能为负数: " + quantity);
        }
        
        return basePrice.multiply(BigDecimal.valueOf(quantity))
                       .setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES,
                               EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
    }
    
    /**
     * 计算指定数量和折扣率的商品总价
     * 
     * @param quantity 数量（斤）
     * @param discountRate 折扣率（如0.8表示8折）
     * @return 折扣后总价
     */
    public BigDecimal calculateDiscountedSubtotal(final int quantity, final BigDecimal discountRate) {
        if (quantity < 0) {
            throw new IllegalArgumentException("商品数量不能为负数: " + quantity);
        }
        if (discountRate == null || discountRate.compareTo(BigDecimal.ZERO) <= 0 || 
            discountRate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("折扣率必须在0到1之间: " + discountRate);
        }
        
        return basePrice.multiply(BigDecimal.valueOf(quantity))
                       .multiply(discountRate)
                       .setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES,
                               EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
    }
    
    /**
     * 验证商品ID
     */
    private void validateProductId(final String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
    }
    
    /**
     * 验证英文名称
     */
    private void validateEnglishName(final String englishName) {
        if (englishName == null || englishName.trim().isEmpty()) {
            throw new IllegalArgumentException("商品英文名称不能为空");
        }
    }
    
    /**
     * 验证中文名称
     */
    private void validateChineseName(final String chineseName) {
        if (chineseName == null || chineseName.trim().isEmpty()) {
            throw new IllegalArgumentException("商品中文名称不能为空");
        }
    }
    
    /**
     * 验证基础价格
     */
    private void validateBasePrice(final BigDecimal basePrice) {
        if (basePrice == null || basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("商品价格必须大于0: " + basePrice);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(productId, product.productId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
    
    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s(%s)', price=%s, category='%s'}", 
                           productId, chineseName, englishName, basePrice, category);
    }
}