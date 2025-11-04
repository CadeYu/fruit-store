

## 功能特性

- **完全动态的商品管理**：
  - 运行时添加任意新商品种类（水果、蔬菜、肉类等）
  - 动态修改商品价格
  - 灵活的商品分类管理

- **灵活的折扣配置**：
  - 任意商品任意折扣率配置
  - 多商品同时打折支持
  - 预定义促销场景（黑色星期五、会员专享等）
  - 批量折扣规则（满额减免）

- **BigDecimal精确计算**：
  - 避免浮点数精度问题
  - 统一的舍入策略
  - 货币计算精度保证

- **设计模式**：
  - 策略模式 (Strategy Pattern) - 动态定价规则
  - 装饰器模式 (Decorator Pattern) - 批量折扣
  - 工厂模式 (Factory Pattern) - 预定义促销配置


## 项目结构

```
src/main/java/com/supermarket/
├── constants/               # 常量类
│   └── EnhancedPricingConstants.java # BigDecimal定价常量
├── model/                   # 数据模型
│   ├── Product.java         # 动态商品类
│   ├── DynamicPurchase.java # 动态购买记录
│   └── DynamicDiscountConfig.java # 动态折扣配置
├── rule/                   # 定价规则（策略模式）
│   ├── DynamicPricingRule.java      # 动态定价规则接口
│   ├── DynamicPromotionRule.java    # 动态促销规则
│   └── DynamicBulkDiscountRule.java # 动态批量折扣（装饰器模式）
├── service/                # 服务层
│   └── ProductCatalog.java # 商品目录管理服务
└── DynamicSupermarketDemo.java # 动态系统演示程序
```

## 运行方式

### 1. 编译项目
```bash
mvn compile
```

### 2. 运行动态系统演示程序
```bash
mvn exec:java -Dexec.mainClass="com.supermarket.DynamicSupermarketDemo"
```

### 4. 运行单元测试
```bash
mvn test
```

## 测试覆盖

项目包含6个完整的动态系统测试，重点关注：
- **动态商品扩展测试**：验证运行时添加新商品种类的能力
- **灵活折扣配置测试**：验证任意商品任意折扣率配置
- **复杂促销场景测试**：验证黑色星期五等复杂促销场景
- **批量折扣边界测试**：验证动态系统中的批量折扣边界条件
- **运行时管理测试**：验证商品的动态管理能力
- **终极扩展性测试**：验证全新商品类别（如蔬菜类）的支持

## 系统要求

- Java 11+
- Maven 3.6+

## 示例输出

```
=== 🚀 动态超市定价系统演示 - 彻底解决扩展性问题 ===

📦 【演示1：动态商品扩展能力】
初始商品目录：0种商品
动态添加后：5种商品
商品目录 (共5种商品):
  Product{id='DURIAN', name='榴莲(Durian)', price=50.00, category='热带水果'}
  Product{id='DRAGON_FRUIT', name='火龙果(Dragon Fruit)', price=25.00, category='热带水果'}
  Product{id='AVOCADO', name='牛油果(Avocado)', price=35.00, category='热带水果'}
  Product{id='BLUEBERRY', name='蓝莓(Blueberry)', price=45.00, category='浆果类'}
  Product{id='CHERRY', name='樱桃(Cherry)', price=40.00, category='核果类'}

购买记录：购买记录: 榴莲 1斤, 火龙果 2斤, 蓝莓 1斤
总价：145.00元
✅ 新商品添加和购买测试成功！

💰 【演示2：灵活折扣配置能力】
场景1：草莓专享8折
场景2：多水果不同折扣 (苹果9折、草莓8折、芒果85折、橙子7折、香蕉6折)
折扣后总价：91.80元
✅ 多商品灵活折扣测试成功！

🛍️ 【演示3：复杂促销场景 - 黑色星期五】
原价：446.00元
促销价：248.00元
最终价格（含批量折扣）：233.00元
总共节省：213.00元
✅ 复杂促销场景测试成功！

🔧 【演示4：运行时商品管理能力】
✅ 运行时商品管理测试成功！

=== ✅ 演示完成：系统扩展性问题已彻底解决！ ===
```
