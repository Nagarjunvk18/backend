package com.news.service.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.entity.Advertiser;
import com.news.entity.User;
import com.news.exception.AdvertiserNotFoundException;
import com.news.exception.UserNotFoundException;
import com.news.repository.AdvertisementRepository;
import com.news.repository.AdvertiserRepository;
import com.news.repository.UserRepository;
import com.news.service.AdvertiserService;

@Service
public class AdvertiserServiceImpl implements AdvertiserService {

	@Autowired
    private AdvertiserRepository advertiserRepository;

	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    public AdvertisementRepository advertisementRepository;

    @Override
    public Advertiser addAdvertiser(Advertiser advertiser) {
        return advertiserRepository.save(advertiser);
    }

    @Override
    public Advertiser updateAdvertiser(Advertiser advertiser) throws AdvertiserNotFoundException {
        Optional<Advertiser> existingAdvertiser = advertiserRepository.findById(advertiser.getId());
        if (!existingAdvertiser.isPresent()) {
            throw new AdvertiserNotFoundException("Advertiser not found with ID: " + advertiser.getId());
        }
        return advertiserRepository.save(advertiser);
    }

    @Override
    public Advertiser findAdvertiserById(long advertiserId) throws AdvertiserNotFoundException {
        Optional<Advertiser> advertiser = advertiserRepository.findById(advertiserId);
        if (!advertiser.isPresent()) {
            throw new AdvertiserNotFoundException("Advertiser not found with ID: " + advertiserId);
        }
        return advertiser.get();
    }

    @Override
    public void deleteAdvertiser(long advertiserId) throws AdvertiserNotFoundException {
        Optional<Advertiser> advertiser = advertiserRepository.findById(advertiserId);
        if (!advertiser.isPresent()) {
            throw new AdvertiserNotFoundException("Advertiser not found with ID: " + advertiserId);
        }
        advertiserRepository.delete(advertiser.get());
    }

    @Override
    public List<Advertiser> getAllAdvertisers() {
        return advertiserRepository.findAll();
    }

    @Override
    public Advertiser addAdvertiserForUser(long userId, Advertiser advertiser) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        advertiser.setUser(user.get());
        return advertiserRepository.save(advertiser);
    }
    
}
