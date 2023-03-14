package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.request.DeliveryRequest;
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

    public abstract Delivery mapToEntity(DeliveryRequest deliveryRequest);

    public abstract DeliveryResponse mapToResponse(Delivery delivery);

    public abstract List<DeliveryResponse> mapToResponses(List<Delivery> deliveryList);

    @AfterMapping
    void map(@MappingTarget Delivery delivery, DeliveryRequest deliveryRequest) {
        UserData user = userHelper.findUserById(deliveryRequest.getCourierId());
        delivery.setUser(user);
        List<Ordering> orderingList = orderingHelper.findOrderingsByIds(deliveryRequest.getOrderIds());
        delivery.setOrderings(orderingList);
        delivery.setDeliveryStatus(DeliveryStatus.STATUS_ASSIGNED);
    }

    @AfterMapping
    void map(@MappingTarget DeliveryResponse response, Delivery delivery) {
        response.setCourierId(delivery.getUser().getId());
    }

}
