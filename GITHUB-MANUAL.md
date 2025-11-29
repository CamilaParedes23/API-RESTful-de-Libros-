# 📖 GUÍA MANUAL - CONECTAR PROYECTO CON GITHUB

## 🎯 Repositorio Destino
**URL**: https://github.com/CamilaParedes23/API-RESTful-de-Libros-.git

---

## 🚀 OPCIÓN 1: SCRIPT AUTOMATIZADO (RECOMENDADO)

### Prerrequisitos
1. **Git instalado**: Descargar de https://git-scm.com/download/win
2. **Acceso al repositorio**: Permisos de escritura en el repo de GitHub
3. **Terminal/PowerShell**: Ejecutar como administrador si es necesario

### Ejecución
```bash
# Ejecutar script automatizado
.\github-upload.bat

# El script te pedirá:
# - Tu nombre para Git
# - Tu email para Git
# - Manejará toda la configuración automáticamente
```

---

## 🔧 OPCIÓN 2: COMANDOS MANUALES

### 1. Verificar que Git esté instalado
```bash
git --version
# Si no funciona, instalar Git desde: https://git-scm.com/download/win
```

### 2. Configurar Git (primera vez)
```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu-email@ejemplo.com"
```

### 3. Inicializar repositorio local
```bash
# Navegar al directorio del proyecto
cd C:\Users\usuario\Documents\UNIVERSIDAD\7MO\DISTRIBUIDAS\SEGUNDO\PROYECTOS\test\test

# Inicializar git
git init
```

### 4. Agregar archivos al staging
```bash
# Agregar todos los archivos
git add .

# Verificar archivos agregados
git status
```

### 5. Crear commit inicial
```bash
git commit -m "Initial commit: Complete Libros API with Docker - Spring Boot 3.4.12

Features implemented:
✅ Complete CRUD REST API for books
✅ Bean Validation with Spanish messages  
✅ Global exception handling
✅ OpenAPI/Swagger documentation
✅ Spring Actuator health checks
✅ Multi-stage Docker optimization
✅ MySQL database integration
✅ Postman collection with 13 automated tests
✅ Complete technical documentation
✅ Automation scripts for deployment

Technologies: Java 17, Spring Boot 3.4.12, MySQL 8.0, Docker, Postman"
```

### 6. Conectar con repositorio remoto
```bash
# Agregar repositorio remoto
git remote add origin https://github.com/CamilaParedes23/API-RESTful-de-Libros-.git

# Verificar remoto agregado
git remote -v
```

### 7. Configurar rama principal
```bash
# Cambiar a rama main
git branch -M main
```

### 8. Sincronizar con repositorio remoto (si hay contenido)
```bash
# Hacer pull por si hay archivos en el repo remoto
git pull origin main --allow-unrelated-histories --no-edit

# Si hay conflictos, resolverlos y hacer commit
```

### 9. Subir proyecto a GitHub
```bash
# Push inicial
git push -u origin main

# Si requiere autenticación, usar token personal de GitHub
```

### 10. Crear tag de versión (opcional)
```bash
# Crear tag
git tag -a v1.0.0 -m "Version 1.0.0 - API RESTful completa con Docker"

# Subir tag
git push origin v1.0.0
```

---

## 🔐 AUTENTICACIÓN CON GITHUB

### Para HTTPS (Recomendado)
1. **Crear Personal Access Token**:
   - Ve a GitHub → Settings → Developer settings → Personal access tokens
   - Generate new token (classic)
   - Selecciona scopes: `repo`, `workflow`, `write:packages`
   - Copia el token generado

2. **Usar en push**:
   - Usuario: tu-usuario-github
   - Password: el-token-generado

### Para SSH (Alternativo)
```bash
# Generar clave SSH
ssh-keygen -t rsa -b 4096 -C "tu-email@ejemplo.com"

# Agregar a SSH agent
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa

# Copiar clave pública y agregar en GitHub Settings → SSH keys
cat ~/.ssh/id_rsa.pub

# Cambiar URL remota a SSH
git remote set-url origin git@github.com:CamilaParedes23/API-RESTful-de-Libros-.git
```

---

## 🚨 TROUBLESHOOTING COMÚN

### Error: "repository not found"
- Verificar que la URL del repositorio sea correcta
- Verificar permisos de acceso al repositorio
- El repositorio podría ser privado

### Error: "authentication failed"
- Usar Personal Access Token en lugar de contraseña
- Verificar que el token tenga permisos correctos
- Configurar credenciales:
  ```bash
  git config credential.helper manager
  ```

### Error: "refused to merge unrelated histories"
- Usar flag `--allow-unrelated-histories`:
  ```bash
  git pull origin main --allow-unrelated-histories
  ```

### Error: "permission denied"
- Verificar permisos de escritura en el repositorio
- Contactar al propietario del repo para agregar como colaborador

### Archivos muy grandes
- Verificar que no se suban archivos innecesarios
- El `.gitignore` ya está configurado para excluir:
  - `target/` (archivos compilados)
  - `*.jar` (ejecutables)
  - `.idea/`, `.vscode/` (configuraciones IDE)

---

## 📊 VERIFICACIÓN POST-UPLOAD

### 1. Verificar en GitHub Web
- Ir a: https://github.com/CamilaParedes23/API-RESTful-de-Libros-
- Verificar que todos los archivos estén presentes:
  - ✅ README.md
  - ✅ REPORTE-TECNICO.md
  - ✅ Dockerfile
  - ✅ postman-collection.json
  - ✅ Código fuente en src/
  - ✅ pom.xml

### 2. Verificar funcionalidad de clonado
```bash
# En otra carpeta temporal, probar clonado
git clone https://github.com/CamilaParedes23/API-RESTful-de-Libros-.git test-clone
cd test-clone

# Verificar que compile
.\mvnw.cmd clean compile
```

### 3. Agregar descripción al repositorio
En GitHub web:
- Ir a Settings del repositorio
- Agregar descripción: "Complete RESTful API for book management with Spring Boot 3.4.12, MySQL, Docker, and comprehensive testing"
- Agregar topics: `spring-boot`, `java`, `rest-api`, `mysql`, `docker`, `crud`

---

## 🎯 PRÓXIMOS PASOS DESPUÉS DEL UPLOAD

1. **Configurar GitHub Pages** (opcional):
   - Settings → Pages
   - Source: Deploy from branch
   - Branch: main, folder: / (root)

2. **Agregar badges al README**:
   ```markdown
   ![Java](https://img.shields.io/badge/Java-17-orange)
   ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.12-green)
   ![Docker](https://img.shields.io/badge/Docker-Multi--stage-blue)
   ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
   ```

3. **Configurar Actions** (CI/CD):
   - Crear `.github/workflows/ci.yml` para builds automáticos

4. **Release management**:
   - Crear releases desde tags
   - Documentar changelog

---

## 📞 SOPORTE

Si tienes problemas:

1. **Revisar logs de error** completos
2. **Verificar configuración Git**:
   ```bash
   git config --list
   ```
3. **Probar conexión con GitHub**:
   ```bash
   ssh -T git@github.com  # Para SSH
   ```
4. **Consultar GitHub Docs**: https://docs.github.com/en/get-started

---

**¡Una vez subido, tu proyecto estará disponible públicamente y listo para colaboración! 🚀**
