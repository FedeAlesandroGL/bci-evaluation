
Luego de clonar y ejecutar la aplicación, para probarlo primero hay que:

Ejecutar una llamada a " POST http://localhost:8080/api/user " utilizando el siguiente request:

{
    "name": "Juan Rodriguez",
    "email": "usernaamae@dominio.cl",
    "password": "Password1!",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}

(lo que tiene distinto este request al enviado es que le puse lowerCamelCase al json y le cambie que decía contry en vez de country)

El email tiene que respetar el formato solicitado (intuí que como eran 7 letras antes del arroba, que sea ese largo era una condición así también como que sean solo letras) y luego la contraseña tiene que respetar el formato de contener al menos una letra minúscula, una mayúscula, un número, un caracter especial (@!#$%^&+=) y 8 dígitos de largo.

Una vez creado el usuario, para obtener el token se tiene que autenticar llamando a POST http://localhost:8080/api/authentication utilizando el siguiente request:

{
    "username": "username@dominio.cl",
    "password": "Password1!"
}

Si el usuario y la contraseña no coinciden con los datos previamente ingresados, va a devolver una respuesta de error.

Para poder buscar un usuario en específico puede usar el siguiente endpoint enviando el email por pathVariable, utiliza el email para buscarlo:
GET http://localhost:8080/api/user/{email}

Para poder eliminar un usuario (soft delete) puede usar el siguiente endpoint enviando el email por pathVariable, utiliza el email para buscarlo:
DELETE http://localhost:8080/api/user/{email}

NOTA IMPORTANTE: Al estar eliminado el usuario, no se podrá traer de nuevo ya que filtro por los registros activos.

