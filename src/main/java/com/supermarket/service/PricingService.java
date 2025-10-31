package com.supermarket.service;

import com.supermarket.constants.PricingConstants;
import com.supermarket.model.Fruit;
import com.supermarket.model.Purchase;
import com.supermarket.rule.BulkDiscountRule;
import com.supermarket.rule.PricingRule;
import com.supermarket.rule.StandardPricingRule;
import com.supermarket.rule.StrawberryPromotionRule;

/**
 * 定价服务类
 * 协调定价计算的主要服务类，为不同顾客场景提供计算方法
 * 使用策略模式管理不同的定价规则
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public class PricingService {
    
    /** 标准定价规则 */
    private final PricingRule standardPricingRule;
    
    /** 草莓促销定价规则 */
    private final PricingRule strawberryPromotionPricingRule;
    
    /**
     * 构造函数，初始化定价规则
     */
    public PricingService() {
        this.standardPricingRule = new StandardPricingRule();
        this.strawberryPromotionPricingRule = new StrawberryPromotionRule();
    }
    
    /**
     * 计算标准顾客的总费用
     * 标准顾客购买苹果和草莓，使用标准定价（无折扣）
     * 
     * @param appleQuantity 苹果数量（斤），必须大于等于0
     * @param strawberryQuantity 草莓数量（斤），必须大于等于0
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果数量为负数
     */
    public double calculateStandardCustomerTotal(final int appleQuantity, final int strawberryQuantity) {
        validateQuantity(appleQuantity, "苹果");
        validateQuantity(strawberryQuantity, "草莓");
        
        final Purchase purchaseRecord = createPurchaseRecord();
        purchaseRecord.setFruitQuantity(Fruit.APPLE, appleQuantity);
        purchaseRecord.setFruitQuantity(Fruit.STRAWBERRY, strawberryQuantity);
        
        return standardPricingRule.calculateTotalAmount(purchaseRecord);
    }
    
    /**
     * 计算扩展顾客的总费用
     * 扩展顾客购买苹果、草莓和芒果，使用标准定价（无折扣）
     * 
     * @param appleQuantity 苹果数量（斤），必须大于等于0
     * @param strawberryQuantity 草莓数量（斤），必须大于等于0
     * @param mangoQuantity 芒果数量（斤），必须大于等于0
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果数量为负数
     */
    public double calculateExtendedCustomerTotal(final int appleQuantity, final int strawberryQuantity, final int mangoQuantity) {
        validateQuantity(appleQuantity, "苹果");
        validateQuantity(strawberryQuantity, "草莓");
        validateQuantity(mangoQuantity, "芒果");
        
        final Purchase purchaseRecord = createPurchaseRecord();
        purchaseRecord.setFruitQuantity(Fruit.APPLE, appleQuantity);
        purchaseRecord.setFruitQuantity(Fruit.STRAWBERRY, strawberryQuantity);
        purchaseRecord.setFruitQuantity(Fruit.MANGO, mangoQuantity);
        
        return standardPricingRule.calculateTotalAmount(purchaseRecord);
    }
    
    /**
     * 计算促销顾客的总费用
     * 促销顾客购买苹果、草莓和芒果，草莓享受8折优惠
     * 
     * @param appleQuantity 苹果数量（斤），必须大于等于0
     * @param strawberryQuantity 草莓数量（斤），必须大于等于0
     * @param mangoQuantity 芒果数量（斤），必须大于等于0
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果数量为负数
     */
    public double calculatePromotionalCustomerTotal(final int appleQuantity, final int strawberryQuantity, final int mangoQuantity) {
        validateQuantity(appleQuantity, "苹果");
        validateQuantity(strawberryQuantity, "草莓");
        validateQuantity(mangoQuantity, "芒果");
        
        final Purchase purchaseRecord = createPurchaseRecord();
        purchaseRecord.setFruitQuantity(Fruit.APPLE, appleQuantity);
        purchaseRecord.setFruitQuantity(Fruit.STRAWBERRY, strawberryQuantity);
        purchaseRecord.setFruitQuantity(Fruit.MANGO, mangoQuantity);
        
        return strawberryPromotionPricingRule.calculateTotalAmount(purchaseRecord);
    }
    
    /**
     * 计算批量顾客的总费用
     * 批量顾客购买苹果、草莓和芒果，草莓享受8折优惠，且满100元减10元
     * 
     * @param appleQuantity 苹果数量（斤），必须大于等于0
     * @param strawberryQuantity 草莓数量（斤），必须大于等于0
     * @param mangoQuantity 芒果数量（斤），必须大于等于0
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果数量为负数
     */
    public double calculateBulkCustomerTotal(final int appleQuantity, final int strawberryQuantity, final int mangoQuantity) {
        validateQuantity(appleQuantity, "苹果");
        validateQuantity(strawberryQuantity, "草莓");
        validateQuantity(mangoQuantity, "芒果");
        
        final Purchase purchaseRecord = createPurchaseRecord();
        purchaseRecord.setFruitQuantity(Fruit.APPLE, appleQuantity);
        purchaseRecord.setFruitQuantity(Fruit.STRAWBERRY, strawberryQuantity);
        purchaseRecord.setFruitQuantity(Fruit.MANGO, mangoQuantity);
        
        // 使用批量折扣规则包装草莓促销规则（装饰器模式）
        final PricingRule bulkDiscountPricingRule = new BulkDiscountRule(strawberryPromotionPricingRule);
        return bulkDiscountPricingRule.calculateTotalAmount(purchaseRecord);
    }
    
    /**
     * 通用计算方法，使用指定的定价规则
     * 
     * @param purchaseRecord 购买记录，不能为null
     * @param pricingRule 定价规则，不能为null
     * @return 总费用（元）
     * @throws IllegalArgumentException 如果购买记录或定价规则为null
     */
    public double calculateWithCustomRule(final Purchase purchaseRecord, final PricingRule pricingRule) {
        validatePurchaseRecord(purchaseRecord);
        validatePricingRule(pricingRule);
        
        return pricingRule.calculateTotalAmount(purchaseRecord);
    }
    
    /**
     * 创建新的购买记录实例
     * 
     * @return 新的购买记录
     */
    private Purchase createPurchaseRecord() {
        return new Purchase();
    }
    
    /**
     * 验证数量的有效性
     * 
     * @param quantity 数量
     * @param fruitName 水果名称
     * @throws IllegalArgumentException 如果数量为负数
     */
    private void validateQuantity(final int quantity, final String fruitName) {
        if (quantity < 0) {
            throw new IllegalArgumentException(fruitName + "数量不能为负数: " + quantity);
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
    
    /**
     * 验证定价规则的有效性
     * 
     * @param pricingRule 要验证的定价规则
     * @throws IllegalArgumentException 如果定价规则为null
     */
    private void validatePricingRule(final PricingRule pricingRule) {
        if (pricingRule == null) {
            throw new IllegalArgumentException("定价规则不能为空");
        }
    }
    
    /**
     * 获取标准定价规则
     * 
     * @return 标准定价规则
     */
    public PricingRule getStandardPricingRule() {
        return standardPricingRule;
    }
    
    /**
     * 获取草莓促销定价规则
     * 
     * @return 草莓促销定价规则
     */
    public PricingRule getStrawberryPromotionPricingRule() {
        return strawberryPromotionPricingRule;
    }
}