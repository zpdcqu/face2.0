From  jrottenberg/ffmpeg
MAINTAINER  liuhaoran
# ִ������(���ﴴ����һ��Ŀ¼)
RUN mkdir /usr/local/java
RUN mkdir /usr/local/images
# ��copyһ���������ļ���ָ��Ŀ¼������copy���ܽ�ѹ��add�Զ���ѹ
# (��֪���ļ�������������������ѹ��һ��)
ADD jdk-8u221-linux-x64.tar.gz /usr/local/java
# ������
RUN ln -s /usr/local/java/jdk1.8.0_221 /usr/local/java/jdk
# ���û�������
ENV JAVA_HOME /usr/local/java/jdk
ENV JRE_HOME ${JAVA_HOME}/jre
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib
ENV PATH ${JAVA_HOME}/bin:$PATH
add monitorjar /usr/local
add /monitorjar/monitor-0.0.1-SNAPSHOT.jar /usr/local/java/jdk/app.jar
add dll /usr/lib/
workdir /usr/local/
ENTRYPOINT ["java","-jar","-Dfile.encoding = utf-8","monitor-0.0.1-SNAPSHOT.jar"]