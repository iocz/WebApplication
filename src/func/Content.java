package func;

import model.Country;
import model.Holiday;
import model.Tradition;

import java.util.*;

/**
 * Created by iocz on 14/09/15.
 */
public class Content {

    public static ArrayList<String> getPages() {
        ArrayList<String> pages = new ArrayList<>();
        for (int i = Calendar.getIdList().size() - 1; i > -1; i--){
            //String num = Integer.toString(i);
            int curValue = Calendar.getIdList().get(i);
            StringBuilder pg = new StringBuilder("htable.jsp?title=".concat(
                    Calendar.getTraditions().get(curValue).getHoliday().getName()).concat(
                    "&img=/WebApp/images/str/img").concat(Integer.toString(curValue)).concat(".gif").concat("&id=").concat(
                    Integer.toString(curValue)).concat(
                    "&text=").concat(Calendar.getTraditions().get(curValue).getDescription()).concat(
                    "&country=").concat(Calendar.getTraditions().get(curValue).getCountry().getName()));
            pages.add(pg.toString());
        }
        return pages;
    }

    public static Integer getHashMapKey(HashMap<Integer, Tradition> map, Tradition desiredTradition) {
        Set<Map.Entry<Integer, Tradition>> entrySet=map.entrySet();
        Integer result = 0;

        for (Map.Entry<Integer, Tradition> pair : entrySet) {
            if (desiredTradition.equals(pair.getValue())) {
                result = pair.getKey();
            }
        }
        return result;
    }

    public static void getCountries(HashMap<Integer, Country> map) {
        Set<Map.Entry<Integer, Country>> entrySet = map.entrySet();

        for (Map.Entry<Integer, Country> pair : entrySet) {
            System.out.println(pair.getKey() + " " +
                pair.getValue());
        }
    }
}
