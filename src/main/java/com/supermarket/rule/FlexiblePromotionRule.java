package com.supermarket.rule;

import com.supermarket.constants.EnhancedPricingConstants;
import com.supermarket.model.DiscountConfig;
import com.supermarket.model.EnhancedPurchase;
import java.math.BigDecimal;

/**
 * 灵活的促销定价规则实现类
 * 支持多种水果同时打折，可灵活配置不同的折扣组合
 * 
 * @author SupermarketPricingSystem
 * @version 2.0
 */
public class FlexiblePromotionRule implements EnhancedPricingRule {
    
    /** 折扣配置 */
    private final DiscountConfig discountConfig;
    
    /**
     * 构造函数
     * 
     * @param discountConfig 折扣配置，不能为null
     * @throws IllegalArgumentException 如果折扣配置为null
     */
    public FlexiblePromotionRule(final DiscountConfig discountConfig) {
        if (discountConfig == null) {
            throw new IllegalArgumentException("折扣配置不能为空");
        }
        this.discountConfig = discountConfig;
    }
    
    /**
     * 计算购买记录的总费用
     * 根据配置的折扣规则对相应水果应用折扣
     * 
     * @param purchaseRecord 购买记录，不能为null
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果购买记录为null
     */
    @Override
    public BigDecimal calculateTotalAmount(final EnhancedPurchase purchaseRecord) {
        validatePurchaseRecord(purchaseRecord);
        
        return purchaseRecord.calculateTotalWithDiscount(discountConfig);
    }
    
    /**
     * 获取定价规则的描述信息
     * 
     * @return 规则描述
     */
    @Override
    public String getRuleDescription() {
        return "灵活促销规则 - " + discountConfig.getDescription();
    }
    
    /**
     * 获取折扣配置
     * 
     * @return 当前的折扣配置
     */
    public DiscountConfig getDiscountConfig() {
        return discountConfig;
    }
    
    /**
     * 验证购买记录的有效性
     * 
     * @param purchaseRecord 要验证的购买记录
     * @throws IllegalArgumentException 如果购买记录为null
     */
    private void validatePurchaseRecord(final EnhancedPurchase purchaseRecord) {
        if (purchaseRecord == null) {
            throw new IllegalArgumentException("购买记录不能为空");
        }
    }
}