package com.tact.threatanalysiscontextualizationtool;

public record URL(Talos talos, URLVoid urlVoid, VirusTotalURL vtURL) {

    @Override
    public Talos talos() {
        return talos;
    }

    @Override
    public URLVoid urlVoid() {
        return urlVoid;
    }

    @Override
    public VirusTotalURL vtURL() {
        return vtURL;
    }
}
