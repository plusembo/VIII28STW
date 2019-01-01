package com.eighttwentyeightsoftware.pensiltikbackend.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGenerator implements IdentifierGenerator {

    private static IdGenerator uniqueInstance;
    private static final Random RANDOM = new Random();

    public IdGenerator() {
    }

    public static synchronized IdGenerator getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new IdGenerator();
        }
        return uniqueInstance;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor ssci, Object o) throws HibernateException {
        try {
            return generateId();
        } catch (Exception ex) {
            Logger.getLogger(IdGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String generateId() throws Exception {
        Date today;
        String output;
        SimpleDateFormat formatter;
        String pattern = "ddMMyyyyHHmmssSSS";
        String alphabet = "_-@%&";

        formatter = new SimpleDateFormat(pattern, Locale.CANADA);
        today = new Date();
        output = formatter.format(today);

        char ins = alphabet.charAt(new Random().nextInt(alphabet.length()));
        int generatedInt = 1 + (int) (new Random().nextFloat() * (10 - 1));

        String idGen = output;

        idGen = new StringBuilder(idGen).insert(idGen.length() - generatedInt, ins).toString();
        return randomAlphabetic(3) + idGen + randomAlphabetic(1);
    }

    private static String random(int count) {
        return random(count, false, false);
    }

    private static String randomAscii(int count) {
        return random(count, 32, 127, false, false);
    }

    private static String randomAlphabetic(int count) {
        return random(count, true, false);
    }

    private static String randomAlphanumeric(int count) {
        return random(count, true, true);
    }

    private static String randomNumeric(int count) {
        return random(count, false, true);
    }

    private static String random(int count, boolean letters, boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }

    private static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return random(count, start, end, letters, numbers, null, RANDOM);
    }

    private static String random(int count, int start, int end, boolean letters, boolean numbers, char... chars) {
        return random(count, start, end, letters, numbers, chars, RANDOM);
    }

    private static String random(int count, String chars) {
        if (chars == null) {
            return random(count, 0, 0, false, false, null, RANDOM);
        }
        return random(count, chars.toCharArray());
    }

    private static String random(int count, char... chars) {
        if (chars == null) {
            return random(count, 0, 0, false, false, null, RANDOM);
        }
        return random(count, 0, chars.length, false, false, chars, RANDOM);
    }

    private static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        }
        if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        if ((start == 0) && (end == 0)) {
            end = 123;
            start = 32;
            if ((!letters) && (!numbers)) {
                start = 0;
                end = Integer.MAX_VALUE;
            }
        }

        char[] buffer = new char[count];
        int gap = end - start;

        while (count-- != 0) {
            char ch;
            if (chars == null) {
                ch = (char) (random.nextInt(gap) + start);
            } else {
                ch = chars[(random.nextInt(gap) + start)];
            }
            if (((letters) && (Character.isLetter(ch))) || ((numbers) && (Character.isDigit(ch))) || ((!letters) && (!numbers))) {

                if ((ch >= 56320) && (ch <= 57343)) {
                    if (count == 0) {
                        count++;
                    } else {
                        buffer[count] = ch;
                        count--;
                        buffer[count] = ((char) (55296 + random.nextInt(128)));
                    }
                } else if ((ch >= 55296) && (ch <= 56191)) {
                    if (count == 0) {
                        count++;
                    } else {
                        buffer[count] = ((char) (56320 + random.nextInt(128)));
                        count--;
                        buffer[count] = ch;
                    }
                } else if ((ch >= 56192) && (ch <= 56319)) {
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return new String(buffer).replace(" ", "-");
    }

}
