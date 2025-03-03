package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.RankDto;
import ru.otus.hw.services.RankService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RankController {
    private final RankService rankService;

    @GetMapping("/api/v1/rank")
    public List<RankDto> getRanks(){
        return rankService.findAll();
    }

    @GetMapping("/api/v1/rank/{id}")
    public RankDto getRank(@PathVariable long id) {
        return rankService.findById(id);
    }

    @PostMapping("/api/v1/rank")
    public RankDto createRank(@Valid @RequestBody RankDto rank) {
        return rankService.create(rank);
    }

    @PutMapping("/api/v1/rank")
    public RankDto updateRank(@Valid @RequestBody RankDto rank) {
        return rankService.update(rank);
    }

    @DeleteMapping("/api/v1/rank/{id}")
    public void deleteRank(@PathVariable("id") long id) {
        rankService.delete(id);
    }
}
