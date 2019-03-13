/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserrrService {

    private final String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final String candidateNum = "0123456789";

    @Autowired
    private Utils appUtil;

    public List<User> findAllUsers() {


        return IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new User(appUtil.generateRandomChars(candidateChars, 10), appUtil.generateRandomChars
                        (candidateChars, 10), appUtil.generateRandomInteger(i),
                        appUtil.generateRandomChars(candidateChars, 15), appUtil.generateRandomChars(candidateChars,
                        15), appUtil.generateRandomChars(candidateChars, 20),
                        appUtil.generateRandomChars(candidateChars, 10), appUtil.generateRandomChars(candidateChars,
                        10), appUtil.generateRandomChars(candidateNum, 10)))
                .collect(Collectors.toList());

    }
}
