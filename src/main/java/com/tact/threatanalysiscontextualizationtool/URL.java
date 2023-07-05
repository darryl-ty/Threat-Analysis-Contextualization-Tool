package com.tact.threatanalysiscontextualizationtool;

public class URL {
    Talos talos;
    URLVoid urlVoid;
    VirusTotalURL vtURL;

    public URL(Talos talos, URLVoid urlVoid, VirusTotalURL vtURL){
        this.talos = talos;
        this.urlVoid = urlVoid;
        this.vtURL = vtURL;
    }

    public Talos getTalos() {
        return talos;
    }

    public URLVoid getUrlVoid() {
        return urlVoid;
    }

    public VirusTotalURL getVtURL() {
        return vtURL;
    }

}
