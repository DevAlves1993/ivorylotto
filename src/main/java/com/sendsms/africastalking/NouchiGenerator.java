package com.sendsms.africastalking;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Christian Amani on 12/04/2019.
 */
public abstract class NouchiGenerator {

    private static List<String> dictionary = Arrays.asList(""
            ,"","","","","","","","","","","","",""
            ,"","","","","","","","","","","","");

    public static String get() {
        Random random =  new Random(Instant.now().toEpochMilli());
        int position = random.nextInt(25);
        return dictionary.get(position);
    }
}
