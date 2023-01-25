package tn.esprit.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IResponseService;
import tn.esprit.spring.entity.Article;
import tn.esprit.spring.entity.Response;
import tn.esprit.spring.entity.Subject;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.ArticleRepository;
import tn.esprit.spring.repository.ResponseRepository;

@Service
public class ResponseService implements IResponseService {
	@Autowired
	ResponseRepository responseRepository;

	@Autowired
	ArticleRepository articleRepository;

	@Override
	public void createResponse(Response response, int articleId) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Article article = articleRepository.findById(articleId).orElse(null);
		response.setArticle(article);
		response.setPublishDate(now);
		response.setNlikes(0);
		responseRepository.save(response);
	}

	@Override
	public void deleteResponse(int idResponse) {
		Response response = responseRepository.findById(idResponse).orElse(null);
		responseRepository.delete(response);
	}

	@Override
	public List<Response> getAllResponses() {
		List<Response> responses = (List<Response>) responseRepository.findAll();
		return responses;

	}

	@Override
	public void updateResponse(Response response) {
		responseRepository.save(response);

	}

	@Override
	public List<Response> getAllResponsesNlikesDesc() {
		List<Response> responses = (List<Response>) responseRepository.findByNlikes();
		return responses;

	}

	@Override
	public List<Response> getAllResponsesDateDesc() {
		List<Response> responses = (List<Response>) responseRepository.findAllByOrderByPublishDateDesc() ;
		return responses;
	}

	@Override
	public List<Response> findAllByArticle(int articleId) {
		Article article = articleRepository.findById(articleId).orElse(null);
		List<Response> ResponseArticle = (List<Response>) responseRepository.findAllByArticle(article);
		return ResponseArticle;
	}
}
