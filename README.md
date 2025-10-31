

## 功能特性

- **四种顾客类型**：
  - 标准顾客 (Standard Customer)：苹果和草莓标准定价
  - 扩展顾客 (Extended Customer)：苹果、草莓和芒果标准定价  
  - 促销顾客 (Promotional Customer)：草莓8折优惠
  - 批量顾客 (Bulk Customer)：草莓8折 + 满100减10元

- **水果价格**：
  - 苹果 (Apple)：8元/斤
  - 草莓 (Strawberry)：13元/斤（促销时10.4元/斤）
  - 芒果 (Mango)：20元/斤

- **设计模式**：
  - 策略模式 (Strategy Pattern) - 定价规则
  - 装饰器模式 (Decorator Pattern) - 批量折扣
  - 枚举模式 (Enum Pattern) - 水果类型


## 项目结构

```
src/main/java/com/supermarket/
├── constants/               # 常量类
│   └── PricingConstants.java        # 定价常量（避免魔法数字）
├── model/                   # 数据模型
│   ├── Fruit.java          # 水果枚举（英文+中文名称）
│   └── Purchase.java       # 购买记录
├── rule/                   # 定价规则（策略模式）
│   ├── PricingRule.java    # 定价规则接口
│   ├── StandardPricingRule.java     # 标准定价
│   ├── StrawberryPromotionRule.java # 草莓促销
│   └── BulkDiscountRule.java        # 批量折扣（装饰器模式）
├── service/                # 服务层
│   └── PricingService.java # 定价服务
└── ImprovedSupermarketPricingApplication.java # 改进的主程序
```

## 运行方式

### 1. 编译项目
```bash
mvn compile
```

### 2. 运行改进的主程序演示
```bash
mvn exec:java -Dexec.mainClass="com.supermarket.ImprovedSupermarketPricingApplication"
```

### 4. 运行单元测试
```bash
mvn test
```

## 测试覆盖

项目包含11个改进的单元测试，重点关注：
- **正例测试**：正常业务场景
- **反例测试**：异常输入验证
- **边界值测试**：
  - 99.2元（不满100元，不减10元）
  - 100.0元（刚好100元，减10元）
  - 107.2元（超过100元，减10元）
- **精度测试**：货币计算精度验证
- **零值测试**：边界条件处理

## 系统要求

- Java 11+
- Maven 3.6+

## 示例输出

```
=== Improved Supermarket Pricing System Demo ===

【Standard Customer】Apple: 8.0 yuan/jin, Strawberry: 13.0 yuan/jin
Apple 2 jin + Strawberry 1 jin = 29.00 yuan (Expected: 29.00 yuan) ✓
Apple 3 jin + Strawberry 2 jin = 50.00 yuan (Expected: 50.00 yuan) ✓

【Extended Customer】Apple: 8.0 yuan/jin, Strawberry: 13.0 yuan/jin, Mango: 20.0 yuan/jin
Apple 1 jin + Strawberry 1 jin + Mango 1 jin = 41.00 yuan (Expected: 41.00 yuan) ✓
Apple 2 jin + Strawberry 3 jin + Mango 1 jin = 75.00 yuan (Expected: 75.00 yuan) ✓

【Promotional Customer】Apple: 8.0 yuan/jin, Strawberry: 10.4 yuan/jin (20% off), Mango: 20.0 yuan/jin
Apple 1 jin + Strawberry 2 jin + Mango 1 jin = 48.80 yuan (Expected: 48.80 yuan) ✓
Apple 3 jin + Strawberry 4 jin + Mango 2 jin = 105.60 yuan (Expected: 105.60 yuan) ✓

【Bulk Customer】Apple: 8.0 yuan/jin, Strawberry: 10.4 yuan/jin (20% off), Mango: 20.0 yuan/jin, 10 yuan off when total >= 100 yuan
Apple 2 jin + Strawberry 2 jin + Mango 1 jin = 56.80 yuan (Subtotal: 56.80 yuan, Expected: 56.80 yuan) ✓
Apple 0 jin + Strawberry 0 jin + Mango 5 jin = 90.00 yuan (Subtotal: 100.00 yuan, Expected: 90.00 yuan) ✓
Apple 3 jin + Strawberry 4 jin + Mango 2 jin = 95.60 yuan (Subtotal: 105.60 yuan, Expected: 95.60 yuan) ✓

【Boundary Condition Tests】
Zero quantity test: 0.00 yuan (Expected: 0.00 yuan) ✓
Bulk discount boundary test (< 100): 99.20 yuan (Subtotal: 99.20 yuan, Expected: 99.20 yuan) ✓
Bulk discount boundary test (= 100): 90.00 yuan (Subtotal: 100.00 yuan, Expected: 90.00 yuan) ✓
Bulk discount boundary test (> 100): 97.20 yuan (Subtotal: 107.20 yuan, Expected: 97.20 yuan) ✓

=== Demo Completed Successfully ===
```
