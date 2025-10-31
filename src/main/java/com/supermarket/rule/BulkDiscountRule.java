package com.supermarket.rule;

import com.supermarket.constants.PricingConstants;
import com.supermarket.model.Purchase;

/**
 * 批量折扣定价规则实现类
 * 使用装饰器模式，在基础定价规则上应用批量折扣
 * 当总金额达到阈值时，减免固定金额
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public class BulkDiscountRule implements PricingRule {
    
    /** 被装饰的基础定价规则 */
    private final PricingRule basePricingRule;
    
    /**
     * 构造函数
     * 
     * @param basePricingRule 基础定价规则，不能为null
     * @throws IllegalArgumentException 如果基础规则为null
     */
    public BulkDiscountRule(final PricingRule basePricingRule) {
        validateBasePricingRule(basePricingRule);
        this.basePricingRule = basePricingRule;
    }
    
    /**
     * 计算购买记录的总费用
     * 先使用基础规则计算小计，然后根据批量折扣条件决定是否减免
     * 
     * @param purchaseRecord 购买记录，不能为null
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果购买记录为null
     */
    @Override
    public double calculateTotalAmount(final Purchase purchaseRecord) {
        validatePurchaseRecord(purchaseRecord);
        
        // 先使用基础规则计算小计
        final double subtotalAmount = basePricingRule.calculateTotalAmount(purchaseRecord);
        
        // 如果小计满足批量折扣条件，则减免相应金额
        if (isEligibleForBulkDiscount(subtotalAmount)) {
            return subtotalAmount - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT;
        }
        
        return subtotalAmount;
    }
    
    /**
     * 获取定价规则的描述信息
     * 
     * @return 规则描述
     */
    @Override
    public String getRuleDescription() {
        return String.format("批量折扣规则 - %s，满%s元减%s元", 
                           basePricingRule.getRuleDescription(),
                           (int) PricingConstants.BulkDiscount.THRESHOLD_AMOUNT,
                           (int) PricingConstants.BulkDiscount.DISCOUNT_AMOUNT);
    }
    
    /**
     * 获取批量折扣阈值
     * 
     * @return 阈值金额（元）
     */
    public double getBulkDiscountThreshold() {
        return PricingConstants.BulkDiscount.THRESHOLD_AMOUNT;
    }
    
    /**
     * 获取批量折扣金额
     * 
     * @return 折扣金额（元）
     */
    public double getBulkDiscountAmount() {
        return PricingConstants.BulkDiscount.DISCOUNT_AMOUNT;
    }
    
    /**
     * 获取基础定价规则
     * 
     * @return 基础规则
     */
    public PricingRule getBasePricingRule() {
        return basePricingRule;
    }
    
    /**
     * 判断是否符合批量折扣条件
     * 
     * @param subtotalAmount 小计金额
     * @return 如果符合条件返回true，否则返回false
     */
    private boolean isEligibleForBulkDiscount(final double subtotalAmount) {
        return subtotalAmount >= PricingConstants.BulkDiscount.THRESHOLD_AMOUNT;
    }
    
    /**
     * 验证基础定价规则的有效性
     * 
     * @param basePricingRule 要验证的基础定价规则
     * @throws IllegalArgumentException 如果基础规则为null
     */
    private void validateBasePricingRule(final PricingRule basePricingRule) {
        if (basePricingRule == null) {
            throw new IllegalArgumentException("基础定价规则不能为空");
        }
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