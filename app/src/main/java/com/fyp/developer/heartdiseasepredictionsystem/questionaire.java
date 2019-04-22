package com.fyp.developer.heartdiseasepredictionsystem;

public class questionaire {

    public questionaire(String questionString, String answer) {
        QuestionString = questionString;
        Answer = answer;
    }

    public String getQuestionString() {
        return QuestionString;
    }

    public void setQuestionString(String questionString) {
        QuestionString = questionString;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    private String QuestionString;
    private String Answer;



}
