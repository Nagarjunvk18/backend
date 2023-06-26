package com.news.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.news.entity.Advertiser;
import com.news.exception.AdvertiserNotFoundException;
import com.news.service.AdvertiserService;

public class AdvertiserControllerTest {

    @Mock
    private AdvertiserService advertiserService;

    @InjectMocks
    private AdvertiserController advertiserController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAdvertiser() {
        Advertiser advertiser = new Advertiser();
        advertiser.setId(1L);
        advertiser.setName("Test Advertiser");
        advertiser.setMobile("1234567890");
        advertiser.setEmail("test@example.com");
        advertiser.setCompany("Test Company");

        ResponseEntity<String> response = advertiserController.addAdvertiser(advertiser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("advertiser details are added successfully in DB", response.getBody());

        verify(advertiserService, times(1)).addAdvertiser(advertiser);
    }

    @Test
    public void testFindAdvertiserById() throws AdvertiserNotFoundException {
        Advertiser advertiser = new Advertiser();
        advertiser.setId(1L);
        advertiser.setName("Test Advertiser");

        when(advertiserService.findAdvertiserById(1L)).thenReturn(advertiser);

        ResponseEntity<Advertiser> response = advertiserController.findAdvertiserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertiser, response.getBody());

        verify(advertiserService, times(1)).findAdvertiserById(1L);
    }

    @Test
    public void testUpdateAdvertiser() throws AdvertiserNotFoundException {
        Advertiser advertiser = new Advertiser();
        advertiser.setId(1L);
        advertiser.setName("Updated Advertiser");

        when(advertiserService.updateAdvertiser(advertiser)).thenReturn(advertiser);

        ResponseEntity<Advertiser> response = advertiserController.updateAdvertiser(1L, advertiser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertiser, response.getBody());

        verify(advertiserService, times(1)).updateAdvertiser(advertiser);
    }

    @Test
    public void testGetAllAdvertisers() {
        Advertiser advertiser1 = new Advertiser();
        advertiser1.setId(1L);
        advertiser1.setName("Advertiser 1");

        Advertiser advertiser2 = new Advertiser();
        advertiser2.setId(2L);
        advertiser2.setName("Advertiser 2");

        List<Advertiser> advertisers = Arrays.asList(advertiser1, advertiser2);
        when(advertiserService.getAllAdvertisers()).thenReturn(advertisers);

        List<Advertiser> response = advertiserController.getAllAdvertisers();

        assertEquals(advertisers, response);

        verify(advertiserService, times(1)).getAllAdvertisers();
    }

    @Test
    public void testDeleteAdvertiser() throws AdvertiserNotFoundException {
        ResponseEntity<Void> response = advertiserController.deleteAdvertiser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(advertiserService, times(1)).deleteAdvertiser(1L);
    }
/*
    @Test
    public void testGetAllAdvertisementsById() {
        Advertiser advertiser = new Advertiser();
        advertiser.setId(1L);

        when(advertiserService.getAllAdvertisementsById(1L)).thenReturn(advertiser.getAdvertisements());

        ResponseEntity<List<Advertisement>> response = advertiserController.getAllAdvertisementsById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertiser.getAdvertisements(), response.getBody());

        verify(advertiserService, times(1)).getAllAdvertisementsById(1L);
    }
    */
}