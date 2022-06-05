package ru.lanit.utils.functions;

import java.time.temporal.ChronoUnit;

public enum OffsetUnit {

    DAYS("д", ChronoUnit.DAYS),
    WEEKS("н", ChronoUnit.WEEKS),
    MONTHS("м", ChronoUnit.WEEKS);

    private final String ident;
    private final ChronoUnit chronoUnit;

    OffsetUnit(String ident, ChronoUnit chronoUnit) {
        this.ident = ident;
        this.chronoUnit = chronoUnit;
    }

    public String getIdent() {
        return ident;
    }

    public ChronoUnit getChronoUnit() {
        return chronoUnit;
    }
}
