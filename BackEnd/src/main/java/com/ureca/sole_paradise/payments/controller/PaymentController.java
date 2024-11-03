package com.ureca.sole_paradise.payments.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.ureca.sole_paradise.payments.db.dto.PaymentsListDTO;
import com.ureca.sole_paradise.payments.db.entity.PaymentsEntity;
import com.ureca.sole_paradise.payments.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentsService paymentsService;

    @PostMapping("/list/{iamUid}/{userId}")
    public ResponseEntity<?> paymentResult(@PathVariable(name = "iamUid") String iamUid, @PathVariable(name = "userId") int userId) throws IamportResponseException, IOException {
        paymentsService.createPayLog(iamUid, userId);
        return null;
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<PaymentsListDTO>> getPaymentLogList(@PathVariable(name = "userId") int userId) {
        return new ResponseEntity<>(paymentsService.getPaymentList(userId), HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> paymentCancel(@RequestParam(value = "id") int id,
                                           @RequestParam(value = "iamUid") String iamUid) throws IamportResponseException, IOException {
        paymentsService.cancelPayLog(id, iamUid);

        return null;
    }

}
