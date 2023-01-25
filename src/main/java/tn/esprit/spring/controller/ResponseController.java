package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import tn.esprit.spring.IService.IResponseService;
import tn.esprit.spring.entity.Article;
import tn.esprit.spring.entity.Response;

@RestController
@RequestMapping("/Response")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ResponseController {
	@Autowired
	IResponseService responseService;

	// http://localhost:8089/Response/create

	@PostMapping("create/{articleId}")
	@ResponseBody
	public void createResponse(@RequestBody Response response, @PathVariable("articleId") int articleId) {
		responseService.createResponse(response,articleId);
	}

	// http://localhost:8089/Response/getAllResponses
	@GetMapping("/getAllResponses")
	@ResponseBody
	public List<Response> getAllResponses() {

		List<Response> responses = responseService.getAllResponses();
		return responses;

	}
	
	// http://localhost:8089/Response/getAllResponsesDesc
		@GetMapping("/getAllResponsesDesc")
		@ResponseBody
		public List<Response> getAllResponsesNlikesDesc() {

			List<Response> responses = responseService.getAllResponsesNlikesDesc();
			return responses;

		}
		
		
	// http://localhost:8089/Response/delete/1

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") int responseId) {

		responseService.deleteResponse(responseId);		
	}
	// http://localhost:8089/Response/update/1

	@PutMapping("/update")
	public void updateResponse(@RequestBody Response response) {
		responseService.updateResponse(response);
	}
	
	// http://localhost:8089/Response/getAllResponsesByDate
			@GetMapping("/getAllResponsesByDate")
			@ResponseBody
			public List<Response> getAllResponsesByDate() {

				List<Response> responses = responseService.getAllResponsesNlikesDesc();
				return responses;

			}
			
			// http://localhost:8089/SpringMVC/Response/getAllResponsesByArticle
			@GetMapping("/getAllResponsesByArticle/{articleId}")
			@ResponseBody
			public List<Response> getAllResponsesByArticle(@PathVariable("articleId") int articleId) {

				List<Response> ResponseArticle = responseService.findAllByArticle(articleId);
				return ResponseArticle;

			}

	
}
