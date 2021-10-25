package com.test.tokoko.service.impl;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.common.payload.CommonMessage;
import com.test.tokoko.model.Order;
import com.test.tokoko.model.Product;
import com.test.tokoko.model.User;
import com.test.tokoko.module.order.payload.OrderRequest;
import com.test.tokoko.repository.OrderRepository;
import com.test.tokoko.repository.ProductRepository;
import com.test.tokoko.repository.UserRepository;
import com.test.tokoko.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponse inputOrder(OrderRequest orderRequest) {
        try {
            Order order = new Order();
            User user = userRepository.findByIdAndIsDeleted(orderRequest.getUserId(), 0);
            Product product = productRepository.findByIdAndIsDeleted(orderRequest.getProductId(), 0);

            if (user != null && product != null) {
                order.setUserId(user);
                order.setProductId(product);
                order.setAmount(orderRequest.getAmount());
                order.setTotalPrice(orderRequest.getAmount() * product.getPrice());
                order.setIsCancel(0);
                order.setIsDeleted(0);
                orderRepository.save(order);
                product.setAmount(product.getAmount() - order.getAmount());
                return new BaseResponse(CommonMessage.SAVED, order);
            } else {
                return new BaseResponse(CommonMessage.NOT_FOUND);
            }
        } catch (Exception e) {
            return new BaseResponse(CommonMessage.NOT_SAVED);
        }
    }

    @Override
    public BaseResponse updateOrder(OrderRequest orderRequest, Long id) {
        try {
            Order order = orderRepository.findByIdAndIsDeleted(id, 0);
            Product product = productRepository.findByIdAndIsDeleted(orderRequest.getProductId(), 0);
            int orderDB = order.getAmount();
            if (order != null) {
                order.setAmount(orderRequest.getAmount());
                order.setTotalPrice(orderRequest.getAmount() * product.getPrice());
                order.setIsCancel(order.getIsCancel());
                orderRepository.save(order);
                product.setAmount(product.getAmount() - (orderRequest.getAmount() - orderDB));
                return new BaseResponse(CommonMessage.UPDATED, order);
            } else {
                return new BaseResponse(CommonMessage.NOT_FOUND);
            }
        } catch (Exception e) {
            return new BaseResponse(CommonMessage.NOT_UPDATED);
        }
    }

    @Override
    public BaseResponse cancelOrder(Long id) {
        try {
            Order order = orderRepository.findByIdAndIsDeleted(id, 0);
            Product orderDB = order.getProductId();
            Product product = productRepository.findByIdAndIsDeleted(orderDB.getId(), 0);
            order.setIsCancel(1);
            product.setAmount(product.getAmount() + order.getAmount());
            orderRepository.save(order);
            return new BaseResponse(CommonMessage.ORDER_CANCEL, order);

        } catch (Exception e) {
            return new BaseResponse(CommonMessage.ERROR);

        }
    }
}
