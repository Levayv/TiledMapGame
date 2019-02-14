//package com.mygdx.game.craps;
//
//public class ForG {
//    // for admin inputs imitation
//    private int plainAddition;
//    private float modifer1 = 7;
//    private float modifer2 = 5;
//    private float modifer3 = 6;
//    private float modifer4 = 15;
//    private float modifer5 = 14;
//    private float modifer6 = 15;
//
//
//
//
//    // for user inputs imitation
//    private int[] inputs;
//    // for system out print
//    private int[] outputs;
//    // other
//    // ...
//    // logic triggered by pressing F
//    public ForG(){
//        // admin inputs imitation
//        plainAddition = 1;
//        Range range0 = new Range(0,10,0);
//        Range range1 = new Range(10,20,1);
//        Range range2 = new Range(20,30,2);
//        Range range3 = new Range(30,40,3);
//        Range range4 = new Range(40,50,4);
//        Range range5 = new Range(50,100,10);
//        Range[] allRanges = new Range[]{
//                range0,
//                range1,
//                range2,
//                range3,
//                range4,
//                range5,
//        };
//        for (int i = 0; i < allRanges.length-1; i++) {
//            allRanges[i].setAbstracts(
//                    allRanges[i+1].min+0,
//                    allRanges[i+1].add+0
//            );
//        }
//        allRanges[allRanges.length-1].setAbstracts(true);
//        // user inputs imitation
//        inputs = new int[]{10,19,20,21,22,23,29,30,39,40,49,50,59,66,77,99};
//        outputs = new int[inputs.length];
//        // calculations
//        for (int i = 0; i < inputs.length; i++) {
////            outputs[i] = plainAddition(inputs[i],add);
//            outputs[i] = rangeAddition(inputs[i],allRanges, true);
//        }
//        System.out.println(" --- FORGE START ---");
//        System.out.println(" - addition -");
//        for (int i = 0; i < inputs.length; i++) {
//            System.out.println("IN:" + inputs[i] + " $ OUT: "+ outputs[i]+" $");
//        }
////        checker();
//        System.out.println(" - abstraction - ");
//        inputs = new int[]{10,19,20,21,22,23,29,30,39,40,49,50,59,66,77,999};
//        for (int i = 0; i < inputs.length; i++) {
////            outputs[i] = plainAddition(inputs[i],add);
//            outputs[i] = rangeAddition(inputs[i],allRanges, false);
//        }
//        for (int i = 0; i < inputs.length; i++) {
//            System.out.println("IN:" + inputs[i] + " $ OUT: "+ outputs[i]+" $");
//        }
//        String s = "asd\ta    s " +
//                "d\nasdasd";
//        System.out.println(" --- FORGE  END  ---");
//        System.out.println(s);
//    }
//
//    private int plainAddition(int input,boolean add){
//        if (add){
//            return input + plainAddition;
//        }else {
//            return input - plainAddition;
//        }
//    }
//    private int rangeAddition(int input,Range[] ranges,boolean add){
//        int output = input;
//        if (add){
//            for (int i = 0; i < ranges.length; i++) {
//                output += ranges[i].inRangeAdd(input);
//            }
//        }else{
//            for (int i = 0; i < ranges.length; i++) {
//                output -= ranges[i].inRangeAbs(input);
//            }
////            System.out.println("bug");
//        }
//        return output;
//    }
//    private class Range{
//        private int min;
//        private int max;
//        private int add;
//        private int minAbs;
//        private int maxAbs;
////        private boolean minIncluded = true;
////        private boolean maxIncluded = false;
//        private Range(int from, int to, int add){
//            // multiple ranges must not cross ?
//            this.min = from;
//            this.max = to;
//            this.add = add;
//        }
//        private int inRangeAdd(int value){
//            if (value>=min && value<max){
//                return add;
//            }//else is optional , same sh*t
//            return 0; // must be 1 if %
//        }
//        private int inRangeAbs(int value){
//            if (value>=minAbs && value<maxAbs){
//                return add;
//            }//else is optional , same sh*t
//            return 0; // must be 1 if %
//        }
//        private void setAbstracts(int nextRangeMin, int nextRangeAdd){
//            minAbs = min + add;
//            maxAbs = nextRangeMin+nextRangeAdd;
//        }
//        private void setAbstracts(boolean last){
//            minAbs = min + add;
//            maxAbs = 2147483647;
//        }
//    }
//}
