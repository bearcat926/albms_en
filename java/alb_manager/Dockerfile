### Kernel
FROM centos:7
MAINTAINER haohao<hh18622073649@gmail.com>

### Component
RUN curl -SL https://repo.huaweicloud.com/java/jdk/8u181-b13/jdk-8u181-linux-x64.tar.gz \
    | tar -xzC /usr/local
RUN yum install -y vim && \
#    yum instatll -y dos2unix && \ # 脚本编码转化
     yum install -y net-tools

### Environment
ENV MYPATH /usr/local/vacation
WORKDIR $MYPATH

ENV JAVA_HOME /usr/local/jdk1.8.0_181
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH ${JAVA_HOME}/bin:$PATH

### 中文乱码
RUN  yum -y install kde-l10n-Chinese telnet && \
     yum -y reinstall glibc-common &&\
     yum clean all  && \
     localedef -c -f UTF-8 -i zh_CN zh_CN.utf8  && \
     cat /dev/null > /etc/locale.conf && \
     echo "LC_ALL=\"zh_CN.UTF-8\"" > /etc/locale.conf

ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN:zh
ENV LC_ALL zh_CN.UTF-8

### Project
COPY target/vacation.jar $MYPATH/vacation.jar

### start
CMD ["java","-Xms1g","-Xmx1g","-jar","vacation.jar","-Djava.security.egd=file:/dev/./urandom"]
