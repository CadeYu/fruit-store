package com.supermarket.rule;

import com.supermarket.model.Fruit;
import com.supermarket.model.Purchase;

/**
 * 标准定价规则实现类
 * 按照水果的基础价格计算总费用，不应用任何折扣
 * 实现策略模式中的具体策略
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public class StandardPricingRule implements PricingRule {
    
    /**
     * 计算购买记录的总费用
     * 使用每种水果的基础价格进行计算
     * 
     * @param purchaseRecord 购买记录，不能为null
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果购买记录为null
     */
    @Override
    public double calculateTotalAmount(final Purchase purchaseRecord) {
        validatePurchaseRecord(purchaseRecord);
        
        double totalAmount = 0.0;
        
        // 遍历所有购买的水果，按基础价格计算
        for (final Fruit fruitType : purchaseRecord.getPurchasedFruitTypes()) {
            final int quantity = purchaseRecord.getFruitQuantity(fruitType);
            final double fruitSubtotal = fruitType.getBasePrice() * quantity;
            totalAmount += fruitSubtotal;
        }
        
        return totalAmount;
    }
    
    /**
     * 获取定价规则的描述信息
     * 
     * @return 规则描述
     */
    @Override
    public String getRuleDescription() {
        return "标准定价规则 - 按基础价格计算，无任何折扣";
    }
    
    /**
     * 验证购买记录的有效性
     * 
     * @param purchaseRecord 要验证的购买记录
     * @throws IllegalArgumentException 如果购买记录为null
     */
    private void validatePurchaseRecord(final Purchase purchaseRecord) {
        if (purchaseRecord == null) {
            throw new IllegalArgumentException("购买记录不能为空");
        }
    }
}