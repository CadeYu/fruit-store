package com.supermarket.rule;

import com.supermarket.model.DynamicDiscountConfig;
import com.supermarket.model.DynamicPurchase;
import java.math.BigDecimal;

/**
 * 动态定价规则接口
 * 支持完全动态的商品和折扣配置，彻底解决扩展性问题
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
public interface DynamicPricingRule {
    
    /**
     * 计算购买记录的总费用
     * 
     * @param purchaseRecord 购买记录，包含各种商品及其数量，不能为null
     * @return 总费用（元），使用BigDecimal确保精度
     * @throws IllegalArgumentException 如果购买记录为null
     */
    BigDecimal calculateTotalAmount(DynamicPurchase purchaseRecord);
    
    /**
     * 获取定价规则的描述信息
     * 
     * @return 规则描述，不能为null
     */
    default String getRuleDescription() {
        return "动态定价规则";
    }
    
    /**
     * 检查规则是否适用于指定的购买记录
     * 
     * @param purchaseRecord 购买记录
     * @return 如果规则适用返回true，否则返回false
     */
    default boolean isApplicable(final DynamicPurchase purchaseRecord) {
        return purchaseRecord != null && !purchaseRecord.isEmpty();
    }
}