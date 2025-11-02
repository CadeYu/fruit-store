package com.supermarket.rule;

import com.supermarket.model.EnhancedPurchase;
import java.math.BigDecimal;

/**
 * 增强的定价规则接口
 * 使用BigDecimal确保计算精度，支持灵活的定价策略
 * 
 * @author SupermarketPricingSystem
 * @version 2.0
 */
public interface EnhancedPricingRule {
    
    /**
     * 计算购买记录的总费用
     * 
     * @param purchaseRecord 购买记录，包含各种水果及其数量，不能为null
     * @return 总费用（元），使用BigDecimal确保精度
     * @throws IllegalArgumentException 如果购买记录为null
     */
    BigDecimal calculateTotalAmount(EnhancedPurchase purchaseRecord);
    
    /**
     * 获取定价规则的描述信息
     * 
     * @return 规则描述，不能为null
     */
    default String getRuleDescription() {
        return "基础定价规则";
    }
    
    /**
     * 检查规则是否适用于指定的购买记录
     * 
     * @param purchaseRecord 购买记录
     * @return 如果规则适用返回true，否则返回false
     */
    default boolean isApplicable(final EnhancedPurchase purchaseRecord) {
        return purchaseRecord != null && !purchaseRecord.isEmpty();
    }
}