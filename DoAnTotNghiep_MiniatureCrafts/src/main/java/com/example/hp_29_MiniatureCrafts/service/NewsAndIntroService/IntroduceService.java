package com.example.hp_29_MiniatureCrafts.service.NewsAndIntroService;
import com.example.hp_29_MiniatureCrafts.dto.IntroduceDTO;
import com.example.hp_29_MiniatureCrafts.entity.introduce;
import com.example.hp_29_MiniatureCrafts.repository.auth.NewsAndIntro.IntroduceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntroduceService {
    @Autowired
    private IntroduceRepository introduceRepository;

    public List<IntroduceDTO> getAllIntroduces() {
        return introduceRepository.findAll().stream()
                .map(intro -> new IntroduceDTO(intro.getId(), intro.getHeader(), intro.getTitle(), intro.getContent()))
                .collect(Collectors.toList());
    }

    public IntroduceDTO getIntroduceById(Integer id) {
        return introduceRepository.findById(id)
                .map(intro -> new IntroduceDTO(intro.getId(), intro.getHeader(), intro.getTitle(), intro.getContent()))
                .orElse(null);
    }

    public IntroduceDTO saveIntroduce(IntroduceDTO introduceDTO) {
        introduce introduce = new introduce(introduceDTO.getId(), introduceDTO.getHeader(), introduceDTO.getTitle(), introduceDTO.getContent());
        introduce = introduceRepository.save(introduce);
        return new IntroduceDTO(introduce.getId(), introduce.getHeader(), introduce.getTitle(), introduce.getContent());
    }

    public void deleteIntroduce(Integer id) {
        introduceRepository.deleteById(id);
    }
}
