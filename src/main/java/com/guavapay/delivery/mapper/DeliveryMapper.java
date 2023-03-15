package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.request.AssignDeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryFullResponse;
import com.guavapay.delivery.dto.response.DeliveryResponse;
import com.guavapay.delivery.entity.Delivery;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.DeliveryStatus;
import com.guavapay.delivery.helper.OrderingHelper;
import com.guavapay.delivery.helper.UserHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderingMapper.class})
public abstract class DeliveryMapper {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private OrderingHelper orderingHelper;

    public abstract Delivery mapToEntity(AssignDeliveryRequest assignDeliveryRequest);

    public abstract DeliveryResponse mapToResponse(Delivery delivery);

    public abstract DeliveryFullResponse mapToFullResponse(Delivery delivery);

    public abstract List<DeliveryResponse> mapToResponses(List<Delivery> deliveryList);

    public abstract List<DeliveryFullResponse> mapToFullResponses(List<Delivery> deliveryList);

    @AfterMapping
    void map(@MappingTarget Delivery delivery, AssignDeliveryRequest assignDeliveryRequest) {
        UserData user = userHelper.findUserById(assignDeliveryRequest.getCourierId());
        delivery.setUser(user);
        List<Ordering> orderingList = orderingHelper.findOrderingsByIds(assignDeliveryRequest.getOrderIds());
        delivery.setOrderings(orderingList);
        delivery.setDeliveryStatus(DeliveryStatus.STATUS_ASSIGNED);
    }

    @AfterMapping
    void map(@MappingTarget DeliveryResponse response, Delivery delivery) {
        response.setCourierId(delivery.getUser().getId());
    }

    @AfterMapping
    void map(@MappingTarget DeliveryFullResponse response, Delivery delivery) {
        response.setCourierId(delivery.getUser().getId());
    }

}
