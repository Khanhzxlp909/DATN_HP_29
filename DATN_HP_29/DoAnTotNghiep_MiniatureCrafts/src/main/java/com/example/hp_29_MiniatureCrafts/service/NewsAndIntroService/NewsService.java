package com.example.hp_29_MiniatureCrafts.service.NewsAndIntroService;

import com.example.hp_29_MiniatureCrafts.dto.NewsDTO;
import com.example.hp_29_MiniatureCrafts.entity.News;
import com.example.hp_29_MiniatureCrafts.repository.auth.NewsAndIntro.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(news -> new NewsDTO(news.getId(), news.getTitle(), news.getSummary(), news.getContent(), news.getImg()))
                .collect(Collectors.toList());
    }

    public NewsDTO getNewsById(Integer id) {
        return newsRepository.findById(id)
                .map(news -> new NewsDTO(news.getId(), news.getTitle(), news.getSummary(), news.getContent(), news.getImg()))
                .orElse(null);
    }

    public NewsDTO saveNews(NewsDTO newsDTO) {
        News news = new News(newsDTO.getId(), newsDTO.getTitle(), newsDTO.getSummary(), newsDTO.getContent(), newsDTO.getImg());
        news = newsRepository.save(news);
        return new NewsDTO(news.getId(), news.getTitle(), news.getSummary(), news.getContent(), news.getImg());
    }

    public void deleteNews(Integer id) {
        newsRepository.deleteById(id);
    }
}
