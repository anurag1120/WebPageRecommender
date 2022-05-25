package util;
import java.util.ArrayList;
public class Utility{
    public static ArrayList<Integer> convertArraytoArrayList(int arr[]){
        ArrayList<Integer> res = new ArrayList<Integer>();
        for(int i=0;i<arr.length;i++){
            res.add(arr[i]);
        }
        return res;
    }
}
