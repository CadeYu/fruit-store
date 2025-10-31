package com.supermarket.constants;

/**
 * 定价系统常量类
 * 包含所有系统中使用的常量值，避免魔法数字
 * 
 * @author SupermarketPricingSystem
 * @version 1.0
 */
public final class PricingConstants {
    
    /**
     * 私有构造函数，防止实例化
     */
    private PricingConstants() {
        throw new UnsupportedOperationException("常量类不能被实例化");
    }
    
    /**
     * 水果基础价格常量
     */
    public static final class FruitPrices {
        /** 苹果价格：8.0元/斤 */
        public static final double APPLE_PRICE = 8.0;
        
        /** 草莓价格：13.0元/斤 */
        public static final double STRAWBERRY_PRICE = 13.0;
        
        /** 芒果价格：20.0元/斤 */
        public static final double MANGO_PRICE = 20.0;
        
        private FruitPrices() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
    
    /**
     * 促销折扣常量
     */
    public static final class DiscountRates {
        /** 草莓促销折扣率：0.8 (8折) */
        public static final double STRAWBERRY_DISCOUNT_RATE = 0.8;
        
        private DiscountRates() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
    
    /**
     * 批量折扣常量
     */
    public static final class BulkDiscount {
        /** 批量折扣阈值：100.0元 */
        public static final double THRESHOLD_AMOUNT = 100.0;
        
        /** 批量折扣金额：10.0元 */
        public static final double DISCOUNT_AMOUNT = 10.0;
        
        private BulkDiscount() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
    
    /**
     * 数值精度常量
     */
    public static final class Precision {
        /** 货币计算精度：0.01 */
        public static final double CURRENCY_PRECISION = 0.01;
        
        private Precision() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
    
    /**
     * 顾客类型常量
     */
    public static final class CustomerTypes {
        /** 标准顾客 - 苹果和草莓 */
        public static final String STANDARD_CUSTOMER = "STANDARD";
        
        /** 扩展顾客 - 三种水果 */
        public static final String EXTENDED_CUSTOMER = "EXTENDED";
        
        /** 促销顾客 - 草莓8折 */
        public static final String PROMOTIONAL_CUSTOMER = "PROMOTIONAL";
        
        /** 批量顾客 - 草莓8折+满100减10 */
        public static final String BULK_CUSTOMER = "BULK";
        
        private CustomerTypes() {
            throw new UnsupportedOperationException("常量类不能被实例化");
        }
    }
}