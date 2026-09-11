package com.collegefinder.controller;

import com.collegefinder.dto.PredictionRequest;
import com.collegefinder.dto.PredictionResponse;
import com.collegefinder.service.PredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predictor")
@CrossOrigin(origins = "http://localhost:5173")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping
    public ResponseEntity<PredictionResponse> predict(
            @RequestBody PredictionRequest request) {

        return ResponseEntity.ok(
                predictionService.predict(request)
        );
    }
}