package tn.esprit.spring.controller;

import java.text.ParseException;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.IOfferService;
import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.Offer;



@RestController
@RequestMapping("/Offer")
public class OfferController {
	@Autowired
	IOfferService OfferService;


	// http://localhost:8089/Badge/create

	@PostMapping("create")
	@ResponseBody
	public void createOffer(@RequestBody Offer Offer) {
		OfferService.createOffer(Offer);
	}

	// http://localhost:8089/Badge/getAllBadges
	@GetMapping("/getAllOffers")
	@ResponseBody
	public List<Offer> getAllOffers() {

		List<Offer> Offers = OfferService.getAllOffers();
		return Offers;

	}
	// http://localhost:8089/Badge/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteOffer(@PathVariable("id") int OfferId) {

		OfferService.deleteOffer(OfferId);		
	}
	// http://localhost:8089/Badge/update/1

	@PutMapping("/update")
	public void updateOffer(@RequestBody Offer Offer) {
		OfferService.updateOffer(Offer);
	}
	

	// http://localhost:8089/Badge/getConvention
	@GetMapping("/getConvention")
	@ResponseBody
	public String getConvention() {

		return OfferService.getConvention();


	}
	// http://localhost:8089/Offer/createOfferByConvention
	@PutMapping("/createOfferByConvention")
	@ResponseBody
	public void createOfferByConvention() throws ParseException {
		 OfferService.createOfferByConvention();
	}
	
	
	// http://localhost:8089/Offer/getOffreById
	@GetMapping("/getOffreById/{offreId}")
	@ResponseBody
	public Offer getOffreById(@PathVariable("offreId") int offreId) {
		return OfferService.getOffreById(offreId);
		
	}
	
	// http://localhost:8089/Offer/getOffreById
	@GetMapping("/getColabByOffer/{offreId}")
	@ResponseBody
	public Collaboration getColabByOffer(@PathVariable("offreId") int offreId) {
		return OfferService.getColabByOffer(offreId);
	}


}
