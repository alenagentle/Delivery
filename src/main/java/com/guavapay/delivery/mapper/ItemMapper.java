package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.request.ItemRequest;
import com.guavapay.delivery.dto.response.ItemResponse;
import com.guavapay.delivery.entity.Item;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.helper.ProductHelper;
import com.guavapay.delivery.helper.UserHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private ProductHelper productHelper;

    public abstract Item mapToEntity(ItemRequest itemRequest);

    public abstract ItemResponse mapToResponse(Item item);

    @AfterMapping
    protected void map(@MappingTarget ItemResponse response, Item item) {
        response.setProductId(item.getProduct().getId());
        response.setUserId(item.getUser().getId());
    }

    @AfterMapping
    protected void map(@MappingTarget Item item, ItemRequest itemRequest) {
        item.setProduct(productHelper.findProductById(itemRequest.getIdProduct()));
        UserData user = userHelper.getCurrentUserData();
        item.setUser(user);

    }
}
