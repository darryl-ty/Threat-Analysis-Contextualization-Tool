package com.tact.threatanalysiscontextualizationtool;

public record FILE (VirusTotalFile vtFile) {

    public VirusTotalFile virusTotalFile () {return vtFile;}
}
