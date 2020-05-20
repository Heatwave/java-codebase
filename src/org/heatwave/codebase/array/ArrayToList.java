package org.heatwave.codebase.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayToList {

    @Test
    public void test() {
        List<String> myList = Arrays.asList("Apple", "Orange");

        String[] myArray = {"Apple", "Banana", "Orange"};
        List<String> mySecondList = Arrays.asList(myArray);

        List<String> mySingleList = Collections.singletonList("single");

        int[] myIntArray = {1, 2, 3};
        List<int[]> myIntList = Arrays.asList(myIntArray);
        System.out.println(myIntList.size());   // 1
        System.out.println(myIntList.get(0));   // [I@704d6e83

        List<String> fixedList = Arrays.asList(myArray);
//        fixedList.add("Guava"); // java.lang.UnsupportedOperationException

        List<Integer> myPrimitiveList = Arrays.stream(myIntArray).boxed().collect(Collectors.toList());
        System.out.println(myPrimitiveList);    // [1, 2, 3]

        List<String> allowsAddingList = new ArrayList<>(Arrays.asList(myArray));
        allowsAddingList.add("Guava");
        System.out.println(allowsAddingList);   // [Apple, Banana, Orange, Guava]

        String[] myStringArray = {"5", "6", "7"};
        List<Integer> myIntegerList = new ArrayList<>();
        for (String str : myStringArray) {
            myIntegerList.add(Integer.valueOf(str));
        }
        System.out.println(myIntegerList);
    }
}
