package com.supermarket.model;

import com.supermarket.constants.EnhancedPricingConstants;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 增强的购买记录类
 * 使用BigDecimal确保计算精度，支持灵活的水果配置
 * 
 * @author SupermarketPricingSystem
 * @version 2.0
 */
public class EnhancedPurchase {
    
    /** 水果数量映射表，键为水果类型，值为购买数量（斤） */
    private final Map<EnhancedFruit, Integer> fruitQuantities;
    
    /**
     * 构造函数，创建空的购买记录
     */
    public EnhancedPurchase() {
        this.fruitQuantities = new HashMap<>();
    }
    
    /**
     * 添加水果到购买记录
     * 如果该水果已存在，则累加数量
     * 
     * @param fruitType 水果类型，不能为null
     * @param quantityToAdd 要添加的数量（斤），必须大于等于0
     * @throws IllegalArgumentException 如果数量为负数或水果类型为null
     */
    public void addFruit(final EnhancedFruit fruitType, final int quantityToAdd) {
        validateQuantity(quantityToAdd);
        validateFruitType(fruitType);
        
        final int currentQuantity = fruitQuantities.getOrDefault(fruitType, 0);
        fruitQuantities.put(fruitType, currentQuantity + quantityToAdd);
    }
    
    /**
     * 设置特定水果的数量
     * 如果数量为0，则从购买记录中移除该水果
     * 
     * @param fruitType 水果类型，不能为null
     * @param quantityToSet 要设置的数量（斤），必须大于等于0
     * @throws IllegalArgumentException 如果数量为负数或水果类型为null
     */
    public void setFruitQuantity(final EnhancedFruit fruitType, final int quantityToSet) {
        validateQuantity(quantityToSet);
        validateFruitType(fruitType);
        
        if (quantityToSet == 0) {
            fruitQuantities.remove(fruitType);
        } else {
            fruitQuantities.put(fruitType, quantityToSet);
        }
    }
    
    /**
     * 获取特定水果的数量
     * 
     * @param fruitType 水果类型
     * @return 数量（斤），如果没有购买该水果则返回0
     */
    public int getFruitQuantity(final EnhancedFruit fruitType) {
        return fruitQuantities.getOrDefault(fruitType, 0);
    }
    
    /**
     * 获取所有购买的水果类型
     * 
     * @return 水果类型集合的只读视图
     */
    public Set<EnhancedFruit> getPurchasedFruitTypes() {
        return fruitQuantities.keySet();
    }
    
    /**
     * 检查是否为空购买记录
     * 
     * @return 如果没有购买任何水果返回true，否则返回false
     */
    public boolean isEmpty() {
        return fruitQuantities.isEmpty();
    }
    
    /**
     * 获取购买记录的副本
     * 
     * @return 数量映射的不可变副本
     */
    public Map<EnhancedFruit, Integer> getFruitQuantitiesMap() {
        return new HashMap<>(fruitQuantities);
    }
    
    /**
     * 计算购买记录的基础总价（不含任何折扣）
     * 
     * @return 基础总价
     */
    public BigDecimal calculateBaseTotal() {
        BigDecimal total = BigDecimal.ZERO;
        
        for (Map.Entry<EnhancedFruit, Integer> entry : fruitQuantities.entrySet()) {
            final EnhancedFruit fruit = entry.getKey();
            final int quantity = entry.getValue();
            final BigDecimal subtotal = fruit.calculateSubtotal(quantity);
            total = total.add(subtotal);
        }
        
        return total.setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES, 
                            EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
    }
    
    /**
     * 根据折扣配置计算总价
     * 
     * @param discountConfig 折扣配置
     * @return 折扣后总价
     */
    public BigDecimal calculateTotalWithDiscount(final DiscountConfig discountConfig) {
        BigDecimal total = BigDecimal.ZERO;
        
        for (Map.Entry<EnhancedFruit, Integer> entry : fruitQuantities.entrySet()) {
            final EnhancedFruit fruit = entry.getKey();
            final int quantity = entry.getValue();
            final BigDecimal discountRate = discountConfig.getDiscountRate(fruit);
            final BigDecimal subtotal = fruit.calculateDiscountedSubtotal(quantity, discountRate);
            total = total.add(subtotal);
        }
        
        return total.setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES, 
                            EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
    }
    
    /**
     * 验证数量的有效性
     * 
     * @param quantity 要验证的数量
     * @throws IllegalArgumentException 如果数量为负数
     */
    private void validateQuantity(final int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("水果数量不能为负数: " + quantity);
        }
    }
    
    /**
     * 验证水果类型的有效性
     * 
     * @param fruitType 要验证的水果类型
     * @throws IllegalArgumentException 如果水果类型为null
     */
    private void validateFruitType(final EnhancedFruit fruitType) {
        if (fruitType == null) {
            throw new IllegalArgumentException("水果类型不能为空");
        }
    }
    
    /**
     * 返回购买记录的字符串表示
     * 
     * @return 购买记录的格式化字符串
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "空购买记录";
        }
        
        final StringBuilder purchaseDescription = new StringBuilder("购买记录: ");
        for (final Map.Entry<EnhancedFruit, Integer> entry : fruitQuantities.entrySet()) {
            purchaseDescription.append(entry.getKey().getChineseName())
                              .append(" ")
                              .append(entry.getValue())
                              .append("斤, ");
        }
        
        // 移除最后的逗号和空格
        final int descriptionLength = purchaseDescription.length();
        if (descriptionLength > 2) {
            purchaseDescription.setLength(descriptionLength - 2);
        }
        
        return purchaseDescription.toString();
    }
}