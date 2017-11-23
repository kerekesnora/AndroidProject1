package sapiadvertiser.sapiadvertiser;

import java.io.Serializable;

/**
 * Created by Elod on 11/17/2017.
 */

public class ModelList implements Serializable{
    private String start;
    private String finish;
    private String message;
    private String date;
    private String clock;
    private String phone;

    public ModelList() {
    }

    public ModelList(String start, String finis, String message, String datum) {
        this.start = start;
        this.finish = finis;
        this.message = message;
        this.date = datum;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
