package com.supermarket.rule;

import com.supermarket.model.Purchase;

/**
 * 定价规则接口
 * 定义不同定价策略的契约，使用策略模式实现不同的计费规则
 * 所有具体的定价规则都必须实现此接口
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public interface PricingRule {
    
    /**
     * 计算购买记录的总费用
     * 
     * @param purchaseRecord 购买记录，包含各种水果及其数量，不能为null
     * @return 总费用（元），必须大于等于0
     * @throws IllegalArgumentException 如果购买记录为null
     */
    double calculateTotalAmount(Purchase purchaseRecord);
    
    /**
     * 获取定价规则的描述信息
     * 
     * @return 规则描述，不能为null
     */
    default String getRuleDescription() {
        return "基础定价规则";
    }
}