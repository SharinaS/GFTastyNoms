package com.sharinastubbs.gftastynoms;

public class GFTastyNomPlace {

    String nomplacename;
    String address;
    Boolean gfmenu;
    Boolean dedicatedgf;
    int gffriendlyrange;


    public GFTastyNomPlace(String nomplacename, String address, Boolean gfmenu, Boolean dedicatedgf, int gffriendlyrange) {
        this.nomplacename = nomplacename;
        this.address = address;
        this.gfmenu = gfmenu;
        this.dedicatedgf = dedicatedgf;
        this.gffriendlyrange = gffriendlyrange;
    }

    //TODO: Write method that gets average for gffriendlyrange

    public String getNomplacename() {
        return nomplacename;
    }

    public void setNomplacename(String nomplacename) {
        this.nomplacename = nomplacename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGfmenu() {
        return gfmenu;
    }

    public void setGfmenu(Boolean gfmenu) {
        this.gfmenu = gfmenu;
    }

    public Boolean getDedicatedgf() {
        return dedicatedgf;
    }

    public void setDedicatedgf(Boolean dedicatedgf) {
        this.dedicatedgf = dedicatedgf;
    }

    public int getGffriendlyrange() {
        return gffriendlyrange;
    }

    public void setGffriendlyrange(int gffriendlyrange) {
        this.gffriendlyrange = gffriendlyrange;
    }
}
