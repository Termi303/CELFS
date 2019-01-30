package uk.ac.bris.celfs.services;

import uk.ac.bris.celfs.database.MicroResearchReportRepository;
import uk.ac.bris.celfs.database.MicroResearchReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
