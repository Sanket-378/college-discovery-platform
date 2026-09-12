package com.collegefinder.dto;

import java.util.List;

public class PredictionResponse {

    private Integer rank;
    private String exam;

    private List<CollegePrediction> goodChances;
    private List<CollegePrediction> moderateChances;
    private List<CollegePrediction> ambitious;

    public PredictionResponse() {
    }

    public PredictionResponse(
            Integer rank,
            String exam,
            List<CollegePrediction> goodChances,
            List<CollegePrediction> moderateChances,
            List<CollegePrediction> ambitious) {

        this.rank = rank;
        this.exam = exam;
        this.goodChances = goodChances;
        this.moderateChances = moderateChances;
        this.ambitious = ambitious;
    }

    public Integer getRank() {
        return rank;
    }

    public String getExam() {
        return exam;
    }

    public List<CollegePrediction> getGoodChances() {
        return goodChances;
    }

    public List<CollegePrediction> getModerateChances() {
        return moderateChances;
    }

    public List<CollegePrediction> getAmbitious() {
        return ambitious;
    }

    public static class CollegePrediction {

        private Long id;
        private String name;
        private String city;
        private String state;
        private Integer cutoff;

        public CollegePrediction(
                Long id,
                String name,
                String city,
                String state,
                Integer cutoff) {

            this.id = id;
            this.name = name;
            this.city = city;
            this.state = state;
            this.cutoff = cutoff;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public Integer getCutoff() {
            return cutoff;
        }
    }
}