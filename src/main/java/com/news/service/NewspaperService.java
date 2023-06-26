package com.news.service;
import com.news.entity.Newspaper;
import com.news.exception.NewspaperNotFoundException;

import java.util.List;

public interface NewspaperService {
	Newspaper addNewspaper(Newspaper newspaper);

    Newspaper updateNewspaper(Newspaper newspaper) throws NewspaperNotFoundException;

    Newspaper findNewspaperById(long newspaperId) throws NewspaperNotFoundException;

    void deleteNewspaperById(long newspaperId) throws NewspaperNotFoundException;

    List<Newspaper> getAllNewspapers();
}
