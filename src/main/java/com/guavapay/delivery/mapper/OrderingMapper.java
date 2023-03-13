package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.request.OrderingRequest;
import com.guavapay.delivery.dto.response.OrderingResponse;
import com.guavapay.delivery.entity.Item;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.OrderingStatus;
import com.guavapay.delivery.helper.ItemHelper;
import com.guavapay.delivery.helper.UserHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderingMapper {

    @Autowired
    private ItemHelper itemHelper;

    @Autowired
    private UserHelper userHelper;

    public abstract Ordering mapToEntity(OrderingRequest orderingRequest);

    public abstract OrderingResponse mapToResponse(Ordering ordering);

    @AfterMapping
    protected void map(@MappingTarget Ordering order, OrderingRequest orderingRequest) {
        List<Item> items = itemHelper.findItemsByIds(orderingRequest.getItemIds());
        order.setItems(items);
        UserData user = userHelper.getCurrentUserData();
        order.setUser(user);
        Date currentDate = new Date();
        order.setOrderDate(currentDate.toInstant());
        order.setOrderStatus(OrderingStatus.STATUS_PROCESSED);
        order.setCost(calculateCost(items));
    }

    private double calculateCost(List<Item> items) {
        double cost = 0.0;
        for (Item item : items) {
            cost += getMultiplying(item.getProduct().getPrice(), item.getAmount());
        }
        return cost;
    }

    private double getMultiplying(Double price, Integer amount) {
        return price * amount;
    }
}
