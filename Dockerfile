FROM tomcat:9-jdk8-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/university.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]