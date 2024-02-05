package edu.escuelaing.arem.ASE.app;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {

            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean fline = true;
            boolean necessaryFlag = true;
            String uriS = "";
            String uriWithFileName = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (necessaryFlag) {
                    if (fline) {
                        fline = false;
                        uriS = uriS = inputLine.split(" ")[1];
                    }
                    if (inputLine.startsWith("Content-Disposition:")) {
                        necessaryFlag = false;
                        uriWithFileName = inputLine;
                    }
                }
                if (!in.ready()) {
                    break;
                }
            }
            if (uriS.startsWith("/upload")) {
                outputLine = findAndGetBoundaries(uriWithFileName);
            } else {
                outputLine = getHomeIndex();
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Obtiene el nombre de archivo y la ruta desde una cadena de entrada, busca el archivo y devuelve su contenido formateado según su tipo.
     *
     * @param inputString La cadena de entrada que contiene la información del nombre de archivo.
     * @return Una cadena con la representación del archivo como respuesta HTTP (HTML, imagen, etc.), o un mensaje de error si no se encuentra el archivo o no se soporta el tipo.
     * @throws IOException Si se produce un error al leer el archivo.
     */
    public static String findAndGetBoundaries(String inputString) throws IOException {
        String[] parts = inputString.split(";");
        String filename = null;
        String path = "Taller02\\\\src\\\\main\\\\resource\\\\";

        for (String part : parts) {
            if (part.trim().startsWith("filename")) {
                String[] filenameParts = part.split("=");
                if (filenameParts.length > 1) {
                    filename = filenameParts[1].trim().replace("\"", "");
                }
            }
        }
        if (filename != null) {
            Path filePath = Paths.get(path + filename);
            if (Files.exists(filePath)) {
                long size = Files.size(filePath);
                File file = new File(path + filename);
                String type = filename.substring(filename.lastIndexOf(".") + 1);
                try {
                    switch (type) {
                        case "html":
                        case "txt":
                            return toHTML(file);
                        case "jpg":
                        case "jpeg":
                        case "jpge":
                        case "png":
                            return toImage(file, type);
                        case "js":
                            return toJs(file);
                        case "css":
                            return toCSS(file);
                        default:
                            return "Tipo de archivo no soportado";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return "Archivo no encontrado";
            }
        } else {
            return "Nombre de archivo no proporcionado";
        }
        return filename;
    }

    /**
     * Lee el contenido de un archivo y lo devuelve como un StringBuilder.
     *
     * @param file El archivo a leer.
     * @return Un StringBuilder con el contenido del archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static StringBuilder fileToString(File file) throws IOException {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line).append("\n");
            }
        }
        return body;
    }

    /**
     * Convierte el contenido de un archivo en una cadena HTML, formateada como una respuesta HTTP.
     *
     * @param file El archivo a convertir.
     * @return Una cadena HTML que contiene el contenido del archivo, envuelta en una cabecera de respuesta HTTP y centrada dentro del cuerpo.
     * @throws IOException Si se produce un error al leer el archivo.
     */
    public static String toHTML(File file) throws IOException {
        StringBuilder body = fileToString(file);
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<center>" + body + "</center>";
    }

    /**
     * Convierte una imagen de un archivo en una cadena codificada en Base64, encapsulada en una respuesta HTTP.
     *
     * @param file El archivo de imagen a convertir.
     * @param type El tipo de la imagen (e.g., "jpeg", "png", "gif").
     * @return Una cadena HTML que contiene la imagen codificada en Base64, formateada como una respuesta HTTP y centrada.
     * @throws IOException Si se produce un error al leer el archivo.
     */
    public static String toImage(File file, String type) throws IOException{
        byte[] bytes = Files.readAllBytes(file.toPath());
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/"+ type + "\r\n"
                + "\r\n"
                + "<center><img src=\"data:image/" + type + ";base64," + base64 + "\"></center>";
    }

    /**
     * Convierte el contenido de un archivo CSS en una cadena, formateada como una respuesta HTTP.
     *
     * @param file El archivo CSS a convertir.
     * @return Una cadena HTML que contiene el CSS, envuelta en una cabecera de respuesta HTTP y centrada.
     * @throws IOException Si se produce un error al leer el archivo.
     */
    public static String toCSS(File file) throws IOException{
        StringBuilder body = fileToString(file);
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/css\r\n"
                + "\r\n"
                + "<center>"+body+"</center>";
    }
    /**
     * Convierte el contenido de un archivo JavaScript en una cadena, formateada como una respuesta HTTP.
     *
     * @param file El archivo JavaScript a convertir.
     * @return Una cadena HTML que contiene el JavaScript, envuelta en una cabecera de respuesta HTTP y centrada.
     * @throws IOException Si se produce un error al leer el archivo.
     */
    public static String toJs(File file)throws IOException{
        StringBuilder body = fileToString(file);
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/javascript\r\n"
                + "\r\n"
                + "<center>"+body+"</center>";
    }

    /**
     * Genera la página de inicio HTML para la carga de archivos.
     *
     * @return Una cadena con el contenido HTML de la página de inicio.
     */
    public static String getHomeIndex() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>File Upload</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\">\n" +
                "    <style>\n" +
                "        body {\n" +
                "            background-image: url(https://i.pinimg.com/originals/c5/e4/b5/c5e4b54952c6857bf00a02929abffe51.gif);\n" +
                "background-size: cover;"+
                "background-repeat: no-repeat;"+
                "background-position: center center;"+
                "            font-family: \"Ubuntu\", sans-serif;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 1000px;\n" +
                "            padding: 50px;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        .form-group {\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .btn {\n" +
                "            background-color: #D9B9EA;\n" +
                "            color: #ffffff;\n" +
                "            border: none;\n" +
                "            border-radius: 5px;\n" +
                "            padding: 10px 20px;\n" +
                "            cursor: pointer;\n" +
                "            transition: background-color 0.3s ease;\n" +
                "        }\n" +
                "\n" +
                "        .btn:hover {\n" +
                "            background-color: #7663C6;\n" +
                "        }\n" +
                "\n" +
                "        #uploadMsg {\n" +
                "            margin-top: 20px;\n" +
                "            color: #007bff;\n" +
                "        }\n" +
                "\n" +
                "        .btn-file {\n" +
                "            position: relative;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .btn-file input[type=\"file\"] {\n" +
                "            position: absolute;\n" +
                "            top: 0;\n" +
                "            right: 0;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            font-size: 20px;\n" +
                "            cursor: pointer;\n" +
                "            opacity: 0;\n" +
                "            filter: alpha(opacity=0);\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            position: absolute;\n" +
                "            left: 0;\n" +
                "            top: 0;\n" +
                "            z-index: 20;\n" +
                "        }\n" +
                "\n" +
                "        .btn-file:before {\n" +
                "            content: \"Escoge un archivo:\";\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #D9B9EA;\n" +
                "            color: #ffffff;\n" +
                "            border-radius: 5px;\n" +
                "            margin-right: 10px;\n" +
                "            font-size: 16px;\n" +
                "            line-height: 24px;\n" +
                "            vertical-align: middle;\n" +
                "        }\n" +
                "\n" +
                "        .filename {\n" +
                "            display: inline-block;\n" +
                "            margin-left: 10px;\n" +
                "            vertical-align: middle;\n" +
                "            line-height: 24px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1 class=\"text-center\">CARGAR UN ARCHIVO</h1>\n" +
                "        <form action=\"/upload\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
                "            <div class=\"form-group\">\n" +
                "                <div class=\"btn btn-file\">\n" +
                "                    <input type=\"file\" id=\"file\" name=\"file\" class=\"form-control\">\n" +
                "                </div>\n" +
                "                <span id=\"filename\" class=\"filename\"></span>\n" +
                "            </div>\n" +
                "            <button type=\"button\" class=\"btn btn-block\" onclick=\"uploadFile()\">Subir</button>\n" +
                "        </form>\n" +
                "        <div id=\"uploadMsg\"></div>\n" +
                "    </div>\n" +
                "\n" +
                "    <script>\n" +
                "        function uploadFile() {\n" +
                "            const fileInput = document.getElementById(\"file\");\n" +
                "            const filenameSpan = document.getElementById(\"filename\");\n" +
                "            filenameSpan.textContent = fileInput.value.split(\"\\\\\").pop();\n" +
                "\n" +
                "            const formData = new FormData();\n" +
                "            formData.append(\"file\", fileInput.files[0]);\n" +
                "\n" +
                "            const xhr = new XMLHttpRequest();\n" +
                "            xhr.onload = function () {\n" +
                "                document.getElementById(\"uploadMsg\").innerHTML = this.responseText;\n" +
                "            };\n" +
                "            xhr.open(\"POST\", \"/upload\");\n" +
                "            xhr.send(formData);\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }
}