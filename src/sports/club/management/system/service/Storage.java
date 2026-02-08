package sports.club.management.system.service;

import java.util.ArrayList;
import java.util.List;

public class Storage<T> {
    private List<T> items =  new ArrayList<T>();

    public void add(T item){
        items.add(item);
    }

    public Storage() {

    }

    public List<T> getAll() {
        return items;
    }

}
