package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Article;

public interface IArticleService {
	//CRUD
		void createArticle(Article Article, int userId, int subjectId) ;
		void deleteArticle(int  idArticle) ;
		void updateArticle(Article Article);
		public List<Article> getAllArticles();
		public List<Article> findAllBySubject(int subjectId);
}
