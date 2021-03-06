package controllers.backend;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import com.avaje.ebean.Ebean;
import json.OperationResult;
import models.Admin;
import models.House;
import models.HouseState;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.backend.order_handle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Summer on 16/4/19.
 */

@Restrict(@Group("ADMIN"))
public class OrderHandle extends Controller {
    @SuppressWarnings("unchecked")
    public Result index () {
        HouseState ordered = HouseState.find.where().eq("name", "已预订").findUnique();
        HouseState orderHandled = HouseState.find.where().eq("name", "预订已处理").findUnique();

        List<House> houses;

        if (ordered == null || ordered == null) houses = new ArrayList<>();
        else houses = House.find.where().in("house_state_id", new Object[]{ordered.getId(), orderHandled.getId()}).findList();

        Logger.info(String.valueOf(houses.size()));

        Optional<Admin> user = (Optional<Admin>) ctx().args.get("user");
        return ok(order_handle.render("订单处理", user.get(), houses));
    }

    public Result handle (Long id) {
        House house = House.find.byId(id);

        // 检查订单是否已经处理
        if (house.getState().getName().equals("预订已处理")) return badRequest("订单已经处理");

        Ebean.beginTransaction();
        try {
            house.setState(HouseState.find.where().eq("name", "预订已处理").findUnique());
            house.save();
            Ebean.commitTransaction();
        }
        catch (Exception e) {
            e.printStackTrace();
            Ebean.rollbackTransaction();
            return internalServerError("服务器端错误");
        }
        finally {
            Ebean.endTransaction();
        }

        return ok("操作成功");
    }

    public Result delete (Long id) {
        Logger.info(String.valueOf(id));
        House house = House.find.byId(id);

        // 检查订单是否已经删除
        if (!house.getState().getName().equals("已预订")) return badRequest(Json.toJson(new OperationResult(400, 1, "订单已经删除")));

        Ebean.beginTransaction();
        try {
            house.setState(HouseState.find.where().eq("name", "未售出").findUnique());
            house.setUser(null);
            house.save();
            Ebean.commitTransaction();
        }
        catch (Exception e) {
            e.printStackTrace();
            Ebean.rollbackTransaction();
            return internalServerError("服务器端错误");
        }
        finally {
            Ebean.endTransaction();
        }

        return ok("操作成功");
    }
}
