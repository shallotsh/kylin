from tomcat:8.0-jre8

MAINTAINER shallotsh shallotsh@gmail.com

ADD ./dependency/server.xml /usr/local/tomcat/conf/server.xml

COPY ./target/kylin.war /app/

CMD ["catalina.sh", "run"]