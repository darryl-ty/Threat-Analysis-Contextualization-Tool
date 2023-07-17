package com.tact.threatanalysiscontextualizationtool.records;

import com.tact.threatanalysiscontextualizationtool.VirusTotalFile;

public record FILE (VirusTotalFile vtFile) {

    public VirusTotalFile virusTotalFile () {return vtFile;}
}
