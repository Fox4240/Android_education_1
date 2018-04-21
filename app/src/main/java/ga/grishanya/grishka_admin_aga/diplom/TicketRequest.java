package ga.grishanya.grishka_admin_aga.diplom;
import com.google.gson.annotations.SerializedName;

public class TicketRequest {

    public TicketRequest(String title, String subject, String message) {
        this.title = title;
        this.article = new Article(subject, message);
    }

    @SerializedName("title")
    public String title;

    @SerializedName("article")
    public Article article;

    public class Article {

        public Article(String subject, String message) {
            this.subject = subject;
            this.message = message;
        }

        @SerializedName("subject")
        public String subject;

        @SerializedName("message")
        public String message;

    }

}
