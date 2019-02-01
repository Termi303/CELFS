package uk.ac.bris.celfs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import uk.ac.bris.celfs.database.*;

@Service
public class TablesService {
    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CriteriaRepository criteriaRepository;
    @Autowired
    private CellRepository cellRepository;

    public Band getBandById(Long id) {
        return bandRepository.findById(id).get();
    }

    public List<Band> getAllBands() {
        List<Band> bands = new ArrayList<>();
        bandRepository.findAll()
                .forEach(bands::add);
        return bands;
    }

    public Band getBandByName(String bandName) {
        List<Band> bands = getAllBands();
        Band result = bands.get(0);
        for(Band band : bands) {
            if(band.getName().equals(bandName)) {
                result = band;
                break;
            }
        }
        return result;
    }
}
