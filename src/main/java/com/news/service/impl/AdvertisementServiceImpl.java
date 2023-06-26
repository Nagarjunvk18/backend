package com.news.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.entity.Advertisement;
import com.news.entity.Advertiser;
import com.news.entity.Newspaper;
import com.news.exception.AdvertisementNotFoundException;
import com.news.exception.AdvertiserNotFoundException;
import com.news.exception.NewspaperNotFoundException;
import com.news.repository.AdvertisementRepository;
import com.news.repository.AdvertiserRepository;
import com.news.repository.NewspaperRepository;
import com.news.service.AdvertisementService;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	private final AdvertisementRepository advertisementRepository;

	@Autowired
	private AdvertiserRepository advertiserRepository;
	@Autowired
	private NewspaperRepository newspaperRepository;

	@Autowired
	public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
		this.advertisementRepository = advertisementRepository;
	}

	@Override
	public Advertisement addAdvertisement(Advertisement advertisement) {
		return advertisementRepository.save(advertisement);
	}

	@Override
	public Advertisement updateAdvertisement(Advertisement advertisement) throws AdvertisementNotFoundException {
		Optional<Advertisement> existingAdvertisement = advertisementRepository
				.findById(advertisement.getAdvertisementId());
		if (!existingAdvertisement.isPresent()) {
			throw new AdvertisementNotFoundException(
					"Advertisement not found with ID: " + advertisement.getAdvertisementId());
		}
		return advertisementRepository.save(advertisement);
	}

	@Override
	public Advertisement findAdvertisementById(long advertisementId) throws AdvertisementNotFoundException {
		Optional<Advertisement> advertisement = advertisementRepository.findById(advertisementId);
		if (!advertisement.isPresent()) {
			throw new AdvertisementNotFoundException("Advertisement not found with ID: " + advertisementId);
		}
		return advertisement.get();
	}

	@Override
	public void deleteAdvertisement(long advertisementId) throws AdvertisementNotFoundException {
		Optional<Advertisement> advertisement = advertisementRepository.findById(advertisementId);
		if (!advertisement.isPresent()) {
			throw new AdvertisementNotFoundException("Advertisement not found with ID: " + advertisementId);
		}
		advertisementRepository.delete(advertisement.get());
	}

	@Override
	public List<Advertisement> getAllAdvertisements() {
		return advertisementRepository.findAll();
	}

	// AdvertisementServiceImpl.java

	@Override
	public List<Advertisement> getAllAdvertisementsById(long id) {
		List<Advertisement> advertisements = advertisementRepository.findAll();

		// Filter the advertisements by the provided advertiser id
		advertisements = advertisements.stream().filter(advertisement -> {
			Advertiser advertiser = advertisement.getAdvertiser();
			return advertiser != null && advertiser.getId() == id;
		}).collect(Collectors.toList());

		return advertisements;
	}

	@Override
	public Advertisement addAdvertisementForNewspaper(long advertiserId, long newspaperId, Advertisement advertisement)
			throws AdvertiserNotFoundException, NewspaperNotFoundException {

		Optional<Advertiser> advertiser = advertiserRepository.findById(advertiserId);
		if (!advertiser.isPresent()) {
			throw new AdvertiserNotFoundException("Advertiser not found with ID: " + advertiserId);
		}

		Optional<Newspaper> newspaper = newspaperRepository.findById(newspaperId);
		if (!newspaper.isPresent()) {
			throw new NewspaperNotFoundException("Newspaper not found with ID: " + newspaperId);
		}

		advertisement.setAdvertiser(advertiser.get());
		advertisement.setNewspaper(newspaper.get());

		return advertisementRepository.save(advertisement);
	}

	@Override
	public List<Advertisement> getAllAdvertisementsByNewspaperId(long nid) {
		List<Advertisement> allAdvertisements = advertisementRepository.findAll();

		// Filter the advertisements by the provided newspaperId
		List<Advertisement> filteredAdvertisements = allAdvertisements.stream().filter(advertisement -> {
			Newspaper newspaper = advertisement.getNewspaper();
			return newspaper != null && newspaper.getNewspaperId() == nid;
		}).collect(Collectors.toList());

		return filteredAdvertisements;
	}

}
