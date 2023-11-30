package com.miniproject.domain.payment.service;

import com.miniproject.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentQueryService implements PaymentQueryUseCase {

    private final PaymentRepository paymentRepository;

}
