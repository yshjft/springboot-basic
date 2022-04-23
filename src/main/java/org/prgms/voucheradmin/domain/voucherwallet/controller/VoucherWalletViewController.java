package org.prgms.voucheradmin.domain.voucherwallet.controller;

import org.prgms.voucheradmin.domain.customer.dto.CustomerCreateReqDto;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.prgms.voucheradmin.domain.voucherwallet.service.VoucherWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherWalletViewController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherWalletViewController.class);

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherWalletService voucherWalletService;

    public VoucherWalletViewController(CustomerService customerService, VoucherService voucherService, VoucherWalletService voucherWalletService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.voucherWalletService = voucherWalletService;
    }

    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        logger.error(ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        return "views/error";
    }

    @GetMapping("/voucher-wallets")
    public String viewVoucherWalletMainPage(Model model) throws IOException {
        List<Customer> allCustomers = customerService.getCustomers();
        model.addAttribute("customers", allCustomers);

        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", allVouchers);

        return "views/voucher-wallet/voucher-wallet-main";
    }

    @GetMapping("/voucher-wallets/{customerId}")
    public String viewAllocatedVouchers(@PathVariable UUID customerId, Model model) {
        Customer customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);

        List<Voucher> allocatedVouchers = voucherWalletService.getAllocatedVouchers(customerId);
        model.addAttribute("allocatedVouchers", allocatedVouchers);

        return "views/voucher-wallet/allocated-vouchers";
    }

    // @PostMapping("/voucher-wallets/{customerId}")

}
