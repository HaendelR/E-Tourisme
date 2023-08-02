package first.app.e_tourisme.model;

public class Place {
    private String id;
    private String entitled;

    public Place() {
    }

    public Place(String id, String entitled) {
        this.id = id;
        this.entitled = entitled;
    }

    public Place(String entitled) {
        this.entitled = entitled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntitled() {
        return entitled;
    }

    public void setEntitled(String entitled) {
        this.entitled = entitled;
    }
}
