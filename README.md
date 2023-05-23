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


> 适当玩梗

## 环境部署

- [x] jdk1.8
- [x] mysql8.0
- [x] docker
- [x] redis 6.2.6
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

> 当然如果写的代码别人能一眼能看懂自然不需要写注释 **注释是给人看的**

### 变量

> 类名`class`大驼峰尽量表达完整意思，长一点也没关系。
>
>  变量命名就是用小驼峰命名类属于`helloWorld`如果就一个单词`add`

## 结构层级

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
     │  │          │  └─user
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