package com.example.hp_29_MiniatureCrafts.controller.admin.newandintro;
import com.example.hp_29_MiniatureCrafts.dto.NewsDTO;
import com.example.hp_29_MiniatureCrafts.service.NewsAndIntroService.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("findall")
    public List<NewsDTO> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("result/{id}")
    public NewsDTO getNewsById(@PathVariable Integer id) {
        return newsService.getNewsById(id);
    }

    @PostMapping("save")
    public NewsDTO createNews(@RequestBody NewsDTO newsDTO) {
        return newsService.saveNews(newsDTO);
    }

    @DeleteMapping("delete/{id}")
    public void deleteNews(@PathVariable Integer id) {
        newsService.deleteNews(id);
    }
}
