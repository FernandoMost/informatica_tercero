#!/bin/bash

systemctl stop tomcat.service

rm -f /home/fm/Desktop/infomatica/daw/practicas/06/WEB/WEB-INF/classes/*.class

javac -verbose -cp /opt/tomcat/apache-tomcat-9.0.27/lib/servlet-api.jar:/home/fm/Desktop/infomatica/daw/practicas/06/WEB/WEB-INF/lib/mysql-connector-java-5.1.48-bin.jar /home/fm/Desktop/infomatica/daw/practicas/06/WEB/WEB-INF/classes/*.java

rm -rf /opt/tomcat/apache-tomcat-9.0.27/webapps/VizualContender.war
rm -rf /opt/tomcat/apache-tomcat-9.0.27/webapps/VizualContender

cd /home/fm/Desktop/infomatica/daw/practicas/06/WEB/

jar -cf VizualContender.war .

cp --verbose VizualContender.war /opt/tomcat/apache-tomcat-9.0.27/webapps

systemctl start tomcat.service