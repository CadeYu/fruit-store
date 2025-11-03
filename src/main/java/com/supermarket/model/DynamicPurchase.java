package com.supermarket.model;

import com.supermarket.constants.EnhancedPricingConstants;
import com.supermarket.service.ProductCatalog;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 动态购买记录类
 * 支持任意商品的购买记录，完全解决扩展性问题
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
public class DynamicPurchase {
    
    /** 商品数量映射表，键为商品ID，值为购买数量（斤） */
    private final Map<String, Integer> productQuantities;
    
    /** 商品目录引用 */
    private final ProductCatalog productCatalog;
    
    /**
     * 构造函数
     * 
     * @param productCatalog 商品目录，不能为null
     * @throws IllegalArgumentException 如果商品目录为null
     */
    public DynamicPurchase(final ProductCatalog productCatalog) {
        if (productCatalog == null) {
            throw new IllegalArgumentException("商品目录不能为空");
        }
        
        this.productCatalog = productCatalog;
        this.productQuantities = new HashMap<>();
    }
    
    /**
     * 添加商品到购买记录
     * 如果该商品已存在，则累加数量
     * 
     * @param productId 商品ID，不能为null或空字符串
     * @param quantityToAdd 要添加的数量（斤），必须大于等于0
     * @return 当前购买记录对象，支持链式调用
     * @throws IllegalArgumentException 如果参数无效或商品不存在
     */
    public DynamicPurchase addProduct(final String productId, final int quantityToAdd) {
        validateProductId(productId);
        validateQuantity(quantityToAdd);
        validateProductExists(productId);
        
        final int currentQuantity = productQuantities.getOrDefault(productId, 0);
        productQuantities.put(productId, currentQuantity + quantityToAdd);
        return this;
    }
    
    /**
     * 设置特定商品的数量
     * 如果数量为0，则从购买记录中移除该商品
     * 
     * @param productId 商品ID，不能为null或空字符串
     * @param quantityToSet 要设置的数量（斤），必须大于等于0
     * @return 当前购买记录对象，支持链式调用
     * @throws IllegalArgumentException 如果参数无效或商品不存在
     */
    public DynamicPurchase setProductQuantity(final String productId, final int quantityToSet) {
        validateProductId(productId);
        validateQuantity(quantityToSet);
        validateProductExists(productId);
        
        if (quantityToSet == 0) {
            productQuantities.remove(productId);
        } else {
            productQuantities.put(productId, quantityToSet);
        }
        return this;
    }
    
    /**
     * 获取特定商品的数量
     * 
     * @param productId 商品ID
     * @return 数量（斤），如果没有购买该商品则返回0
     */
    public int getProductQuantity(final String productId) {
        return productQuantities.getOrDefault(productId, 0);
    }
    
    /**
     * 获取所有购买的商品ID
     * 
     * @return 商品ID集合的只读视图
     */
    public Set<String> getPurchasedProductIds() {
        return productQuantities.keySet();
    }
    
    /**
     * 检查是否为空购买记录
     * 
     * @return 如果没有购买任何商品返回true，否则返回false
     */
    public boolean isEmpty() {
        return productQuantities.isEmpty();
    }
    
    /**
     * 获取购买记录的副本
     * 
     * @return 数量映射的不可变副本
     */
    public Map<String, Integer> getProductQuantitiesMap() {
        return new HashMap<>(productQuantities);
    }
    
    /**
     * 计算购买记录的基础总价（不含任何折扣）
     * 
     * @return 基础总价
     */
    public BigDecimal calculateBaseTotal() {
        BigDecimal total = BigDecimal.ZERO;
        
        for (final Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            final String productId = entry.getKey();
            final int quantity = entry.getValue();
            
            final Product product = productCatalog.getProduct(productId)
                .orElseThrow(() -> new IllegalStateException("商品不存在: " + productId));
            
            final BigDecimal subtotal = product.calculateSubtotal(quantity);
            total = total.add(subtotal);
        }
        
        return total.setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES, 
                            EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
    }
    
    /**
     * 根据折扣配置计算总价
     * 
     * @param discountConfig 折扣配置
     * @return 折扣后总价
     */
    public BigDecimal calculateTotalWithDiscount(final DynamicDiscountConfig discountConfig) {
        BigDecimal total = BigDecimal.ZERO;
        
        for (final Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            final String productId = entry.getKey();
            final int quantity = entry.getValue();
            
            final Product product = productCatalog.getProduct(productId)
                .orElseThrow(() -> new IllegalStateException("商品不存在: " + productId));
            
            final BigDecimal discountRate = discountConfig.getDiscountRate(productId);
            final BigDecimal subtotal = product.calculateDiscountedSubtotal(quantity, discountRate);
            total = total.add(subtotal);
        }
        
        return total.setScale(EnhancedPricingConstants.MoneyPrecision.DECIMAL_PLACES, 
                            EnhancedPricingConstants.MoneyPrecision.ROUNDING_MODE);
    }
    
    /**
     * 获取商品目录
     * 
     * @return 商品目录
     */
    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }
    
    /**
     * 验证商品ID的有效性
     */
    private void validateProductId(final String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
    }
    
    /**
     * 验证数量的有效性
     */
    private void validateQuantity(final int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("商品数量不能为负数: " + quantity);
        }
    }
    
    /**
     * 验证商品是否存在于目录中
     */
    private void validateProductExists(final String productId) {
        if (!productCatalog.hasProduct(productId)) {
            throw new IllegalArgumentException("商品不存在于目录中: " + productId);
        }
    }
    
    /**
     * 返回购买记录的字符串表示
     * 
     * @return 购买记录的格式化字符串
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "空购买记录";
        }
        
        final StringBuilder purchaseDescription = new StringBuilder("购买记录: ");
        for (final Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            final String productId = entry.getKey();
            final int quantity = entry.getValue();
            
            final Product product = productCatalog.getProduct(productId).orElse(null);
            final String productName = (product != null) ? product.getChineseName() : productId;
            
            purchaseDescription.append(productName)
                              .append(" ")
                              .append(quantity)
                              .append("斤, ");
        }
        
        // 移除最后的逗号和空格
        final int descriptionLength = purchaseDescription.length();
        if (descriptionLength > 2) {
            purchaseDescription.setLength(descriptionLength - 2);
        }
        
        return purchaseDescription.toString();
    }
}