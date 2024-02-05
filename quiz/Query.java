package quiz;

import util.Color;

public class Query {
    private String topic;
    private String question;
    private boolean proof;

    public Query(String topic, String question, boolean proof) {
        this.topic = topic;
        this.question = question;
        this.proof = proof;
    }
    public Query(String topic, String question) {
        this(topic, question, false);
    }
    public boolean hasTopic() {
        return !(topic == null || topic.length() == 0);
    }
    public String getTopic() {
        return topic;
    }
    public String getQuestion() {
        return question;
    }
    public boolean requestedProof() {
        return proof;
    }

    public String toString() {
        String query = "     << " + Color.makeYellow(getQuestion()) + " >>";
        if (requestedProof()) query += "\n\nProof is required.";
        return query;
    }
}