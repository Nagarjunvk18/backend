package com.news.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.news.entity.Advertisement;
import com.news.exception.AdvertisementNotFoundException;
import com.news.service.AdvertisementService;

public class AdvertisementControllerTest {

    @Mock
    private AdvertisementService advertisementService;

    @InjectMocks
    private AdvertisementController advertisementController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAdvertisement() {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(1L);
        advertisement.setAdvertisementTitle("Test Advertisement");
        advertisement.setAdvertisementDescription("This is a test advertisement");
        advertisement.setPublicationDate(LocalDate.now());
        advertisement.setExpiryDate(LocalDate.now().plusDays(30));
        advertisement.setAdvertisementPrice(100.0);
        advertisement.setAdvertisementStatus("Active");

        ResponseEntity<String> response = advertisementController.addAdvertisement(advertisement);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Advertisement details are added in DB", response.getBody());

        verify(advertisementService, times(1)).addAdvertisement(advertisement);
    }

    @Test
    public void testUpdateAdvertisement() throws AdvertisementNotFoundException {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(1L);
        advertisement.setAdvertisementTitle("Updated Advertisement");
        advertisement.setAdvertisementDescription("This is an updated advertisement");
        advertisement.setPublicationDate(LocalDate.now());
        advertisement.setExpiryDate(LocalDate.now().plusDays(30));
        advertisement.setAdvertisementPrice(200.0);
        advertisement.setAdvertisementStatus("Active");

        when(advertisementService.updateAdvertisement(advertisement)).thenReturn(advertisement);

        ResponseEntity<Advertisement> response = advertisementController.updateAdvertisement(1L, advertisement);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertisement, response.getBody());

        verify(advertisementService, times(1)).updateAdvertisement(advertisement);
    }

    @Test
    public void testFindAdvertisementById() throws AdvertisementNotFoundException {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(1L);
        advertisement.setAdvertisementTitle("Test Advertisement");
        advertisement.setAdvertisementDescription("This is a test advertisement");
        advertisement.setPublicationDate(LocalDate.now());
        advertisement.setExpiryDate(LocalDate.now().plusDays(30));
        advertisement.setAdvertisementPrice(100.0);
        advertisement.setAdvertisementStatus("Active");

        when(advertisementService.findAdvertisementById(1L)).thenReturn(advertisement);

        ResponseEntity<Advertisement> response = advertisementController.findAdvertisementById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertisement, response.getBody());

        verify(advertisementService, times(1)).findAdvertisementById(1L);
    }

    @Test
    public void testDeleteAdvertisement() throws AdvertisementNotFoundException {
        ResponseEntity<String> response = advertisementController.deleteAdvertisement(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Advertisement details Deleted successfully", response.getBody());

        verify(advertisementService, times(1)).deleteAdvertisement(1L);
    }

    @Test
    public void testGetAllAdvertisements() {
        Advertisement advertisement1 = new Advertisement();
        advertisement1.setAdvertisementId(1L);
        advertisement1.setAdvertisementTitle("Advertisement 1");

        Advertisement advertisement2 = new Advertisement();
        advertisement2.setAdvertisementId(2L);
        advertisement2.setAdvertisementTitle("Advertisement 2");

        List<Advertisement> advertisements = Arrays.asList(advertisement1, advertisement2);
        when(advertisementService.getAllAdvertisements()).thenReturn(advertisements);

        ResponseEntity<List<Advertisement>> response = advertisementController.getAllAdvertisements();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertisements, response.getBody());

        verify(advertisementService, times(1)).getAllAdvertisements();
    }
}
