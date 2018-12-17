package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.models.Competitor;
import com.gambleton.presentation.viewModels.competitorController.CreateCompetitor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableCaching
public class CompetitorController {
    CompetitorLogic competitorLogic;

    public CompetitorController() {
        this.competitorLogic = Factory.getCompetitorLogic();
    }

    @PostMapping("/competitor")
    @CacheEvict(value = "competitors", allEntries = true)
    public ResponseEntity<Object> createCompetitor(@RequestBody CreateCompetitor competitor) {
        try {
            this.competitorLogic.createCompetitor(competitor.getName(), competitor.getDescription(), competitor.getGameId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/competitor")
    @Cacheable("competitors")
    public ResponseEntity<Object> getAllCompetitors() {
        List<Competitor> competitors;
        try {
            competitors = this.competitorLogic.getAllCompetitors();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(competitors);
    }
}
