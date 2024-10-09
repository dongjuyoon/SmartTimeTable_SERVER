package SERVER.SmartTimeTable.Domain;

public class Member {
    private Long id;
    private String name;

    public void setId(Long id){
        this.id = id;
    }
    public Long getID(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
