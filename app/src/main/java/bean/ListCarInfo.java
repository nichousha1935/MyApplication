package bean;

import java.util.List;

public class ListCarInfo extends response {
    private List<CarInfo> list;

    public List<CarInfo> getList() {
        return list;
    }

    public void setList(List<CarInfo> list) {
        this.list = list;
    }
}
