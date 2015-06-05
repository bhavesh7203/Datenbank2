/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitatsklasse;

import java.util.List;

/**
 *
 * @author alexander
 */
public class MietvA {
    int mietnr;
    int knr;
    String mietdat;
    double gespreis;
    String rdat;
    double mwst;
    double bruttopreis;
    List<cont> contl;

    public MietvA(int mietnr, int knr, String mietdat, double gespreis, String rdat, double mwst, double bruttopreis, List<cont> contl) {
        this.mietnr = mietnr;
        this.knr = knr;
        this.mietdat = mietdat;
        this.gespreis = gespreis;
        this.rdat = rdat;
        this.mwst = mwst;
        this.bruttopreis = bruttopreis;
        this.contl = contl;
    }

    public MietvA() {
    }

    public int getMietnr() {
        return mietnr;
    }

    public void setMietnr(int mietnr) {
        this.mietnr = mietnr;
    }

    public int getKnr() {
        return knr;
    }

    public void setKnr(int knr) {
        this.knr = knr;
    }

    public String getMietdat() {
        return mietdat;
    }

    public void setMietdat(String mietdat) {
        this.mietdat = mietdat;
    }

    public double getGespreis() {
        return gespreis;
    }

    public void setGespreis(double gespreis) {
        this.gespreis = gespreis;
    }

    public String getRdat() {
        return rdat;
    }

    public void setRdat(String rdat) {
        this.rdat = rdat;
    }

    public double getMwst() {
        return mwst;
    }

    public void setMwst(double mwst) {
        this.mwst = mwst;
    }

    public double getBruttopreis() {
        return bruttopreis;
    }

    public void setBruttopreis(double bruttopreis) {
        this.bruttopreis = bruttopreis;
    }

    public List<cont> getContl() {
        return contl;
    }

    public void setContl(List<cont> contl) {
        this.contl = contl;
    }
}
