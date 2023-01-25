package tn.esprit.spring.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IOfferService;
import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.Offer;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.repository.OfferRepository;
import tn.esprit.spring.repository.CollaborationRepository;



@Service
public class OfferService implements IOfferService{
	

	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	CollaborationRepository CollaborationRepository;

	@Override
	public void createOffer(Offer offer) {
		offerRepository.save(offer);
		
	}

	@Override
	public void deleteOffer(int idOffer) {
		Offer offer = offerRepository.findById(idOffer).orElse(null);
		offerRepository.delete(offer);
	}

	@Override
	public void updateOffer(Offer offer) {
		offerRepository.save(offer);
		
	}

	@Override
	public List<Offer> getAllOffers() {
		List<Offer> offers = (List<Offer>) offerRepository.findAll();
		return offers;
	}

	@Override
	public String getConvention() {
	       String content="";
	        try {
	            File file=new ClassPathResource("ConventionOffre.txt").getFile();
	            content= new String(Files.readAllBytes(file.toPath()));
	            System.out.println(content);
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        }
	        return content;
        

    	
	}

	@Override
	public void setConvention(String convention) {
       /* try {
            String filePath ="C:\\Users\\LENOVO\\Desktop\\viwellProject\\src\\main\\resources\\ConventionOffre.txt";
            FileOutputStream f = new FileOutputStream(filePath, true);
            String lineToAppend = getForbiddenWords() +"\t"+ words.replaceAll("[\\t\\n\\r]+", " ").replaceAll(" +", " ");
            byte[] byteArr = lineToAppend.getBytes();//converting string into byte array
           // pw.write(getForbiddenWords() + words.replaceAll("[\\t\\n\\r]+", " ").replaceAll(" +", " "));
            f.write(byteArr);
            f.close();   
        } catch(Exception ex) {
            ex.printStackTrace();
        }
		*/
	}

	@Override
	public void createOfferByConvention() throws ParseException {
		//String text = getConvention().replace("\n", "");
		 String[] conventions=getConvention().split("\n");
         boolean forbiden = false;
         String[] content;
         String test=" ";

      //   int j = 0; //compteur des offres
         String[] uneoffre;
         String[] CollaborationName = getConvention().split("\n");
         //String[] CollaborationID = getConvention().split("\\*");
         String[] type = getConvention().split("\n");;
         String[] startDate= getConvention().split("\n");
         String[] Duration=  getConvention().split("\n");
         String regex = "^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$";
        
         
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher;
         Collaboration Collab;
         
        for(int i =0; i<conventions.length; i++) {
        	 
        	/* test += conventions[i]+" "; */
        	uneoffre = conventions[i].split(" "); //orange cette offre est un happy hour pour 2heures  à partir de 01/01/2022 12:00 sur toutes nos produits
        	//String text = getConvention().replace("\n", "");
        	CollaborationName[i]=uneoffre[0];
        	CollaborationName[i] = CollaborationName[i].replace(System.getProperty("line.separator").toString(), "");
        			//replace("\n", " ");
        	for(int j =0; j<uneoffre.length; j++) {
        		 if(     uneoffre[j].contains("black") &&  uneoffre[j+1].contains("friday") ) {
            		type[i] = "black friday";}
            	else if(    uneoffre[j].contains("happy") &&  uneoffre[j+1].contains("hour")  ) {
            		type[i] = "Happy Hour";}
            	else if(   uneoffre[j].contains("happy") &&  uneoffre[j+1].contains("days") ){
            		type[i] = "Happy Days";} 
        		 matcher = pattern.matcher(uneoffre[j]);
        		 if(matcher.matches()  ) {
        			 startDate[i]= uneoffre[j];}
        		 if(uneoffre[j].contains("for")) {
        			 if(uneoffre[j+2].contains("hours") || uneoffre[j+2].contains("month")) {
        				 Duration[i] = uneoffre[j+1]+" "+uneoffre[j+2];
        			 }
        			 //for 2 hours for 1 month
        			//j   j+1 j+2	 
        		 }    	
        	}
        }
        //boucle pour tester
        for(int m =0; m<CollaborationName.length; m++) {
        	System.out.println("waw"+CollaborationName[m]+"waw");
        	
        }
        
        for(int m =0; m<CollaborationName.length; m++) {
        	test += " no: "+ m+" "+Duration[m];
        	Offer offer = new Offer();
        	offer.setType(type[m]);
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(startDate[m]);  
        	offer.setStartDate(date1);
        	offer.setEndDate(date1);
        	 Collab = CollaborationRepository.findCollabByCompanyName(CollaborationName[m]);
        	offer.setCollaboration(Collab);
        	//System.out.println(Collab.getCompanyName());
        	String description = "cette offre est proposé par:  ";
        	offer.setDescription(description);
        	System.out.println("colab name"+Collab.getCompanyName());
        	//Collab.getOffers().add(offer);
        	offerRepository.save(offer);
        	
        	System.out.println(" nchoufou loffre : "+offer.getOfferId());
        	if(Collab.getOffers().isEmpty() ) {
        		
        		List<Offer> collabOffers = new ArrayList<Offer>();
        		collabOffers.add(offer);
        		System.out.println(" empy ");
        	}else {
        		List<Offer> collabOffers = Collab.getOffers();
        		collabOffers.add(offer);
        		System.out.println(" mahich empy ");}
        		
        		
        		
    		
    		
    		
        }

     	//tawa on va faire lajout
     	
		
	}

	@Override
	public Offer getOffreById(int offreId) {
		// TODO Auto-generated method stub
		return offerRepository.findById(offreId).orElse(null);
	}

	@Override
	public Collaboration getColabByOffer(int offreId) {
		// TODO Auto-generated method stub
		Offer offre = offerRepository.findById(offreId).orElse(null);
		
		return offre.getCollaboration();
	}

}
