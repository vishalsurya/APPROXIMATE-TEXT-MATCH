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

    public void approximatelyEquals(String needle,String haystack,double threshold) {
        double defaultThreshold=0.5;
        if (needle==null ||haystack==null){
            System.out.println("Needle or haystack is null . PLease enter non null value");
            return;
        }
        if(threshold==0){
            threshold=defaultThreshold;
        }
        StringUtils s = new StringUtils();
        int lev_dist;
        if(threshold==1||threshold==1.0){
            if (needle.equalsIgnoreCase(haystack)){
                System.out.println("RESULT - TRUE  The strings match exactly");
            }
            else{
                System.out.println(" RESULT - FALSE The strings donot match exactly");
            }

        }
        else {

            lev_dist = s.getLevenshteinDistance(needle.toLowerCase(), haystack.toLowerCase());
            System.out.println(" lev dist " + lev_dist);
            System.out.println();

            double ratio = 1-(((double) lev_dist) / (Math.max(needle.length(), haystack.length())));
            System.out.println("ratio " + ratio);
            if (ratio >= threshold) {
                System.out.println("RESULT - TRUE  The strings match within the given threshold " + threshold);

            } else {
                System.out.println(" RESULT - FALSE The strings donot match with the given threshold " + threshold);
            }
        }

    }

    public  void approx_contains(String needle,String haystack,double threshold) {
        double defaultThreshold=0.5;
        if (needle==null ||haystack==null){
            System.out.println("Needle or haystack is null . PLease enter non null value");
            return;
        }
        if(threshold==0){
            threshold=defaultThreshold;
        }

        StringUtils s = new StringUtils();
        LevenshteinDistance ld = LevenshteinDistance.getDefaultInstance();
        double distance=0;
        int flag=0;
        int lev_dist;
        lev_dist = s.getLevenshteinDistance(needle.toLowerCase(),haystack.toLowerCase());

        double ratio = 1-((double)lev_dist) / (Math.max(needle.length(), haystack.length()));
        String pattern = "\\b"+needle+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(haystack);
        boolean t =m.find();
        if (t){
         System.out.println("TRUE The needle "+needle+" is exactly contained in the haystack - "+haystack);

        }
        if(threshold==1||threshold==1.0){
            if(t){
                System.out.println("TRUE The needle "+needle+" is exactly contained in the haystack - "+haystack);

            }
            else{
                System.out.println("FALSE The needle "+needle+" is NOT exactly contained in the haystack - "+haystack);

            }

        }
        else {
         if (lev_dist != -1 && ratio > threshold) {
             //   System.out.println("lev distance" + lev_dist);
                System.out.println("ratio" + ratio);
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
                    System.out.println("ratio"+ratio);
                    System.out.println("RESULT - FALSE No String " + needle + "is NOT approximately contained in String " + haystack + "  within given threshold value " + threshold);

                } else if (flag == 1) {
                    System.out.println("ratio"+ratio);
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


            System.out.println("You input:  String 1  - " + str1 + " and String2  " + str2 + " methodname " + method_name + " threshold " + threshold);
            MyClass obj = new MyClass(str1, str2, method_name, threshold);
            if (method_name.equalsIgnoreCase("eq")) {
                obj.approximatelyEquals(str1, str2, threshold);
            } else if (method_name.equalsIgnoreCase("co")) {
                obj.approx_contains(str1, str2, threshold);
            }
            Map<CharSequence, Integer> leftVector =
                    Arrays.stream(str1.split(""))
                            .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
            Map<CharSequence, Integer> rightVector =
                    Arrays.stream(str2.split(""))
                            .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
            cosineSimilarityScore = cs.cosineSimilarity(leftVector, rightVector);
            jaccardSimilarityScore = j.apply(str1, str2);
            System.out.println("Jaccard similarity score" + jaccardSimilarityScore);
            System.out.println("Cosine similarity score" + cosineSimilarityScore);
            System.out.println();
            System.out.println("Do you want to continue y/n ");
            ch = sc.next();
        }while(ch.equalsIgnoreCase("y"));


    }


}

