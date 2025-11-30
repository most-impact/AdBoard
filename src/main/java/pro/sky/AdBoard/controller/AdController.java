package pro.sky.AdBoard.controller;

import pro.sky.AdBoard.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ads")
public class AdController {

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        AdsDto adsDto = new AdsDto();
        return ResponseEntity.ok(adsDto);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<AdDto> addAd(
            @RequestPart("properties") CreateOrUpdateAdDto properties,
            @RequestPart("image") MultipartFile image) {
        AdDto adDto = new AdDto();
        return ResponseEntity.status(201).body(adDto);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Integer id) {
        CommentsDto commentsDto = new CommentsDto();
        return ResponseEntity.ok(commentsDto);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable("id") Integer id,
            @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        CommentDto commentDto = new CommentDto();
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable("id") Integer id) {
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        return ResponseEntity.ok(extendedAdDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(
            @PathVariable("id") Integer id,
            @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        AdDto adDto = new AdDto();
        return ResponseEntity.ok(adDto);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("adId") Integer adId,
            @PathVariable("commentId") Integer commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("adId") Integer adId,
            @PathVariable("commentId") Integer commentId,
            @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        CommentDto commentDto = new CommentDto();
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe() {
        AdsDto adsDto = new AdsDto();
        return ResponseEntity.ok(adsDto);
    }

    @PatchMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> updateImage(
            @PathVariable("id") Integer id,
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(new byte[0]);
    }
}