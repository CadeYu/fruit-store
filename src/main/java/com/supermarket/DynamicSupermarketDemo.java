package com.supermarket;

import com.supermarket.model.*;
import com.supermarket.rule.*;
import com.supermarket.service.ProductCatalog;
import java.math.BigDecimal;

/**
 * 动态超市定价系统演示程序
 * 展示完全动态的商品和折扣配置能力，彻底解决扩展性问题
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
public class DynamicSupermarketDemo {
    
    public static void main(String[] args) {
        System.out.println("动态超市定价系统演示 - 彻底解决扩展性问题 ===\n");
        
        // 演示1：动态添加新商品
//        demonstrateProductExtensibility();
        
        // 演示2：灵活的折扣配置
//        demonstrateDiscountFlexibility();
        
        // 演示3：复杂促销场景
//        demonstrateComplexPromotions();
        
        // 演示4：运行时商品管理
        demonstrateRuntimeManagement();
        
        System.out.println(" 演示完成：系统扩展性问题已彻底解决！ ===");
    }
    
    /**
     * 演示商品扩展性：动态添加新商品种类
     */
    private static void demonstrateProductExtensibility() {
        System.out.println("【演示1：动态商品扩展能力】");
        
        // 创建空的商品目录
        ProductCatalog catalog = new ProductCatalog();
        catalog.clearAllProducts(); // 清空默认商品，从零开始
        System.out.println("初始商品目录：" + catalog.getProductCount() + "种商品");
        
        // 动态添加各种新商品
        catalog.addProduct("DURIAN", "Durian", "榴莲", new BigDecimal("50.00"), "热带水果")
               .addProduct("DRAGON_FRUIT", "Dragon Fruit", "火龙果", new BigDecimal("25.00"), "热带水果")
               .addProduct("AVOCADO", "Avocado", "牛油果", new BigDecimal("35.00"), "热带水果")
               .addProduct("BLUEBERRY", "Blueberry", "蓝莓", new BigDecimal("45.00"), "浆果类")
               .addProduct("CHERRY", "Cherry", "樱桃", new BigDecimal("40.00"), "核果类");
        
        System.out.println("动态添加后：" + catalog.getProductCount() + "种商品");
        System.out.println(catalog);
        
        // 测试新商品购买
        DynamicPurchase purchase = new DynamicPurchase(catalog);
        purchase.addProduct("DURIAN", 1)
                .addProduct("DRAGON_FRUIT", 2)
                .addProduct("BLUEBERRY", 1);
        
        DynamicDiscountConfig noDiscount = new DynamicDiscountConfig("标准定价");
        DynamicPromotionRule rule = new DynamicPromotionRule(noDiscount);
        
        BigDecimal total = rule.calculateTotalAmount(purchase);
        System.out.println("购买记录：" + purchase.toString());
        System.out.println("总价：" + total + "元");
        System.out.println(" 新商品添加和购买测试成功！\n");
    }
    
    /**
     * 演示折扣配置的灵活性：任意商品任意折扣
     */
    private static void demonstrateDiscountFlexibility() {
        System.out.println("【演示2：灵活折扣配置能力】");
        
        // 使用扩展商品目录
        ProductCatalog catalog = ProductCatalog.createExtendedCatalog();
        
        // 场景1：单一商品促销
        System.out.println("场景1：草莓专享8折");
        DynamicDiscountConfig strawberryPromo = new DynamicDiscountConfig("草莓专享促销");
        strawberryPromo.addProductDiscount("STRAWBERRY", new BigDecimal("0.8"));
        
        testPromotionScenario(catalog, strawberryPromo, "STRAWBERRY", 5);
        
        // 场景2：多商品不同折扣
        System.out.println("场景2：多水果不同折扣");
        DynamicDiscountConfig multiPromo = new DynamicDiscountConfig("多水果促销");
        multiPromo.addProductDiscount("APPLE", new BigDecimal("0.9"))      // 苹果9折
                 .addProductDiscount("STRAWBERRY", new BigDecimal("0.8"))   // 草莓8折
                 .addProductDiscount("MANGO", new BigDecimal("0.85"))       // 芒果85折
                 .addProductDiscount("ORANGE", new BigDecimal("0.7"))       // 橙子7折
                 .addProductDiscount("BANANA", new BigDecimal("0.6"));      // 香蕉6折
        
        DynamicPurchase multiPurchase = new DynamicPurchase(catalog);
        multiPurchase.addProduct("APPLE", 2)
                    .addProduct("STRAWBERRY", 2)
                    .addProduct("MANGO", 1)
                    .addProduct("ORANGE", 3)
                    .addProduct("BANANA", 4);
        
        DynamicPromotionRule multiRule = new DynamicPromotionRule(multiPromo);
        BigDecimal multiTotal = multiRule.calculateTotalAmount(multiPurchase);
        
        System.out.println("促销配置：" + multiPromo.toString());
        System.out.println("购买记录：" + multiPurchase.toString());
        System.out.println("折扣后总价：" + multiTotal + "元");
        System.out.println(" 多商品灵活折扣测试成功！\n");
    }
    
    /**
     * 演示复杂促销场景：黑色星期五大促销
     */
    private static void demonstrateComplexPromotions() {
        System.out.println("【演示3：复杂促销场景 - 黑色星期五】");
        
        ProductCatalog catalog = ProductCatalog.createExtendedCatalog();
        
        // 黑色星期五大促销配置
        DynamicDiscountConfig blackFridayConfig = DynamicDiscountConfig.createBlackFridayPromotion();
        DynamicPromotionRule blackFridayRule = new DynamicPromotionRule(blackFridayConfig);
        
        // 添加批量折扣：满100元减15元
        DynamicBulkDiscountRule bulkRule = new DynamicBulkDiscountRule(
            blackFridayRule, 
            new BigDecimal("100.00"), 
            new BigDecimal("15.00")
        );
        
        // 模拟大量购买
        DynamicPurchase bigPurchase = new DynamicPurchase(catalog);
        bigPurchase.addProduct("APPLE", 10)      // 苹果10斤
                   .addProduct("STRAWBERRY", 8)  // 草莓8斤
                   .addProduct("MANGO", 5)       // 芒果5斤
                   .addProduct("ORANGE", 6)      // 橙子6斤
                   .addProduct("BANANA", 15);    // 香蕉15斤
        
        // 计算原价
        DynamicDiscountConfig noDiscount = new DynamicDiscountConfig("原价");
        DynamicPromotionRule originalRule = new DynamicPromotionRule(noDiscount);
        BigDecimal originalTotal = originalRule.calculateTotalAmount(bigPurchase);
        
        // 计算促销价
        BigDecimal promotionTotal = blackFridayRule.calculateTotalAmount(bigPurchase);
        
        // 计算最终价格（含批量折扣）
        BigDecimal finalTotal = bulkRule.calculateTotalAmount(bigPurchase);
        
        System.out.println("黑色星期五促销规则：" + blackFridayConfig.toString());
        System.out.println("批量折扣规则：" + bulkRule.getRuleDescription());
        System.out.println("购买记录：" + bigPurchase.toString());
        System.out.println("原价：" + originalTotal + "元");
        System.out.println("促销价：" + promotionTotal + "元");
        System.out.println("最终价格（含批量折扣）：" + finalTotal + "元");
        
        BigDecimal savings = originalTotal.subtract(finalTotal);
        System.out.println("总共节省：" + savings + "元");
        System.out.println("复杂促销场景测试成功！\n");
    }
    
    /**
     * 演示运行时商品管理能力
     */
    private static void demonstrateRuntimeManagement() {
        System.out.println("【演示4：运行时商品管理能力】");
        
        // 创建可管理的商品目录
        ProductCatalog managedCatalog = new ProductCatalog();
        managedCatalog.clearAllProducts(); // 清空默认商品
        System.out.println("初始商品数量：" + managedCatalog.getProductCount());
        
        // 1. 动态添加新商品
        System.out.println("\n1. 动态添加新商品：");
        managedCatalog.addProduct("PINEAPPLE", "Pineapple", "菠萝", new BigDecimal("8.50"))
                     .addProduct("COCONUT", "Coconut", "椰子", new BigDecimal("12.00"));
        
        System.out.println("添加后商品数量：" + managedCatalog.getProductCount());
        
        // 2. 测试新商品购买
        DynamicPurchase purchase = new DynamicPurchase(managedCatalog);
        purchase.addProduct("PINEAPPLE", 2);
        
        DynamicDiscountConfig config = new DynamicDiscountConfig("测试");
        DynamicPromotionRule rule = new DynamicPromotionRule(config);
        
        BigDecimal total1 = rule.calculateTotalAmount(purchase);
        System.out.println("菠萝2斤原价：" + total1 + "元");
        
        // 3. 动态修改价格
        System.out.println("\n2. 动态修改商品价格：");
        boolean updated = managedCatalog.updateProductPrice("PINEAPPLE", new BigDecimal("10.00"));
        System.out.println("价格更新" + (updated ? "成功" : "失败"));
        
        BigDecimal total2 = rule.calculateTotalAmount(purchase);
        System.out.println("菠萝2斤新价：" + total2 + "元");
        
        // 4. 动态添加促销
        System.out.println("\n3. 动态添加促销：");
        config.addProductDiscount("PINEAPPLE", new BigDecimal("0.8")); // 菠萝8折
        
        BigDecimal total3 = rule.calculateTotalAmount(purchase);
        System.out.println("菠萝2斤促销价：" + total3 + "元");
        
        // 5. 动态移除商品
        System.out.println("\n4. 动态移除商品：");
        Product removed = managedCatalog.removeProduct("COCONUT");
        System.out.println("移除商品：" + (removed != null ? removed.getChineseName() : "无"));
        System.out.println("移除后商品数量：" + managedCatalog.getProductCount());
        
        System.out.println("运行时商品管理测试成功！\n");
    }
    
    /**
     * 测试促销场景的辅助方法
     */
    private static void testPromotionScenario(ProductCatalog catalog, 
                                            DynamicDiscountConfig config, 
                                            String productId, 
                                            int quantity) {
        DynamicPurchase purchase = new DynamicPurchase(catalog);
        purchase.addProduct(productId, quantity);
        
        // 计算原价
        DynamicDiscountConfig noDiscount = new DynamicDiscountConfig("原价");
        DynamicPromotionRule originalRule = new DynamicPromotionRule(noDiscount);
        BigDecimal originalPrice = originalRule.calculateTotalAmount(purchase);
        
        // 计算促销价
        DynamicPromotionRule promoRule = new DynamicPromotionRule(config);
        BigDecimal promoPrice = promoRule.calculateTotalAmount(purchase);
        
        System.out.println("促销配置：" + config.toString());
        System.out.println("购买：" + productId + " " + quantity + "斤");
        System.out.println("原价：" + originalPrice + "元，促销价：" + promoPrice + "元");
        System.out.println("节省：" + originalPrice.subtract(promoPrice) + "元");
    }
}