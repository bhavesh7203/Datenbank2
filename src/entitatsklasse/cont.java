/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitatsklasse;

/**
 *
 * @author alexander
 */
public class cont {
    int posnr,woab,wobis,conr;
    String stao;
    String edat;

    public cont(int posnr, int woab, int wobis, int conr, String stao, String edat) {
        this.posnr = posnr;
        this.woab = woab;
        this.wobis = wobis;
        this.conr = conr;
        this.stao = stao;
        this.edat = edat;
    }

    public int getPosnr() {
        return posnr;
    }

    public void setPosnr(int posnr) {
        this.posnr = posnr;
    }

    public int getWoab() {
        return woab;
    }

    public void setWoab(int woab) {
        this.woab = woab;
    }

    public int getWobis() {
        return wobis;
    }

    public void setWobis(int wobis) {
        this.wobis = wobis;
    }

    public int getConr() {
        return conr;
    }

    public void setConr(int conr) {
        this.conr = conr;
    }

    public String getStao() {
        return stao;
    }

    public void setStao(String stao) {
        this.stao = stao;
    }

    public String getEdat() {
        return edat;
    }

    public void setEdat(String edat) {
        this.edat = edat;
    }
    
}
