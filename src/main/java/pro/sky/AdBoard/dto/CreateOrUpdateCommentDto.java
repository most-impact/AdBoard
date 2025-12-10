package pro.sky.AdBoard.dto;

public class CreateOrUpdateCommentDto {
    private String text;

    public CreateOrUpdateCommentDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
