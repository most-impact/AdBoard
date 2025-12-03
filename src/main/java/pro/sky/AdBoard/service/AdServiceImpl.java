package pro.sky.AdBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.AdBoard.dto.*;
import pro.sky.AdBoard.mapper.AdMapper;
import pro.sky.AdBoard.mapper.CommentMapper;
import pro.sky.AdBoard.model.Ad;
import pro.sky.AdBoard.model.Comment;
import pro.sky.AdBoard.model.User;
import pro.sky.AdBoard.model.UserRole;
import pro.sky.AdBoard.repository.AdRepository;
import pro.sky.AdBoard.repository.CommentRepository;
import pro.sky.AdBoard.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public AdsDto getAllAds() {
        List<Ad> ads = adRepository.findAll();
        return adMapper.toAdsDto(ads);
    }

    @Override
    @Transactional
    public AdDto addAd(CreateOrUpdateAdDto properties, byte[] image, String contentType) {
        User author = getCurrentUserEntity();
        String imagePath = saveImage(image, contentType);
        
        Ad ad = adMapper.fromCreateOrUpdateAdDto(properties, author, imagePath);
        ad.setCreatedAt(LocalDateTime.now());
        
        adRepository.save(ad);
        return adMapper.toAdDto(ad);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentsDto getComments(Integer adId) {
        Ad ad = getAdEntity(adId);
        List<Comment> comments = commentRepository.findAllByAd(ad);
        return commentMapper.toCommentsDto(comments);
    }

    @Override
    @Transactional
    public CommentDto addComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Ad ad = getAdEntity(adId);
        User author = getCurrentUserEntity();
        
        Comment comment = commentMapper.fromCreateDto(createOrUpdateCommentDto, ad, author);
        commentRepository.save(comment);
        
        return commentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public ExtendedAdDto getAd(Integer id) {
        Ad ad = getAdEntity(id);
        return adMapper.toExtendedAdDto(ad);
    }

    @Override
    @Transactional
    public void removeAd(Integer id) {
        Ad ad = getAdEntity(id);
        checkPermission(ad.getAuthor());
        commentRepository.deleteAll(commentRepository.findAllByAd(ad)); // cascade delete comments manually if not set in DB
        adRepository.delete(ad);
    }

    @Override
    @Transactional
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad ad = getAdEntity(id);
        checkPermission(ad.getAuthor());
        adMapper.updateAdFromDto(createOrUpdateAdDto, ad);
        adRepository.save(ad);
        return adMapper.toAdDto(ad);
    }

    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer commentId) {
        Comment comment = getCommentEntity(commentId);
        if (!comment.getAd().getPk().equals(adId)) {
             throw new IllegalArgumentException("Comment does not belong to this ad");
        }
        checkPermission(comment.getAuthor());
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Comment comment = getCommentEntity(commentId);
         if (!comment.getAd().getPk().equals(adId)) {
             throw new IllegalArgumentException("Comment does not belong to this ad");
        }
        checkPermission(comment.getAuthor());
        commentMapper.updateCommentFromDto(createOrUpdateCommentDto, comment);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public AdsDto getUserAds() {
        User author = getCurrentUserEntity();
        List<Ad> ads = adRepository.findAllByAuthor(author);
        return adMapper.toAdsDto(ads);
    }

    @Override
    @Transactional
    public byte[] updateAdImage(Integer id, byte[] image, String contentType) {
        Ad ad = getAdEntity(id);
        checkPermission(ad.getAuthor());
        String imagePath = saveImage(image, contentType);
        ad.setImage(imagePath);
        adRepository.save(ad);
        return image; // Returning the uploaded image bytes as confirmation
    }

    private User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Ad getAdEntity(Integer id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
    }

    private Comment getCommentEntity(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }

    private void checkPermission(User owner) {
        User currentUser = getCurrentUserEntity();
        if (!currentUser.getId().equals(owner.getId()) && currentUser.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("You do not have permission to modify this resource");
        }
    }
    
    private String saveImage(byte[] image, String contentType) {
        // Placeholder for image saving logic
        return "/images/" + UUID.randomUUID() + (contentType != null && contentType.contains("png") ? ".png" : ".jpg");
    }
}
