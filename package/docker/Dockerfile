#
# 进行测试的项目
# author : daniel_du
#
FROM index.docker.io/primetoninc/jre

MAINTAINER "duxiaoxia"<duxiaoxia@caijinquan.com>

WORKDIR /usr/local

ADD springboot-1.0-SNAPSHOT.zip ./springboot.zip

RUN /usr/bin/unzip ./springboot.zip

RUN rm -rf ./ springboot.zip

WORKDIR /usr/local/springboot

RUN chmod +x ./bin/*

EXPOSE 80 8080 

CMD ["./bin/start.sh", "jmx"]
