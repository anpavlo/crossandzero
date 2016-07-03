package com.softserve.edu.websoket;

public class Player implements Comparable<Player>{
    
    private Integer id;
    private String nik;
    private Boolean isFree;
   
    
    
    public Player() {
        super();
    }

    public Player(String nik) {
        super();
        this.setNik(nik);
        setIsFree(true);
    }
    
    public Player(Integer id, String nik) {
        super();
        this.id = id;
        this.nik = nik;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }
    @Override
    public int compareTo(Player o) {
        
        return this.nik.compareTo(o.nik);
    }
    
    
    
    
}
