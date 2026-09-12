package com.collegefinder.service;

import com.collegefinder.dto.PredictionRequest;
import com.collegefinder.dto.PredictionResponse;
import com.collegefinder.entity.College;
import com.collegefinder.repository.CollegeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    private final CollegeRepository collegeRepository;

    public PredictionService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public PredictionResponse predict(PredictionRequest request) {

        if (request.getRank() == null || request.getRank() <= 0) {
            throw new IllegalArgumentException("Rank must be greater than 0");
        }

        List<PredictionResponse.CollegePrediction> good = new ArrayList<>();
        List<PredictionResponse.CollegePrediction> moderate = new ArrayList<>();
        List<PredictionResponse.CollegePrediction> ambitious = new ArrayList<>();

        List<PredictionResponse.CollegePrediction> colleges = getColleges();

        for (PredictionResponse.CollegePrediction college : colleges) {

            // Preferred location filter
            if (request.getLocation() != null
                    && !request.getLocation().isBlank()
                    && !college.getState().equalsIgnoreCase(request.getLocation())) {
                continue;
            }

            int rank = request.getRank();
            int cutoff = college.getCutoff();

            /*
             * Rule-based prediction:
             *
             * rank <= cutoff
             *     Good chance
             *
             * rank <= cutoff * 1.5
             *     Moderate chance
             *
             * otherwise
             *     Ambitious
             */

            if (rank <= cutoff) {
                good.add(college);

            } else if (rank <= cutoff * 1.5) {
                moderate.add(college);

            } else {
                ambitious.add(college);
            }
        }

        return new PredictionResponse(
                request.getRank(),
                request.getExam(),
                good,
                moderate,
                ambitious
        );
    }

    private List<PredictionResponse.CollegePrediction> getColleges() {

        List<PredictionResponse.CollegePrediction> colleges = new ArrayList<>();

        colleges.add(predictionFor(
                "College of Engineering Pune (COEP)",
                "Pune",
                "Maharashtra",
                20000
        ));

        colleges.add(predictionFor(
                "Veermata Jijabai Technological Institute (VJTI)",
                "Mumbai",
                "Maharashtra",
                22000
        ));

        colleges.add(predictionFor(
                "Walchand College of Engineering",
                "Sangli",
                "Maharashtra",
                30000
        ));

        colleges.add(predictionFor(
                "Pimpri Chinchwad College of Engineering",
                "Pune",
                "Maharashtra",
                40000
        ));

        colleges.add(predictionFor(
                "Indian Institute of Technology Bombay",
                "Mumbai",
                "Maharashtra",
                5000
        ));

        colleges.add(predictionFor(
                "Indian Institute of Technology Hyderabad",
                "Hyderabad",
                "Telangana",
                10000
        ));

        return colleges;
    }

    private PredictionResponse.CollegePrediction predictionFor(
            String name, String city, String state, int cutoff) {

        Long realId = collegeRepository.findByNameIgnoreCase(name)
                .map(College::getId)
                .orElse(null);

        return new PredictionResponse.CollegePrediction(realId, name, city, state, cutoff);
    }
}