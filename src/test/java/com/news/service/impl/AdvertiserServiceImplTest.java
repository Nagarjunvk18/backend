package com.news.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.news.entity.Advertiser;
import com.news.entity.User;
import com.news.exception.AdvertiserNotFoundException;
import com.news.exception.UserNotFoundException;
import com.news.repository.AdvertiserRepository;
import com.news.repository.UserRepository;

@SpringBootTest
public class AdvertiserServiceImplTest {

	@Mock
	private AdvertiserRepository advertiserRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private AdvertiserServiceImpl advertiserService;

	@Test
	public void testAddAdvertiser() {
		Advertiser advertiser = new Advertiser();
		advertiser.setId(1L);
		advertiser.setName("Test Advertiser");
		advertiser.setMobile("1234567890");
		advertiser.setEmail("test@example.com");
		advertiser.setCompany("Test Company");

		when(advertiserRepository.save(advertiser)).thenReturn(advertiser);

		Advertiser result = advertiserService.addAdvertiser(advertiser);

		assertEquals(advertiser, result);
	}

	@Test
	public void testUpdateAdvertiser() throws AdvertiserNotFoundException {
		long advertiserId = 1L;
		Advertiser existingAdvertiser = new Advertiser();
		existingAdvertiser.setId(advertiserId);
		existingAdvertiser.setName("Existing Advertiser");

		Advertiser updatedAdvertiser = new Advertiser();
		updatedAdvertiser.setId(advertiserId);
		updatedAdvertiser.setName("Updated Advertiser");

		when(advertiserRepository.findById(advertiserId)).thenReturn(Optional.of(existingAdvertiser));
		when(advertiserRepository.save(updatedAdvertiser)).thenReturn(updatedAdvertiser);

		Advertiser result = advertiserService.updateAdvertiser(updatedAdvertiser);

		assertEquals(updatedAdvertiser, result);
	}

	@Test
	public void testUpdateAdvertiserNotFound() {
		long advertiserId = 1L;
		Advertiser updatedAdvertiser = new Advertiser();
		updatedAdvertiser.setId(advertiserId);
		updatedAdvertiser.setName("Updated Advertiser");

		when(advertiserRepository.findById(advertiserId)).thenReturn(Optional.empty());

		assertThrows(AdvertiserNotFoundException.class, () -> advertiserService.updateAdvertiser(updatedAdvertiser));
	}

	@Test
	public void testFindAdvertiserById() throws AdvertiserNotFoundException {
		long advertiserId = 1L;
		Advertiser advertiser = new Advertiser();
		advertiser.setId(advertiserId);
		advertiser.setName("Test Advertiser");

		when(advertiserRepository.findById(advertiserId)).thenReturn(Optional.of(advertiser));

		Advertiser result = advertiserService.findAdvertiserById(advertiserId);

		assertEquals(advertiser, result);
	}

	@Test
	public void testFindAdvertiserByIdNotFound() {
		long advertiserId = 1L;

		when(advertiserRepository.findById(advertiserId)).thenReturn(Optional.empty());

		assertThrows(AdvertiserNotFoundException.class, () -> advertiserService.findAdvertiserById(advertiserId));
	}

	@Test
	public void testDeleteAdvertiser() throws AdvertiserNotFoundException {
		long advertiserId = 1L;
		Advertiser advertiser = new Advertiser();
		advertiser.setId(advertiserId);
		advertiser.setName("Test Advertiser");

		when(advertiserRepository.findById(advertiserId)).thenReturn(Optional.of(advertiser));

		advertiserService.deleteAdvertiser(advertiserId);

		Mockito.verify(advertiserRepository).delete(advertiser);
	}

	@Test
	public void testDeleteAdvertiserNotFound() {
		long advertiserId = 1L;

		when(advertiserRepository.findById(advertiserId)).thenReturn(Optional.empty());

		assertThrows(AdvertiserNotFoundException.class, () -> advertiserService.deleteAdvertiser(advertiserId));
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

		when(advertiserRepository.findAll()).thenReturn(advertisers);

		List<Advertiser> result = advertiserService.getAllAdvertisers();

		assertEquals(advertisers, result);
	}

	@Test
	public void testAddAdvertiserForUser() throws UserNotFoundException {
		// Create a sample advertiser and user
		Advertiser advertiser = new Advertiser();
		User user = new User();
		user.setId(1L);

		// Mock the behavior of the userRepository
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		// Mock the behavior of the advertiserRepository
		when(advertiserRepository.save(advertiser)).thenReturn(advertiser);

		// Perform the test
		Advertiser result = advertiserService.addAdvertiserForUser(1L, advertiser);

		// Verify the userRepository method was called
		verify(userRepository, times(1)).findById(1L);

		// Verify the advertiserRepository method was called
		verify(advertiserRepository, times(1)).save(advertiser);

		// Assert the result
		Assertions.assertEquals(advertiser, result);
		Assertions.assertEquals(user, advertiser.getUser());
	}

	@Test
	public void testAddAdvertiserForUserWithInvalidUserId() {
		// Create a sample advertiser
		Advertiser advertiser = new Advertiser();

		// Mock the behavior of the userRepository
		when(userRepository.findById(1L)).thenReturn(Optional.empty()); // User not found

		// Perform the test
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			advertiserService.addAdvertiserForUser(1L, advertiser);
		});

		// Verify the userRepository method was called
		verify(userRepository, times(1)).findById(1L);

		// Verify that the advertiserRepository save method was not called
		verify(advertiserRepository, never()).save(advertiser);
	}
}