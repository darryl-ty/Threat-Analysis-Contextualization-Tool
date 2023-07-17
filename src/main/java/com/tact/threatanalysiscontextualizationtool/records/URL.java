package com.tact.threatanalysiscontextualizationtool.records;

import com.tact.threatanalysiscontextualizationtool.Talos;
import com.tact.threatanalysiscontextualizationtool.URLVoid;
import com.tact.threatanalysiscontextualizationtool.VirusTotalURL;

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
