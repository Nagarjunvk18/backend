package com.news.service;

import com.news.entity.Advertisement;
import com.news.entity.Payment;
import com.news.exception.AdvertisementNotFoundException;
import com.news.exception.AdvertiserNotFoundException;
import com.news.exception.NewspaperNotFoundException;

import java.util.List;

public interface AdvertisementService {
	Advertisement addAdvertisement(Advertisement advertisement);

	Advertisement updateAdvertisement(Advertisement advertisement) throws AdvertisementNotFoundException;

	Advertisement findAdvertisementById(long advertisementId) throws AdvertisementNotFoundException;

	void deleteAdvertisement(long advertisementId) throws AdvertisementNotFoundException;

	List<Advertisement> getAllAdvertisements();

	List<Advertisement> getAllAdvertisementsById(long id);

	List<Advertisement> getAllAdvertisementsByNewspaperId(long nid);

	Advertisement addAdvertisementForNewspaper(long advertiserId, long newspaperId, Advertisement advertisement) throws AdvertiserNotFoundException, NewspaperNotFoundException;

}