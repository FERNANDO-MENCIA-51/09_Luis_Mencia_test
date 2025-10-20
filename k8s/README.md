# 🚀 Despliegue en Kubernetes

## 📋 Archivos de Manifiestos

Los siguientes manifiestos están listos para desplegar en Kubernetes:

### ✅ Archivos Creados:

1. **`09-luis-mencia-namespace.yml`** - Namespace aislado para la aplicación
2. **`09-luis-mencia-secret.yml`** - Secrets para PostgreSQL y JWT
3. **`09-luis-mencia-service.yml`** - Service ClusterIP para exponer los pods
4. **`09-luis-mencia-deployment.yml`** - Deployment con 2 réplicas (pods)

---

## 🎯 Configuración del Deployment

- **Imagen**: `luismencia/09-luis-mencia:1.0`
- **Réplicas**: 2 pods
- **Puerto**: 5002
- **Namespace**: `luis-mencia-namespace`
- **Base de datos**: PostgreSQL (Neon Cloud)
- **Health checks**: Configurados en `/actuator/health`

---

## 🚀 Despliegue Rápido

### Opción 1: Script Automático (Recomendado)

```bash
./k8s-deploy.sh
```

Este script:
1. ✅ Crea el namespace
2. ✅ Aplica los secrets
3. ✅ Crea el service
4. ✅ Despliega la aplicación con 2 pods
5. ✅ Verifica el estado de los recursos

---

### Opción 2: Manual (Paso a Paso)

#### 1️⃣ Crear el namespace:
```bash
kubectl apply -f k8s/09-luis-mencia-namespace.yml
```

#### 2️⃣ Crear los secrets:
```bash
kubectl apply -f k8s/09-luis-mencia-secret.yml
```

#### 3️⃣ Crear el service:
```bash
kubectl apply -f k8s/09-luis-mencia-service.yml
```

#### 4️⃣ Crear el deployment:
```bash
kubectl apply -f k8s/09-luis-mencia-deployment.yml
```

#### 5️⃣ Verificar el despliegue:
```bash
kubectl get all -n luis-mencia-namespace
```

---

## 🌉 Port-Forward (Acceso Local)

Para acceder a la aplicación desde tu máquina local:

### Opción A: Script Automático
```bash
./k8s-port-forward.sh
```

### Opción B: Manual
```bash
kubectl port-forward -n luis-mencia-namespace service/luis-mencia-service 5002:5002
```

Luego accede a:
- **Health**: http://localhost:5002/actuator/health
- **Swagger**: http://localhost:5002/swagger-ui.html
- **API Docs**: http://localhost:5002/v3/api-docs

---

## 📊 Comandos Útiles

### Ver todos los recursos:
```bash
kubectl get all -n luis-mencia-namespace
```

### Ver los pods:
```bash
kubectl get pods -n luis-mencia-namespace
```

### Ver los logs de un pod:
```bash
# Listar pods
kubectl get pods -n luis-mencia-namespace

# Ver logs de un pod específico
kubectl logs -n luis-mencia-namespace <nombre-del-pod>

# Seguir logs en tiempo real
kubectl logs -f -n luis-mencia-namespace <nombre-del-pod>
```

### Ver detalles de un pod:
```bash
kubectl describe pod -n luis-mencia-namespace <nombre-del-pod>
```

### Ver los secrets (sin valores):
```bash
kubectl get secrets -n luis-mencia-namespace
```

### Ver el service:
```bash
kubectl get service -n luis-mencia-namespace
```

### Ver el deployment:
```bash
kubectl get deployment -n luis-mencia-namespace
```

### Escalar los pods:
```bash
# Aumentar a 3 pods
kubectl scale deployment luis-mencia-deployment -n luis-mencia-namespace --replicas=3

# Volver a 2 pods
kubectl scale deployment luis-mencia-deployment -n luis-mencia-namespace --replicas=2
```

---

## 🔄 Actualizar la Aplicación

Si actualizas la imagen en Docker Hub, puedes actualizar el deployment:

```bash
# Aplicar cambios
kubectl apply -f k8s/09-luis-mencia-deployment.yml

# Reiniciar el deployment (forzar actualización)
kubectl rollout restart deployment luis-mencia-deployment -n luis-mencia-namespace

# Ver el estado del rollout
kubectl rollout status deployment luis-mencia-deployment -n luis-mencia-namespace
```

---

## 🧹 Limpiar Recursos

### Eliminar todo el namespace (y todos sus recursos):
```bash
kubectl delete namespace luis-mencia-namespace
```

### Eliminar recursos individuales:
```bash
kubectl delete -f k8s/09-luis-mencia-deployment.yml
kubectl delete -f k8s/09-luis-mencia-service.yml
kubectl delete -f k8s/09-luis-mencia-secret.yml
kubectl delete -f k8s/09-luis-mencia-namespace.yml
```

---

## 🔍 Verificación y Troubleshooting

### Verificar estado de los pods:
```bash
kubectl get pods -n luis-mencia-namespace -w
```

### Si un pod está en estado "CrashLoopBackOff":
```bash
# Ver logs del pod
kubectl logs -n luis-mencia-namespace <nombre-del-pod>

# Ver eventos del namespace
kubectl get events -n luis-mencia-namespace --sort-by='.lastTimestamp'
```

### Verificar conectividad con la base de datos:
```bash
# Ejecutar un shell en el pod
kubectl exec -it -n luis-mencia-namespace <nombre-del-pod> -- /bin/sh

# Dentro del pod, verificar variables de entorno
env | grep DATABASE
```

---

## 📋 Estructura de Archivos

```
k8s/
├── 09-luis-mencia-namespace.yml    # Namespace aislado
├── 09-luis-mencia-secret.yml       # Credenciales y configuración
├── 09-luis-mencia-service.yml      # Service ClusterIP
└── 09-luis-mencia-deployment.yml   # Deployment con 2 pods

Scripts:
├── k8s-deploy.sh                   # Deploy automático
└── k8s-port-forward.sh             # Port-forward automático
```

---

## ⚙️ Configuración de los Manifiestos

### Namespace
- **Nombre**: `luis-mencia-namespace`
- **Labels**: project, environment

### Secret
- **Tipo**: Opaque
- **Datos**: DATABASE_URL, DB_USERNAME, DB_PASSWORD, JWT_SECRET, JWT_EXPIRATION, JWT_REFRESH_EXPIRATION, PORT

### Service
- **Tipo**: ClusterIP
- **Puerto**: 5002
- **Selector**: app=authentication-service

### Deployment
- **Réplicas**: 2
- **Strategy**: RollingUpdate
- **Image**: luismencia/09-luis-mencia:1.0
- **Resources**:
  - Requests: 512Mi RAM, 250m CPU
  - Limits: 1Gi RAM, 500m CPU
- **Probes**:
  - Liveness: `/actuator/health` (60s delay)
  - Readiness: `/actuator/health` (45s delay)

---

## 🎯 Checklist de Despliegue

Antes de desplegar, verifica:

- [ ] ✅ Cluster de Kubernetes disponible
- [ ] ✅ `kubectl` configurado y funcionando
- [ ] ✅ Imagen `luismencia/09-luis-mencia:1.0` disponible en Docker Hub
- [ ] ✅ Credenciales de PostgreSQL (Neon) válidas
- [ ] ✅ JWT_SECRET configurado (cambiar en producción)

Durante el despliegue:

- [ ] ✅ Namespace creado
- [ ] ✅ Secrets aplicados
- [ ] ✅ Service creado
- [ ] ✅ Deployment creado con 2 pods
- [ ] ✅ Pods en estado "Running"
- [ ] ✅ Health checks pasando

Después del despliegue:

- [ ] ✅ Port-forward funcionando
- [ ] ✅ Endpoint `/actuator/health` responde
- [ ] ✅ Swagger UI accesible
- [ ] ✅ Aplicación conecta a PostgreSQL

---

## 🎉 ¡Listo!

Tu aplicación está desplegada en Kubernetes con 2 pods, conectada a PostgreSQL en Neon, y lista para usar.

**Autor**: Luis Mencia  
**Fecha**: Octubre 2025
