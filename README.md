# LABORATORIO 02 DE AREP 
En este laboratorio, explorará la arquitectura de aplicaciones distribuidas, específicamente la arquitectura de servidores web y el protocolo HTTP en el que se basan. El objetivo es escribir un servidor web simple en Java que pueda manejar múltiples solicitudes de forma secuencial (no al mismo tiempo). El servidor debería poder leer archivos del disco local y devolver todos los archivos solicitados, incluidas páginas HTML, archivos JavaScript, hojas de estilo CSS e imágenes.

# Instalación 
+ Para poder correr el laboratorio se clona el repositorio en una máquina local con el siguiente comando
  
    ```
  git clone https://github.com/Juc28/CNYT-DeLoClasicoALoCuantico.git](https://github.com/Juc28/AREP_LAB02.git
    ```
  
+ Para ejecutar el laboratrio es la clase [HttpServer](https://github.com/Juc28/AREP_LAB02/blob/master/Taller02/src/main/java/edu/escuelaing/arem/ASE/app/HttpServer.java) y desde el ide de prefencia correr la clase mencionada, Luego, ejecute la clase y abra su navegador de confianza(En mi caso use [Google](https://www.google.com/?hl=es)). En la barra de direcciones, escriba  ``` localhost:35000 ```.
  
+ Siempre se debe de seleccionar un archivo que se encuentre en el Archivo de [Recursos](https://github.com/Juc28/AREP_LAB02/tree/master/Taller02/src/main/resource)
  
## Ejecutar Test.
Se puede desde la consola o desde el ide de preferencia 
- Clonar repositorio
   ```
  git clone https://github.com/Juc28/CNYT-DeLoClasicoALoCuantico.git](https://github.com/Juc28/AREP_LAB02.git
   ```

- Cambiar directorio
  ```
  cd AREP_LAB02
  ```

- Ejecutar 
  ```
  mvn test
  ```
- Para ejecutrar desde el ide es la clase [HttpServerTest](https://github.com/Juc28/AREP_LAB02/blob/master/Taller02/src/test/java/edu/escuelaing/arem/ASE/app/HttpServerTest.java)

# Arquitectura

Es un servidor HTTP simple implementado en Java que maneja la carga de archivos mediante el método HTTP POST. Cuando se inicia el servidor, escucha las solicitudes de los clientes en el puerto 35000. Una vez recibida la solicitud, el servidor lee la línea de solicitud y los encabezados para determinar el URI solicitado y el tipo de contenido. Si el URI solicitado comienza con "/upload", el servidor manejará la carga del archivo.

Al descargar archivos, el servidor lee el cuerpo de la solicitud como un flujo de datos de varias partes. Busca el parámetro "nombre de archivo". en el encabezado del diseño del contenido para obtener el nombre del archivo cargado. Luego crea un nuevo archivo en el directorio "Taller02/src/main/sourceandquot; con el mismo nombre que el archivo descargado. El contenido del archivo descargado se escribirá en el archivo recién creado.

Cuando finaliza la descarga del archivo, el servidor devuelve una respuesta al lado del cliente con el contenido del archivo descargado en el formato apropiado (HTML, CSS, JavaScript o imagen) dependiendo de la extensión del archivo. Si la extensión del archivo no es compatible, el servidor devuelve un mensaje de error. La respuesta contiene los encabezados HTTP necesarios, como Content-Type y Content-Duration, para garantizar que se muestre el contenido correcto en el lado del cliente.

Si el URI solicitado no comienza con "/upload", el servidor devuelve una página HTML predeterminada que contiene el formulario de carga del archivo. El formulario tiene un campo de entrada de archivo y un botón de envío. Cuando el usuario selecciona un archivo y hace clic en el botón enviar, los datos del formulario se envían al servidor como un flujo de datos de formulario de varias partes.

En resumen, este servidor HTTP simple puede manejar cargas de archivos utilizando el método HTTP POST y devolver el contenido del archivo cargado en el formato apropiado al lado del cliente. También puede servir la página HTML predeterminada que contiene un formulario para cargar archivos.

# Pruebas 

# Lincencia
Licenciado por GNU General Public License v3.0 [LICENSE](https://github.com/Juc28/AREP_LAB01/blob/master/LICENSE)


 


# Autor 
Erika Juliana Castro Romero [Juc28](https://github.com/Juc28)
