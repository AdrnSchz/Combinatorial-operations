package Entities;

import java.util.ArrayList;
import java.util.List;

public class Center {
    private String name;
    private int num_types;
    private List<String> types;

    public Center(String name) {
        this.name = name;
        this.num_types = 0;
        this.types = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public int getNum_types() {
        return num_types;
    }
    public void setNum_types(int num_types) {
        this.num_types += num_types;
    }
    public List<String> getTypes() {
        return types;
    }
}
