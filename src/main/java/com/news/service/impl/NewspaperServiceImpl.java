package com.news.service.impl;

import com.news.entity.Newspaper;
import com.news.exception.NewspaperNotFoundException;
import com.news.repository.NewspaperRepository;
import com.news.service.NewspaperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class NewspaperServiceImpl implements NewspaperService {

	private final NewspaperRepository newspaperRepository;

	@Autowired

	public NewspaperServiceImpl(NewspaperRepository newspaperRepository) {

		this.newspaperRepository = newspaperRepository;

	}

	@Override

	public Newspaper addNewspaper(Newspaper newspaper) {

		return newspaperRepository.save(newspaper);

	}

	@Override

	public Newspaper updateNewspaper(Newspaper newspaper) throws NewspaperNotFoundException {

		Optional<Newspaper> existingNewspaper = newspaperRepository.findById(newspaper.getNewspaperId());

		if (!existingNewspaper.isPresent()) {

			throw new NewspaperNotFoundException("Newspaper not found with ID: " + newspaper.getNewspaperId());

		}

		return newspaperRepository.save(newspaper);

	}

	@Override

	public Newspaper findNewspaperById(long newspaperId) throws NewspaperNotFoundException {

		Optional<Newspaper> newspaper = newspaperRepository.findById(newspaperId);

		if (!newspaper.isPresent()) {

			throw new NewspaperNotFoundException("Newspaper not found with ID: " + newspaperId);

		}

		return newspaper.get();

	}

	@Override

	public void deleteNewspaperById(long newspaperId) throws NewspaperNotFoundException {

		Optional<Newspaper> newspaper = newspaperRepository.findById(newspaperId);

		if (!newspaper.isPresent()) {

			throw new NewspaperNotFoundException("Newspaper not found with ID: " + newspaperId);

		}

		newspaperRepository.delete(newspaper.get());

	}

	@Override

	public List<Newspaper> getAllNewspapers() {

		return newspaperRepository.findAll();

	}

}
