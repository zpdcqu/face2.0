From  jrottenberg/ffmpeg
MAINTAINER  liuhaoran
# 执行命令(这里创建了一个目录)
RUN mkdir /usr/local/java
RUN mkdir /usr/local/images
# 和copy一样，复制文件到指定目录，但是copy不能解压，add自动解压
# (不知道文件名可以现在宿主机解压后看一下)
ADD jdk-8u221-linux-x64.tar.gz /usr/local/java
# 重命名
RUN ln -s /usr/local/java/jdk1.8.0_221 /usr/local/java/jdk
# 设置环境变量
ENV JAVA_HOME /usr/local/java/jdk
ENV JRE_HOME ${JAVA_HOME}/jre
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib
ENV PATH ${JAVA_HOME}/bin:$PATH
add monitorjar /usr/local
add /monitorjar/monitor-0.0.1-SNAPSHOT.jar /usr/local/java/jdk/app.jar
add dll /usr/lib/
workdir /usr/local/
ENTRYPOINT ["java","-jar","-Dfile.encoding = utf-8","monitor-0.0.1-SNAPSHOT.jar"]