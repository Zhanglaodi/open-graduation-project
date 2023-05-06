FROM  williamyeh/java8:latest
VOLUME /tmp/springboot
# 作者信息
MAINTAINER zhanglaodi
# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
# 拷贝jar 把可执行jar包复制到基础镜像的根目录下
ADD anti-fan-1.0-SNAPSHOT.jar app.jar
ADD setup.sh setup.sh
RUN chmod -x setup.sh
# 设置暴露的端口号
EXPOSE 19999 1005
RUN sh -c 'touch /app.jar'
# 在镜像运行为容器后执行的命令
ENTRYPOINT ["sh","./setup.sh"]