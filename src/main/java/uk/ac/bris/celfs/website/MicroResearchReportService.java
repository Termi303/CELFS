package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Service
public class MicroResearchReportService {
    @Autowired MicroResearchReportRepository reportRepository;
    
    public void add(MicroResearchReport report) {
        reportRepository.save(report);
    }
    
}
