package func;

import models.Tradition;

import java.util.*;

/**
 * Created by iocz on 14/09/15.
 */
public class Content {

    public static ArrayList<String> getPages() {
        ArrayList<String> pages = new ArrayList<>();
        for (int i = Calendar.getTraditions().size() - 1; i > -1; i--){
            StringBuilder pg = new StringBuilder("htable.jsp?title=".concat(
                    Calendar.getTraditions().get(i).getHoliday().getName()).concat(
                    "&img=/WebApp/images/str/").concat(Calendar.getImgURlList().get(i)).concat(
                    "&id=").concat(Integer.toString(i)).concat(
                    "&text=").concat(Calendar.getTraditions().get(i).getDescription()).concat(
                    "&country=").concat(Calendar.getTraditions().get(i).getCountry().getName()));
            pages.add(pg.toString());
        }
        return pages;
    }
}
