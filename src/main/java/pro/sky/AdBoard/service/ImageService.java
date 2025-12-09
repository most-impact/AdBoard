package pro.sky.AdBoard.service;

public interface ImageService {

    String saveImage(byte[] bytes, String contentType);

    byte[] getImage(String filename);
}
