#!/bin/bash

# Script para hacer push de la imagen a Docker Hub
# Autor: Luis Mencia

set -e

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}   Push a Docker Hub${NC}"
echo -e "${BLUE}   09-luis-mencia:1.0${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Verificar si la imagen existe
if ! docker images 09-luis-mencia:1.0 | grep -q "09-luis-mencia"; then
    echo -e "${RED}✗${NC} La imagen 09-luis-mencia:1.0 no existe"
    echo -e "${YELLOW}→${NC} Ejecuta primero: ./build-and-run.sh"
    exit 1
fi

# Solicitar usuario de Docker Hub
echo -e "${YELLOW}Ingresa tu usuario de Docker Hub:${NC}"
read -r DOCKER_USERNAME

if [ -z "$DOCKER_USERNAME" ]; then
    echo -e "${RED}✗${NC} Usuario no puede estar vacío"
    exit 1
fi

# Paso 1: Login a Docker Hub
echo ""
echo -e "${YELLOW}[1/4]${NC} Iniciando sesión en Docker Hub..."
docker login

if [ $? -ne 0 ]; then
    echo -e "${RED}✗${NC} Error al iniciar sesión"
    exit 1
fi
echo -e "${GREEN}✓${NC} Sesión iniciada correctamente"
echo ""

# Paso 2: Etiquetar imagen
echo -e "${YELLOW}[2/4]${NC} Etiquetando imagen..."
docker tag 09-luis-mencia:1.0 ${DOCKER_USERNAME}/09-luis-mencia:1.0
docker tag 09-luis-mencia:1.0 ${DOCKER_USERNAME}/09-luis-mencia:latest
echo -e "${GREEN}✓${NC} Imagen etiquetada con:"
echo -e "   • ${DOCKER_USERNAME}/09-luis-mencia:1.0"
echo -e "   • ${DOCKER_USERNAME}/09-luis-mencia:latest"
echo ""

# Paso 3: Push versión 1.0
echo -e "${YELLOW}[3/4]${NC} Subiendo imagen versión 1.0..."
docker push ${DOCKER_USERNAME}/09-luis-mencia:1.0
echo -e "${GREEN}✓${NC} Versión 1.0 subida exitosamente"
echo ""

# Paso 4: Push latest
echo -e "${YELLOW}[4/4]${NC} Subiendo imagen latest..."
docker push ${DOCKER_USERNAME}/09-luis-mencia:latest
echo -e "${GREEN}✓${NC} Versión latest subida exitosamente"
echo ""

# Resumen
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}   ✓ Push Exitoso${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "${BLUE}Tu imagen está disponible en:${NC}"
echo -e "  ${GREEN}https://hub.docker.com/r/${DOCKER_USERNAME}/09-luis-mencia${NC}"
echo ""
echo -e "${BLUE}Para descargar la imagen:${NC}"
echo -e "  ${YELLOW}docker pull ${DOCKER_USERNAME}/09-luis-mencia:1.0${NC}"
echo ""
echo -e "${BLUE}Para ejecutar la imagen:${NC}"
echo -e "  ${YELLOW}docker run -d -p 5002:5002 ${DOCKER_USERNAME}/09-luis-mencia:1.0${NC}"
echo ""
echo -e "${GREEN}¡Listo!${NC}"
