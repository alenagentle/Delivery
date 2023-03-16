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

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public abstract class OrderingMapper {

    @Autowired
    private ItemHelper itemHelper;

    @Autowired
    private UserHelper userHelper;

    public abstract Ordering mapToEntity(OrderingRequest orderingRequest);

    public abstract OrderingResponse mapToResponse(Ordering ordering);

    public abstract List<OrderingResponse> mapToResponses(List<Ordering> orderings);

    @AfterMapping
    protected void map(@MappingTarget Ordering ordering, OrderingRequest orderingRequest) {
        List<Item> items = itemHelper.findItemsByIds(orderingRequest.getItemIds());
        ordering.setItems(items);
        UserData user = userHelper.getCurrentUserData();
        ordering.setUser(user);
        Date currentDate = new Date();
        ordering.setOrderingDate(currentDate.toInstant());
        ordering.setOrderingStatus(OrderingStatus.STATUS_PROCESSED);
        ordering.setCost(calculateCost(items));
    }

    @AfterMapping
    protected void map(@MappingTarget OrderingResponse response, Ordering ordering) {
        UserData user = userHelper.getCurrentUserData();
        response.setUserId(user.getId());
    }

    public void setItemHelper(ItemHelper itemHelper) {
        this.itemHelper = itemHelper;
    }

    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
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
