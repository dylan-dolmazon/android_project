package com.example.tp2.final_project;

import java.util.ArrayList;

public class Data {

    protected String departement = null;
    protected String commune = null;
    protected String nom = null;
    protected int nbEquipement;
    protected int nbPlaceTribune;
    protected String natureSol = null;
    protected String natureLibelle = null;
    protected boolean equipementAccesHandi;
    protected double lat;
    protected double lng;

    public Data(ArrayList<Object> datas) {
        this.departement = (String)datas.get(0);
        this.commune = (String)datas.get(1);
        this.nom = (String)datas.get(2);
        this.nbEquipement = (int)datas.get(3);
        this.nbPlaceTribune = (int)datas.get(4);
        this.natureSol = (String)datas.get(5);
        this.natureLibelle = (String)datas.get(6);
        this.equipementAccesHandi = (boolean)datas.get(7);
        this.lat = (double) datas.get(8);
        this.lng = (double)  datas.get(9);
    }

    public Data() {
        this.commune = "";
        this.departement = "";
        this.equipementAccesHandi = false;
        this.natureLibelle = "";
        this.natureSol = "";
        this.nbEquipement = 0;
        this.nbPlaceTribune = 0;
        this.nom = "";
    }

    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }

    public String getDepartement() {
        return departement;
    }

    public String getCommune() {
        return commune;
    }

    public String getNom() {
        return nom;
    }

    public int getNbEquipement() {
        return nbEquipement;
    }

    public int getNbPlaceTribune() {
        return nbPlaceTribune;
    }

    public String getNatureSol() {
        return natureSol;
    }

    public String getNatureLibelle() {
        return natureLibelle;
    }

    public boolean isEquipementAccesHandi() {
        return equipementAccesHandi;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbEquipement(int nbEquipement) {
        this.nbEquipement = nbEquipement;
    }

    public void setNbPlaceTribune(int nbPlaceTribune) {
        this.nbPlaceTribune = nbPlaceTribune;
    }

    public void setNatureSol(String natureSol) {
        this.natureSol = natureSol;
    }

    public void setNatureLibelle(String natureLibelle) {
        this.natureLibelle = natureLibelle;
    }

    public void setEquipementAccesHandi(boolean equipementAccesHandi) {
        this.equipementAccesHandi = equipementAccesHandi;
    }

    @Override
    public String toString() {

        String tmp;

        if(equipementAccesHandi){
            tmp = "Le complexe est disponible aux personnes à mobilité réduite " ;
        }else{
            tmp = "Le complexe n'est pas disponible aux personnes à mobilité réduite " ;
        }

        return "Le " + nom + " posséde " + nbPlaceTribune + " places dans les tribunes." + "\n" + "\n"
                + "C'est un terrain " + natureLibelle + ".\n" + "\n" +
                "Il est situé à " + commune + " dans le " + departement + "\n"+ "\n" +
                tmp + "\n"+ "\n"
                ;
    }
}
