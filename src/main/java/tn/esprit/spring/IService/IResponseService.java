package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Article;
import tn.esprit.spring.entity.Response;

public interface IResponseService {
	// CRUD
	void createResponse(Response response,int articleId);

	void deleteResponse(int idResponse);

	void updateResponse(Response response);

	public List<Response> getAllResponses();

	List<Response> getAllResponsesNlikesDesc();

	List<Response> getAllResponsesDateDesc();
	
	public List<Response> findAllByArticle(int articleId);

}
