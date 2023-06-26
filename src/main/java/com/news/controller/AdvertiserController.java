package com.news.controller;
import com.news.entity.Advertisement;
import com.news.entity.Advertiser;
import com.news.entity.Payment;
import com.news.exception.AdvertiserNotFoundException;
import com.news.exception.PaymentNotFoundException;
import com.news.exception.UserNotFoundException;
import com.news.service.AdvertisementService;
import com.news.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 

import java.util.List;

 

@RestController
@RequestMapping("/advertisers")
@CrossOrigin(origins = "*", maxAge = 3006)
public class AdvertiserController {

    @Autowired
    private  AdvertiserService advertiserService;

    @Autowired
    private AdvertisementService advertisementService;


 

    @PostMapping("/add")
    public ResponseEntity<String> addAdvertiser(@RequestBody Advertiser advertiser) {
        advertiserService.addAdvertiser(advertiser);
        return ResponseEntity.status(HttpStatus.OK).body("advertiser details are added successfully in DB");
    }
    @GetMapping("/one/{id}")
    public ResponseEntity<Advertiser> findAdvertiserById(@PathVariable("id") long id) throws AdvertiserNotFoundException{

 

        Advertiser advertiser = advertiserService.findAdvertiserById(id);

 

        return new ResponseEntity<>(advertiser, HttpStatus.OK);

 

    }

 

    @PutMapping("/{id}")
    public ResponseEntity<Advertiser> updateAdvertiser(@PathVariable("id") long id, @RequestBody Advertiser advertiser) throws AdvertiserNotFoundException {
        advertiser.setId(id);
        Advertiser updatedAdvertiser = advertiserService.updateAdvertiser(advertiser);
        return new ResponseEntity<>(updatedAdvertiser, HttpStatus.OK);
    }

 

    @GetMapping("/getall")
    public  List<Advertiser> getAllAdvertisers(){
        List<Advertiser> list =advertiserService.getAllAdvertisers();
     return list;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdvertiser(@PathVariable("id") long id) throws AdvertiserNotFoundException {
        advertiserService.deleteAdvertiser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/getall/{id}")
    public ResponseEntity<List<Advertisement>> getAllAdvertisementsById(@PathVariable("id") long id){
        List<Advertisement> advertisements = advertisementService.getAllAdvertisementsById(id);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }
// api to add the advertiser for particular user
    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addAdvertiserForUser(@PathVariable("userId") long userId, @RequestBody Advertiser advertiser) throws UserNotFoundException {
        advertiserService.addAdvertiserForUser(userId, advertiser);
		return ResponseEntity.status(HttpStatus.OK).body("Advertiser details are added successfully for the user.");
    }


 

    
}