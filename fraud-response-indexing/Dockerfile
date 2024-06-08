# Usar una imagen base con Java 11 para la etapa de despliegue
FROM openjdk:11

# Crear un directorio en la imagen para la aplicación
WORKDIR /app

# Copiar el archivo JAR del módulo fraud-response-indexing a la etapa de despliegue
COPY target/*.jar fraud-response-indexing.jar

# Especificar el comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","fraud-response-indexing.jar"]