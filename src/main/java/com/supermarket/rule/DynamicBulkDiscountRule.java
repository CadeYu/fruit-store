package com.supermarket.rule;

import com.supermarket.constants.EnhancedPricingConstants;
import com.supermarket.model.DynamicPurchase;
import java.math.BigDecimal;

/**
 * 动态批量折扣定价规则实现类
 * 使用装饰器模式，在基础定价规则上应用批量折扣
 * 支持完全动态的商品配置和BigDecimal精确计算
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
public class DynamicBulkDiscountRule implements DynamicPricingRule {
    
    /** 被装饰的基础定价规则 */
    private final DynamicPricingRule basePricingRule;
    
    /** 批量折扣阈值 */
    private final BigDecimal thresholdAmount;
    
    /** 批量折扣金额 */
    private final BigDecimal discountAmount;
    
    /**
     * 构造函数（使用默认阈值和折扣金额）
     * 
     * @param basePricingRule 基础定价规则，不能为null
     * @throws IllegalArgumentException 如果基础规则为null
     */
    public DynamicBulkDiscountRule(final DynamicPricingRule basePricingRule) {
        this(basePricingRule, 
             EnhancedPricingConstants.BulkDiscount.THRESHOLD_AMOUNT,
             EnhancedPricingConstants.BulkDiscount.DISCOUNT_AMOUNT);
    }
    
    /**
     * 构造函数（自定义阈值和折扣金额）
     * 
     * @param basePricingRule 基础定价规则，不能为null
     * @param thresholdAmount 批量折扣阈值，不能为null且必须大于0
     * @param discountAmount 批量折扣金额，不能为null且必须大于0
     * @throws IllegalArgumentException 如果参数无效
     */
    public DynamicBulkDiscountRule(final DynamicPricingRule basePricingRule,
                                  final BigDecimal thresholdAmount,
                                  final BigDecimal discountAmount) {
        validateBasePricingRule(basePricingRule);
        validateThresholdAmount(thresholdAmount);
        validateDiscountAmount(discountAmount);
        
        this.basePricingRule = basePricingRule;
        this.thresholdAmount = thresholdAmount;
        this.discountAmount = discountAmount;
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
    public BigDecimal calculateTotalAmount(final DynamicPurchase purchaseRecord) {
        validatePurchaseRecord(purchaseRecord);
        
        // 先使用基础规则计算小计
        final BigDecimal subtotalAmount = basePricingRule.calculateTotalAmount(purchaseRecord);
        
        // 如果小计满足批量折扣条件，则减免相应金额
        if (isEligibleForBulkDiscount(subtotalAmount)) {
            return subtotalAmount.subtract(discountAmount)
                                .setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES,
                                        EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
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
        return String.format("动态批量折扣规则 - %s，满%s元减%s元", 
                           basePricingRule.getRuleDescription(),
                           thresholdAmount.toPlainString(),
                           discountAmount.toPlainString());
    }
    
    /**
     * 获取批量折扣阈值
     * 
     * @return 阈值金额（元）
     */
    public BigDecimal getBulkDiscountThreshold() {
        return thresholdAmount;
    }
    
    /**
     * 获取批量折扣金额
     * 
     * @return 折扣金额（元）
     */
    public BigDecimal getBulkDiscountAmount() {
        return discountAmount;
    }
    
    /**
     * 获取基础定价规则
     * 
     * @return 基础规则
     */
    public DynamicPricingRule getBasePricingRule() {
        return basePricingRule;
    }
    
    /**
     * 判断是否符合批量折扣条件
     * 
     * @param subtotalAmount 小计金额
     * @return 如果符合条件返回true，否则返回false
     */
    private boolean isEligibleForBulkDiscount(final BigDecimal subtotalAmount) {
        return subtotalAmount.compareTo(thresholdAmount) >= 0;
    }
    
    /**
     * 验证基础定价规则的有效性
     */
    private void validateBasePricingRule(final DynamicPricingRule basePricingRule) {
        if (basePricingRule == null) {
            throw new IllegalArgumentException("基础定价规则不能为空");
        }
    }
    
    /**
     * 验证阈值金额的有效性
     */
    private void validateThresholdAmount(final BigDecimal thresholdAmount) {
        if (thresholdAmount == null || thresholdAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("批量折扣阈值必须大于0");
        }
    }
    
    /**
     * 验证折扣金额的有效性
     */
    private void validateDiscountAmount(final BigDecimal discountAmount) {
        if (discountAmount == null || discountAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("批量折扣金额必须大于0");
        }
    }
    
    /**
     * 验证购买记录的有效性
     */
    private void validatePurchaseRecord(final DynamicPurchase purchaseRecord) {
        if (purchaseRecord == null) {
            throw new IllegalArgumentException("购买记录不能为空");
        }
    }
}