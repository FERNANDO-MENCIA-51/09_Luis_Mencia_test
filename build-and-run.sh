#!/bin/bash

# Script para construir y probar la imagen Docker
# Autor: Luis Mencia

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}   Construcción de Imagen Docker${NC}"
echo -e "${BLUE}   09-luis-mencia:1.0${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Paso 1: Construir imagen
echo -e "${YELLOW}[1/5]${NC} Construyendo imagen Docker..."
docker build -t 09-luis-mencia:1.0 .

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} Imagen construida exitosamente"
else
    echo -e "${RED}✗${NC} Error al construir la imagen"
    exit 1
fi
echo ""

# Paso 2: Verificar tamaño
echo -e "${YELLOW}[2/5]${NC} Verificando tamaño de la imagen..."
IMAGE_SIZE=$(docker images 09-luis-mencia:1.0 --format "{{.Size}}")
echo -e "${GREEN}✓${NC} Tamaño de la imagen: ${IMAGE_SIZE}"

# Extraer tamaño en MB para validación
SIZE_MB=$(docker image inspect 09-luis-mencia:1.0 --format='{{.Size}}' | awk '{print $1/1024/1024}')
echo -e "   Tamaño en MB: ${SIZE_MB}"

if (( $(echo "$SIZE_MB > 300" | bc -l) )); then
    echo -e "${YELLOW}⚠${NC}  Advertencia: La imagen supera los 300MB"
elif (( $(echo "$SIZE_MB < 100" | bc -l) )); then
    echo -e "${YELLOW}⚠${NC}  Advertencia: La imagen es menor a 100MB"
else
    echo -e "${GREEN}✓${NC} Tamaño de imagen correcto (100-300MB)"
fi
echo ""

# Paso 3: Limpiar contenedores previos
echo -e "${YELLOW}[3/5]${NC} Limpiando contenedores previos..."
if [ "$(docker ps -a -q -f name=auth-service)" ]; then
    docker stop auth-service 2>/dev/null || true
    docker rm auth-service 2>/dev/null || true
    echo -e "${GREEN}✓${NC} Contenedor anterior eliminado"
else
    echo -e "${GREEN}✓${NC} No hay contenedores previos"
fi
echo ""

# Paso 4: Ejecutar contenedor
echo -e "${YELLOW}[4/5]${NC} Iniciando contenedor..."
docker run -d \
    -p 5002:5002 \
    --name auth-service \
    09-luis-mencia:1.0

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} Contenedor iniciado exitosamente"
    echo -e "   Container ID: $(docker ps -q -f name=auth-service)"
else
    echo -e "${RED}✗${NC} Error al iniciar el contenedor"
    exit 1
fi
echo ""

# Paso 5: Verificar salud del servicio
echo -e "${YELLOW}[5/5]${NC} Esperando a que el servicio esté listo..."
echo -e "   Esto puede tomar hasta 40 segundos..."

for i in {1..40}; do
    if curl -s http://localhost:5002/actuator/health > /dev/null 2>&1; then
        echo -e "${GREEN}✓${NC} Servicio listo y respondiendo"
        break
    fi
    if [ $i -eq 40 ]; then
        echo -e "${RED}✗${NC} El servicio no está respondiendo después de 40 segundos"
        echo -e "${YELLOW}Mostrando logs del contenedor:${NC}"
        docker logs auth-service
        exit 1
    fi
    sleep 1
    echo -n "."
done
echo ""

# Resumen
echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}   ✓ Despliegue Exitoso${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "${BLUE}Información del servicio:${NC}"
echo -e "  • Imagen: ${GREEN}09-luis-mencia:1.0${NC}"
echo -e "  • Tamaño: ${GREEN}${IMAGE_SIZE}${NC}"
echo -e "  • Puerto: ${GREEN}5002${NC}"
echo -e "  • Contenedor: ${GREEN}auth-service${NC}"
echo ""
echo -e "${BLUE}Endpoints disponibles:${NC}"
echo -e "  • Health: ${GREEN}http://localhost:5002/actuator/health${NC}"
echo -e "  • Swagger: ${GREEN}http://localhost:5002/swagger-ui.html${NC}"
echo -e "  • API Docs: ${GREEN}http://localhost:5002/v3/api-docs${NC}"
echo ""
echo -e "${BLUE}Comandos útiles:${NC}"
echo -e "  • Ver logs: ${YELLOW}docker logs -f auth-service${NC}"
echo -e "  • Detener: ${YELLOW}docker stop auth-service${NC}"
echo -e "  • Eliminar: ${YELLOW}docker rm auth-service${NC}"
echo -e "  • Verificar salud: ${YELLOW}curl http://localhost:5002/actuator/health${NC}"
echo ""

# Probar endpoint de salud
echo -e "${BLUE}Probando endpoint de salud:${NC}"
HEALTH_RESPONSE=$(curl -s http://localhost:5002/actuator/health)
echo "$HEALTH_RESPONSE" | jq . 2>/dev/null || echo "$HEALTH_RESPONSE"
echo ""

echo -e "${GREEN}¡Listo para usar!${NC}"
