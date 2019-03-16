package uk.ac.bris.celfs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.coursework.CourseworkEntryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseworkEntryService {
    @Autowired
    private CourseworkEntryRepository reportRepository;
    
    public void add(CourseworkEntry report) {
        reportRepository.save(report);
    }
    
    public CourseworkEntry get(Long id) {
        Optional<CourseworkEntry> report = reportRepository.findById(id);
        if(report.isPresent()) return report.get();
        return null;
    }

    public List<CourseworkEntry> getAll() {
        List<CourseworkEntry> reports = new ArrayList<>();
        reportRepository.findAll()
                .forEach(reports::add);
        return reports;
    }
    
    public List<CourseworkEntry> getAllByType(Long id) {
        List<CourseworkEntry> reports = new ArrayList<>();
        reportRepository.findByCourseworkId(id)
                .forEach(reports::add);
        return reports;
    }

    public void updateMark(Long id, Float newMark) {
        Optional<CourseworkEntry> optionalReport = reportRepository.findById(id);
        CourseworkEntry report;
        
        if(!optionalReport.isPresent()) return;
        
        report = optionalReport.get();
        report.setOverallScore(newMark);
        this.add(report);
    }
    
}
