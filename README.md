- [坤吧管理系统🐥](#--------)
    * [环境部署](#----)
    * [技术选型](#----)
    * [编码规范](#----)
        + [注释](#--)
        + [变量](#--)
    * [结构层级](#----)
    * [测试](#--)
    * [Dockerfile镜像的一些坑](#dockerfile------)
        + [openjdk:8-jdk-alpine过于精简？](#openjdk-8-jdk-alpine-----)
    * [参考文献](#----)
# 坤吧管理系统🐥


> 功能抽卡 无限制抽卡

为了减轻抽奖接口压力可以把抽奖结果使用消息队列把数据转存到数据库

## 环境部署

- [x] jdk1.8
- [x] mysql8.0
- [x] docker
- [x] redis 6.2.6
- [x] rabbitmq
1. 不想搭建可以让别人给搭建(既然最求刺激那就贯彻到底喽)
2. **dockerfile使用之前记得修改一下docker的配置文件**

## 技术选型

- [x] Spring Boot 2.7.7
- [x] Spring Admin 2.7.7
- [x] Lombok
- [x] AOP
- [x] Mybatis Plus

## 编码规范

### 注释

> 如果写的代码别人能一眼能看懂自然不需要写注释 **注释是给人看的**

1. ```java
   //单行注释
   ```

2. ```java
   /*
    块注释
   */
   ```

3. ```java
   /**
   * 这是文档注释，用于生成API文档
   */
   ```

4. ```java
   // TODO: 待办事项，用于标记未完成的任务
   ```

5. ```java
   // FIXME: 需要修复的问题，用于标记代码中的缺陷或错误
   ```

### 变量

1. 类名`class`大驼峰尽量表达完整意思，长一点也没关系。
2. 变量命名就是用小驼峰命名类属于`helloWorld`如果就一个单词`add`
## 结构层级
`anti-fan`
```bash
C:.
└─src
     ├─main
     │  ├─java
     │  │  └─com
     │  │      └─ctrl
     │  │          ├─config
     │  │          ├─controller
     │  │          │  ├─login
     │  │          │  ├─user
     │  │          │  ├─myconfif
     │  │          │  └─raffle
     │  │          ├─dao
     │  │          ├─entity
     │  │          ├─exception
     │  │          ├─service
     │  │          └─utils
     │  └─resources
     └─test
        └─java
```
## 测试

## Dockerfile镜像的一些坑

### openjdk:8-jdk-alpine过于精简？

```bash
java.lang.NoClassDefFoundError: Could not initialize class sun.awt.X11FontManager
```

这个错误一般出现在生成验证码绘制的时候，这个错误大概原因就是由于在alpine上太过于精简了，导致初始化FontManagerFactory工厂初始化失败，那么解决办法就是安装glibc。

## 参考文献

<a href="https://github.com/Zhanglaodi/open-graduation-project/blob/main/ana/ikun.md">ikun语录</a>