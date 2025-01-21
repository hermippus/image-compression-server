package me.hermippus.server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ImageService {

    public byte[] compressImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new IOException("Only JPG and PNG images are allowed.");
        }

        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new IOException("Could not read image from stream.");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (contentType.equals("image/jpeg")) {
            compressJpeg(image, byteArrayOutputStream);
        } else {
            compressPng(image, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void compressJpeg(BufferedImage image, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IOException("No JPG image writer found.");
        }
        ImageWriter writer = writers.next();
        try (ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream)) {
            writer.setOutput(imageOutputStream);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f);
            writer.write(null, new IIOImage(image, null, null), param);
        }
    }

    private void compressPng(BufferedImage image, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        //TODO
    }
}
