# JDBC-BBDD

Proyecto realizado por Daniel Hurtado Perera.

Lenguaje utilizado: Java.

Librerias: JDBC


AVISO PARA USO DEL PROGRAMA:

Se tiene que que modificar manualmente la dirección IP dentro del proyecto para que se pueda conectar a un servidor.

Todos los metodos han sido escritos por el mismo autor, por lo que se puede mejorar y optimizar el codigo si se echa en mano la documentación de las clases de JDBC.


AVISO IMPORTANTE PARA IMPORTAR FICHEROS:

  Ejecución de INSERT:
  
  El formato de fichero que se tiene que usar es el siguiente
  
    Primera linea: nombre de base de datos
	
    Segunda linea: nombre de la tabla a la que se va a insertar los datos
	
    Tercera linea: nombre de las columnas separadas por comas. Por ejemplo: [id,nombre,edad,dni]
	
    Cuarta linea y las siguientes:valores que se van a introducir separados por comas. Por ejemplo: [5,Sebastian,11110000T]
	
    
 Ejecución de UPDATE:
 
 El formato de fichero que se tiene que usar es el siguiente
 
    Primera linea: nombre de base de datos
	
    Segunda linea: nombre de la tabla a la que se van a actualizar los datos
	
    Tercera linea: Identificador de la tabla y las nombre de las columnas que se quieran actualizar 
	
    Cuarta linea y las siguientes: Valor del identificador de la tabla y valor de las columnas que se quieran actualizar separados por comas.
	
    
 Ejecución de DELETE:
 
 El formato de fichero que se tiene que usar es el siguiente
 
    Primera linea: nombre de base de datos
	
    Segunda linea: nombre de la tabla a la que se quieren borrar registros
	
    Tercera linea: Los identificadores de los registros que se quieran borrar separados por comas.
	
    

