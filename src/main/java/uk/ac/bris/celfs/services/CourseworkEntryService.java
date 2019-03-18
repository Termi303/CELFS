package uk.ac.bris.celfs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.coursework.CourseworkEntryRepository;

import java.util.*;

@Service
public class CourseworkEntryService {
    @Autowired
    private CourseworkEntryRepository courseworkEntryRepository;
    
    public void add(CourseworkEntry courseworkEntry) {
        courseworkEntryRepository.save(courseworkEntry);
    }
    
    public CourseworkEntry get(Long id) {
        Optional<CourseworkEntry> optionalCourseworkEntry = courseworkEntryRepository.findById(id);
        if(optionalCourseworkEntry.isPresent()) return optionalCourseworkEntry.get();
        return null;
    }

    public List<CourseworkEntry> getAll() {
        List<CourseworkEntry> courseworkEntries = new ArrayList<>();
        courseworkEntryRepository.findAll()
                .forEach(courseworkEntries::add);
        return courseworkEntries;
    }
    
    public List<CourseworkEntry> getAllByType(Long id) {
        List<CourseworkEntry> courseworkEntries = new ArrayList<>();
        courseworkEntryRepository.findByCourseworkId(id)
                .forEach(courseworkEntries::add);
        return courseworkEntries;
    }

    public void updateMark(Long id, Float newMark) {
        Optional<CourseworkEntry> optionalCourseworkEntry = courseworkEntryRepository.findById(id);
        CourseworkEntry report;
        
        if(!optionalCourseworkEntry.isPresent()) return;
        
        report = optionalCourseworkEntry.get();
        report.setOverallScore(newMark);
        this.add(report);
    }

    public List<List<CourseworkEntry>> getDoubleMarkedEntries() {
        return null;
    }

    public boolean isEntryDoubleMarked(CourseworkEntry courseworkEntry) {
        String studentId = courseworkEntry.getStudent().getId();
        Coursework coursework = courseworkEntry.getCoursework();
        List<CourseworkEntry> entries = courseworkEntryRepository.findByStudentId(studentId);
        Set<CourseworkEntry> uniqueCourseworkEntries = new HashSet<>();

        for(CourseworkEntry entry : entries) {
            if(entry.getCoursework().equals(coursework)) {
                uniqueCourseworkEntries.add(entry);
            }
        }

        return uniqueCourseworkEntries.size() >= 2;
    }
    
}
