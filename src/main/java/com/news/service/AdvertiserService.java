package com.news.service;
import com.news.entity.Advertisement;
import com.news.entity.Advertiser;
import com.news.exception.AdvertiserNotFoundException;
import com.news.exception.DuplicateAdvertiserException;
import com.news.exception.UserNotFoundException;

import java.util.List;

public interface AdvertiserService {
    Advertiser addAdvertiser(Advertiser advertiser);
  
    Advertiser updateAdvertiser(Advertiser advertiser) throws AdvertiserNotFoundException;
  
    Advertiser findAdvertiserById(long advertiserId) throws AdvertiserNotFoundException;
  
    void deleteAdvertiser(long advertiserId) throws AdvertiserNotFoundException;
  
    List<Advertiser> getAllAdvertisers();

	Advertiser addAdvertiserForUser(long userId, Advertiser advertiser) throws UserNotFoundException;
}

