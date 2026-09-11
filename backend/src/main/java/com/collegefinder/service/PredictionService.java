package com.collegefinder.service;

import com.collegefinder.dto.PredictionRequest;
import com.collegefinder.dto.PredictionResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

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

        colleges.add(new PredictionResponse.CollegePrediction(
                "College of Engineering Pune (COEP)",
                "Pune",
                "Maharashtra",
                20000
        ));

        colleges.add(new PredictionResponse.CollegePrediction(
                "Veermata Jijabai Technological Institute (VJTI)",
                "Mumbai",
                "Maharashtra",
                22000
        ));

        colleges.add(new PredictionResponse.CollegePrediction(
                "Walchand College of Engineering",
                "Sangli",
                "Maharashtra",
                30000
        ));

        colleges.add(new PredictionResponse.CollegePrediction(
                "Pimpri Chinchwad College of Engineering",
                "Pune",
                "Maharashtra",
                40000
        ));

        colleges.add(new PredictionResponse.CollegePrediction(
                "Indian Institute of Technology Bombay",
                "Mumbai",
                "Maharashtra",
                5000
        ));

        colleges.add(new PredictionResponse.CollegePrediction(
                "Indian Institute of Technology Hyderabad",
                "Hyderabad",
                "Telangana",
                10000
        ));

        return colleges;
    }
}