/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package export;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utils {

    private final Random random;

    public Utils() {

        random = new Random();
    }

    public String generateRandomChars(String pattern, int length) {

        StringBuilder sb = new StringBuilder();


        return random.ints(0, pattern.length())
                .mapToObj(pattern::charAt)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public Integer generateRandomInteger(Integer integer) {

        return random.ints(integer, 80)
                .findAny()
                .getAsInt();
    }
}
