package com.supermarket.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 增强的定价系统常量类
 * 使用BigDecimal确保货币计算精度，支持灵活的水果和折扣配置
 * 
 * @author SupermarketPricingSystem
 * @version 2.0
 */
public final class EnhancedPricingConstants {
    
    /**
     * 私有构造函数，防止实例化
     */
    private EnhancedPricingConstants() {
        throw new UnsupportedOperationException("常量类不能被实例化");
    }
    
    /**
     * 货币计算精度设置
     */
    public static final class MoneyPrecision {
        /** 货币计算精度：2位小数 */
        public static final int DECIMAL_PLACES = 2;
        
        /** 货币计算舍入模式：四舍五入 */
        public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
        
        /** 货币比较精度 */
        public static final BigDecimal COMPARISON_PRECISION = new BigDecimal("0.01");
        
        private MoneyPrecision() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
    
    /**
     * 水果基础价格常量（使用BigDecimal）
     */
    public static final class FruitPrices {
        /** 苹果价格：8.00元/斤 */
        public static final BigDecimal APPLE_PRICE = new BigDecimal("8.00");
        
        /** 草莓价格：13.00元/斤 */
        public static final BigDecimal STRAWBERRY_PRICE = new BigDecimal("13.00");
        
        /** 芒果价格：20.00元/斤 */
        public static final BigDecimal MANGO_PRICE = new BigDecimal("20.00");
        
        private FruitPrices() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
    
    /**
     * 促销折扣常量
     */
    public static final class DiscountRates {
        /** 草莓促销折扣率：0.8 (8折) */
        public static final BigDecimal STRAWBERRY_DISCOUNT_RATE = new BigDecimal("0.8");
        
        /** 通用折扣率：0.9 (9折) */
        public static final BigDecimal GENERAL_DISCOUNT_RATE = new BigDecimal("0.9");
        
        /** 会员折扣率：0.85 (85折) */
        public static final BigDecimal MEMBER_DISCOUNT_RATE = new BigDecimal("0.85");
        
        private DiscountRates() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
    
    /**
     * 批量折扣常量
     */
    public static final class BulkDiscount {
        /** 批量折扣阈值：100.00元 */
        public static final BigDecimal THRESHOLD_AMOUNT = new BigDecimal("100.00");
        
        /** 批量折扣金额：10.00元 */
        public static final BigDecimal DISCOUNT_AMOUNT = new BigDecimal("10.00");
        
        private BulkDiscount() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
}