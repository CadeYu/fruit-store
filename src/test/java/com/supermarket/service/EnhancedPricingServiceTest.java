package com.supermarket.service;

import com.supermarket.constants.EnhancedPricingConstants;
import com.supermarket.model.DiscountConfig;
import com.supermarket.model.EnhancedFruit;
import com.supermarket.model.EnhancedPurchase;
import com.supermarket.rule.EnhancedBulkDiscountRule;
import com.supermarket.rule.FlexiblePromotionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

/**
 * 增强的定价服务测试类
 * 包含完整的中文注释，使用BigDecimal确保计算精度
 * 测试灵活的折扣配置和多种水果打折场景
 * 
 * @author SupermarketPricingSystem
 * @version 2.0
 */
@DisplayName("Enhanced Pricing Service Tests")
public class EnhancedPricingServiceTest {
    
    /** 标准定价规则（无折扣） */
    private FlexiblePromotionRule standardRule;
    
    /** 草莓8折促销规则 */
    private FlexiblePromotionRule strawberryPromotionRule;
    
    /** 多水果促销规则（草莓8折+芒果9折） */
    private FlexiblePromotionRule multiFruitPromotionRule;
    
    /**
     * 测试前的初始化方法
     * 创建各种定价规则实例用于测试
     */
    @BeforeEach
    public void setUp() {
        // 创建标准定价规则（无任何折扣）
        DiscountConfig standardConfig = new DiscountConfig("标准定价");
        standardRule = new FlexiblePromotionRule(standardConfig);
        
        // 创建草莓8折促销规则
        DiscountConfig strawberryConfig = new DiscountConfig("草莓促销");
        strawberryConfig.addFruitDiscount(EnhancedFruit.STRAWBERRY, 
                                        EnhancedPricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE);
        strawberryPromotionRule = new FlexiblePromotionRule(strawberryConfig);
        
        // 创建多水果促销规则（草莓8折+芒果9折）
        DiscountConfig multiConfig = new DiscountConfig("多水果促销");
        multiConfig.addFruitDiscount(EnhancedFruit.STRAWBERRY, new BigDecimal("0.8"))
                   .addFruitDiscount(EnhancedFruit.MANGO, new BigDecimal("0.9"));
        multiFruitPromotionRule = new FlexiblePromotionRule(multiConfig);
    }
    
    /**
     * 测试标准顾客的正例场景
     * 
     * 测试目的：验证标准顾客（只购买苹果和草莓）的定价计算是否正确
     * 测试场景：
     * 1. 正常数量购买：苹果2斤 + 草莓1斤，预期总价 = 2×8.00 + 1×13.00 = 29.00元
     * 2. 大数量购买：苹果100斤 + 草莓50斤，预期总价 = 100×8.00 + 50×13.00 = 1450.00元
     * 
     * 验证要点：
     * - 标准定价规则的正确性（无任何折扣）
     * - BigDecimal精确计算的准确性
     * - 多种数量组合的计算准确性
     * - 大数量购买的处理能力
     */
    @Test
    @DisplayName("标准顾客 - 正例测试")
    public void testStandardCustomerPositiveCases() {
        // 正例测试1：正常数量购买
        // 苹果2斤(8.00元/斤) + 草莓1斤(13.00元/斤) = 16.00 + 13.00 = 29.00元
        EnhancedPurchase purchase1 = new EnhancedPurchase();
        purchase1.setFruitQuantity(EnhancedFruit.APPLE, 2);
        purchase1.setFruitQuantity(EnhancedFruit.STRAWBERRY, 1);
        
        BigDecimal total1 = standardRule.calculateTotalAmount(purchase1);
        BigDecimal expected1 = new BigDecimal("29.00");
        
        assertEquals(0, expected1.compareTo(total1), 
                    "标准顾客正常购买计算错误：预期" + expected1 + "元，实际" + total1 + "元");
        
        // 正例测试2：大数量购买
        // 苹果100斤(8.00元/斤) + 草莓50斤(13.00元/斤) = 800.00 + 650.00 = 1450.00元
        EnhancedPurchase purchase2 = new EnhancedPurchase();
        purchase2.setFruitQuantity(EnhancedFruit.APPLE, 100);
        purchase2.setFruitQuantity(EnhancedFruit.STRAWBERRY, 50);
        
        BigDecimal total2 = standardRule.calculateTotalAmount(purchase2);
        BigDecimal expected2 = new BigDecimal("1450.00");
        
        assertEquals(0, expected2.compareTo(total2), 
                    "标准顾客大数量购买计算错误：预期" + expected2 + "元，实际" + total2 + "元");
    }
    
    /**
     * 测试标准顾客的边界值场景
     * 
     * 测试目的：验证边界条件下的计算准确性
     * 测试场景：
     * 1. 零数量边界：苹果0斤 + 草莓0斤 = 0.00元
     * 2. 单边界条件：苹果5斤 + 草莓0斤 = 40.00元
     * 3. 单边界条件：苹果0斤 + 草莓3斤 = 39.00元
     * 
     * 验证要点：
     * - 零值处理的正确性
     * - 单一水果购买的计算准确性
     * - 边界条件下BigDecimal的精确性
     */
    @Test
    @DisplayName("标准顾客 - 边界值测试")
    public void testStandardCustomerBoundaryCases() {
        // 边界值测试1：零数量购买
        // 苹果0斤 + 草莓0斤 = 0.00元
        EnhancedPurchase emptyPurchase = new EnhancedPurchase();
        BigDecimal zeroTotal = standardRule.calculateTotalAmount(emptyPurchase);
        BigDecimal expectedZero = BigDecimal.ZERO.setScale(2);
        
        assertEquals(0, expectedZero.compareTo(zeroTotal), 
                    "零数量购买计算错误：预期" + expectedZero + "元，实际" + zeroTotal + "元");
        
        // 边界值测试2：只购买苹果
        // 苹果5斤(8.00元/斤) + 草莓0斤 = 40.00元
        EnhancedPurchase applePurchase = new EnhancedPurchase();
        applePurchase.setFruitQuantity(EnhancedFruit.APPLE, 5);
        
        BigDecimal appleTotal = standardRule.calculateTotalAmount(applePurchase);
        BigDecimal expectedApple = new BigDecimal("40.00");
        
        assertEquals(0, expectedApple.compareTo(appleTotal), 
                    "单一苹果购买计算错误：预期" + expectedApple + "元，实际" + appleTotal + "元");
        
        // 边界值测试3：只购买草莓
        // 苹果0斤 + 草莓3斤(13.00元/斤) = 39.00元
        EnhancedPurchase strawberryPurchase = new EnhancedPurchase();
        strawberryPurchase.setFruitQuantity(EnhancedFruit.STRAWBERRY, 3);
        
        BigDecimal strawberryTotal = standardRule.calculateTotalAmount(strawberryPurchase);
        BigDecimal expectedStrawberry = new BigDecimal("39.00");
        
        assertEquals(0, expectedStrawberry.compareTo(strawberryTotal), 
                    "单一草莓购买计算错误：预期" + expectedStrawberry + "元，实际" + strawberryTotal + "元");
    }
    
    /**
     * 测试标准顾客的反例场景（异常输入处理）
     * 
     * 测试目的：验证异常输入的处理能力
     * 测试场景：
     * 1. 负数数量输入：苹果-1斤，应抛出IllegalArgumentException
     * 2. null水果类型：添加null水果，应抛出IllegalArgumentException
     * 3. null购买记录：传入null购买记录，应抛出IllegalArgumentException
     * 
     * 验证要点：
     * - 输入验证的完整性
     * - 异常处理的正确性
     * - 错误信息的准确性
     */
    @Test
    @DisplayName("标准顾客 - 反例测试（异常处理）")
    public void testStandardCustomerNegativeCases() {
        // 反例测试1：负数数量
        EnhancedPurchase purchase = new EnhancedPurchase();
        
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            purchase.setFruitQuantity(EnhancedFruit.APPLE, -1);
        }, "负数苹果数量应该抛出异常");
        
        assertTrue(exception1.getMessage().contains("水果数量不能为负数"), 
                  "异常信息应包含'水果数量不能为负数'");
        
        // 反例测试2：null水果类型
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            purchase.setFruitQuantity(null, 1);
        }, "null水果类型应该抛出异常");
        
        assertTrue(exception2.getMessage().contains("水果类型不能为空"), 
                  "异常信息应包含'水果类型不能为空'");
        
        // 反例测试3：null购买记录
        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
            standardRule.calculateTotalAmount(null);
        }, "null购买记录应该抛出异常");
        
        assertTrue(exception3.getMessage().contains("购买记录不能为空"), 
                  "异常信息应包含'购买记录不能为空'");
    }    
/**
     * 测试草莓促销顾客的正例场景
     * 
     * 测试目的：验证草莓8折促销规则的计算准确性
     * 测试场景：
     * 1. 草莓促销购买：苹果1斤 + 草莓2斤(8折) + 芒果1斤
     *    预期总价 = 1×8.00 + 2×13.00×0.8 + 1×20.00 = 8.00 + 20.80 + 20.00 = 48.80元
     * 2. 纯草莓购买：草莓5斤(8折)
     *    预期总价 = 5×13.00×0.8 = 52.00元
     * 
     * 验证要点：
     * - 草莓8折促销规则的正确应用
     * - 混合水果购买时折扣的精确计算
     * - BigDecimal在小数计算中的精度保证
     */
    @Test
    @DisplayName("草莓促销顾客 - 正例测试")
    public void testStrawberryPromotionPositiveCases() {
        // 正例测试1：混合水果购买，草莓享受8折
        // 苹果1斤(8.00元/斤) + 草莓2斤(13.00×0.8=10.40元/斤) + 芒果1斤(20.00元/斤)
        // = 8.00 + 20.80 + 20.00 = 48.80元
        EnhancedPurchase mixedPurchase = new EnhancedPurchase();
        mixedPurchase.setFruitQuantity(EnhancedFruit.APPLE, 1);
        mixedPurchase.setFruitQuantity(EnhancedFruit.STRAWBERRY, 2);
        mixedPurchase.setFruitQuantity(EnhancedFruit.MANGO, 1);
        
        BigDecimal mixedTotal = strawberryPromotionRule.calculateTotalAmount(mixedPurchase);
        BigDecimal expectedMixed = new BigDecimal("48.80");
        
        assertEquals(0, expectedMixed.compareTo(mixedTotal), 
                    "草莓促销混合购买计算错误：预期" + expectedMixed + "元，实际" + mixedTotal + "元");
        
        // 正例测试2：纯草莓购买，验证8折计算精度
        // 草莓5斤(13.00×0.8=10.40元/斤) = 52.00元
        EnhancedPurchase strawberryOnlyPurchase = new EnhancedPurchase();
        strawberryOnlyPurchase.setFruitQuantity(EnhancedFruit.STRAWBERRY, 5);
        
        BigDecimal strawberryTotal = strawberryPromotionRule.calculateTotalAmount(strawberryOnlyPurchase);
        BigDecimal expectedStrawberry = new BigDecimal("52.00");
        
        assertEquals(0, expectedStrawberry.compareTo(strawberryTotal), 
                    "纯草莓促销购买计算错误：预期" + expectedStrawberry + "元，实际" + strawberryTotal + "元");
    }
    
    /**
     * 测试多水果促销的正例场景
     * 
     * 测试目的：验证多种水果同时打折的计算准确性
     * 测试场景：
     * 1. 多水果促销：苹果2斤(原价) + 草莓3斤(8折) + 芒果2斤(9折)
     *    预期总价 = 2×8.00 + 3×13.00×0.8 + 2×20.00×0.9 = 16.00 + 31.20 + 36.00 = 83.20元
     * 
     * 验证要点：
     * - 多种水果同时打折的正确处理
     * - 不同折扣率的精确计算
     * - 系统扩展性的验证（支持新增水果打折）
     */
    @Test
    @DisplayName("多水果促销顾客 - 正例测试")
    public void testMultiFruitPromotionPositiveCases() {
        // 多水果促销测试：苹果原价，草莓8折，芒果9折
        // 苹果2斤(8.00元/斤) + 草莓3斤(13.00×0.8=10.40元/斤) + 芒果2斤(20.00×0.9=18.00元/斤)
        // = 16.00 + 31.20 + 36.00 = 83.20元
        EnhancedPurchase multiPromoPurchase = new EnhancedPurchase();
        multiPromoPurchase.setFruitQuantity(EnhancedFruit.APPLE, 2);
        multiPromoPurchase.setFruitQuantity(EnhancedFruit.STRAWBERRY, 3);
        multiPromoPurchase.setFruitQuantity(EnhancedFruit.MANGO, 2);
        
        BigDecimal multiTotal = multiFruitPromotionRule.calculateTotalAmount(multiPromoPurchase);
        BigDecimal expectedMulti = new BigDecimal("83.20");
        
        assertEquals(0, expectedMulti.compareTo(multiTotal), 
                    "多水果促销购买计算错误：预期" + expectedMulti + "元，实际" + multiTotal + "元");
    }
    
    /**
     * 测试批量折扣的边界值场景（重点测试）
     * 
     * 测试目的：验证满100元减10元的批量折扣边界条件
     * 测试场景：
     * 1. 边界值99.20元：苹果6斤 + 草莓3斤(8折) + 芒果1斤 = 48.00 + 31.20 + 20.00 = 99.20元 < 100元，不减10元
     * 2. 边界值100.00元：芒果5斤 = 100.00元 = 100元，减10元 = 90.00元
     * 3. 边界值107.20元：苹果7斤 + 草莓3斤(8折) + 芒果1斤 = 56.00 + 31.20 + 20.00 = 107.20元 > 100元，减10元 = 97.20元
     * 
     * 验证要点：
     * - 批量折扣阈值判断的精确性
     * - 边界条件的正确处理（99元、100元、101元）
     * - 装饰器模式的正确实现
     * - BigDecimal在阈值比较中的准确性
     * 
     * 这是面试的重点加分项！
     */
    @Test
    @DisplayName("批量折扣 - 边界值测试（重点）")
    public void testBulkDiscountBoundaryValues() {
        // 创建批量折扣规则（基于草莓促销规则）
        EnhancedBulkDiscountRule bulkDiscountRule = new EnhancedBulkDiscountRule(strawberryPromotionRule);
        
        // 边界值测试1：99.20元，不满100元，不应减10元
        // 苹果6斤(8.00元/斤) + 草莓3斤(13.00×0.8=10.40元/斤) + 芒果1斤(20.00元/斤)
        // = 48.00 + 31.20 + 20.00 = 99.20元 < 100元，不减10元
        EnhancedPurchase purchase99 = new EnhancedPurchase();
        purchase99.setFruitQuantity(EnhancedFruit.APPLE, 6);
        purchase99.setFruitQuantity(EnhancedFruit.STRAWBERRY, 3);
        purchase99.setFruitQuantity(EnhancedFruit.MANGO, 1);
        
        BigDecimal total99 = bulkDiscountRule.calculateTotalAmount(purchase99);
        BigDecimal expected99 = new BigDecimal("99.20");  // 不减10元
        
        assertEquals(0, expected99.compareTo(total99), 
                    "99.20元边界测试失败：预期" + expected99 + "元，实际" + total99 + "元（不应减10元）");
        
        // 验证小计确实小于100元
        BigDecimal subtotal99 = strawberryPromotionRule.calculateTotalAmount(purchase99);
        assertTrue(subtotal99.compareTo(EnhancedPricingConstants.BulkDiscount.THRESHOLD_AMOUNT) < 0,
                  "小计应小于100元，实际为" + subtotal99 + "元");
        
        // 边界值测试2：100.00元，刚好满100元，应减10元
        // 芒果5斤(20.00元/斤) = 100.00元，减10元 = 90.00元
        EnhancedPurchase purchase100 = new EnhancedPurchase();
        purchase100.setFruitQuantity(EnhancedFruit.MANGO, 5);
        
        BigDecimal total100 = bulkDiscountRule.calculateTotalAmount(purchase100);
        BigDecimal expected100 = new BigDecimal("90.00");  // 100 - 10 = 90
        
        assertEquals(0, expected100.compareTo(total100), 
                    "100.00元边界测试失败：预期" + expected100 + "元，实际" + total100 + "元（应减10元）");
        
        // 验证小计确实等于100元
        BigDecimal subtotal100 = strawberryPromotionRule.calculateTotalAmount(purchase100);
        assertEquals(0, EnhancedPricingConstants.BulkDiscount.THRESHOLD_AMOUNT.compareTo(subtotal100),
                    "小计应等于100元，实际为" + subtotal100 + "元");
        
        // 边界值测试3：107.20元，超过100元，应减10元
        // 苹果7斤(8.00元/斤) + 草莓3斤(13.00×0.8=10.40元/斤) + 芒果1斤(20.00元/斤)
        // = 56.00 + 31.20 + 20.00 = 107.20元，减10元 = 97.20元
        EnhancedPurchase purchase107 = new EnhancedPurchase();
        purchase107.setFruitQuantity(EnhancedFruit.APPLE, 7);
        purchase107.setFruitQuantity(EnhancedFruit.STRAWBERRY, 3);
        purchase107.setFruitQuantity(EnhancedFruit.MANGO, 1);
        
        BigDecimal total107 = bulkDiscountRule.calculateTotalAmount(purchase107);
        BigDecimal expected107 = new BigDecimal("97.20");  // 107.20 - 10 = 97.20
        
        assertEquals(0, expected107.compareTo(total107), 
                    "107.20元边界测试失败：预期" + expected107 + "元，实际" + total107 + "元（应减10元）");
        
        // 验证小计确实大于100元
        BigDecimal subtotal107 = strawberryPromotionRule.calculateTotalAmount(purchase107);
        assertTrue(subtotal107.compareTo(EnhancedPricingConstants.BulkDiscount.THRESHOLD_AMOUNT) > 0,
                  "小计应大于100元，实际为" + subtotal107 + "元");
    }
    
    /**
     * 测试BigDecimal计算精度
     * 
     * 测试目的：验证BigDecimal在货币计算中的精度保证
     * 测试场景：
     * 1. 小数折扣计算：草莓1斤×8折 = 13.00×0.8 = 10.40元
     * 2. 复杂小数运算：多种水果混合计算的精度验证
     * 
     * 验证要点：
     * - BigDecimal小数计算的精确性
     * - 舍入模式的正确应用
     * - 货币计算中避免浮点数误差
     */
    @Test
    @DisplayName("BigDecimal精度测试")
    public void testBigDecimalPrecision() {
        // 精度测试：草莓8折计算
        // 草莓1斤(13.00×0.8=10.40元/斤) = 10.40元
        EnhancedPurchase precisionPurchase = new EnhancedPurchase();
        precisionPurchase.setFruitQuantity(EnhancedFruit.STRAWBERRY, 1);
        
        BigDecimal precisionTotal = strawberryPromotionRule.calculateTotalAmount(precisionPurchase);
        BigDecimal expectedPrecision = new BigDecimal("10.40");
        
        assertEquals(0, expectedPrecision.compareTo(precisionTotal), 
                    "BigDecimal精度测试失败：预期" + expectedPrecision + "元，实际" + precisionTotal + "元");
        
        // 验证小数位数正确
        assertEquals(2, precisionTotal.scale(), 
                    "结果应保留2位小数，实际小数位数：" + precisionTotal.scale());
    }
    
    /**
     * 测试折扣配置的灵活性
     * 
     * 测试目的：验证系统支持灵活配置不同水果的折扣
     * 测试场景：
     * 1. 动态添加新的折扣配置
     * 2. 修改现有折扣配置
     * 3. 移除折扣配置
     * 
     * 验证要点：
     * - 折扣配置的动态性
     * - 系统的可扩展性
     * - 新增水果种类的支持能力
     */
    @Test
    @DisplayName("折扣配置灵活性测试")
    public void testDiscountConfigFlexibility() {
        // 创建自定义折扣配置：苹果9折
        DiscountConfig customConfig = new DiscountConfig("苹果促销");
        customConfig.addFruitDiscount(EnhancedFruit.APPLE, new BigDecimal("0.9"));
        
        FlexiblePromotionRule customRule = new FlexiblePromotionRule(customConfig);
        
        // 测试苹果9折
        // 苹果10斤(8.00×0.9=7.20元/斤) = 72.00元
        EnhancedPurchase customPurchase = new EnhancedPurchase();
        customPurchase.setFruitQuantity(EnhancedFruit.APPLE, 10);
        
        BigDecimal customTotal = customRule.calculateTotalAmount(customPurchase);
        BigDecimal expectedCustom = new BigDecimal("72.00");
        
        assertEquals(0, expectedCustom.compareTo(customTotal), 
                    "自定义苹果9折测试失败：预期" + expectedCustom + "元，实际" + customTotal + "元");
        
        // 验证折扣配置描述
        assertTrue(customRule.getRuleDescription().contains("苹果促销"), 
                  "规则描述应包含'苹果促销'");
    }
}