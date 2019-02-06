import DoubleHashMap.DoubleHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DoubleHashMapTest {

    public static void main(String[] args){

        System.out.println("Double Hashing Test 1 table start length is 13:");
        DoubleHashMap<Integer,Integer> map1 =new DoubleHashMap<Integer, Integer>();
        map1.put(4,5);
        map1.put(16,8);
        map1.put(4,19);
        map1.put(4,27);
        map1.put(1,15);
        map1.put(14,27);
        map1.put(27,18);
        map1.put(5,54);
        map1.put(18,58);
        map1.put(13,7);
        map1.put(26,24);
        map1.put(23,32);
        map1.put(17,77);
        System.out.println();
        System.out.println("size is "+map1.size());
        System.out.println();
        System.out.println("get(4) is "+map1.get(4));
        System.out.println("get(1) is "+map1.get(1));
        System.out.println("get(5) is "+map1.get(5));
        System.out.println();
        System.out.println("removing 4 "+map1.remove(4));
        System.out.println("removing 1 "+map1.remove(1));
        System.out.println("removing 5 "+map1.remove(5));
        System.out.println();
        System.out.println("get(4) is "+map1.get(4));
        System.out.println("get(1) is "+map1.get(1));
        System.out.println("get(5) is "+map1.get(5));
        System.out.println();
        System.out.println("containsKey(1) "+map1.containsKey(1));
        System.out.println("containsKey(14) "+map1.containsKey(14));
        System.out.println("containsKey(18) "+map1.containsKey(18));
        System.out.println();
        System.out.println("containsValue(15) "+map1.containsValue(15));
        System.out.println("containsValue(32) "+map1.containsValue(32));
        System.out.println("containsValue(58) "+map1.containsValue(58));
        System.out.println();
        TreeSet<Integer> keySet = (TreeSet<Integer>) map1.keySet();
        TreeSet<Integer> valueSet =(TreeSet<Integer>) map1.values();
        TreeSet<Map.Entry<Integer, Integer>> mapSet = (TreeSet<Map.Entry<Integer, Integer>>) map1.entrySet();
        System.out.println("Key set : "+keySet.toString());
        System.out.println("Value set : "+valueSet.toString());
        System.out.println("Map set : "+mapSet.toString());
        System.out.println("Map print : "+map1);
        System.out.println();
        System.out.println("Double Hashing Test 2 table start length is 13:");
        DoubleHashMap<String,Integer> map2 =new DoubleHashMap<String, Integer>();
        map2.put("ferhat",5);
        map2.put("gtu",8);
        map2.put("ferhat",19);
        map2.put("sign",27);
        map2.put("sing",15);
        map2.put("hash",27);
        map2.put("double",18);
        map2.put("integer",54);
        map2.put("java",58);
        map2.put("get",7);
        map2.put("teg",24);
        map2.put("set",32);
        map2.put("tes",77);
        System.out.println();
        System.out.println("size is "+map2.size());
        System.out.println();
        System.out.println("get(gtu) is "+map2.get("gtu"));
        System.out.println("get(sign) is "+map2.get("sign"));
        System.out.println("get(java) is "+map2.get("java"));
        System.out.println();
        System.out.println("removing gtu "+map2.remove("gtu"));
        System.out.println("removing sign "+map2.remove("sign"));
        System.out.println("removing java "+map2.remove("java"));
        System.out.println();
        System.out.println("get(gtu) is "+map2.get("gtu"));
        System.out.println("get(get) is "+map2.get("get"));
        System.out.println("get(sing) is "+map2.get("sing"));
        System.out.println();
        System.out.println("containsKey(hash) "+map2.containsKey("hash"));
        System.out.println("containsKey(double) "+map2.containsKey("double"));
        System.out.println("containsKey(gtu) "+map2.containsKey("gtu"));
        System.out.println();
        System.out.println("containsValue(8) "+map2.containsValue(8));
        System.out.println("containsValue(27) "+map2.containsValue(27));
        System.out.println("containsValue(32) "+map2.containsValue(32));
        System.out.println();
        TreeSet<String> keySet2 = (TreeSet<String>) map2.keySet();
        TreeSet<Integer> valueSet2 =(TreeSet<Integer>) map2.values();
        TreeSet<Map.Entry<String, Integer>> mapSet2 = (TreeSet<Map.Entry<String, Integer>>) map2.entrySet();
        System.out.println("Key set : "+keySet2.toString());
        System.out.println("Value set : "+valueSet2.toString());
        System.out.println("Map set : "+mapSet2.toString());
        System.out.println("Map print :"+map2);


    }
}
