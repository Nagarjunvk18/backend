package com.news.controller;

import com.news.entity.Advertisement;
import com.news.entity.Newspaper;
import com.news.exception.NewspaperNotFoundException;
import com.news.service.AdvertisementService;
import com.news.service.NewspaperService;
import com.news.util.LoggerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/newspapers")
@CrossOrigin(origins = "*", maxAge = 3006)
public class NewspaperController {

	@Autowired

	private NewspaperService newspaperService;

	@Autowired

	private AdvertisementService advertisementService;

	@PostMapping("/add")

	public ResponseEntity<String> postNewspaper(@RequestBody Newspaper newspaper) {

		newspaperService.addNewspaper(newspaper);
		return ResponseEntity.status(HttpStatus.OK).body("newspaper added in DB");
	}

	@GetMapping("/getall")

	public List<Newspaper> getAllNewspapers() {

		List<Newspaper> list = newspaperService.getAllNewspapers();

		return list;

	}

	@GetMapping("/one/{id}")

	public ResponseEntity<Newspaper> getNewspaperById(@PathVariable("id") long id) throws NewspaperNotFoundException {

		Newspaper newspaper = newspaperService.findNewspaperById(id);

		return new ResponseEntity<>(newspaper, HttpStatus.OK);

	}

	// NewspaperController.java

	@GetMapping("/api/newspaper/{nid}")
	public List<Advertisement> getAllAdvertisementsByNewspaperId(@PathVariable("nid") int nid) {
	    List<Advertisement> advertisements = advertisementService.getAllAdvertisementsByNewspaperId(nid);
	    
	    // Filter the advertisements by the provided newspaper id
	    advertisements = advertisements.stream()
	            .filter(advertisement -> {
	                Newspaper newspaper = advertisement.getNewspaper();
	                return newspaper != null && newspaper.getNewspaperId() == nid;
	            })
	            .collect(Collectors.toList());
	    
	    return advertisements;
	}


	@DeleteMapping("/delete/{id}")

	public ResponseEntity<String> deleteNewspaperById(@PathVariable("id") int id) throws NewspaperNotFoundException {

		newspaperService.deleteNewspaperById(id);

		return ResponseEntity.status(HttpStatus.OK).body("newspaper details Deleted successfully");

	}

	@PutMapping("/edit/{did}")

	public ResponseEntity<Newspaper> updateNewspaper(@PathVariable("did") long id, @RequestBody Newspaper newspaper)
			throws NewspaperNotFoundException {

		newspaper.setNewspaperId(id);

		Newspaper updatedNewspaper = newspaperService.updateNewspaper(newspaper);

		return new ResponseEntity<>(updatedNewspaper, HttpStatus.OK);

	}

}
