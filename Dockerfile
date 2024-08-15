# Fase de construcción (Usa una imagen base de OpenJDK 17)
FROM openjdk:17-jdk-slim AS build

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Instala locales y genera la localización en_US.UTF-8
#RUN apt-get update && apt-get install -y locales && \
#   echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && locale-gen

# Configura el idioma y la zona horaria
#ENV LANG=en_US.UTF-8
#ENV LANGUAGE=en_US:en
#ENV LC_ALL=en_US.UTF-8
#ENV TZ='America/Argentina/Buenos_Aires'

# Instala herramientas adicionales necesarias
#RUN apt-get update && apt-get install -y curl tzdata net-tools iputils-ping mc

# Copia todos los archivos del proyecto al contenedor
COPY . .

# Ejecuta el comando Gradle para construir el proyecto y generar el JAR
RUN ./gradlew build

# Fase de ejecución
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR generado desde la fase de construcción
COPY --from=build /app/build/libs/tasks-*.jar /app/tasks.jar

# Exponer  puertos que usará la aplicación
EXPOSE 8080 9200

# Comando para ejecutar la aplicación, utilizando el archivo JAR generado
CMD ["java", "-jar", "/app/tasks.jar"]