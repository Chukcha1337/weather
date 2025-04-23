FROM tomcat:10.1.20-jdk21

# Удаляем дефолтные приложения Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем скомпилированный war-файл (предварительно собранный)
COPY build/libs/ROOT.war /usr/local/tomcat/webapps/ROOT.war



