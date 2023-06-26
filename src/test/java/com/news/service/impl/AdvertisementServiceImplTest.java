package com.news.service.impl;
import com.news.entity.Advertisement;
import com.news.entity.Advertiser;
import com.news.entity.Newspaper;
import com.news.exception.AdvertisementNotFoundException;
import com.news.repository.AdvertisementRepository;
import com.news.service.impl.AdvertisementServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdvertisementServiceImplTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private AdvertisementServiceImpl advertisementService;

    @Test
    public void testAddAdvertisement() {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(1L);
        advertisement.setAdvertisementTitle("Test Advertisement");
        advertisement.setAdvertisementDescription("Test Description");
        advertisement.setPublicationDate(LocalDate.now());
        advertisement.setExpiryDate(LocalDate.now().plusDays(30));
        advertisement.setAdvertisementPrice(100.0);
        advertisement.setAdvertisementStatus("Active");

        when(advertisementRepository.save(advertisement)).thenReturn(advertisement);

        Advertisement result = advertisementService.addAdvertisement(advertisement);

        assertEquals(advertisement, result);
    }

    @Test
    public void testUpdateAdvertisement() throws AdvertisementNotFoundException {
        long advertisementId = 1L;
        Advertisement existingAdvertisement = new Advertisement();
        existingAdvertisement.setAdvertisementId(advertisementId);
        existingAdvertisement.setAdvertisementTitle("Existing Advertisement");

        Advertisement updatedAdvertisement = new Advertisement();
        updatedAdvertisement.setAdvertisementId(advertisementId);
        updatedAdvertisement.setAdvertisementTitle("Updated Advertisement");

        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(existingAdvertisement));
        when(advertisementRepository.save(updatedAdvertisement)).thenReturn(updatedAdvertisement);

        Advertisement result = advertisementService.updateAdvertisement(updatedAdvertisement);

        assertEquals(updatedAdvertisement, result);
    }

    @Test
    public void testUpdateAdvertisementNotFound() {
        long advertisementId = 1L;
        Advertisement updatedAdvertisement = new Advertisement();
        updatedAdvertisement.setAdvertisementId(advertisementId);
        updatedAdvertisement.setAdvertisementTitle("Updated Advertisement");

        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> advertisementService.updateAdvertisement(updatedAdvertisement));
    }

    @Test
    public void testFindAdvertisementById() throws AdvertisementNotFoundException {
        long advertisementId = 1L;
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement.setAdvertisementTitle("Test Advertisement");

        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));

        Advertisement result = advertisementService.findAdvertisementById(advertisementId);

        assertEquals(advertisement, result);
    }

    @Test
    public void testFindAdvertisementByIdNotFound() {
        long advertisementId = 1L;

        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> advertisementService.findAdvertisementById(advertisementId));
    }

    @Test
    public void testDeleteAdvertisement() throws AdvertisementNotFoundException {
        long advertisementId = 1L;
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement.setAdvertisementTitle("Test Advertisement");

        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));

        advertisementService.deleteAdvertisement(advertisementId);

        verify(advertisementRepository).delete(advertisement);
    }

    @Test
    public void testDeleteAdvertisementNotFound() {
        long advertisementId = 1L;

        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> advertisementService.deleteAdvertisement(advertisementId));
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

        when(advertisementRepository.findAll()).thenReturn(advertisements);

        List<Advertisement> result = advertisementService.getAllAdvertisements();

        assertEquals(advertisements, result);
    }

    @Test
    public void testGetAllAdvertisementsById() {
        long advertiserId = 1L;
        Advertisement advertisement1 = new Advertisement();
        advertisement1.setAdvertisementId(1L);
        advertisement1.setAdvertisementTitle("Advertisement 1");
        advertisement1.setAdvertiser(new Advertiser());

        Advertisement advertisement2 = new Advertisement();
        advertisement2.setAdvertisementId(2L);
        advertisement2.setAdvertisementTitle("Advertisement 2");
        advertisement2.setAdvertiser(new Advertiser());

        List<Advertisement> advertisements = Arrays.asList(advertisement1, advertisement2);

        when(advertisementRepository.findAll()).thenReturn(advertisements);

        List<Advertisement> result = advertisementService.getAllAdvertisementsById(advertiserId);

        assertEquals(0, result.size());
        //assertEquals(advertisement1, result.get(0));
    }

    /*
    @Test
    public void testGetAllAdvertisementsBynId() {
        long newspaperId = 1L;
        Advertisement advertisement1 = new Advertisement();
        advertisement1.setAdvertisementId(1L);
        advertisement1.setAdvertisementTitle("Advertisement 1");
        advertisement1.setNewspaper(new Newspaper());

        Advertisement advertisement2 = new Advertisement();
        advertisement2.setAdvertisementId(2L);
        advertisement2.setAdvertisementTitle("Advertisement 2");
        advertisement2.setNewspaper(new Newspaper());

        List<Advertisement> advertisements = Arrays.asList(advertisement1, advertisement2);

        when(advertisementRepository.findAll()).thenReturn(advertisements);

        List<Advertisement> result = advertisementService.getAllAdvertisementsBynId(newspaperId);

        assertEquals(1, result.size());
        assertEquals(advertisement1, result.get(0));
    }
    */
}
