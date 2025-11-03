package com.supermarket.service;

import com.supermarket.model.*;
import com.supermarket.rule.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

/**
 * 动态定价系统测试类
 * 展示完全动态的商品和折扣配置能力，彻底解决扩展性问题
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
@DisplayName("Dynamic Pricing System Tests - 完全解决扩展性问题")
public class DynamicPricingSystemTest {
    
    /** 基础商品目录 */
    private ProductCatalog basicCatalog;
    
    /** 扩展商品目录 */
    private ProductCatalog extendedCatalog;
    
    /** 标准定价规则 */
    private DynamicPromotionRule standardRule;
    
    /** 草莓促销规则 */
    private DynamicPromotionRule strawberryPromotionRule;
    
    /** 多水果促销规则 */
    private DynamicPromotionRule multiFruitPromotionRule;
    
    /**
     * 测试前的初始化方法
     */
    @BeforeEach
    public void setUp() {
        // 创建基础商品目录（苹果、草莓、芒果）
        basicCatalog = ProductCatalog.createDefaultCatalog();
        
        // 创建扩展商品目录（包含更多水果）
        extendedCatalog = ProductCatalog.createExtendedCatalog();
        
        // 创建标准定价规则（无折扣）
        DynamicDiscountConfig standardConfig = new DynamicDiscountConfig("标准定价");
        standardRule = new DynamicPromotionRule(standardConfig);
        
        // 创建草莓促销规则
        DynamicDiscountConfig strawberryConfig = DynamicDiscountConfig.createStrawberryPromotion();
        strawberryPromotionRule = new DynamicPromotionRule(strawberryConfig);
        
        // 创建多水果促销规则
        DynamicDiscountConfig multiConfig = DynamicDiscountConfig.createMultiFruitPromotion();
        multiFruitPromotionRule = new DynamicPromotionRule(multiConfig);
    }
    
    /**
     * 测试动态添加新商品的能力
     * 
     * 测试目的：验证系统支持运行时动态添加新商品种类
     * 测试场景：
     * 1. 添加新水果：榴莲、火龙果、猕猴桃
     * 2. 为新水果配置不同的价格
     * 3. 验证新商品可以正常购买和计算价格
     * 
     * 验证要点：
     * - 运行时动态添加商品的能力
     * - 新商品的价格计算准确性
     * - 系统的可扩展性
     * 
     * 🎯 这是扩展性的核心测试！
     */
    @Test
    @DisplayName("🎯 动态添加新商品测试 - 扩展性核心验证")
    public void testDynamicProductAddition() {
        // 创建空的商品目录（不使用默认商品）
        ProductCatalog customCatalog = new ProductCatalog();
        customCatalog.clearAllProducts(); // 清空默认商品
        
        // 动态添加各种新商品
        customCatalog.addProduct("DURIAN", "Durian", "榴莲", new BigDecimal("50.00"), "热带水果")
                    .addProduct("DRAGON_FRUIT", "Dragon Fruit", "火龙果", new BigDecimal("25.00"), "热带水果")
                    .addProduct("KIWI", "Kiwi", "猕猴桃", new BigDecimal("18.00"), "温带水果")
                    .addProduct("LYCHEE", "Lychee", "荔枝", new BigDecimal("22.00"), "热带水果")
                    .addProduct("AVOCADO", "Avocado", "牛油果", new BigDecimal("35.00"), "热带水果");
        
        // 验证商品添加成功
        assertEquals(5, customCatalog.getProductCount(), "应该有5种新商品");
        assertTrue(customCatalog.hasProduct("DURIAN"), "应该包含榴莲");
        assertTrue(customCatalog.hasProduct("DRAGON_FRUIT"), "应该包含火龙果");
        
        // 创建购买记录并测试新商品
        DynamicPurchase purchase = new DynamicPurchase(customCatalog);
        purchase.addProduct("DURIAN", 1)        // 榴莲1斤 = 50.00元
                .addProduct("DRAGON_FRUIT", 2)  // 火龙果2斤 = 50.00元
                .addProduct("KIWI", 3);         // 猕猴桃3斤 = 54.00元
        
        // 使用标准定价计算
        DynamicDiscountConfig noDiscountConfig = new DynamicDiscountConfig("无折扣");
        DynamicPromotionRule noDiscountRule = new DynamicPromotionRule(noDiscountConfig);
        
        BigDecimal total = noDiscountRule.calculateTotalAmount(purchase);
        BigDecimal expected = new BigDecimal("154.00"); // 50 + 50 + 54 = 154
        
        assertEquals(0, expected.compareTo(total), 
                    "新商品价格计算错误：预期" + expected + "元，实际" + total + "元");
        
        System.out.println("✅ 动态添加商品测试通过：" + purchase.toString());
    }
    
    /**
     * 测试动态配置任意商品折扣的能力
     * 
     * 测试目的：验证系统支持为任意商品配置不同的折扣率
     * 测试场景：
     * 1. 为不同商品配置不同折扣率
     * 2. 验证折扣计算的准确性
     * 3. 测试复杂的折扣组合
     * 
     * 验证要点：
     * - 任意商品折扣配置的灵活性
     * - 多种折扣率的精确计算
     * - 折扣配置的动态性
     * 
     * 🚀 这展示了系统的超强灵活性！
     */
    @Test
    @DisplayName("🚀 动态折扣配置测试 - 超强灵活性验证")
    public void testDynamicDiscountConfiguration() {
        // 创建复杂的折扣配置
        DynamicDiscountConfig complexDiscount = new DynamicDiscountConfig("复杂促销活动");
        complexDiscount.addProductDiscount("APPLE", new BigDecimal("0.7"))      // 苹果7折
                      .addProductDiscount("STRAWBERRY", new BigDecimal("0.6"))   // 草莓6折
                      .addProductDiscount("MANGO", new BigDecimal("0.8"))        // 芒果8折
                      .addProductDiscount("ORANGE", new BigDecimal("0.5"))       // 橙子5折
                      .addProductDiscount("BANANA", new BigDecimal("0.4"));      // 香蕉4折
        
        DynamicPromotionRule complexRule = new DynamicPromotionRule(complexDiscount);
        
        // 创建购买记录
        DynamicPurchase purchase = new DynamicPurchase(extendedCatalog);
        purchase.addProduct("APPLE", 2)        // 苹果2斤：8.00×2×0.7 = 11.20元
                .addProduct("STRAWBERRY", 1)   // 草莓1斤：13.00×1×0.6 = 7.80元
                .addProduct("MANGO", 1)        // 芒果1斤：20.00×1×0.8 = 16.00元
                .addProduct("ORANGE", 3)       // 橙子3斤：12.00×3×0.5 = 18.00元
                .addProduct("BANANA", 5);      // 香蕉5斤：6.00×5×0.4 = 12.00元
        
        BigDecimal total = complexRule.calculateTotalAmount(purchase);
        BigDecimal expected = new BigDecimal("65.00"); // 11.20 + 7.80 + 16.00 + 18.00 + 12.00 = 65.00
        
        assertEquals(0, expected.compareTo(total), 
                    "复杂折扣计算错误：预期" + expected + "元，实际" + total + "元");
        
        System.out.println("✅ 动态折扣配置测试通过：" + complexDiscount.toString());
    }
    
    /**
     * 测试黑色星期五大促销场景
     * 
     * 测试目的：验证系统支持复杂的促销活动配置
     * 测试场景：模拟黑色星期五所有商品大幅打折的场景
     * 
     * 验证要点：
     * - 大规模促销活动的支持能力
     * - 批量折扣配置的便捷性
     * - 复杂业务场景的适应性
     */
    @Test
    @DisplayName("🛍️ 黑色星期五大促销测试")
    public void testBlackFridayPromotion() {
        // 使用预定义的黑色星期五促销配置
        DynamicDiscountConfig blackFridayConfig = DynamicDiscountConfig.createBlackFridayPromotion();
        DynamicPromotionRule blackFridayRule = new DynamicPromotionRule(blackFridayConfig);
        
        // 创建大量购买的场景
        DynamicPurchase bigPurchase = new DynamicPurchase(extendedCatalog);
        bigPurchase.addProduct("APPLE", 10)      // 苹果10斤：8.00×10×0.5 = 40.00元
                   .addProduct("STRAWBERRY", 8)  // 草莓8斤：13.00×8×0.6 = 62.40元
                   .addProduct("MANGO", 5)       // 芒果5斤：20.00×5×0.7 = 70.00元
                   .addProduct("ORANGE", 6)      // 橙子6斤：12.00×6×0.55 = 39.60元
                   .addProduct("BANANA", 15);    // 香蕉15斤：6.00×15×0.4 = 36.00元
        
        BigDecimal total = blackFridayRule.calculateTotalAmount(bigPurchase);
        BigDecimal expected = new BigDecimal("248.00"); // 40.00 + 62.40 + 70.00 + 39.60 + 36.00 = 248.00
        
        assertEquals(0, expected.compareTo(total), 
                    "黑色星期五促销计算错误：预期" + expected + "元，实际" + total + "元");
        
        System.out.println("✅ 黑色星期五大促销测试通过，总计：" + total + "元");
    }
    
    /**
     * 测试动态批量折扣的边界值场景
     * 
     * 测试目的：验证动态系统中批量折扣的准确性
     * 测试场景：使用新商品测试批量折扣的边界条件
     * 
     * 验证要点：
     * - 动态商品与批量折扣的兼容性
     * - 边界值判断的准确性
     * - 装饰器模式在动态系统中的正确实现
     */
    @Test
    @DisplayName("💰 动态批量折扣边界值测试")
    public void testDynamicBulkDiscountBoundary() {
        // 创建批量折扣规则
        DynamicBulkDiscountRule bulkRule = new DynamicBulkDiscountRule(strawberryPromotionRule);
        
        // 边界测试1：99.20元，不满100元
        DynamicPurchase purchase99 = new DynamicPurchase(extendedCatalog);
        purchase99.addProduct("APPLE", 6)        // 苹果6斤：8.00×6 = 48.00元
                  .addProduct("STRAWBERRY", 3)   // 草莓3斤：13.00×3×0.8 = 31.20元
                  .addProduct("MANGO", 1);       // 芒果1斤：20.00×1 = 20.00元
        // 小计：48.00 + 31.20 + 20.00 = 99.20元 < 100元，不减10元
        
        BigDecimal total99 = bulkRule.calculateTotalAmount(purchase99);
        BigDecimal expected99 = new BigDecimal("99.20");
        
        assertEquals(0, expected99.compareTo(total99), 
                    "99.20元边界测试失败：预期" + expected99 + "元，实际" + total99 + "元");
        
        // 边界测试2：100.00元，刚好满100元
        DynamicPurchase purchase100 = new DynamicPurchase(extendedCatalog);
        purchase100.addProduct("MANGO", 5);      // 芒果5斤：20.00×5 = 100.00元
        
        BigDecimal total100 = bulkRule.calculateTotalAmount(purchase100);
        BigDecimal expected100 = new BigDecimal("90.00"); // 100.00 - 10.00 = 90.00元
        
        assertEquals(0, expected100.compareTo(total100), 
                    "100.00元边界测试失败：预期" + expected100 + "元，实际" + total100 + "元");
        
        System.out.println("✅ 动态批量折扣边界值测试通过");
    }
    
    /**
     * 测试运行时商品管理能力
     * 
     * 测试目的：验证系统支持运行时的商品管理操作
     * 测试场景：
     * 1. 动态添加商品
     * 2. 动态修改价格
     * 3. 动态移除商品
     * 4. 验证操作的正确性
     * 
     * 验证要点：
     * - 运行时商品管理的完整性
     * - 价格更新的准确性
     * - 商品移除的安全性
     */
    @Test
    @DisplayName("🔧 运行时商品管理测试")
    public void testRuntimeProductManagement() {
        // 创建可管理的商品目录
        ProductCatalog managedCatalog = new ProductCatalog();
        managedCatalog.clearAllProducts(); // 清空默认商品，从零开始
        
        // 1. 动态添加商品
        managedCatalog.addProduct("PINEAPPLE", "Pineapple", "菠萝", new BigDecimal("8.50"));
        assertTrue(managedCatalog.hasProduct("PINEAPPLE"), "应该成功添加菠萝");
        
        // 2. 测试新商品购买
        DynamicPurchase purchase = new DynamicPurchase(managedCatalog);
        purchase.addProduct("PINEAPPLE", 2); // 菠萝2斤 = 17.00元
        
        DynamicDiscountConfig noDiscount = new DynamicDiscountConfig("无折扣");
        DynamicPromotionRule rule = new DynamicPromotionRule(noDiscount);
        
        BigDecimal total1 = rule.calculateTotalAmount(purchase);
        assertEquals(0, new BigDecimal("17.00").compareTo(total1), "菠萝价格计算错误");
        
        // 3. 动态修改价格
        boolean updated = managedCatalog.updateProductPrice("PINEAPPLE", new BigDecimal("10.00"));
        assertTrue(updated, "应该成功更新价格");
        
        // 4. 验证价格更新效果
        BigDecimal total2 = rule.calculateTotalAmount(purchase);
        assertEquals(0, new BigDecimal("20.00").compareTo(total2), "价格更新后计算错误");
        
        // 5. 动态移除商品
        Product removed = managedCatalog.removeProduct("PINEAPPLE");
        assertNotNull(removed, "应该成功移除商品");
        assertFalse(managedCatalog.hasProduct("PINEAPPLE"), "商品应该已被移除");
        
        System.out.println("✅ 运行时商品管理测试通过");
    }
    
    /**
     * 测试系统的终极扩展性
     * 
     * 测试目的：展示系统支持完全自定义的商品和促销场景
     * 测试场景：创建一个全新的商品类别和复杂的促销规则
     * 
     * 验证要点：
     * - 系统对新业务场景的适应能力
     * - 复杂促销规则的支持能力
     * - 完全动态配置的可行性
     * 
     * 🌟 这是扩展性的终极展示！
     */
    @Test
    @DisplayName("🌟 终极扩展性测试 - 自定义商品类别和促销")
    public void testUltimateExtensibility() {
        // 创建全新的商品目录 - 蔬菜类
        ProductCatalog vegetableCatalog = new ProductCatalog();
        vegetableCatalog.addProduct("TOMATO", "Tomato", "西红柿", new BigDecimal("5.00"), "蔬菜")
                       .addProduct("CUCUMBER", "Cucumber", "黄瓜", new BigDecimal("4.50"), "蔬菜")
                       .addProduct("LETTUCE", "Lettuce", "生菜", new BigDecimal("3.80"), "蔬菜")
                       .addProduct("CARROT", "Carrot", "胡萝卜", new BigDecimal("3.20"), "蔬菜")
                       .addProduct("BROCCOLI", "Broccoli", "西兰花", new BigDecimal("8.80"), "蔬菜");
        
        // 创建复杂的蔬菜促销规则
        DynamicDiscountConfig vegetablePromo = new DynamicDiscountConfig("健康蔬菜促销周");
        vegetablePromo.addProductDiscount("TOMATO", new BigDecimal("0.8"))     // 西红柿8折
                     .addProductDiscount("CUCUMBER", new BigDecimal("0.75"))   // 黄瓜75折
                     .addProductDiscount("LETTUCE", new BigDecimal("0.9"))     // 生菜9折
                     .addProductDiscount("CARROT", new BigDecimal("0.7"))      // 胡萝卜7折
                     .addProductDiscount("BROCCOLI", new BigDecimal("0.85"));  // 西兰花85折
        
        // 创建带批量折扣的蔬菜促销规则
        DynamicPromotionRule vegetableRule = new DynamicPromotionRule(vegetablePromo);
        DynamicBulkDiscountRule vegetableBulkRule = new DynamicBulkDiscountRule(
            vegetableRule, 
            new BigDecimal("50.00"),  // 满50元
            new BigDecimal("5.00")    // 减5元
        );
        
        // 创建蔬菜购买场景
        DynamicPurchase vegetablePurchase = new DynamicPurchase(vegetableCatalog);
        vegetablePurchase.addProduct("TOMATO", 3)      // 西红柿3斤：5.00×3×0.8 = 12.00元
                        .addProduct("CUCUMBER", 4)     // 黄瓜4斤：4.50×4×0.75 = 13.50元
                        .addProduct("LETTUCE", 2)      // 生菜2斤：3.80×2×0.9 = 6.84元
                        .addProduct("CARROT", 5)       // 胡萝卜5斤：3.20×5×0.7 = 11.20元
                        .addProduct("BROCCOLI", 2);    // 西兰花2斤：8.80×2×0.85 = 14.96元
        
        // 小计：12.00 + 13.50 + 6.84 + 11.20 + 14.96 = 58.50元
        // 满50元减5元：58.50 - 5.00 = 53.50元
        
        BigDecimal total = vegetableBulkRule.calculateTotalAmount(vegetablePurchase);
        BigDecimal expected = new BigDecimal("53.50");
        
        assertEquals(0, expected.compareTo(total), 
                    "蔬菜促销计算错误：预期" + expected + "元，实际" + total + "元");
        
        System.out.println("🌟 终极扩展性测试通过！");
        System.out.println("   商品目录：" + vegetableCatalog.getProductCount() + "种蔬菜");
        System.out.println("   促销规则：" + vegetablePromo.toString());
        System.out.println("   购买记录：" + vegetablePurchase.toString());
        System.out.println("   最终价格：" + total + "元");
    }
}