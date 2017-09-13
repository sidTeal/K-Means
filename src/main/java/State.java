
public final class State {
    private String name;
    private double x_degreeAndIncome;
    private double y_crimeRate;
    private int cluster;
    private double distanceToCluster;
    
    State(String name, double x_degreeAndIncome, double y_crimeRate) {
        setName(name);
        setX(x_degreeAndIncome);
        setY(y_crimeRate);
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setX (double x_degreeAndIncome) {
        this.x_degreeAndIncome = x_degreeAndIncome;
    }
    
    public Double getX() {
       return x_degreeAndIncome;
    }
    
    public void setY(double y_crimeRate) {
        this.y_crimeRate = y_crimeRate;
    }
    
    public Double getY() {
       return y_crimeRate;
    }
    
    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
    
    public int getCluster() {
       return cluster;
    }
    
    public void setDistanceToCluster(double distanceToCluster) {
        this.distanceToCluster = distanceToCluster;
    }
    
    public Double getDistanceToCluster() {
       return distanceToCluster;
    }
}
