package pro.sky.AdBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.AdBoard.dto.*;
import pro.sky.AdBoard.service.AdService;

import java.io.IOException;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<AdDto> addAd(
            @RequestPart("properties") CreateOrUpdateAdDto properties,
            @RequestPart("image") MultipartFile image) throws IOException
    {
        AdDto adDto = adService.addAd(properties, image.getBytes(), image.getContentType());
        return ResponseEntity.status(201).body(adDto);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getComments(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable("id") Integer id,
            @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        CommentDto commentDto = adService.addComment(id, createOrUpdateCommentDto);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable("id") Integer id) {
        ExtendedAdDto extendedAdDto = adService.getAd(id);
        return ResponseEntity.ok(extendedAdDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id) {
        adService.removeAd(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(
            @PathVariable("id") Integer id,
            @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        AdDto adDto = adService.updateAd(id, createOrUpdateAdDto);
        return ResponseEntity.ok(adDto);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("adId") Integer adId,
            @PathVariable("commentId") Integer commentId) {
        adService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("adId") Integer adId,
            @PathVariable("commentId") Integer commentId,
            @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        CommentDto commentDto = adService.updateComment(adId, commentId, createOrUpdateCommentDto);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe() {
        return ResponseEntity.ok(adService.getUserAds());
    }

    @PatchMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> updateImage(
            @PathVariable("id") Integer id,
            @RequestParam("image") MultipartFile image) throws IOException
    {
        byte[] bytes = adService.updateAdImage(id, image.getBytes(), image.getContentType());
        return ResponseEntity.ok(bytes);
    }
}
