package com.example.sqlitequanlysinhvien;

import java.io.Serializable;

public class SinhVien implements Serializable {
    private String masv,tensv,lop;
    private int tuoi;

    public SinhVien() {
    }

    public SinhVien(String masv, String tensv, String lop, int tuoi) {
        this.masv = masv;
        this.tensv = tensv;
        this.lop = lop;
        this.tuoi = tuoi;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }
}
