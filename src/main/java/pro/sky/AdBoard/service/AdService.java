package pro.sky.AdBoard.service;

import pro.sky.AdBoard.dto.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface AdService {
    AdsDto getAllAds();
    AdDto addAd(CreateOrUpdateAdDto properties, byte[] image, String contentType);
    CommentsDto getComments(Integer adId);
    CommentDto addComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto);
    ExtendedAdDto getAd(Integer id);
    void removeAd(Integer id);
    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);
    void deleteComment(Integer adId, Integer commentId);
    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto);
    AdsDto getUserAds();
    byte[] updateAdImage(Integer id, byte[] image, String contentType);
}
