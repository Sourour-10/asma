package tn.esprit.spring.IService;

import java.text.ParseException;
import java.util.List;

import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.Offer;

public interface IOfferService {

	//CRUD
	void createOffer(Offer offer) ;
	void deleteOffer(int  idOffer) ;
	void updateOffer(Offer offer);
	public List<Offer> getAllOffers();
	public Offer getOffreById(int offreId);
	public String getConvention();
	public void setConvention(String convention);
	public void createOfferByConvention() throws ParseException;
	public Collaboration getColabByOffer(int offreId);
}
