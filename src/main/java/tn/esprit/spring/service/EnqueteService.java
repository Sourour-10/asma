package tn.esprit.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.SystemException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IEnqueteService;
import tn.esprit.spring.entity.Enquete;
import tn.esprit.spring.entity.EnqueteQuestions;
import tn.esprit.spring.entity.Reaction;
import tn.esprit.spring.entity.TypeQuestion;
import tn.esprit.spring.repository.EnqueteRepository;

@Service
public class EnqueteService implements IEnqueteService {

	@Autowired
	EnqueteRepository enqueteRepository;

	@Override
	@Transactional
	public void createEnquete() {
		List<EnqueteQuestions> questList1 = enqueteRepository
				.findEnqueteQuestionByTypeQuestion(TypeQuestion.EnvironnementPhysique);
		int count1 = questList1.size();
		List<EnqueteQuestions> questList2 = enqueteRepository.findEnqueteQuestionByTypeQuestion(TypeQuestion.Equipe);
		int count2 = questList2.size();
		List<EnqueteQuestions> questList3 = enqueteRepository.findEnqueteQuestionByTypeQuestion(TypeQuestion.Boulot);
		int count3 = questList3.size();
		Enquete e = new Enquete();
		Random random = new Random();
		int number1 = random.nextInt((int) count1 - 1);
		// number1++ ;
		// System.out.println("Random :" +number1 );
		int i = 0;
		EnqueteQuestions enq1 = new EnqueteQuestions();
		while (i <= number1) {
			i++;
			enq1 = questList1.get(i);
		}
		// Question 2

		int number2 = random.nextInt((int) count2 - 1);
		i = 0;
		EnqueteQuestions enq2 = new EnqueteQuestions();
		while (i <= number2) {
			i++;
			enq2 = questList2.get(i);
		}

		// Question 3

		int number3 = random.nextInt((int) count3 - 1);
		i = 0;
		EnqueteQuestions enq3 = new EnqueteQuestions();
		while (i <= number3) {
			i++;
			enq3 = questList3.get(i);
		}

		e.setEnqueteQuestions1(enq1.getText());
		e.setEnqueteQuestions2(enq2.getText());
		e.setEnqueteQuestions3(enq3.getText());

		enqueteRepository.save(e);
	}

	@Override
	@Transactional
	public List<String> remplirEnquete(int idEnquete, int val1, int val2, int val3) {

		List<String> messages = new ArrayList<String>();
		float Moyenne = (val1 + val2 + val3) / 3;
		Enquete enqueteAffiche = enqueteRepository.findById(idEnquete).orElse(null);

		enqueteAffiche.setValeur1(val1);
		enqueteAffiche.setValeur2(val2);
		enqueteAffiche.setValeur3(val3);
		enqueteAffiche.setMoyenne(Moyenne);
		System.out.println("Enquete Affiche  : " + enqueteAffiche);
		String msg = "";
		if (Moyenne < 2) {
			messages.add(
					"Il est claire que vous n'appreciez pas les condition de travail de notre entreprise,cela regrettable, en vous conseille d'essayer de s'integrer plus dans l'équipe et d'essayer de créer votre propre ambiance et on espère que la prochaine enquète portera des avis meilleurs");

		} else {
			if (2 <= Moyenne && Moyenne < 4) {

				switch (val1) {
				case 1:
					msg = "On remarque que vous n'appreciez pas notre environnement physique, on attends vos recommendations pour améliorer";
					messages.add(msg);

					break;

				case 2:
					msg = "Votre satisfaction de notre environnement n'est pas total, des petites améliorations peuvent arracher votre bonheur";
					messages.add(msg);
					break;
				case 3:
					msg = "on est tellement heureux par ce que vous etes dans milieu comfortable";
					messages.add(msg);
					break;

				}
				switch (val2) {
				case 1:
					msg = "Val 2";
					messages.add(msg);
					break;

				case 2:
					msg = "Val2 case 1";
					messages.add(msg);
					break;
				case 3:
					msg = "val 2 case 2";
					messages.add(msg);
					break;

				}
				switch (val3) {
				case 1:
					msg = "val3";
					messages.add(msg);
					break;

				case 2:
					msg = "Val 3 case 1";
					messages.add(msg);
					break;

				case 3:
					msg = "Val 3 case 2";
					messages.add(msg);
					break;

				}
			} else {

				messages.add("Vous êtes formidable");

			}

		}
		return messages;
	}

}
