package uk.ac.bris.celfs.website;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MicroResearchReportService {
    @Autowired
    private MicroResearchReportRepository reportRepository;
    
    public void add(MicroResearchReport report) {
        reportRepository.save(report);
    }
    
    public MicroResearchReport get(String id) {
        Optional<MicroResearchReport> report = reportRepository.findById(id);
        if(report.isPresent()) return report.get();
        return null;
    }

    public List<MicroResearchReport> getAll() {
        List<MicroResearchReport> reports = new ArrayList<>();
        reportRepository.findAll()
                .forEach(reports::add);
        return reports;
    }
    
}
