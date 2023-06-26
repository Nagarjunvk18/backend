package com.news.service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.news.entity.Newspaper;
import com.news.exception.NewspaperNotFoundException;
import com.news.repository.NewspaperRepository;

@SpringBootTest
public class NewspaperServiceImplTest {

    @Mock
    private NewspaperRepository newspaperRepository;

    @InjectMocks
    private NewspaperServiceImpl newspaperService;

    @Test
    public void testAddNewspaper() {
        Newspaper newspaper = new Newspaper();
        newspaper.setNewspaperId(1L);
        newspaper.setNewspaperName("Newspaper Name");
        newspaper.setNewspaperDescription("Newspaper Description");
        newspaper.setNewspaperContact("Newspaper Contact");

        when(newspaperRepository.save(newspaper)).thenReturn(newspaper);

        Newspaper result = newspaperService.addNewspaper(newspaper);

        assertEquals(newspaper, result);
    }

    @Test
    public void testUpdateNewspaper() throws NewspaperNotFoundException {
        long newspaperId = 1L;
        Newspaper existingNewspaper = new Newspaper();
        existingNewspaper.setNewspaperId(newspaperId);
        existingNewspaper.setNewspaperName("Existing Name");
        existingNewspaper.setNewspaperDescription("Existing Description");
        existingNewspaper.setNewspaperContact("Existing Contact");

        Newspaper updatedNewspaper = new Newspaper();
        updatedNewspaper.setNewspaperId(newspaperId);
        updatedNewspaper.setNewspaperName("Updated Name");
        updatedNewspaper.setNewspaperDescription("Updated Description");
        updatedNewspaper.setNewspaperContact("Updated Contact");

        when(newspaperRepository.findById(newspaperId)).thenReturn(Optional.of(existingNewspaper));
        when(newspaperRepository.save(updatedNewspaper)).thenReturn(updatedNewspaper);

        Newspaper result = newspaperService.updateNewspaper(updatedNewspaper);

        assertEquals(updatedNewspaper, result);
    }

    @Test
    public void testUpdateNewspaperNotFound() {
        long newspaperId = 1L;
        Newspaper updatedNewspaper = new Newspaper();
        updatedNewspaper.setNewspaperId(newspaperId);
        updatedNewspaper.setNewspaperName("Updated Name");
        updatedNewspaper.setNewspaperDescription("Updated Description");
        updatedNewspaper.setNewspaperContact("Updated Contact");

        when(newspaperRepository.findById(newspaperId)).thenReturn(Optional.empty());

        assertThrows(NewspaperNotFoundException.class, () -> newspaperService.updateNewspaper(updatedNewspaper));
    }

    @Test
    public void testFindNewspaperById() throws NewspaperNotFoundException {
        long newspaperId = 1L;
        Newspaper newspaper = new Newspaper();
        newspaper.setNewspaperId(newspaperId);
        newspaper.setNewspaperName("Newspaper Name");
        newspaper.setNewspaperDescription("Newspaper Description");
        newspaper.setNewspaperContact("Newspaper Contact");

        when(newspaperRepository.findById(newspaperId)).thenReturn(Optional.of(newspaper));

        Newspaper result = newspaperService.findNewspaperById(newspaperId);

        assertEquals(newspaper, result);
    }

    @Test
    public void testFindNewspaperByIdNotFound() {
        long newspaperId = 1L;

        when(newspaperRepository.findById(newspaperId)).thenReturn(Optional.empty());

        assertThrows(NewspaperNotFoundException.class, () -> newspaperService.findNewspaperById(newspaperId));
    }

    @Test
    public void testDeleteNewspaperById() throws NewspaperNotFoundException {
        long newspaperId = 1L;
        Newspaper newspaper = new Newspaper();
        newspaper.setNewspaperId(newspaperId);
        newspaper.setNewspaperName("Newspaper Name");
        newspaper.setNewspaperDescription("Newspaper Description");
        newspaper.setNewspaperContact("Newspaper Contact");

        when(newspaperRepository.findById(newspaperId)).thenReturn(Optional.of(newspaper));

        newspaperService.deleteNewspaperById(newspaperId);

        Mockito.verify(newspaperRepository, Mockito.times(1)).delete(newspaper);
    }

    @Test
    public void testDeleteNewspaperByIdNotFound() {
        long newspaperId = 1L;

        when(newspaperRepository.findById(newspaperId)).thenReturn(Optional.empty());

        assertThrows(NewspaperNotFoundException.class, () -> newspaperService.deleteNewspaperById(newspaperId));
    }

    @Test
    public void testGetAllNewspapers() {
        Newspaper newspaper1 = new Newspaper();
        newspaper1.setNewspaperId(1L);
        newspaper1.setNewspaperName("Newspaper 1");
        newspaper1.setNewspaperDescription("Description 1");
        newspaper1.setNewspaperContact("Contact 1");

        Newspaper newspaper2 = new Newspaper();
        newspaper2.setNewspaperId(2L);
        newspaper2.setNewspaperName("Newspaper 2");
        newspaper2.setNewspaperDescription("Description 2");
        newspaper2.setNewspaperContact("Contact 2");

        List<Newspaper> newspapers = Arrays.asList(newspaper1, newspaper2);

        when(newspaperRepository.findAll()).thenReturn(newspapers);

        List<Newspaper> result = newspaperService.getAllNewspapers();

        assertEquals(newspapers, result);
    }
}