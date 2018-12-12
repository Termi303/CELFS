package uk.ac.bris.celfs.tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BandService {
    @Autowired
    private BandRepository bandRepository;

    public Band get(Long id) {
        return bandRepository.findById(id).get();
    }

    public List<Band> getAll() {
        List<Band> bands = new ArrayList<>();
        bandRepository.findAll()
                .forEach(bands::add);
        return bands;
    }

    public Band get(String bandName) {
        List<Band> bands = getAll();
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
