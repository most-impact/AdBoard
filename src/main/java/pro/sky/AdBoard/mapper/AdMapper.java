package pro.sky.AdBoard.mapper;

import org.springframework.stereotype.Component;
import pro.sky.AdBoard.dto.AdDto;
import pro.sky.AdBoard.dto.AdsDto;
import pro.sky.AdBoard.dto.CreateOrUpdateAdDto;
import pro.sky.AdBoard.dto.ExtendedAdDto;
import pro.sky.AdBoard.model.Ad;
import pro.sky.AdBoard.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class  AdMapper {

    public AdDto toAdDto(Ad ad) {
        AdDto dto = new AdDto();
        dto.setPk(ad.getPk());
        dto.setAuthor(ad.getAuthor() != null ? ad.getAuthor().getId() : null);
        dto.setImage(ad.getImage());
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        return dto;
    }

    public ExtendedAdDto toExtendedAdDto(Ad ad) {
        ExtendedAdDto dto = new ExtendedAdDto();
        dto.setPk(ad.getPk());
        dto.setAuthorFirstName(ad.getAuthor() != null ? ad.getAuthor().getFirstName() : null);
        dto.setAuthorLastName(ad.getAuthor() != null ? ad.getAuthor().getLastName() : null);
        dto.setDescription(ad.getDescription());
        dto.setEmail(ad.getAuthor() != null ? ad.getAuthor().getUsername() : null);
        dto.setImage(ad.getImage());
        dto.setPhone(ad.getAuthor() != null ? ad.getAuthor().getPhone() : null);
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        return dto;
    }

    public AdsDto toAdsDto(List<Ad> ads) {
        AdsDto dto = new AdsDto();
        dto.setCount(ads.size());
        dto.setResults(ads.stream()
                .map(this::toAdDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public Ad fromCreateOrUpdateAdDto(CreateOrUpdateAdDto dto, User author, String imagePath) {
        Ad ad = new Ad();
        ad.setAuthor(author);
        ad.setTitle(dto.getTitle());
        ad.setPrice(dto.getPrice());
        ad.setDescription(dto.getDescription());
        ad.setImage(imagePath);
        // createdAt заполним в сервисе
        return ad;
    }

    public void updateAdFromDto(CreateOrUpdateAdDto dto, Ad ad) {
        ad.setTitle(dto.getTitle());
        ad.setPrice(dto.getPrice());
        ad.setDescription(dto.getDescription());
    }
}
