# APPROXIMATE-TEXT-MATCH
APPROX EQUALS AND APPROX CONTAINS
 
Implementation of a new algorithm to find approximate/exact matches between strings. The class consists of two functions and a main function.
 * The two functions are approximately equals and approximately contains.
 * The main function takes the user input.
 *       param1 - String needle
 *       param2- String haystack
 *       param3- The function name "eq" for approximately equals and "co" for approximately contains
 *       Param 4- The threshold value for similarity (0,1] 1 - for exact match
 *
 *       Default threshold=0.5
 
     /*
FUNCTION NAME - APPROXIMATE_EQUALS

     *       param1 - String needle
     *       param2- String haystack
     *       param3- The function name "eq" for approximately equals and "co" for approximately contains
     *       Param 4- The threshold value for similarity (0,1] 1 - for exact match
     *
     *
     *       If needle length is greater than haystack length we swap the two values and continue.Checks if the string and needle are exactly equal. If yes , no matter any threshold, it returns true.If they are not equal but the threshold given is 1 which measns the user is expecting an exact match, then it checks if there is an exact match and prints the result.If the user enters any value between 0 and 1 for the threshold, then the function returns true if the similarity ratio > threshold and returns false if the similarity ratio is <threshold.
     *       Similarity ratio= 1-levenshtein edit distance/max(length(needle,haystack))
 
     * */
       /*
     *
   FUNCTION NAME - APPROXIMATE CONTAINS
     
     *       param1 - String needle
     *       param2- String haystack
     *       param3- The function name "eq" for approximately equals and "co" for approximately contains
     *       Param 4- The threshold value for similarity (0,1] 1 - for exact match
     *
     *
     *
     *       If needle length is greater than haystack length we swap the two values and continue.
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
