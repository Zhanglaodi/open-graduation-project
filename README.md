[toc]



# 坤吧管理系统

> 全民制作人大家好我是练习时常两年半（一坤年）的只因练习生坤坤。
> \> 为了方便真爱粉（你干嘛哎呦），防止虾头男。应该有一个小黑子管理系统！！！
> \> 当然小黑子可以star一下

> keywords(关键词): 小黑子、java、spring、mysql、毕业设计

<div align="center">
<img src="./images/R-C.jpg" width="300px">
</div>



1. 卤出鸡脚了吧!
2. 树枝666，小黑子 苏珊，食不食油饼？
3. 耗丸吗？
4.  再黑紫砂吧！
5.  4年前的梗你们还在玩，4年前的剩饭你们怎么不吃？
6.  臻果粉！我看你们都馍蒸了！
7.  蒸梅油酥汁！
8.  你们犯法了知道吗？
9. 你们再这样我就煲胫了！
10.  香精煎鱼是吗？
11.  香翅捞饭是吗？
12.  真没有荚饺，
13.  荔枝？
14.  你要我怎么荔枝！？

## 环境部署

- [x] jdk1.8
- [x] mysql8.0

## 技术选型？？？（唱跳rap篮球music）

## 编码规范

一个真正的鳗不需要规范，要啥自行车，写注释多累人啊！！

## Dockerfile镜像的一些坑

### openjdk:8-jdk-alpine过于精简？

```java
java.lang.NoClassDefFoundError: Could not initialize class sun.awt.X11FontManager
```

这个错误一般出现在生成验证码绘制的时候，这个错误大概原因就是由于在alpine上太过于精简了，导致初始化FontManagerFactory工厂初始化失败，那么解决办法就是安装glibc。