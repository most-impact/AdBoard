package pro.sky.AdBoard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final Path imagesDir;

    public ImageServiceImpl(@Value("${images.dir}") String imagesDir) {
        this.imagesDir = Paths.get(imagesDir);
        try {
            Files.createDirectories(this.imagesDir);
            log.info("Images directory: {}", this.imagesDir.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create images directory: " + imagesDir, e);
        }
    }

    @Override
    public String saveImage(byte[] bytes, String contentType) {
        String extension = getExtensionByContentType(contentType);
        String filename = UUID.randomUUID() + extension;
        Path target = imagesDir.resolve(filename);
        try {
            Files.write(target, bytes);
            log.info("Saved image: {}", target.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
        return filename;
    }

    @Override
    public byte[] getImage(String filename) {
        Path target = imagesDir.resolve(filename);
        try {
            return Files.readAllBytes(target);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image: " + filename, e);
        }
    }

    private String getExtensionByContentType(String contentType) {
        if (contentType == null) {
            return "";
        }
        return switch (contentType) {
            case "image/png" -> ".png";
            case "image/jpeg", "image/jpg" -> ".jpg";
            case "image/gif" -> ".gif";
            default -> "";
        };
    }
}
