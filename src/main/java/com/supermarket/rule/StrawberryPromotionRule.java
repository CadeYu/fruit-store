package com.supermarket.rule;

import com.supermarket.constants.PricingConstants;
import com.supermarket.model.Fruit;
import com.supermarket.model.Purchase;

/**
 * 草莓促销定价规则实现类
 * 草莓享受8折优惠，其他水果按基础价格计算
 * 实现策略模式中的具体策略
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public class StrawberryPromotionRule implements PricingRule {
    
    /**
     * 计算购买记录的总费用
     * 对草莓应用促销折扣，其他水果使用基础价格
     * 
     * @param purchaseRecord 购买记录，不能为null
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果购买记录为null
     */
    @Override
    public double calculateTotalAmount(final Purchase purchaseRecord) {
        validatePurchaseRecord(purchaseRecord);
        
        double totalAmount = 0.0;
        
        // 遍历所有购买的水果
        for (final Fruit fruitType : purchaseRecord.getPurchasedFruitTypes()) {
            final int quantity = purchaseRecord.getFruitQuantity(fruitType);
            final double effectivePrice = calculateEffectivePrice(fruitType);
            final double fruitSubtotal = effectivePrice * quantity;
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
        return "草莓促销规则 - 草莓享受8折优惠，其他水果按基础价格";
    }
    
    /**
     * 获取草莓的折扣率
     * 
     * @return 折扣率（0.8表示8折）
     */
    public double getStrawberryDiscountRate() {
        return PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE;
    }
    
    /**
     * 计算水果的有效价格
     * 如果是草莓则应用折扣，否则使用基础价格
     * 
     * @param fruitType 水果类型
     * @return 有效价格
     */
    private double calculateEffectivePrice(final Fruit fruitType) {
        if (fruitType == Fruit.STRAWBERRY) {
            return fruitType.getBasePrice() * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE;
        }
        return fruitType.getBasePrice();
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