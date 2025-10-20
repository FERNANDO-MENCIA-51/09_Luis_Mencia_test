# ========================================
# ETAPA 1: BUILD (Construcción)
# ========================================
FROM maven:3.9.9-eclipse-temurin-17-alpine AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargar dependencias (se cachea esta capa si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación (sin ejecutar tests para acelerar)
RUN mvn clean package -DskipTests -B

# Verificar que el JAR se haya creado
RUN ls -lh /app/target/

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
FROM eclipse-temurin:17-jre-alpine

# Información de la imagen
LABEL maintainer="Luis Mencia <luis.mencia@vallegrande.edu.pe>"
LABEL description="Authentication Service - Microservicio de autenticación"
LABEL version="1.0"

# Crear usuario no-root para mayor seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Establecer directorio de trabajo
WORKDIR /app

# Crear directorio para logs
RUN mkdir -p /app/logs && chown -R spring:spring /app

# Copiar el JAR desde la etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto (flexible mediante variable de entorno)
EXPOSE ${PORT:-5002}

# Variables de entorno por defecto
ENV PORT=5002 \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC" \
    TZ=America/Lima

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT:-5002}/actuator/health || exit 1

# Comando de ejecución
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
