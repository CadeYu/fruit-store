package com.supermarket;

import com.supermarket.constants.PricingConstants;
import com.supermarket.service.PricingService;

/**
 * 改进的超市定价系统主应用程序
 * 演示四种不同顾客场景的计算结果并验证准确性
 * 使用英文命名和常量，包含边界值测试
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public class ImprovedSupermarketPricingApplication {
    
    /** 定价服务实例 */
    private static final PricingService PRICING_SERVICE = new PricingService();
    
    /**
     * 主方法
     * 
     * @param args 命令行参数
     */
    public static void main(final String[] args) {
        System.out.println("=== Improved Supermarket Pricing System Demo ===\n");
        
        // 演示和验证四种顾客场景
        demonstrateStandardCustomer();
        demonstrateExtendedCustomer();
        demonstratePromotionalCustomer();
        demonstrateBulkCustomer();
        
        // 边界条件测试
        demonstrateBoundaryConditions();
        
        System.out.println("=== Demo Completed Successfully ===");
    }
    
    /**
     * 演示标准顾客场景：苹果和草莓的标准定价
     */
    private static void demonstrateStandardCustomer() {
        System.out.println("【Standard Customer】Apple: " + PricingConstants.FruitPrices.APPLE_PRICE + 
                          " yuan/jin, Strawberry: " + PricingConstants.FruitPrices.STRAWBERRY_PRICE + " yuan/jin");
        
        // 测试用例1：苹果2斤，草莓1斤
        final int apples1 = 2;
        final int strawberries1 = 1;
        final double total1 = PRICING_SERVICE.calculateStandardCustomerTotal(apples1, strawberries1);
        final double expected1 = apples1 * PricingConstants.FruitPrices.APPLE_PRICE + 
                               strawberries1 * PricingConstants.FruitPrices.STRAWBERRY_PRICE;
        System.out.printf("Apple %d jin + Strawberry %d jin = %.2f yuan (Expected: %.2f yuan) %s\n", 
                         apples1, strawberries1, total1, expected1, 
                         isCalculationCorrect(total1, expected1) ? "✓" : "✗");
        
        // 测试用例2：苹果3斤，草莓2斤
        final int apples2 = 3;
        final int strawberries2 = 2;
        final double total2 = PRICING_SERVICE.calculateStandardCustomerTotal(apples2, strawberries2);
        final double expected2 = apples2 * PricingConstants.FruitPrices.APPLE_PRICE + 
                               strawberries2 * PricingConstants.FruitPrices.STRAWBERRY_PRICE;
        System.out.printf("Apple %d jin + Strawberry %d jin = %.2f yuan (Expected: %.2f yuan) %s\n", 
                         apples2, strawberries2, total2, expected2, 
                         isCalculationCorrect(total2, expected2) ? "✓" : "✗");
        
        System.out.println();
    }
    
    /**
     * 演示扩展顾客场景：苹果、草莓和芒果的标准定价
     */
    private static void demonstrateExtendedCustomer() {
        System.out.println("【Extended Customer】Apple: " + PricingConstants.FruitPrices.APPLE_PRICE + 
                          " yuan/jin, Strawberry: " + PricingConstants.FruitPrices.STRAWBERRY_PRICE + 
                          " yuan/jin, Mango: " + PricingConstants.FruitPrices.MANGO_PRICE + " yuan/jin");
        
        // 测试用例1：苹果1斤，草莓1斤，芒果1斤
        final int apples1 = 1;
        final int strawberries1 = 1;
        final int mangoes1 = 1;
        final double total1 = PRICING_SERVICE.calculateExtendedCustomerTotal(apples1, strawberries1, mangoes1);
        final double expected1 = apples1 * PricingConstants.FruitPrices.APPLE_PRICE + 
                               strawberries1 * PricingConstants.FruitPrices.STRAWBERRY_PRICE + 
                               mangoes1 * PricingConstants.FruitPrices.MANGO_PRICE;
        System.out.printf("Apple %d jin + Strawberry %d jin + Mango %d jin = %.2f yuan (Expected: %.2f yuan) %s\n", 
                         apples1, strawberries1, mangoes1, total1, expected1, 
                         isCalculationCorrect(total1, expected1) ? "✓" : "✗");
        
        // 测试用例2：苹果2斤，草莓3斤，芒果1斤
        final int apples2 = 2;
        final int strawberries2 = 3;
        final int mangoes2 = 1;
        final double total2 = PRICING_SERVICE.calculateExtendedCustomerTotal(apples2, strawberries2, mangoes2);
        final double expected2 = apples2 * PricingConstants.FruitPrices.APPLE_PRICE + 
                               strawberries2 * PricingConstants.FruitPrices.STRAWBERRY_PRICE + 
                               mangoes2 * PricingConstants.FruitPrices.MANGO_PRICE;
        System.out.printf("Apple %d jin + Strawberry %d jin + Mango %d jin = %.2f yuan (Expected: %.2f yuan) %s\n", 
                         apples2, strawberries2, mangoes2, total2, expected2, 
                         isCalculationCorrect(total2, expected2) ? "✓" : "✗");
        
        System.out.println();
    }
    
    /**
     * 演示促销顾客场景：草莓8折优惠
     */
    private static void demonstratePromotionalCustomer() {
        final double discountedStrawberryPrice = PricingConstants.FruitPrices.STRAWBERRY_PRICE * 
                                               PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE;
        System.out.println("【Promotional Customer】Apple: " + PricingConstants.FruitPrices.APPLE_PRICE + 
                          " yuan/jin, Strawberry: " + discountedStrawberryPrice + 
                          " yuan/jin (20% off), Mango: " + PricingConstants.FruitPrices.MANGO_PRICE + " yuan/jin");
        
        // 测试用例1：苹果1斤，草莓2斤，芒果1斤
        final int apples1 = 1;
        final int strawberries1 = 2;
        final int mangoes1 = 1;
        final double total1 = PRICING_SERVICE.calculatePromotionalCustomerTotal(apples1, strawberries1, mangoes1);
        final double expected1 = apples1 * PricingConstants.FruitPrices.APPLE_PRICE + 
                               strawberries1 * discountedStrawberryPrice + 
                               mangoes1 * PricingConstants.FruitPrices.MANGO_PRICE;
        System.out.printf("Apple %d jin + Strawberry %d jin + Mango %d jin = %.2f yuan (Expected: %.2f yuan) %s\n", 
                         apples1, strawberries1, mangoes1, total1, expected1, 
                         isCalculationCorrect(total1, expected1) ? "✓" : "✗");
        
        // 测试用例2：苹果3斤，草莓4斤，芒果2斤
        final int apples2 = 3;
        final int strawberries2 = 4;
        final int mangoes2 = 2;
        final double total2 = PRICING_SERVICE.calculatePromotionalCustomerTotal(apples2, strawberries2, mangoes2);
        final double expected2 = apples2 * PricingConstants.FruitPrices.APPLE_PRICE + 
                               strawberries2 * discountedStrawberryPrice + 
                               mangoes2 * PricingConstants.FruitPrices.MANGO_PRICE;
        System.out.printf("Apple %d jin + Strawberry %d jin + Mango %d jin = %.2f yuan (Expected: %.2f yuan) %s\n", 
                         apples2, strawberries2, mangoes2, total2, expected2, 
                         isCalculationCorrect(total2, expected2) ? "✓" : "✗");
        
        System.out.println();
    }
    
    /**
     * 演示批量顾客场景：草莓8折 + 满100减10
     */
    private static void demonstrateBulkCustomer() {
        final double discountedStrawberryPrice = PricingConstants.FruitPrices.STRAWBERRY_PRICE * 
                                               PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE;
        System.out.println("【Bulk Customer】Apple: " + PricingConstants.FruitPrices.APPLE_PRICE + 
                          " yuan/jin, Strawberry: " + discountedStrawberryPrice + 
                          " yuan/jin (20% off), Mango: " + PricingConstants.FruitPrices.MANGO_PRICE + 
                          " yuan/jin, " + (int)PricingConstants.BulkDiscount.DISCOUNT_AMOUNT + 
                          " yuan off when total >= " + (int)PricingConstants.BulkDiscount.THRESHOLD_AMOUNT + " yuan");
        
        // 测试用例1：小计未满100元
        final int apples1 = 2;
        final int strawberries1 = 2;
        final int mangoes1 = 1;
        final double total1 = PRICING_SERVICE.calculateBulkCustomerTotal(apples1, strawberries1, mangoes1);
        final double subtotal1 = apples1 * PricingConstants.FruitPrices.APPLE_PRICE + 
                                strawberries1 * discountedStrawberryPrice + 
                                mangoes1 * PricingConstants.FruitPrices.MANGO_PRICE;
        final double expected1 = subtotal1; // 未满100，不减10
        System.out.printf("Apple %d jin + Strawberry %d jin + Mango %d jin = %.2f yuan (Subtotal: %.2f yuan, Expected: %.2f yuan) %s\n", 
                         apples1, strawberries1, mangoes1, total1, subtotal1, expected1, 
                         isCalculationCorrect(total1, expected1) ? "✓" : "✗");
        
        // 测试用例2：小计刚好100元
        final int apples2 = 0;
        final int strawberries2 = 0;
        final int mangoes2 = 5;
        final double total2 = PRICING_SERVICE.calculateBulkCustomerTotal(apples2, strawberries2, mangoes2);
        final double subtotal2 = mangoes2 * PricingConstants.FruitPrices.MANGO_PRICE; // 5 * 20 = 100
        final double expected2 = subtotal2 - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT; // 100 - 10 = 90
        System.out.printf("Apple %d jin + Strawberry %d jin + Mango %d jin = %.2f yuan (Subtotal: %.2f yuan, Expected: %.2f yuan) %s\n", 
                         apples2, strawberries2, mangoes2, total2, subtotal2, expected2, 
                         isCalculationCorrect(total2, expected2) ? "✓" : "✗");
        
        // 测试用例3：小计超过100元
        final int apples3 = 3;
        final int strawberries3 = 4;
        final int mangoes3 = 2;
        final double total3 = PRICING_SERVICE.calculateBulkCustomerTotal(apples3, strawberries3, mangoes3);
        final double subtotal3 = apples3 * PricingConstants.FruitPrices.APPLE_PRICE + 
                                strawberries3 * discountedStrawberryPrice + 
                                mangoes3 * PricingConstants.FruitPrices.MANGO_PRICE;
        final double expected3 = subtotal3 - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT; // 满100减10
        System.out.printf("Apple %d jin + Strawberry %d jin + Mango %d jin = %.2f yuan (Subtotal: %.2f yuan, Expected: %.2f yuan) %s\n", 
                         apples3, strawberries3, mangoes3, total3, subtotal3, expected3, 
                         isCalculationCorrect(total3, expected3) ? "✓" : "✗");
        
        System.out.println();
    }
    
    /**
     * 演示边界条件测试
     */
    private static void demonstrateBoundaryConditions() {
        System.out.println("【Boundary Condition Tests】");
        
        // 零数量测试
        final double zeroTotal = PRICING_SERVICE.calculateStandardCustomerTotal(0, 0);
        System.out.printf("Zero quantity test: %.2f yuan (Expected: 0.00 yuan) %s\n", 
                         zeroTotal, isCalculationCorrect(zeroTotal, 0.0) ? "✓" : "✗");
        
        // 批量折扣边界测试 - 99.2元（不满100）
        final double boundaryTotal1 = PRICING_SERVICE.calculateBulkCustomerTotal(6, 3, 1);
        final double boundarySubtotal1 = 6 * PricingConstants.FruitPrices.APPLE_PRICE + 
                                       3 * PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE + 
                                       1 * PricingConstants.FruitPrices.MANGO_PRICE; // 99.2
        final double boundaryExpected1 = boundarySubtotal1; // 不减10
        System.out.printf("Bulk discount boundary test (< 100): %.2f yuan (Subtotal: %.2f yuan, Expected: %.2f yuan) %s\n", 
                         boundaryTotal1, boundarySubtotal1, boundaryExpected1, 
                         isCalculationCorrect(boundaryTotal1, boundaryExpected1) ? "✓" : "✗");
        
        // 批量折扣边界测试 - 刚好100元
        final double boundaryTotal2 = PRICING_SERVICE.calculateBulkCustomerTotal(0, 0, 5);
        final double boundarySubtotal2 = 5 * PricingConstants.FruitPrices.MANGO_PRICE; // 100.0
        final double boundaryExpected2 = boundarySubtotal2 - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT; // 90.0
        System.out.printf("Bulk discount boundary test (= 100): %.2f yuan (Subtotal: %.2f yuan, Expected: %.2f yuan) %s\n", 
                         boundaryTotal2, boundarySubtotal2, boundaryExpected2, 
                         isCalculationCorrect(boundaryTotal2, boundaryExpected2) ? "✓" : "✗");
        
        // 批量折扣边界测试 - 107.2元（超过100）
        final double boundaryTotal3 = PRICING_SERVICE.calculateBulkCustomerTotal(7, 3, 1);
        final double boundarySubtotal3 = 7 * PricingConstants.FruitPrices.APPLE_PRICE + 
                                       3 * PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE + 
                                       1 * PricingConstants.FruitPrices.MANGO_PRICE; // 107.2
        final double boundaryExpected3 = boundarySubtotal3 - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT; // 97.2
        System.out.printf("Bulk discount boundary test (> 100): %.2f yuan (Subtotal: %.2f yuan, Expected: %.2f yuan) %s\n", 
                         boundaryTotal3, boundarySubtotal3, boundaryExpected3, 
                         isCalculationCorrect(boundaryTotal3, boundaryExpected3) ? "✓" : "✗");
        
        System.out.println();
    }
    
    /**
     * 检查计算结果是否正确
     * 
     * @param actual 实际值
     * @param expected 期望值
     * @return 如果在精度范围内相等返回true，否则返回false
     */
    private static boolean isCalculationCorrect(final double actual, final double expected) {
        return Math.abs(actual - expected) < PricingConstants.Precision.CURRENCY_PRECISION;
    }
}