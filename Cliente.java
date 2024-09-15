public class Cliente {

    private int id;
    private int arribo;
    
    public Cliente(int id, int arribo) {
        this.id = id;
        this.arribo = arribo;
    }
    public int getId() {
        return id;
    }
    public int getArribo() {
        return arribo;
    }
    @Override
    public String toString() {
        return "con id " + id;
    }

}
