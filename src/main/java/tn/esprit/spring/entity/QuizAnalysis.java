package tn.esprit.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnalysis {
    private float classMarksAvg;
    @JsonIgnore
    private ArrayList<User> leaderBoard;
    private long[][] marksFrequencyTable;
    private long attemptRateInPercent; // % of students who attempted the quiz out of all
}
