package com.supermarket.service;

import com.supermarket.model.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 商品目录服务
 * 支持动态管理商品，完全解决扩展性问题
 * 
 * @author SupermarketPricingSystem
 * @version 3.0
 */
public class ProductCatalog {
    
    /** 商品存储映射表 */
    private final Map<String, Product> products;
    
    /**
     * 构造函数
     */
    public ProductCatalog() {
        this.products = new HashMap<>();
        initializeDefaultProducts();
    }
    
    /**
     * 添加商品到目录
     * 
     * @param product 要添加的商品，不能为null
     * @return 当前目录对象，支持链式调用
     * @throws IllegalArgumentException 如果商品为null
     */
    public ProductCatalog addProduct(final Product product) {
        if (product == null) {
            throw new IllegalArgumentException("商品不能为空");
        }
        
        products.put(product.getProductId(), product);
        return this;
    }
    
    /**
     * 创建并添加商品的便捷方法
     * 
     * @param productId 商品ID
     * @param englishName 英文名称
     * @param chineseName 中文名称
     * @param basePrice 基础价格
     * @param category 商品类别
     * @return 当前目录对象，支持链式调用
     */
    public ProductCatalog addProduct(final String productId,
                                   final String englishName,
                                   final String chineseName,
                                   final BigDecimal basePrice,
                                   final String category) {
        final Product product = new Product(productId, englishName, chineseName, basePrice, category);
        return addProduct(product);
    }
    
    /**
     * 创建并添加商品的便捷方法（无类别）
     */
    public ProductCatalog addProduct(final String productId,
                                   final String englishName,
                                   final String chineseName,
                                   final BigDecimal basePrice) {
        return addProduct(productId, englishName, chineseName, basePrice, null);
    }
    
    /**
     * 根据商品ID获取商品
     * 
     * @param productId 商品ID
     * @return 商品对象的Optional包装，如果不存在则为空
     */
    public Optional<Product> getProduct(final String productId) {
        return Optional.ofNullable(products.get(productId));
    }
    
    /**
     * 检查商品是否存在
     * 
     * @param productId 商品ID
     * @return 如果存在返回true，否则返回false
     */
    public boolean hasProduct(final String productId) {
        return products.containsKey(productId);
    }
    
    /**
     * 移除商品
     * 
     * @param productId 要移除的商品ID
     * @return 被移除的商品，如果不存在则返回null
     */
    public Product removeProduct(final String productId) {
        return products.remove(productId);
    }
    
    /**
     * 获取所有商品
     * 
     * @return 所有商品的集合
     */
    public Collection<Product> getAllProducts() {
        return products.values();
    }
    
    /**
     * 获取商品数量
     * 
     * @return 目录中的商品总数
     */
    public int getProductCount() {
        return products.size();
    }
    
    /**
     * 清空所有商品
     */
    public void clearAllProducts() {
        products.clear();
    }
    
    /**
     * 更新商品价格
     * 
     * @param productId 商品ID
     * @param newPrice 新价格
     * @return 如果更新成功返回true，如果商品不存在返回false
     */
    public boolean updateProductPrice(final String productId, final BigDecimal newPrice) {
        final Product existingProduct = products.get(productId);
        if (existingProduct == null) {
            return false;
        }
        
        // 创建新的商品对象（因为Product是不可变的）
        final Product updatedProduct = new Product(
            existingProduct.getProductId(),
            existingProduct.getEnglishName(),
            existingProduct.getChineseName(),
            newPrice,
            existingProduct.getCategory()
        );
        
        products.put(productId, updatedProduct);
        return true;
    }
    
    /**
     * 初始化默认商品（保持向后兼容）
     */
    private void initializeDefaultProducts() {
        // 添加默认的水果商品
        addProduct("APPLE", "Apple", "苹果", new BigDecimal("8.00"), "水果");
        addProduct("STRAWBERRY", "Strawberry", "草莓", new BigDecimal("13.00"), "水果");
        addProduct("MANGO", "Mango", "芒果", new BigDecimal("20.00"), "水果");
    }
    
    /**
     * 创建预定义的商品目录工厂方法
     */
    public static ProductCatalog createDefaultCatalog() {
        return new ProductCatalog();
    }
    
    /**
     * 创建扩展商品目录的工厂方法
     */
    public static ProductCatalog createExtendedCatalog() {
        final ProductCatalog catalog = new ProductCatalog();
        
        // 添加更多商品种类
        catalog.addProduct("ORANGE", "Orange", "橙子", new BigDecimal("12.00"), "水果")
               .addProduct("BANANA", "Banana", "香蕉", new BigDecimal("6.00"), "水果")
               .addProduct("GRAPE", "Grape", "葡萄", new BigDecimal("15.00"), "水果")
               .addProduct("PEAR", "Pear", "梨", new BigDecimal("9.00"), "水果")
               .addProduct("WATERMELON", "Watermelon", "西瓜", new BigDecimal("3.00"), "水果");
        
        return catalog;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("商品目录 (共" + products.size() + "种商品):\n");
        for (final Product product : products.values()) {
            sb.append("  ").append(product).append("\n");
        }
        return sb.toString();
    }
}