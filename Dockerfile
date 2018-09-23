from tomcat:8.0-jre8

MAINTAINER shallotsh shallotsh@gmail.com

COPY ./target/kylin.war /usr/local/tomcat/webapps

EXPOSE 8080

CMD ["catalina.sh", "run"]