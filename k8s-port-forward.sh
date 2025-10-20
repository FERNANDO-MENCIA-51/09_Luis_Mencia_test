#!/bin/bash

# Script para crear port-forward
# Autor: Luis Mencia

set -e

# Colores
BLUE='\033[0;34m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}   Port-Forward a Kubernetes${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

echo -e "${YELLOW}Creando puente entre localhost:5002 y el cluster...${NC}"
echo ""
echo -e "${GREEN}Accede al servicio en:${NC}"
echo -e "  • Health: ${GREEN}http://localhost:5002/actuator/health${NC}"
echo -e "  • Swagger: ${GREEN}http://localhost:5002/swagger-ui.html${NC}"
echo -e "  • API Docs: ${GREEN}http://localhost:5002/v3/api-docs${NC}"
echo ""
echo -e "${YELLOW}Presiona Ctrl+C para detener el port-forward${NC}"
echo ""

# Crear port-forward
kubectl port-forward -n luis-mencia-namespace service/luis-mencia-service 5002:5002
