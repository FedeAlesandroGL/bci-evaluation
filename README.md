# BCI Evaluation

Ejercicio a modo de evaluación para ingresar en el proyecto de BCI

## Correr la aplicación localmente

Clone el proyecto

```bash
  git clone https://github.com/FedeAlesandroGL/bci-evaluation.git
```

Vaya al directorio del proyecto

```bash
  cd bci-evaluation
```

Abralo en su IDE de preferencia. Si no se instalan las dependencias mediante el IDE o quiere iniciar la aplicación sin
el IDE, entonces ejecute el comando

```bash
  mvn install
```

Por último inicie la aplicación.

## API

### Registrar y autenticar el usuario

```http
  POST /api/user
```

#### Datos a tener en cuenta

El email tiene que cumplir con el formato solicitado y no existir en la base de datos.

La contraseña tiene que respetar el formato de contener al menos una letra minúscula, una mayúscula, un número,
uno de los siguientes caracteres especiales @!#$%^&+= y 8 dígitos de largo.

#### Request de ejemplo:

```json
{
  "name": "Juan Rodriguez",
  "email": "username@dominio.cl",
  "password": "Password1!",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ]
}
```

#### Response de ejemplo:

```json
{
  "id": "c037d849-4c6d-43fc-bea1-f80d93c8bf45",
  "name": "Juan Rodriguez",
  "email": "username@dominio.cl",
  "password": "encryptedPassword.",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ],
  "token": "1234token",
  "isActive": true,
  "created": "2024-03-01T14:03:36.351975177",
  "modified": "2024-03-01T14:03:36.378424",
  "lastLogin": "2024-03-01T14:03:36.351975177"
}
```

### Autenticar el usuario nuevamente

```http
  POST /api/authentication
```

#### Request de ejemplo:

```json
{
  "username": "username@dominio.cl",
  "password": "Password1!"
}
```

#### Response de ejemplo:

```json
{
  "token": "1234token"
}
```

### Obtener un usuario por email

```http
  GET /api/user/{email}
```

| Parameter       | Type     | Description                      |
|:----------------|:---------|:---------------------------------|
| `Authorization` | `Header` | **Requerido**. Bearer token      |
| `email`         | `string` | **Requerido**. El email a buscar |

#### Response de ejemplo:

```json
{
  "id": "c037d849-4c6d-43fc-bea1-f80d93c8bf45",
  "name": "Juan Rodriguez",
  "email": "username@dominio.cl",
  "password": "encryptedPassword",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ],
  "token": "1234token",
  "isActive": true,
  "created": "2024-03-01T14:03:36.351975177",
  "modified": "2024-03-01T14:03:36.378424",
  "lastLogin": "2024-03-01T14:03:36.351975177"
}
```

### Eliminar un usuario por email

```http
  DELETE /api/user/{email}
```

| Parameter       | Type     | Description                                    |
|:----------------|:---------|:-----------------------------------------------|
| `Authorization` | `Header` | **Requerido**. Bearer token                    |
| `email`         | `string` | **Requerido**. El email del usuario a eliminar |

### Caso de error

#### Response de ejemplo:

```json
{
  "message": "Error message"
}
```

## Documentación

### Swagger

```http
  /swagger-ui/index.html
```

### Diagrama de la solución

[Diagrama en Miro](https://miro.com/app/board/uXjVNne9tFA=/?share_link_id=89416196724)

### Script de base de datos

```
src/main/resources/schema.sql
```
### Postman
Se encuentra la colección en el archivo:

```
EvaluationBci.postman_collection.json
```
