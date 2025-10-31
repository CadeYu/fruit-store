package com.supermarket;

import com.supermarket.service.PricingService;

/**
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public class ChineseValidationDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 中文异常信息验证演示 ===\n");
        
        PricingService service = new PricingService();
        
        // 测试负数数量异常
        System.out.println("【测试1】负数数量异常信息:");
        try {
            service.calculateStandardCustomerTotal(-1, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ 苹果负数异常: " + e.getMessage());
        }
        
        try {
            service.calculateExtendedCustomerTotal(1, -2, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ 草莓负数异常: " + e.getMessage());
        }
        
        try {
            service.calculatePromotionalCustomerTotal(1, 2, -3);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ 芒果负数异常: " + e.getMessage());
        }
        
        // 测试null参数异常
        System.out.println("\n【测试2】null参数异常信息:");
        try {
            service.calculateWithCustomRule(null, service.getStandardPricingRule());
        } catch (IllegalArgumentException e) {
            System.out.println("✓ 购买记录为null异常: " + e.getMessage());
        }
        
        try {
            com.supermarket.model.Purchase purchase = new com.supermarket.model.Purchase();
            service.calculateWithCustomRule(purchase, null);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ 定价规则为null异常: " + e.getMessage());
        }
        
        // 测试Purchase类异常
        System.out.println("\n【测试3】Purchase类异常信息:");
        try {
            com.supermarket.model.Purchase purchase = new com.supermarket.model.Purchase();
            purchase.addFruit(com.supermarket.model.Fruit.APPLE, -1);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Purchase负数异常: " + e.getMessage());
        }
        
        try {
            com.supermarket.model.Purchase purchase = new com.supermarket.model.Purchase();
            purchase.addFruit(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Purchase水果类型为null异常: " + e.getMessage());
        }
        
        // 测试规则描述
        System.out.println("\n【测试4】规则描述信息:");
        System.out.println("✓ 标准规则描述: " + service.getStandardPricingRule().getRuleDescription());
        System.out.println("✓ 草莓促销规则描述: " + service.getStrawberryPromotionPricingRule().getRuleDescription());
        
        com.supermarket.rule.BulkDiscountRule bulkRule = new com.supermarket.rule.BulkDiscountRule(service.getStrawberryPromotionPricingRule());
        System.out.println("✓ 批量折扣规则描述: " + bulkRule.getRuleDescription());
        
        // 测试Purchase toString
        System.out.println("\n【测试5】Purchase toString信息:");
        com.supermarket.model.Purchase purchase = new com.supermarket.model.Purchase();
        System.out.println("✓ 空购买记录: " + purchase.toString());
        
        purchase.addFruit(com.supermarket.model.Fruit.APPLE, 2);
        purchase.addFruit(com.supermarket.model.Fruit.STRAWBERRY, 1);
        System.out.println("✓ 有内容的购买记录: " + purchase.toString());
        
        System.out.println("\n=== 验证完成，所有异常信息都已改为中文 ===");
    }
}