package com.news.controller;
import com.news.entity.Advertisement;
import com.news.entity.Advertiser;
import com.news.entity.Payment;
import com.news.exception.AdvertisementNotFoundException;
import com.news.exception.AdvertiserNotFoundException;
import com.news.exception.PaymentNotFoundException;
import com.news.service.AdvertisementService;
import com.news.service.AdvertiserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/advertisements")
@CrossOrigin(origins = "*", maxAge = 3006)
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    @Autowired
    private AdvertiserService service;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping("/add")
	public ResponseEntity<String> addAdvertisement(@RequestBody Advertisement advertisement){
    	advertisementService.addAdvertisement(advertisement);
		return ResponseEntity.status(HttpStatus.OK).body("Advertisement details are added in DB");
	}

    @PutMapping("/edit/{did}")
    public ResponseEntity<Advertisement> updateAdvertisement(@PathVariable("did") long id, @RequestBody Advertisement advertisement) throws AdvertisementNotFoundException {
        advertisement.setAdvertisementId(id);
        Advertisement updatedAdvertisement = advertisementService.updateAdvertisement(advertisement);
        return new ResponseEntity<>(updatedAdvertisement, HttpStatus.OK);
    }

    @GetMapping("/one/{id}")
	public ResponseEntity<Advertisement> findAdvertisementById(@PathVariable("id") long id) throws AdvertisementNotFoundException {

        Optional<Advertisement> advertisement = Optional.of(advertisementService.findAdvertisementById(id));

        return new ResponseEntity<>(advertisement.get(), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdvertisement(@PathVariable("id") long id) throws AdvertisementNotFoundException {
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.status(HttpStatus.OK).body("Advertisement details Deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.getAllAdvertisements();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }
    @GetMapping("/api/newspaper/{nid}")

    public List<Advertisement> getAllAdvertisementsByNewspaperid(@PathVariable("nid") int nid) {

        

            List<Advertisement> list = advertisementService.getAllAdvertisementsByNewspaperId(nid);

            return list;

}
  //to add the advertisement for particular newspaper and by advertiserid  
    @PostMapping("/advertiser/{advertiserId}/newspaper/{newspaperId}")
    public ResponseEntity<Advertisement> addAdvertisementForNewspaper(
            @PathVariable long advertiserId,
            @PathVariable long newspaperId,
            @RequestBody Advertisement advertisement) {
        
        try {
            Advertisement addedAdvertisement = advertisementService.addAdvertisementForNewspaper(
                    advertiserId, newspaperId, advertisement);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedAdvertisement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
