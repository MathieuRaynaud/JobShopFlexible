package JobShopFlexible;

import java.util.ArrayList;

public class Generic {

    public static boolean isIncluded(Object e, ArrayList<?> list){
        for(Object element : list){
            if (element==e) return true;
        }
        return false;
    }

}
