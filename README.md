Luego de clonar y ejecutar la aplicación, para probarlo primero hay que:

Ejecutar una llamada a " POST http://localhost:8080/api/user " utilizando el siguiente request:

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

Este request es levemente distinto al del PDF del ejercicio, le puse lowerCamelCase a los campos y le cambie que decía "
contry" en vez de "country")

El email tiene que respetar el formato solicitado, intuí que como eran 7 letras antes del arroba, que sea ese largo era
una condición así también como que sean solo letras.
Luego la contraseña tiene que respetar el formato de contener al menos una letra minúscula, una mayúscula, un número y
uno de los siguientes caracteres especiales @!#$%^&+= y 8 dígitos de largo.

Una vez creado el usuario, se le va a devolver en la respuesta el token. Si desea obtener uno nuevamente, puede hacerlo
llamando al endpoint de autenticación. Este llamado va a modificar el token del usuario en la base de datos, así también
como el lastLogin.
POST http://localhost:8080/api/authentication utilizando el siguiente request:

{
"username": "username@dominio.cl",
"password": "Password1!"
}

Si el usuario y la contraseña no coinciden con los datos previamente ingresados, va a devolver una respuesta de error.

Para poder buscar un usuario en específico puede usar el siguiente endpoint enviando el email por pathVariable y el
bearer token en el header Authorization:
GET http://localhost:8080/api/user/{email}

Para poder eliminar un usuario (soft delete) puede usar el siguiente endpoint enviando el email por pathVariable y el
bearer token en el header Authorization:
DELETE http://localhost:8080/api/user/{email}

Dato a tener en cuenta es que al estar eliminado el usuario, no se podrá traer de nuevo ya que se filtra por los
registros activos exceptuando el caso que se da de alta nuevamente.

El script de la base de datos se encuentra en la carpeta resources en un archivo llamado "schema.sql".

La URL del Swagger es http://localhost:8080/swagger-ui/index.html

El diagrama de la solución es https://miro.com/app/board/uXjVNne9tFA=/?share_link_id=89416196724
