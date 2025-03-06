package com.example.hp_29_MiniatureCrafts.controller.admin.newandintro;
import com.example.hp_29_MiniatureCrafts.dto.IntroduceDTO;
import com.example.hp_29_MiniatureCrafts.service.NewsAndIntroService.IntroduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/introduces")
public class IntroduceController {
    @Autowired
    private IntroduceService introduceService;

    @GetMapping("findall")
    public List<IntroduceDTO> getAllIntroduces() {
        return introduceService.getAllIntroduces();
    }

    @GetMapping("edit/{id}")
    public IntroduceDTO getIntroduceById(@PathVariable Integer id) {
        return introduceService.getIntroduceById(id);
    }

    @PostMapping("save")
    public IntroduceDTO createIntroduce(@RequestBody IntroduceDTO introduceDTO) {
        return introduceService.saveIntroduce(introduceDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteIntroduce(@PathVariable Integer id) {
        introduceService.deleteIntroduce(id);
    }
}
