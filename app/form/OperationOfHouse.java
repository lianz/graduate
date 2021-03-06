package form;

import play.data.validation.Constraints;

/**
 * Created by Summer on 16/4/27.
 */
public class OperationOfHouse {
    @Constraints.Required
    private Long building;

    @Constraints.Required
    private Integer floor;

    @Constraints.Required
    private Integer no;

    @Constraints.Required
    private Long state;

    @Constraints.Required
    private Integer space;

    @Constraints.Required
    private Integer price;

    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
        this.building = building;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Integer getSpace() {
        return space;
    }

    public void setSpace(Integer space) {
        this.space = space;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
