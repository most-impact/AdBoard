package pro.sky.AdBoard.controller;

import pro.sky.AdBoard.dto.*;
import pro.sky.AdBoard.service.AdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdController.class)
@ExtendWith(MockitoExtension.class)
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdService adService;

    @Test
    void getAllAds_ShouldReturnAds() throws Exception {
        AdsDto adsDto = new AdsDto();
        when(adService.getAllAds()).thenReturn(adsDto);

        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk());
    }

    @Test
    void addAd_WithValidData_ShouldReturnCreated() throws Exception {
        CreateOrUpdateAdDto adProperties = new CreateOrUpdateAdDto();
        adProperties.setTitle("Test Ad");
        adProperties.setPrice(1000);
        adProperties.setDescription("Test description");

        MockMultipartFile properties = new MockMultipartFile(
                "properties",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(adProperties)
        );

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image".getBytes()
        );

        AdDto adDto = new AdDto();
        when(adService.addAd(any(CreateOrUpdateAdDto.class), any(byte[].class), anyString()))
                .thenReturn(adDto);

        mockMvc.perform(multipart("/ads")
                        .file(properties)
                        .file(image))
                .andExpect(status().isCreated());
    }

    @Test
    void getAd_WithValidId_ShouldReturnAd() throws Exception {
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        when(adService.getAd(anyInt())).thenReturn(extendedAdDto);

        mockMvc.perform(get("/ads/1"))
                .andExpect(status().isOk());
    }

    @Test
    void removeAd_WithValidId_ShouldReturnNoContent() throws Exception {
        doNothing().when(adService).removeAd(anyInt());

        mockMvc.perform(delete("/ads/1"))
                .andExpect(status().isNoContent());
    }

    // Добавьте остальные тесты для всех методов контроллера
}