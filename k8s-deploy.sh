#!/bin/bash

# Script para desplegar en Kubernetes
# Autor: Luis Mencia

set -e

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}   Despliegue en Kubernetes${NC}"
echo -e "${BLUE}   Authentication Service${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Verificar que kubectl está instalado
if ! command -v kubectl &> /dev/null; then
    echo -e "${RED}✗${NC} kubectl no está instalado"
    exit 1
fi
echo -e "${GREEN}✓${NC} kubectl encontrado"
echo ""

# Paso 1: Crear namespace
echo -e "${YELLOW}[1/5]${NC} Creando namespace..."
kubectl apply -f k8s/09-luis-mencia-namespace.yml
echo -e "${GREEN}✓${NC} Namespace creado"
echo ""

# Paso 2: Crear secrets
echo -e "${YELLOW}[2/5]${NC} Creando secrets..."
kubectl apply -f k8s/09-luis-mencia-secret.yml
echo -e "${GREEN}✓${NC} Secrets creados"
echo ""

# Paso 3: Crear service
echo -e "${YELLOW}[3/5]${NC} Creando service..."
kubectl apply -f k8s/09-luis-mencia-service.yml
echo -e "${GREEN}✓${NC} Service creado"
echo ""

# Paso 4: Crear deployment
echo -e "${YELLOW}[4/5]${NC} Creando deployment con 2 pods..."
kubectl apply -f k8s/09-luis-mencia-deployment.yml
echo -e "${GREEN}✓${NC} Deployment creado"
echo ""

# Paso 5: Verificar recursos
echo -e "${YELLOW}[5/5]${NC} Verificando recursos..."
echo ""
echo -e "${BLUE}Namespace:${NC}"
kubectl get namespace luis-mencia-namespace
echo ""

echo -e "${BLUE}Secrets:${NC}"
kubectl get secrets -n luis-mencia-namespace
echo ""

echo -e "${BLUE}Service:${NC}"
kubectl get service -n luis-mencia-namespace
echo ""

echo -e "${BLUE}Deployment:${NC}"
kubectl get deployment -n luis-mencia-namespace
echo ""

echo -e "${BLUE}Pods:${NC}"
kubectl get pods -n luis-mencia-namespace
echo ""

# Esperar a que los pods estén listos
echo -e "${YELLOW}Esperando a que los pods estén listos...${NC}"
kubectl wait --for=condition=ready pod -l app=authentication-service -n luis-mencia-namespace --timeout=300s

echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}   ✓ Despliegue Exitoso${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""

# Mostrar información de los pods
echo -e "${BLUE}Pods en ejecución:${NC}"
kubectl get pods -n luis-mencia-namespace -o wide
echo ""

# Instrucciones para port-forward
echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}   Port-Forward${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""
echo -e "${YELLOW}Para crear un port-forward, ejecuta:${NC}"
echo -e "${GREEN}kubectl port-forward -n luis-mencia-namespace service/luis-mencia-service 5002:5002${NC}"
echo ""
echo -e "${YELLOW}O usa el script:${NC}"
echo -e "${GREEN}./k8s-port-forward.sh${NC}"
echo ""
echo -e "${YELLOW}Luego accede a:${NC}"
echo -e "  • Health: ${GREEN}http://localhost:5002/actuator/health${NC}"
echo -e "  • Swagger: ${GREEN}http://localhost:5002/swagger-ui.html${NC}"
echo ""
