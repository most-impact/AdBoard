package pro.sky.AdBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.AdBoard.service.ImageService;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(
            value = "/images/{filename}",
            produces = {
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_GIF_VALUE,
                    "image/*"
            }
    )
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] bytes = imageService.getImage(filename);
        return ResponseEntity.ok(bytes);
    }
}
