package pro.sky.AdBoard.service;

import pro.sky.AdBoard.dto.*;

import java.util.List;

@Service
@Transactional
@Slf4j
public class AdServiceImpl implements AdService {

    @Override
    public AdsDto getAllAds() {
        log.info("Getting all ads");
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(0);
        adsDto.setResults(List.of());
        return adsDto;
    }

    @Override
    public AdDto addAd(CreateOrUpdateAdDto properties, byte[] image, String contentType) {
        log.info("Creating new ad with title: {}", properties.getTitle());
        AdDto adDto = new AdDto();
        return adDto;
    }

    @Override
    public CommentsDto getComments(Integer adId) {
        log.info("Getting comments for ad ID: {}", adId);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(0);
        commentsDto.setResults(List.of());
        return commentsDto;
    }

    @Override
    public CommentDto addComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        log.info("Adding comment to ad ID: {}", adId);
        CommentDto commentDto = new CommentDto();
        return commentDto;
    }

    @Override
    public ExtendedAdDto getAd(Integer id) {
        log.info("Getting ad details for ID: {}", id);
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        return extendedAdDto;
    }

    @Override
    public void removeAd(Integer id) {
        log.info("Removing ad with ID: {}", id);
    }

    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        log.info("Updating ad with ID: {}", id);
        AdDto adDto = new AdDto();
        return adDto;
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        log.info("Deleting comment ID: {} from ad ID: {}", commentId, adId);
    }

    @Override
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        log.info("Updating comment ID: {} for ad ID: {}", commentId, adId);
        CommentDto commentDto = new CommentDto();
        return commentDto;
    }

    @Override
    public AdsDto getUserAds() {
        log.info("Getting ads for current user");
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(0);
        adsDto.setResults(List.of());
        return adsDto;
    }

    @Override
    public byte[] updateAdImage(Integer id, byte[] image, String contentType) {
        log.info("Updating image for ad ID: {}", id);
        return new byte[0];
    }
}