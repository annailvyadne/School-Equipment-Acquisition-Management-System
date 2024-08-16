package MyLibs;

interface Subject {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}

interface Observer {
    public void update(String equipmentName, String condition);
}

public class ObserverAdmin implements Observer {

    private String userName;

    public ObserverAdmin(String userName, Subject[] subjects) {
        this.userName = userName;

        for (Subject subject : subjects) {
            subject.addObserver(this);
        }
    }

    @Override
    public void update(String equipmentName, String condition) {
        Mailer mailer = new Mailer();
        String subject = "Notification";
        String body = "Equipment: " + equipmentName + " condition is " + condition;
        
        mailer.sendMail(this.userName, subject, body);
    }
}
