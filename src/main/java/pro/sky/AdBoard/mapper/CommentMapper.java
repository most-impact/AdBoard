package pro.sky.AdBoard.mapper;

import org.springframework.stereotype.Component;
import pro.sky.AdBoard.dto.CommentDto;
import pro.sky.AdBoard.dto.CommentsDto;
import pro.sky.AdBoard.dto.CreateOrUpdateCommentDto;
import pro.sky.AdBoard.model.Ad;
import pro.sky.AdBoard.model.Comment;
import pro.sky.AdBoard.model.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setPk(comment.getPk());
        dto.setAuthor(comment.getAuthor() != null ? comment.getAuthor().getId() : null);
        dto.setAuthorFirstName(comment.getAuthor() != null ? comment.getAuthor().getFirstName() : null);
        dto.setAuthorImage(comment.getAuthor() != null ? comment.getAuthor().getImage() : null);
        dto.setText(comment.getText());

        if (comment.getCreatedAt() != null) {
            dto.setCreatedAt(comment.getCreatedAt().toEpochSecond(ZoneOffset.UTC));
        }

        return dto;
    }

    public CommentsDto toCommentsDto(List<Comment> comments) {
        CommentsDto dto = new CommentsDto();
        dto.setCount(comments.size());
        dto.setResults(comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public Comment fromCreateDto(CreateOrUpdateCommentDto dto, Ad ad, User author) {
        Comment comment = new Comment();
        comment.setAd(ad);
        comment.setAuthor(author);
        comment.setText(dto.getText());
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }

    public void updateCommentFromDto(CreateOrUpdateCommentDto dto, Comment comment) {
        comment.setText(dto.getText());
    }
}
