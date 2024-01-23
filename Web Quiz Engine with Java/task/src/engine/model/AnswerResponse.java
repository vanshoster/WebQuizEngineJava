package engine.model;

public class AnswerResponse {
    private Boolean success;
    private String feedback;
    public final static AnswerResponse WRONG = new AnswerResponse(false, "Wrong answer! Please, try again.");
    public final static AnswerResponse CORRECT = new AnswerResponse(true, "Congratulations, you're right!");

    public AnswerResponse(Boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
