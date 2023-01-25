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


import tn.esprit.spring.IService.IArticleService;
import tn.esprit.spring.entity.Article;

@RestController
@RequestMapping("/Article")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ArticleController {
	@Autowired
	IArticleService ArticleService;

	// http://localhost:8089/SpringMVC/Article/create

	@PostMapping("create/{userId}/{subjectId}")
	@ResponseBody
	public void createArticle(@RequestBody Article Article, @PathVariable("userId") int userId, @PathVariable("subjectId") int subjectId) {
		ArticleService.createArticle(Article,  userId,  subjectId);
	}

	// http://localhost:8089/SpringMVC/Article/getAllArticles
	@GetMapping("/getAllArticles")
	@ResponseBody
	public List<Article> getAllArticles() {

		List<Article> Articles = ArticleService.getAllArticles();
		return Articles;

	}
	// http://localhost:8089/SpringMVC/Article/delete/1

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") int ArticleId) {

		ArticleService.deleteArticle(ArticleId);		
	}
	// http://localhost:8089/SpringMVC/Article/update/1

	@PutMapping("/update")
	public void updateArticle(@RequestBody Article Article) {
		ArticleService.updateArticle(Article);
	}
	
	// http://localhost:8089/SpringMVC/Article/getAllArticlesBySubjects
	@GetMapping("/getAllArticlesBySubjects/{subjectId}")
	@ResponseBody
	public List<Article> getAllArticlesBySubjects(@PathVariable("subjectId") int subjectId) {

		List<Article> ArticlesSubject = ArticleService.findAllBySubject(subjectId);
		return ArticlesSubject;

	}

}
