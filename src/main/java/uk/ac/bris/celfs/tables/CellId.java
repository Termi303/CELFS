package uk.ac.bris.celfs.tables;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class CellId implements Serializable {
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_id", insertable = false, updatable = false)
    private Criteria criteria;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id", insertable = false, updatable = false)
    private Band band;
    
    public CellId(Band band, Criteria criteria) {
        this.band = band;
        this.criteria = criteria;
    }
    
    public Criteria getCriteria() {
        return criteria;
    }
    
    public Band getBand() {
        return band;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellId)) return false;
        CellId that = (CellId) o;
        return Objects.equals(criteria.getId(), that.getCriteria().getId())
               && Objects.equals(band.getId(), that.getBand().getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(criteria.getId(), band.getId());
    }
}
