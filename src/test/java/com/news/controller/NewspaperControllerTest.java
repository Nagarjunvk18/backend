package com.news.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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

import com.news.entity.Advertisement;
import com.news.entity.Newspaper;
import com.news.exception.NewspaperNotFoundException;
import com.news.service.AdvertisementService;
import com.news.service.NewspaperService;

public class NewspaperControllerTest {

    @Mock
    private NewspaperService newspaperService;

    @Mock
    private AdvertisementService advertisementService;

    @InjectMocks
    private NewspaperController newspaperController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostNewspaper() {
        Newspaper newspaper = new Newspaper();
        newspaper.setNewspaperId(1L);
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("newspaper added in DB");

//        doNothing().when(newspaperService).addNewspaper(newspaper);

        ResponseEntity<String> response = newspaperController.postNewspaper(newspaper);

        verify(newspaperService, times(1)).addNewspaper(newspaper);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetAllNewspapers() {
        Newspaper newspaper1 = new Newspaper();
        Newspaper newspaper2 = new Newspaper();
        List<Newspaper> expectedNewspapers = Arrays.asList(newspaper1, newspaper2);

        when(newspaperService.getAllNewspapers()).thenReturn(expectedNewspapers);

        List<Newspaper> newspapers = newspaperController.getAllNewspapers();

        verify(newspaperService, times(1)).getAllNewspapers();
        assertEquals(expectedNewspapers, newspapers);
    }

    @Test
    public void testGetNewspaperById() throws NewspaperNotFoundException {
        long newspaperId = 1L;
        Newspaper expectedNewspaper = new Newspaper();
        expectedNewspaper.setNewspaperId(newspaperId);

        when(newspaperService.findNewspaperById(newspaperId)).thenReturn(expectedNewspaper);

        ResponseEntity<Newspaper> response = newspaperController.getNewspaperById(newspaperId);

        verify(newspaperService, times(1)).findNewspaperById(newspaperId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNewspaper, response.getBody());
    }

    @Test
    public void testGetAllAdvertisementsBynId() {
        long newspaperId = 1L;
        Advertisement advertisement1 = new Advertisement();
        Advertisement advertisement2 = new Advertisement();
        List<Advertisement> expectedAdvertisements = Arrays.asList(advertisement1, advertisement2);

        when(advertisementService.getAllAdvertisementsByNewspaperId(newspaperId)).thenReturn(expectedAdvertisements);

        List<Advertisement> response = newspaperController.getAllAdvertisementsByNewspaperId((int)newspaperId);

        verify(advertisementService, times(1)).getAllAdvertisementsByNewspaperId(newspaperId);
       
        
    }

    @Test
    public void testDeleteNewspaperById() throws NewspaperNotFoundException {
        int newspaperId = 1;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("newspaper details Deleted successfully");

        doNothing().when(newspaperService).deleteNewspaperById(newspaperId);

        ResponseEntity<String> response = newspaperController.deleteNewspaperById(newspaperId);

        verify(newspaperService, times(1)).deleteNewspaperById(newspaperId);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testUpdateNewspaper() throws NewspaperNotFoundException {
        long newspaperId = 1L;
        Newspaper newspaper = new Newspaper();
        newspaper.setNewspaperId(newspaperId);
        Newspaper updatedNewspaper = new Newspaper();
        updatedNewspaper.setNewspaperId(newspaperId);

        when(newspaperService.updateNewspaper(newspaper)).thenReturn(updatedNewspaper);

        ResponseEntity<Newspaper> response = newspaperController.updateNewspaper(newspaperId, newspaper);

        verify(newspaperService, times(1)).updateNewspaper(newspaper);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNewspaper, response.getBody());
    }
}