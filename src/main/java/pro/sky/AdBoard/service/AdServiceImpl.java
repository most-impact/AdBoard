package pro.sky.AdBoard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.AdBoard.dto.*;
import pro.sky.AdBoard.mapper.AdMapper;
import pro.sky.AdBoard.mapper.CommentMapper;
import pro.sky.AdBoard.model.Ad;
import pro.sky.AdBoard.model.Comment;
import pro.sky.AdBoard.model.User;
import pro.sky.AdBoard.repository.AdRepository;
import pro.sky.AdBoard.repository.CommentRepository;
import pro.sky.AdBoard.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdServiceImpl implements AdService {

    private static final String USER_NOT_FOUND = "User not found";

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final CommentMapper commentMapper;
    private final ImageService imageService;

    private User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public AdsDto getAllAds() {
        log.info("Getting all ads");
        List<Ad> ads = adRepository.findAll();
        return adMapper.toAdsDto(ads);
    }

    @Override
    public AdDto addAd(CreateOrUpdateAdDto properties, byte[] image, String contentType) {
        User author = getCurrentUserEntity();
        log.info("Adding new ad, author: {}", author.getUsername());

        String filename = imageService.saveImage(image, contentType);
        String imagePath = "/images/" + filename;

        Ad ad = adMapper.fromCreateOrUpdateAdDto(properties, author, imagePath);

        Ad saved = adRepository.save(ad);
        return adMapper.toAdDto(saved);
    }

    @Override
    public CommentsDto getComments(Integer adId) {
        log.info("Getting comments for ad id={}", adId);
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        List<Comment> comments = commentRepository.findAllByAd(ad);
        return commentMapper.toCommentsDto(comments);
    }

    @Override
    public CommentDto addComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        User author = getCurrentUserEntity();
        log.info("Adding comment to ad id={} by user={}", adId, author.getUsername());

        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));

        Comment comment = commentMapper.fromCreateDto(createOrUpdateCommentDto, ad, author);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toCommentDto(saved);
    }

    @Override
    public ExtendedAdDto getAd(Integer id) {
        log.info("Getting ad id={}", id);
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        return adMapper.toExtendedAdDto(ad);
    }

    @Override
    public void removeAd(Integer id) {
        log.info("Removing ad id={}", id);
        if (!adRepository.existsById(id)) {
            throw new IllegalArgumentException("Ad not found");
        }
        adRepository.deleteById(id);
    }

    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        log.info("Updating ad id={}", id);
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));

        adMapper.updateAdFromDto(createOrUpdateAdDto, ad);
        Ad saved = adRepository.save(ad);
        return adMapper.toAdDto(saved);
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        log.info("Deleting comment id={} for ad id={}", commentId, adId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getAd().getPk().equals(adId)) {
            throw new IllegalArgumentException("Comment does not belong to this ad");
        }

        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        log.info("Updating comment id={} for ad id={}", commentId, adId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getAd().getPk().equals(adId)) {
            throw new IllegalArgumentException("Comment does not belong to this ad");
        }

        commentMapper.updateCommentFromDto(createOrUpdateCommentDto, comment);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toCommentDto(saved);
    }

    @Override
    public AdsDto getUserAds() {
        User user = getCurrentUserEntity();
        log.info("Getting ads for user={}", user.getUsername());

        List<Ad> ads = adRepository.findAllByAuthor(user);
        return adMapper.toAdsDto(ads);
    }

    @Override
    public byte[] updateAdImage(Integer id, byte[] image, String contentType) {
        log.info("Updating image for ad id={}", id);
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));

        String filename = imageService.saveImage(image, contentType);
        String imagePath = "/images/" + filename;

        ad.setImage(imagePath);
        adRepository.save(ad);

        return image;
    }
}
