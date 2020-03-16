package samplerun;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
/*

 *Implementation of a new algorithm to find approximate/exact matches between strings. The class consists of two functions and a main function.
 * The two functions are approximately equals and approximately contains.
 * The main function takes the user input.
 * Input param1 - String needle
 *       param2- String haystack
 *       param3- The function name "eq" for approximately equals and "co" for approximately contains
 *       Param 4- The threshold value for similarity (0,1] 1 - for exact match
 *
 *       Default threshold=0.5
 *
 *
 *
 * @author VISHAL SURYA MADHAVAN
 *
 * */
public class MyClass {
    private String string_1;
    private String string_2;
    private double threshold;
    private String method_name;

    public MyClass(String str1,String str2,String methodName,double thresholdvalue){
        this.string_1=str1;
        this.string_2=str2;
        this.method_name=methodName;
        this.threshold=thresholdvalue;

    }
    /*
     * Approximately equals
     * Input param1 - String needle
     *       param2- String haystack
     *       param3- The function name "eq" for approximately equals and "co" for approximately contains
     *       Param 4- The threshold value for similarity (0,1] 1 - for exact match
     *
     *
     *       if needle length is greater than haystack length we swap the two values and continue.
     *
     *       Checks if the string and needle are exactly equal. If yes , no matter any threshold, it returns true.
     *       If they are not equal but the threshold given is 1 which measns the user is expecting an exact match ,
     *       then it checks if there is an exact match and prints the result.
     *
     *       If the user enters any value between 0 and 1 for the threshold, then the function returns true if the
     *       similarity ratio > threshold and returns false if the similarity ratio is <threshold.
     *
     *       Similarity ratio= 1-levenshtein edit distance/max(length(needle,haystack))
     *
     * */
    public static boolean approximatelyEquals(String needle,String haystack,double threshold) {

        String temp;
        if (needle==null ||haystack==null){
            System.out.println("Needle or haystack is null . PLease enter non null value");
            return false;
        }
        if(needle.length()>haystack.length()){
            temp=haystack;
            haystack=needle;
            needle=temp;
        }
        StringUtils s = new StringUtils();
        double levDist;
        levDist = s.getLevenshteinDistance(needle.toLowerCase(), haystack.toLowerCase());
        double ratio = 1-(( levDist) / (Math.max(needle.length(), haystack.length())));
        if (ratio >= threshold) {
            return true;
        } else {
            return false;
        }
 }
    /*
     *
     * Approximately contains
     * * Input param1 - String needle
     *       param2- String haystack
     *       param3- The function name "eq" for approximately equals and "co" for approximately contains
     *       Param 4- The threshold value for similarity (0,1] 1 - for exact match
     *
     *
     *
     *       if needle length is greater than haystack length we swap the two values and continue.
     *
     *       Checks if needle is an exact substring of haystack. if yes it returns TRUE no matter any threshold value.
     *       If the user requires an exact match giving a threshold of 1 expecting an exact contains, then
     *       checks for exact substring condition and returns true or false.
     *       If the threshold is any value between 0 and 1 then the algorithm uses a sliding window of needle length
     *       across the length of the haystack and checks if the first character of the needle is present in the haystack
     *       .If the first character(prefix of length 1 character) is present in the haystack then it calculates the
     *       levenshtein distance and checks if the ratio of similarity is greater than the threshold to return true or false
     *       respectively.
     *
     *       Similarity ratio= 1-levenshtein edit distance/max(length(needle,haystack))
     *
     *
     * */

    public static boolean approxContains(String needle,String haystack,double threshold) {
        if(threshold==0){
            return true;
        }

        String temp;
        if (needle == null || haystack == null) {
            System.out.println("Needle or haystack is null . PLease enter non null value");
            return false;
        }
        if (needle.length() > haystack.length()) {
            temp = haystack;
            haystack = needle;
            needle = temp;
        }

        StringUtils s = new StringUtils();
        LevenshteinDistance ld = LevenshteinDistance.getDefaultInstance();
        double distance = 0;
        int flag = 0;             //required to see if a match happens. if ratio>threshold, flag value is toggled.
        int levDist;
        levDist = s.getLevenshteinDistance(needle.toLowerCase(), haystack.toLowerCase());
        double ratio = 1 - ((double) levDist) / (Math.max(needle.length(), haystack.length()));
        boolean t = haystack.contains(needle);
         if (t) {
            return true;

        } else {
            if (threshold == 1 || threshold == 1.0) {
                if (t) {
                    return true;
                         } else {
                    return false;
                }
            }

            if (levDist != -1 && ratio > threshold) {
                return true;

            } else {
                 for (int i = 0; i <= haystack.length() - needle.length(); i++) {
                    String substring = haystack.substring(i, i + needle.length());

                    if (substring.indexOf(needle.charAt(0)) >= 0) {
                        distance = ld.apply(substring.toLowerCase(), needle.toLowerCase());
                        ratio = 1 - ((distance) / (Math.max(needle.length(), haystack.length())));

                        if (ratio > threshold) {
                            flag = 1;
                        }
                    }
                }
                if (flag == 0) {
                    return false;

                } else if (flag == 1) {
                    return true;

                }
            }
        }
    return true;
    }




    // Simple test cases
    public static void SimpleTest() {
        System.out.println("Hello");

        boolean res;
        // Approximate Equality Test 1
        res = MyClass.approximatelyEquals("abcd", "abcd", 0);
        if(res != true) System.out.println("Failed Approximate Equality Test 1: (abcd, abcd, 0) -- Expected output is true, got false");

        // Approximate Equality Test 2
        res = MyClass.approximatelyEquals("abcd", "abcd", 1);
        if(res != true) System.out.println("Failed Approximate Equality Test 2: (abcd, abcd, 1) -- Expected output is true, got false");

        // Approximate Equality Test 3
        res = MyClass.approximatelyEquals("abcd", "abcd", 0.1);
        if(res != true) System.out.println("Failed Approximate Equality Test 3: (abcd, abcd, 0.1) -- Expected output is true, got false");

        // Approximate Equality Test 4
        res = MyClass.approximatelyEquals("abcd", "abee", 1);
        if(res != false) System.out.println("Failed Approximate Equality Test 4: (abcd, abee, 1) -- Expected output is false, got true");

        // Approximate Equality Test 5
        res = MyClass.approximatelyEquals("abcd", "abee", 0.4);
        if(res != true) System.out.println("Failed Approximate Equality Test 5: (abcd, abee, 0.4) -- Expected output is true, got false");

        // Approximate Equality Test 6
        res = MyClass.approximatelyEquals("abcd", "abee", 0.8);
        if(res != false) System.out.println("Failed Approximate Equality Test 6: (abcd, abee, 0.8) -- Expected output is false, got true");

        // Approximate Equality Test 7
        res = MyClass.approximatelyEquals("abcd", "defg", 0.8);
        if(res != false) System.out.println("Failed Approximate Equality Test 7: (abcd, abee, 0.8) -- Expected output is false, got true");

        // Approximate Equality Test 8
        res = MyClass.approximatelyEquals("abcd", "defg", 0.2);
        if(res != false) System.out.println("Failed Approximate Equality Test 8: (abcd, abee, 0.8) -- Expected output is false, got true");


        //Approximately contains Test1
        res=MyClass.approxContains("abcd","abcd",0);
        if(res!=true) System.out.println("Failed Approximate Contains Test 1: (abcd, abcd, 0) -- Expected output is false, got true");

        // Approximate Contains Test 2
        res = MyClass.approximatelyEquals("abcd", "abcd", 1);
        if(res != true) System.out.println("Failed Approximate Equality Test 2: (abcd, abcd, 1) -- Expected output is true, got false");

        // Approximately Contains Test 3
        res = MyClass.approxContains("abcd", "abcd", 0.1);
        if(res != true) System.out.println("Failed Approximate Equality Test 3: (abcd, abcd, 0.1) -- Expected output is true, got false");

        // Approximately Contains Test 4
        res = MyClass.approxContains("abcd", "abee", 1);
        if(res != false) System.out.println("Failed Approximate Equality Test 4: (abcd, abee, 1) -- Expected output is false, got true");

        // Approximately Contains Test 5
        res = MyClass.approxContains("abcd", "abee", 0.4);
        if(res != true) System.out.println("Failed Approximate Equality Test 5: (abcd, abee, 0.4) -- Expected output is true, got false");

        // Approximate Contains Test 6
        res = MyClass.approxContains("abcd", "abee", 0.8);
        if(res != false) System.out.println("Failed Approximate Contains Test 6: (abcd, abee, 0.8) -- Expected output is false, got true");

        //Approximate Contains Test 7
        res = MyClass.approxContains("abcdefg", "abc", 0.8);
        if(res != true) System.out.println("Failed Approximate Contains Test 7: (abcdefg, abc, 0.8) -- Expected output is true, got false");

        //Approximate Contains Test 8
        res = MyClass.approxContains("abc", "abcdefg", 0.5);
        if(res != true) System.out.println("Failed Approximate Contains Test 8: (abcdefg, abc, 0.5) -- Expected output is true, got false");

        //Approximate Contains Test 9
        res = MyClass.approxContains("abcdef", "abckkd", 0.4);
        if(res != true) System.out.println("Failed Approximate Equality Test 9: (abcdefg, abc, 0.4) -- Expected output is true, got false");

        //Approximate Contains Test 10
        res = MyClass.approxContains("abcdef", "abckkd", 0.8);
        if(res != false) System.out.println("Failed Approximate Equality Test 10: (abcdefg, abc, 0.8) -- Expected output is false, got true");

        //Approximate Contains Test 11
        res = MyClass.approxContains("akk", "akcccb", 0.2);
        if(res != true) System.out.println("Failed Approximate Equality Test 11: (abcdefg, abc, 0.8) -- Expected output is true, got false");

        //Approximate Contains Test 12
        res = MyClass.approxContains("akk", "akcccb", 0.87);
        if(res != false) System.out.println("Failed Approximate Equality Test 12: (akk, akcccb, 0.8) -- Expected output is false, got true");

        //Approximate Contains Test 13
        res = MyClass.approxContains("abc", "defg", 0.87);
        if(res != false) System.out.println("Failed Approximate Equality Test 13: (abc, defg, 0.8) -- Expected output is false, got true");

        //Approximate Contains Test 14
        res = MyClass.approxContains("abc", "defg", 0.2);
        if(res != false) System.out.println("Failed Approximate Equality Test 13: (abc, defg, 0.2) -- Expected output is false, got true");

        //Approximate Contains Test 15
        res = MyClass.approxContains("abc", "defg", 0);
        if(res != true) System.out.println("Failed Approximate contains Test 15: (abc, defg, 0.0) -- Expected output is true, got false");
    }

    public static void main(String[] args) {

        MyClass.SimpleTest();

    }


}