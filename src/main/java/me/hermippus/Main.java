package me.hermippus;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import me.hermippus.utils.PropertiesUtil;
import org.tinylog.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IOException {
        PropertiesUtil.load("server.properties");
        int port = PropertiesUtil.getInt("server.port", 9000);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/upload", new ImageHandler());
        server.setExecutor(null);
        server.start();
        Logger.info("Server started on port {}", port);
    }

    static class ImageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // POST /upload
            if ("POST".equals(exchange.getRequestMethod())) {
                Logger.info("Received POST request..");
                InputStream stream = exchange.getRequestBody();
                BufferedImage image = ImageIO.read(stream);

                if (image == null) {
                    Logger.error("Failed to read image from request");
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }
                Logger.info("Compressing image..");

                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
                if (!writers.hasNext()) {
                    Logger.error("No image writer found");
                    exchange.sendResponseHeaders(500, -1);
                    return;
                }
                ImageWriter writer = writers.next();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);
                writer.setOutput(imageOutputStream);

                ImageWriteParam param = writer.getDefaultWriteParam();
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.4f);

                IIOImage iioImage = new IIOImage(image, null, null);
                writer.write(null, iioImage, param);

                imageOutputStream.close();
                writer.dispose();

                byte[] response = byteArrayOutputStream.toByteArray();
                exchange.sendResponseHeaders(200, response.length);
                Logger.info("Sending response..");

                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response);
                outputStream.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
                Logger.error("Someone sent an unsupported request method: " + exchange.getRequestMethod());
            }
        }
    }
}
