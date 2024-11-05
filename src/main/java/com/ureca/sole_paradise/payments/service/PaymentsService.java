package com.ureca.sole_paradise.payments.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.ureca.sole_paradise.cart.db.entity.CartEntity;
import com.ureca.sole_paradise.cart.db.repository.CartRepository;
import com.ureca.sole_paradise.order.db.entity.OrderEntity;
import com.ureca.sole_paradise.order.db.repository.OrderRepository;
import com.ureca.sole_paradise.payments.db.dto.CartListReq;
import com.ureca.sole_paradise.payments.db.dto.PaymentsListDTO;
import com.ureca.sole_paradise.payments.db.entity.PaymentsEntity;
import com.ureca.sole_paradise.payments.db.repository.PaymentsRepository;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentsService {

    private final IamportClient iamportClient;

    private final PaymentsRepository paymentsRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public void createPayLog(String impUid, int userId) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(impUid);

        Payment payment = iamportResponse.getResponse();

        PaymentsEntity paymentEntity = PaymentsEntity.builder()
                .user(UserEntity.builder().userId(1).build())
                .pg(payment.getPgProvider())
                .payAmount(payment.getAmount().intValue())
                .payStatus(payment.getStatus())
                .impUid(impUid)
                .cardName(payment.getCardName())
                .cardNumber(payment.getCardCode())
                .installmentMonths(payment.getCardQuota())
                .approvalNumber(payment.getApplyNum())
                .payMethod(payment.getPayMethod())
                .payName(payment.getName()).build();

        PaymentsEntity payments = paymentsRepository.save(paymentEntity);

        List<CartEntity> cartEntityList = cartRepository.findByUser_UserId(userId);

        List<OrderEntity> orderEntityList = new ArrayList<>();
        for (CartEntity cartEntity : cartEntityList) {
            OrderEntity order = OrderEntity.builder()
                    .productEntity(cartEntity.getProduct())
                    .userEntity(UserEntity.builder().userId(userId).build())
                    .paymentsEntity(payments)
                    .quantity(cartEntity.getQuantity())
                    .build();
            orderEntityList.add(order);
        }
        orderRepository.saveAll(orderEntityList);

    }

    public List<PaymentsListDTO> getPaymentList(int userId) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PaymentsEntity> paymentsEntityPage = paymentsRepository.findByUser_UserId(userId, pageable);

        List<PaymentsEntity> list = paymentsEntityPage.get().toList();

        List<PaymentsListDTO> result = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            PaymentsEntity paymentsEntity = list.get(i);
            PaymentsListDTO paymentsListDTO = PaymentsListDTO.builder()
                    .merchantId(paymentsEntity.getMerchantId())
                    .pg(paymentsEntity.getPg())
                    .approvalNumber(paymentsEntity.getApprovalNumber())
                    .cardName(paymentsEntity.getCardName())
                    .cardNumber(paymentsEntity.getCardNumber())
                    .impUid(paymentsEntity.getImpUid())
                    .payMethod(paymentsEntity.getPayMethod())
                    .payName(paymentsEntity.getPayName())
                    .payStatus(paymentsEntity.getPayStatus())
                    .payAmount(paymentsEntity.getPayAmount())
                    .installmentMonths(paymentsEntity.getInstallmentMonths())
                    .updatedAt(paymentsEntity.getUpdatedAt())
                    .createdAt(paymentsEntity.getCreatedAt())
                    .userId(paymentsEntity.getUser().getUserId())
                    .build();
            result.add(paymentsListDTO);
        }
        return result;
    }

    public void cancelPayLog(int id, String iamUid) throws IamportResponseException, IOException {
        Optional<PaymentsEntity> payments = paymentsRepository.findById(id);
        if (payments.isPresent()) {
            PaymentsEntity paymentsEntity = payments.get();
            paymentsEntity.setPayStatus(iamportClient.paymentByImpUid(iamUid).getResponse().getStatus());

            paymentsRepository.save(paymentsEntity);
        }

    }
}