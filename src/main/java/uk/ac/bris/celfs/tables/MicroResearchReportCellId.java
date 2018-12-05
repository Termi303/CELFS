package uk.ac.bris.celfs.tables;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class MicroResearchReportCellId implements Serializable {
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_id", insertable = false, updatable = false)
    private MicroResearchReportCriteria criteria;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id", insertable = false, updatable = false)
    private Band band;
    
    public MicroResearchReportCellId(Band band, MicroResearchReportCriteria criteria) {
        this.band = band;
        this.criteria = criteria;
    }
    
    public MicroResearchReportCriteria getCriteria() {
        return criteria;
    }
    
    public Band getBand() {
        return band;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MicroResearchReportCellId)) return false;
        MicroResearchReportCellId that = (MicroResearchReportCellId) o;
        return Objects.equals(criteria.getId(), that.getCriteria().getId())
               && Objects.equals(band.getId(), that.getBand().getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(criteria.getId(), band.getId());
    }
}
