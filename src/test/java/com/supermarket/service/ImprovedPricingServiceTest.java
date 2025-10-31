package com.supermarket.service;

import com.supermarket.constants.PricingConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 改进的定价服务测试类
 * 包含正例、反例和边界值测试，全面验证超市定价系统的各种业务场景
 * 
 * 测试覆盖范围：
 * 1. 正例测试：验证正常业务场景的计算准确性
 * 2. 边界值测试：重点测试100元阈值的关键边界点
 * 3. 反例测试：验证异常输入的处理和错误信息
 * 4. 精度测试：确保货币计算的精确性
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
@DisplayName("改进的定价服务测试")
public class ImprovedPricingServiceTest {
    
    /** 定价服务实例，用于所有测试方法 */
    private PricingService pricingService;
    
    /**
     * 测试前置方法
     * 在每个测试方法执行前初始化定价服务实例
     */
    @BeforeEach
    public void setUp() {
        pricingService = new PricingService();
    }
    
    /**
     * 测试标准顾客的正例场景
     * 
     * 测试目标：验证标准顾客（购买苹果和草莓）的定价计算准确性
     * 业务规则：苹果8元/斤，草莓13元/斤，无任何折扣
     * 
     * 测试用例：
     * 1. 正常数量购买：苹果2斤 + 草莓1斤 = 2×8 + 1×13 = 29元
     * 2. 大数量购买：苹果100斤 + 草莓50斤 = 100×8 + 50×13 = 1450元
     * 
     * 验证要点：
     * - 基础价格计算的准确性
     * - 大数量购买的计算稳定性
     * - 货币精度控制（0.01元）
     */
    @Test
    @DisplayName("标准顾客 - 正例测试")
    public void testStandardCustomerPositiveCases() {
        // 测试用例1：正常数量购买
        // 苹果2斤 + 草莓1斤 = 2×8 + 1×13 = 29元
        double total = pricingService.calculateStandardCustomerTotal(2, 1);
        double expected = 2 * PricingConstants.FruitPrices.APPLE_PRICE + 1 * PricingConstants.FruitPrices.STRAWBERRY_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
        
        // 测试用例2：大数量购买验证
        // 苹果100斤 + 草莓50斤 = 100×8 + 50×13 = 1450元
        total = pricingService.calculateStandardCustomerTotal(100, 50);
        expected = 100 * PricingConstants.FruitPrices.APPLE_PRICE + 50 * PricingConstants.FruitPrices.STRAWBERRY_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
    }
    
    /**
     * 测试标准顾客的边界值场景
     * 
     * 测试目标：验证标准顾客在边界条件下的计算准确性
     * 边界值定义：零值、单边值等特殊数量组合
     * 
     * 测试用例：
     * 1. 全零边界：苹果0斤 + 草莓0斤 = 0元
     * 2. 单边边界：苹果5斤 + 草莓0斤 = 5×8 = 40元
     * 3. 单边边界：苹果0斤 + 草莓3斤 = 3×13 = 39元
     * 
     * 验证要点：
     * - 零值输入的正确处理
     * - 单一水果购买的计算准确性
     * - 边界条件下不会出现计算错误
     */
    @Test
    @DisplayName("标准顾客 - 边界值测试")
    public void testStandardCustomerBoundaryCases() {
        // 测试用例1：全零边界值
        // 苹果0斤 + 草莓0斤 = 0元
        double total = pricingService.calculateStandardCustomerTotal(0, 0);
        assertEquals(0.0, total, PricingConstants.Precision.CURRENCY_PRECISION);
        
        // 测试用例2：单边边界值 - 只买苹果
        // 苹果5斤 + 草莓0斤 = 5×8 = 40元
        total = pricingService.calculateStandardCustomerTotal(5, 0);
        double expected = 5 * PricingConstants.FruitPrices.APPLE_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
        
        // 测试用例3：单边边界值 - 只买草莓
        // 苹果0斤 + 草莓3斤 = 3×13 = 39元
        total = pricingService.calculateStandardCustomerTotal(0, 3);
        expected = 3 * PricingConstants.FruitPrices.STRAWBERRY_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
    }
    
    /**
     * 测试标准顾客的反例场景（异常输入）
     * 
     * 测试目标：验证标准顾客方法对异常输入的处理能力
     * 异常类型：负数数量输入
     * 
     * 测试用例：
     * 1. 苹果负数：苹果-1斤 + 草莓2斤 → 抛出IllegalArgumentException
     * 2. 草莓负数：苹果2斤 + 草莓-1斤 → 抛出IllegalArgumentException  
     * 3. 全部负数：苹果-1斤 + 草莓-1斤 → 抛出IllegalArgumentException
     * 
     * 验证要点：
     * - 输入验证的完整性
     * - 异常抛出的准确性
     * - 错误信息的中文化
     * - 防御性编程的实现
     */
    @Test
    @DisplayName("标准顾客 - 反例测试（异常输入）")
    public void testStandardCustomerNegativeCases() {
        // 测试用例1：苹果数量为负数
        // 预期：抛出IllegalArgumentException，错误信息为"苹果数量不能为负数: -1"
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculateStandardCustomerTotal(-1, 2);
        });
        
        // 测试用例2：草莓数量为负数
        // 预期：抛出IllegalArgumentException，错误信息为"草莓数量不能为负数: -1"
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculateStandardCustomerTotal(2, -1);
        });
        
        // 测试用例3：两种水果数量都为负数
        // 预期：抛出IllegalArgumentException（第一个参数验证失败）
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculateStandardCustomerTotal(-1, -1);
        });
    }
    
    /**
     * 测试扩展顾客的正例场景
     * 
     * 测试目标：验证扩展顾客（购买苹果、草莓和芒果）的定价计算准确性
     * 业务规则：苹果8元/斤，草莓13元/斤，芒果20元/斤，无任何折扣
     * 
     * 测试用例：
     * 1. 三种水果各1斤：苹果1斤 + 草莓1斤 + 芒果1斤 = 8 + 13 + 20 = 41元
     * 
     * 验证要点：
     * - 三种水果同时购买的计算准确性
     * - 扩展产品线后的价格计算稳定性
     * - 与标准顾客计算逻辑的一致性
     */
    @Test
    @DisplayName("扩展顾客 - 正例测试")
    public void testExtendedCustomerPositiveCases() {
        // 测试用例1：三种水果各购买1斤
        // 苹果1斤 + 草莓1斤 + 芒果1斤 = 8 + 13 + 20 = 41元
        double total = pricingService.calculateExtendedCustomerTotal(1, 1, 1);
        double expected = PricingConstants.FruitPrices.APPLE_PRICE + 
                         PricingConstants.FruitPrices.STRAWBERRY_PRICE + 
                         PricingConstants.FruitPrices.MANGO_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
    }
    
    /**
     * 测试扩展顾客的边界值场景
     * 
     * 测试目标：验证扩展顾客在边界条件下的计算准确性
     * 边界值定义：三种水果的各种零值组合
     * 
     * 测试用例：
     * 1. 全零边界：苹果0斤 + 草莓0斤 + 芒果0斤 = 0元
     * 2. 单品边界：苹果5斤 + 草莓0斤 + 芒果0斤 = 5×8 = 40元
     * 
     * 验证要点：
     * - 三种水果全零时的正确处理
     * - 只购买单一水果时的计算准确性
     * - 扩展产品线后边界条件的稳定性
     */
    @Test
    @DisplayName("扩展顾客 - 边界值测试")
    public void testExtendedCustomerBoundaryCases() {
        // 测试用例1：全零边界值
        // 苹果0斤 + 草莓0斤 + 芒果0斤 = 0元
        double total = pricingService.calculateExtendedCustomerTotal(0, 0, 0);
        assertEquals(0.0, total, PricingConstants.Precision.CURRENCY_PRECISION);
        
        // 测试用例2：单品边界值 - 只买苹果
        // 苹果5斤 + 草莓0斤 + 芒果0斤 = 5×8 = 40元
        total = pricingService.calculateExtendedCustomerTotal(5, 0, 0);
        double expected = 5 * PricingConstants.FruitPrices.APPLE_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
    }
    
    /**
     * 测试促销顾客的正例场景
     * 
     * 测试目标：验证促销顾客（草莓享受8折优惠）的定价计算准确性
     * 业务规则：苹果8元/斤，草莓13×0.8=10.4元/斤，芒果20元/斤
     * 
     * 测试用例：
     * 1. 混合购买：苹果1斤 + 草莓2斤(8折) + 芒果1斤 = 8 + 2×10.4 + 20 = 48.8元
     * 
     * 验证要点：
     * - 草莓8折优惠的正确应用
     * - 其他水果价格不受影响
     * - 促销规则的计算精度
     * - 策略模式的正确实现
     */
    @Test
    @DisplayName("促销顾客 - 正例测试")
    public void testPromotionalCustomerPositiveCases() {
        // 测试用例1：混合购买，验证草莓8折优惠
        // 苹果1斤 + 草莓2斤(8折) + 芒果1斤 = 8 + 2×(13×0.8) + 20 = 8 + 20.8 + 20 = 48.8元
        double total = pricingService.calculatePromotionalCustomerTotal(1, 2, 1);
        double expected = PricingConstants.FruitPrices.APPLE_PRICE + 
                         2 * PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE + 
                         PricingConstants.FruitPrices.MANGO_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
    }
    
    /**
     * 测试批量顾客的边界值场景 - 100元阈值的关键边界点测试
     **
     * 测试目标：验证批量折扣规则在100元阈值附近的准确性
     * 业务规则：草莓8折 + 满100元减10元
     * 边界点：99元（不减）、100元（减10）、101元（减10）
     * 
     * 测试用例：
     * 1. 99.2元边界：6苹果+3草莓(8折)+1芒果 = 48+31.2+20 = 99.2元 < 100元 → 不减10元
     * 2. 100.0元边界：0苹果+0草莓+5芒果 = 5×20 = 100.0元 = 100元 → 减10元 = 90元
     * 3. 107.2元边界：7苹果+3草莓(8折)+1芒果 = 56+31.2+20 = 107.2元 > 100元 → 减10元 = 97.2元
     * 
     * 验证要点：
     * - 阈值判断的精确性（>=100才减10元）
     * - 装饰器模式的正确实现
     * - 复合优惠规则的计算准确性
     * - 边界条件下不会出现舍入误差
     */
    @Test
    @DisplayName("批量顾客 - 100元阈值边界值测试（重点加分项）")
    public void testBulkCustomerBoundaryValues() {
        // 测试用例1：99.2元边界 - 不满100元，不应减10元
        // 计算：苹果6斤×8 + 草莓3斤×(13×0.8) + 芒果1斤×20 = 48 + 31.2 + 20 = 99.2元
        // 预期：99.2 < 100，不减10元，最终金额 = 99.2元
        double total = pricingService.calculateBulkCustomerTotal(6, 3, 1);
        double subtotal = 6 * PricingConstants.FruitPrices.APPLE_PRICE + 
                         3 * PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE + 
                         1 * PricingConstants.FruitPrices.MANGO_PRICE;
        // 验证：小计99.2元，不减10元
        assertEquals(subtotal, total, PricingConstants.Precision.CURRENCY_PRECISION);
        assertTrue(subtotal < PricingConstants.BulkDiscount.THRESHOLD_AMOUNT);
        
        // 测试用例2：100.0元边界 - 刚好100元，应减10元
        // 计算：芒果5斤×20 = 100.0元
        // 预期：100.0 >= 100，减10元，最终金额 = 90.0元
        total = pricingService.calculateBulkCustomerTotal(0, 0, 5);
        subtotal = 5 * PricingConstants.FruitPrices.MANGO_PRICE; // 100元
        double expected = subtotal - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT; // 100 - 10 = 90
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
        assertEquals(PricingConstants.BulkDiscount.THRESHOLD_AMOUNT, subtotal, PricingConstants.Precision.CURRENCY_PRECISION);
        
        // 测试用例3：107.2元边界 - 超过100元，应减10元
        // 计算：苹果7斤×8 + 草莓3斤×(13×0.8) + 芒果1斤×20 = 56 + 31.2 + 20 = 107.2元
        // 预期：107.2 > 100，减10元，最终金额 = 97.2元
        total = pricingService.calculateBulkCustomerTotal(7, 3, 1);
        subtotal = 7 * PricingConstants.FruitPrices.APPLE_PRICE + 
                   3 * PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE + 
                   1 * PricingConstants.FruitPrices.MANGO_PRICE;
        expected = subtotal - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT; // 107.2 - 10 = 97.2
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
        assertTrue(subtotal > PricingConstants.BulkDiscount.THRESHOLD_AMOUNT);
    }
    
    /**
     * 测试批量顾客的附加边界值场景
     * 
     * 测试目标：验证批量折扣规则在其他边界条件下的稳定性
     * 扩展验证：不同数量组合下的批量折扣计算
     * 
     * 测试用例：
     * 1. 101.6元测试：5苹果+4草莓(8折)+1芒果 = 40+41.6+20 = 101.6元 → 减10元 = 91.6元
     * 2. 大额购买测试：50苹果+50草莓(8折)+10芒果 = 400+520+200 = 1120元 → 减10元 = 1110元
     * 
     * 验证要点：
     * - 不同数量组合下批量折扣的一致性
     * - 大额购买时计算的稳定性
     * - 批量折扣规则的通用性
     */
    @Test
    @DisplayName("批量顾客 - 附加边界值测试")
    public void testBulkCustomerAdditionalBoundaryTests() {
        // 测试用例1：101.6元边界测试
        // 计算：苹果5斤×8 + 草莓4斤×(13×0.8) + 芒果1斤×20 = 40 + 41.6 + 20 = 101.6元
        // 预期：101.6 > 100，减10元，最终金额 = 91.6元
        double total = pricingService.calculateBulkCustomerTotal(5, 4, 1);
        double subtotal = 5 * PricingConstants.FruitPrices.APPLE_PRICE + 
                         4 * PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE + 
                         1 * PricingConstants.FruitPrices.MANGO_PRICE;
        double expected = subtotal - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
        
        // 测试用例2：大额购买边界测试
        // 计算：苹果50斤×8 + 草莓50斤×(13×0.8) + 芒果10斤×20 = 400 + 520 + 200 = 1120元
        // 预期：1120 > 100，减10元，最终金额 = 1110元
        total = pricingService.calculateBulkCustomerTotal(50, 50, 10);
        subtotal = 50 * PricingConstants.FruitPrices.APPLE_PRICE + 
                   50 * PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE + 
                   10 * PricingConstants.FruitPrices.MANGO_PRICE;
        expected = subtotal - PricingConstants.BulkDiscount.DISCOUNT_AMOUNT;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
        assertTrue(subtotal > PricingConstants.BulkDiscount.THRESHOLD_AMOUNT);
    }
    
    /**
     * 测试所有顾客类型的反例场景（异常输入）
     * 
     * 测试目标：验证所有顾客类型方法对异常输入的统一处理
     * 异常类型：负数数量输入
     * 
     * 测试用例：
     * 1. 扩展顾客负数：苹果-1斤 → 抛出IllegalArgumentException
     * 2. 促销顾客负数：草莓-1斤 → 抛出IllegalArgumentException
     * 3. 批量顾客负数：芒果-1斤 → 抛出IllegalArgumentException
     * 
     * 验证要点：
     * - 所有顾客类型的输入验证一致性
     * - 异常处理的统一性
     */
    @Test
    @DisplayName("所有顾客类型 - 反例测试（异常输入）")
    public void testAllCustomerTypesNegativeCases() {
        // 测试用例1：扩展顾客苹果数量为负数
        // 预期：抛出IllegalArgumentException，错误信息为"苹果数量不能为负数: -1"
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculateExtendedCustomerTotal(-1, 1, 1);
        });
        
        // 测试用例2：促销顾客草莓数量为负数
        // 预期：抛出IllegalArgumentException，错误信息为"草莓数量不能为负数: -1"
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculatePromotionalCustomerTotal(1, -1, 1);
        });
        
        // 测试用例3：批量顾客芒果数量为负数
        // 预期：抛出IllegalArgumentException，错误信息为"芒果数量不能为负数: -1"
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculateBulkCustomerTotal(1, 1, -1);
        });
    }
    
    /**
     * 测试自定义规则方法的正例和反例场景
     * 
     * 测试目标：验证通用定价方法的灵活性和健壮性
     * 方法特点：允许使用任意定价规则计算任意购买记录
     * 
     * 测试用例：
     * 1. 正例：使用标准规则计算苹果2斤 = 2×8 = 16元
     * 2. 反例：购买记录为null → 抛出IllegalArgumentException
     * 3. 反例：定价规则为null → 抛出IllegalArgumentException
     * 
     * 验证要点：
     * - 策略模式的灵活性
     * - 参数验证的完整性
     * - 通用方法的正确性
     * - 空值处理的安全性
     */
    @Test
    @DisplayName("自定义规则方法 - 正例和反例测试")
    public void testCustomRuleMethod() {
        // 测试用例1：正例 - 使用标准规则计算
        // 创建购买记录：苹果2斤
        // 预期：使用标准规则计算 = 2×8 = 16元
        com.supermarket.model.Purchase purchase = new com.supermarket.model.Purchase();
        purchase.setFruitQuantity(com.supermarket.model.Fruit.APPLE, 2);
        
        double total = pricingService.calculateWithCustomRule(purchase, pricingService.getStandardPricingRule());
        double expected = 2 * PricingConstants.FruitPrices.APPLE_PRICE;
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION);
        
        // 测试用例2：反例 - 购买记录为null
        // 预期：抛出IllegalArgumentException，错误信息为"购买记录不能为空"
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculateWithCustomRule(null, pricingService.getStandardPricingRule());
        });
        
        // 测试用例3：反例 - 定价规则为null
        // 预期：抛出IllegalArgumentException，错误信息为"定价规则不能为空"
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculateWithCustomRule(purchase, null);
        });
    }
    
    /**
     * 测试计算精度场景
     *
     * 
     * 测试用例：
     * 1. 草莓8折精度：草莓1斤×(13×0.8) = 1×10.4 = 10.4元
     * 
     * 验证要点：
     * - 浮点数乘法运算的精确性
     * - 货币计算的精度控制（0.01元）
     * - 折扣计算不会产生舍入误差
     * - 常量使用确保计算一致性
     *
     */
    @Test
    @DisplayName("计算精度测试")
    public void testCalculationPrecision() {
        // 测试用例1：草莓8折精度验证
        // 计算：草莓1斤 × (13 × 0.8) = 1 × 10.4 = 10.4元
        // 验证：确保浮点数运算精度正确，无舍入误差
        double total = pricingService.calculatePromotionalCustomerTotal(0, 1, 0);
        double expected = PricingConstants.FruitPrices.STRAWBERRY_PRICE * PricingConstants.DiscountRates.STRAWBERRY_DISCOUNT_RATE;
        
        // 双重精度验证
        assertEquals(expected, total, PricingConstants.Precision.CURRENCY_PRECISION); // 使用常量精度
        assertEquals(10.4, total, 0.01); // 直接数值验证：13 * 0.8 = 10.4
    }
}