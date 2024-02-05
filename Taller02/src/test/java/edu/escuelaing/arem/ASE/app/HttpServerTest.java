package edu.escuelaing.arem.ASE.app;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;

public class HttpServerTest {

    @Test
    public void testConvertFileToHTML() throws IOException {
        HttpServer httpServer = new HttpServer();
        File file = new File("Taller02\\src\\main\\resource\\prueba.html");
        String result;
        try{
            result = httpServer.toHTML(file);
            assertTrue(result.contains("Content-Type: text/html"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertFileToImage() throws IOException {
        HttpServer httpServer = new HttpServer();
        File file = new File("Taller02\\src\\main\\resource\\inuyasha.jpg");
        String result;
        try {
            result = httpServer.toImage(file, "jpg");
            assertTrue(result.contains("Content-Type: text/jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testConvertFileToCSS() throws IOException {
        HttpServer httpServer = new HttpServer();
        File file = new File("Taller02\\src\\main\\resource\\harry.css");
        String result;
        try{
            result = httpServer.toJs(file);
            assertTrue(result.contains("Content-Type: text/css"));
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    @Test
    public void testConvertFileToJS() throws IOException {
        HttpServer httpServer = new HttpServer();
        File file = new File("\"Taller02\\src\\main\\resource\\harry.js\"");
        String result;
        try{
            result = httpServer.toJs(file);
            assertTrue(result.contains("application/javascript"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
