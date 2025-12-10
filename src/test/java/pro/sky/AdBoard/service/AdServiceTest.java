package pro.sky.AdBoard.service;

import pro.sky.AdBoard.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdServiceTest {

    @InjectMocks
    private AdServiceImpl adService;

    @Test
    void getAllAds_ShouldReturnAdsDto() {
        AdsDto result = adService.getAllAds();

        assertNotNull(result);
        assertEquals(0, result.getCount());
        assertNotNull(result.getResults());
    }

    @Test
    void getComments_WithValidAdId_ShouldReturnCommentsDto() {
        CommentsDto result = adService.getComments(1);

        assertNotNull(result);
        assertEquals(0, result.getCount());
        assertNotNull(result.getResults());
    }

    @Test
    void getUserAds_ShouldReturnAdsDto() {
        AdsDto result = adService.getUserAds();

        assertNotNull(result);
        assertInstanceOf(AdsDto.class, result);
    }

    @Test
    void removeAd_WithValidId_ShouldNotThrowException() {
        assertDoesNotThrow(() -> adService.removeAd(1));
    }

    @Test
    void deleteComment_WithValidIds_ShouldNotThrowException() {
        assertDoesNotThrow(() -> adService.deleteComment(1, 1));
    }
}