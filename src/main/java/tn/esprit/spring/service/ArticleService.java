package tn.esprit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IArticleService;
import tn.esprit.spring.entity.Article;
import tn.esprit.spring.entity.Subject;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.ArticleRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.SubjectRepository;

@Service
public class ArticleService implements IArticleService {
	@Autowired
	ArticleRepository ArticleRepository;

	@Autowired
	UserRepository UserRepository;

	@Autowired
	SubjectRepository SubjectRepository;

	@Override
	public void createArticle(Article Article, int userId, int subjectId) {
		User user = UserRepository.findById(userId).orElse(null);
		Subject subject = SubjectRepository.findById(subjectId).orElse(null);
		Article.setSubject(subject);
		Article.setUser(user);
		ArticleRepository.save(Article);
	}

	@Override
	public void deleteArticle(int idArticle) {
		Article Article = ArticleRepository.findById(idArticle).orElse(null);
		ArticleRepository.delete(Article);
	}

	@Override
	public List<Article> getAllArticles() {
		List<Article> Articles = (List<Article>) ArticleRepository.findAll();
		return Articles;

	}

	@Override
	public void updateArticle(Article Article) {
		ArticleRepository.save(Article);

	}

	@Override
	public List<Article> findAllBySubject(int subjectId) {
		Subject subject = SubjectRepository.findById(subjectId).orElse(null);
		List<Article> ArticlesSubject = (List<Article>) ArticleRepository.findAllBySubject(subject);
		return ArticlesSubject;
	}

	/*
	 * @Override public List<User> getUsersByArticles(int idArticle) { // TODO
	 * Auto-generated method stub return null; }
	 */

}
