package samplerun;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
     *       Default threshold=0.5
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
    public void approximatelyEquals(String needle,String haystack,double threshold) {
        double defaultThreshold=0.5;
        String temp;
        if (needle==null ||haystack==null){
            System.out.println("Needle or haystack is null . PLease enter non null value");
            return;
        }
        if(threshold==0){
            threshold=defaultThreshold;
        }
        if(needle.length()>haystack.length()){
            temp=haystack;
            haystack=needle;
            needle=temp;
        }
        StringUtils s = new StringUtils();
        double lev_dist;
        if (needle.equalsIgnoreCase(haystack)){
            if(threshold==1||threshold==1.0){

                System.out.println("RESULT - TRUE  The strings match exactly");
            }
            else{
                System.out.println(" RESULT - FALSE The strings donot match exactly");
            }

        }
        else {

            lev_dist = s.getLevenshteinDistance(needle.toLowerCase(), haystack.toLowerCase());
           // System.out.println(" lev dist " + lev_dist);
           // System.out.println();

            double ratio = 1-(( lev_dist) / (Math.max(needle.length(), haystack.length())));
            System.out.println("Similarity Ratio " + ratio);
            System.out.println();
            if (ratio >= threshold) {
                System.out.println("RESULT - TRUE  The strings match within the given threshold " + threshold);

            } else {
                System.out.println(" RESULT - FALSE The strings donot match with the given threshold " + threshold);
            }
        }

    }
    /*
    *
    * Approximately contains
    * * Input param1 - String needle
     *       param2- String haystack
     *       param3- The function name "eq" for approximately equals and "co" for approximately contains
     *       Param 4- The threshold value for similarity (0,1] 1 - for exact match
     *       Default threshold=0.5
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

    public  void approx_contains(String needle,String haystack,double threshold) {
        double defaultThreshold=0.5;
        String temp;
        if (needle==null ||haystack==null){
            System.out.println("Needle or haystack is null . PLease enter non null value");
            return;
        }
        if(threshold==0){
            threshold=defaultThreshold;
        }
        if(needle.length()>haystack.length()){
            temp=haystack;
            haystack=needle;
            needle=temp;
        }

        StringUtils s = new StringUtils();
        LevenshteinDistance ld = LevenshteinDistance.getDefaultInstance();
        double distance=0;
        int flag=0;             //required to see if a match happens. if ratio>threshold, flag value is toggled.
        int lev_dist;
        lev_dist = s.getLevenshteinDistance(needle.toLowerCase(),haystack.toLowerCase());

        double ratio = 1-((double)lev_dist) / (Math.max(needle.length(), haystack.length()));
//        String pattern = "\\b"+needle+"\\b";
//        Pattern p=Pattern.compile(pattern);
//        Matcher m=p.matcher(haystack);
//        t=m.find()                        //Commented code can be used in the future if any case that requires plurals
//                                          to be treated different arises
        boolean t =haystack.contains(needle);

        if (t) {
            System.out.println("TRUE The needle " + needle + " is exactly contained in the haystack - " + haystack);
      }
        else {
                if (threshold == 1 || threshold == 1.0) {
                    if (t) {

                        System.out.println("TRUE The needle " + needle + " is exactly contained in the haystack - " + haystack);

                    } else {
                        System.out.println("FALSE The needle " + needle + " is NOT exactly contained in the haystack - " + haystack);

                    }
                }

            if (lev_dist != -1 && ratio > threshold) {
             //   System.out.println("lev distance" + lev_dist);
                System.out.println("Similarity Ratio" + ratio);
                System.out.println("TRUE " + needle + " approx contained in " + haystack);
            } else {


                for (int i = 0; i <= haystack.length() - needle.length(); i++) {
                    String substring = haystack.substring(i, i + needle.length());

                    if (substring.indexOf(needle.charAt(0)) >= 0) {
                        distance = ld.apply(substring.toLowerCase(), needle.toLowerCase());
                      //  System.out.println("distance" + distance);
                        ratio = 1-((distance) / (Math.max(needle.length(), haystack.length())));

                        if (ratio > threshold) {
                            flag = 1;

                        }
                    }
                }
                if (flag == 0) {
                    System.out.println("Similarity ratio"+ratio);
                    System.out.println("RESULT - FALSE No String " + needle + "is NOT approximately contained in String " + haystack + "  within given threshold value " + threshold);

                } else if (flag == 1) {
                    System.out.println("Similarity Ratio"+ratio);
                    System.out.println(" RESULT - TRUE Yes String1-  " + needle + " approximately contained in String2 -  " + haystack + "  within given threshold value " + threshold);

                }
            }
        }

    }


    public static void main(String[] args) {

        CosineSimilarity cs = new CosineSimilarity();

        JaccardSimilarity j = new JaccardSimilarity();

        double cosineSimilarityScore = 0;
        String ch;
        double jaccardSimilarityScore = 0;
        do {

            Scanner sc = new Scanner(System.in);
            System.out.print("String1 : ");
            String str1 = sc.nextLine();
            System.out.print("String2 : ");
            String str2 = sc.nextLine();
            System.out.print("Method name- eq or co : ");
            String method_name = sc.nextLine();


            System.out.print("Threshold : ");
            double threshold = sc.nextDouble();


          //  System.out.println("You input:  String 1  - " + str1 + " and String2  " + str2 + " methodname " + method_name + " threshold " + threshold);
            MyClass obj = new MyClass(str1, str2, method_name, threshold);
            if (method_name.equalsIgnoreCase("eq")) {
                obj.approximatelyEquals(str1, str2, threshold);
            } else if (method_name.equalsIgnoreCase("co")) {
                obj.approx_contains(str1, str2, threshold);
            }
            /*
            Code to convert the strings to vectors for calculating cosine similarity
            * */
            Map<CharSequence, Integer> leftVector =
                    Arrays.stream(str1.split(""))
                            .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
            Map<CharSequence, Integer> rightVector =
                    Arrays.stream(str2.split(""))
                            .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
            cosineSimilarityScore = cs.cosineSimilarity(leftVector, rightVector);
            jaccardSimilarityScore = j.apply(str1, str2);
           // System.out.println("Jaccard similarity score" + jaccardSimilarityScore);
           // System.out.println("Cosine similarity score" + cosineSimilarityScore);
            System.out.println();
            System.out.println("Do you want to continue y/n ");
            ch = sc.next();
        }while(ch.equalsIgnoreCase("y"));


    }


}

