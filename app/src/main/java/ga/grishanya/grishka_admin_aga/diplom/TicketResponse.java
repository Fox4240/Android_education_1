package ga.grishanya.grishka_admin_aga.diplom;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketResponse {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("responsible")
    @Expose
    private Object responsible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Object getResponsible() {
        return responsible;
    }

    public void setResponsible(Object responsible) {
        this.responsible = responsible;
    }

}
