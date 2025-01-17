package org.prgms.voucheradmin.domain.administrator.controller;

import static org.prgms.voucheradmin.domain.console.enums.CommandAboutCustomer.CREATE;
import static org.prgms.voucheradmin.domain.console.enums.CommandAboutCustomer.UPDATE;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.console.enums.CommandAboutVoucherWallet;
import org.prgms.voucheradmin.domain.console.service.InputService;
import org.prgms.voucheradmin.domain.console.service.OutputService;
import org.prgms.voucheradmin.domain.console.enums.Command;
import org.prgms.voucheradmin.domain.console.enums.CommandAboutCustomer;
import org.prgms.voucheradmin.domain.console.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.customer.dto.BlacklistCustomerDto;
import org.prgms.voucheradmin.domain.customer.dto.CustomerCreateReqDto;
import org.prgms.voucheradmin.domain.customer.dto.CustomerUpdateReqDto;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
import org.prgms.voucheradmin.domain.voucherwallet.service.VoucherWalletService;
import org.prgms.voucheradmin.global.exception.customexception.WrongInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 로직 수행을 위해 필요한 메서드를 호출하는 클래스 입니다.
 */
@Component
public class Administrator {
    private final static Logger logger = LoggerFactory.getLogger(Administrator.class);

    private final OutputService outputService;
    private final InputService inputService;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final VoucherWalletService voucherWalletService;

    public Administrator(OutputService outputService, InputService inputService, VoucherService voucherService, CustomerService customerService, VoucherWalletService voucherWalletService) {
        this.outputService = outputService;
        this.inputService = inputService;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.voucherWalletService = voucherWalletService;
    }

    /**
     * 어플리케이션을 시작하는 메서드입니다.
     **/
    public void run() {
        while (true) {
            try {
                outputService.showCommandList();
                Command command = inputService.selectCommand();

                switch (command) {
                    case VOUCHER:
                        outputService.showCommandAboutVoucher();
                        CommandAboutVoucher commandAboutVoucher = inputService.selectCommandAboutVoucher();
                        doCommandAboutVoucher(commandAboutVoucher);
                        break;
                    case CUSTOMER:
                        outputService.showCommandAboutCustomer();
                        CommandAboutCustomer commandAboutCustomer = inputService.selectCommandAboutCustomer();
                        doCommandAboutCustomer(commandAboutCustomer);
                        break;
                    case VOUCHER_WALLET:
                        outputService.showCommandAboutVoucherWallet();
                        CommandAboutVoucherWallet commandAboutVoucherWallet = inputService.selectCommandAboutVoucherWallet();
                        doCommandAboutVoucherWallet(commandAboutVoucherWallet);
                        break;
                    case BLACKLIST:
                        List<BlacklistCustomerDto> blackListedCustomers = customerService.getBlackList();
                        outputService.showBlacklist(blackListedCustomers);
                        break;
                    case EXIT:
                        return;
                    default:
                        throw new WrongInputException();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 바우처에서 선택된 추가 명령에 따라 필요한 메서드 실행을 담당하는 메서드입니다.
     **/
    private void doCommandAboutVoucher(CommandAboutVoucher commandAboutVoucher) throws IOException {
        switch (commandAboutVoucher) {
            case CREATE:
                outputService.showVoucherType();
                VoucherType voucherTypeForCreate = inputService.selectVoucherType();
                long amountForCreate = inputService.inputAmount(voucherTypeForCreate);

                Voucher createdVoucher = voucherService.createVoucher(new VoucherReqDto(voucherTypeForCreate, amountForCreate));
                outputService.showVoucher(createdVoucher, CommandAboutVoucher.CREATE);
                break;
            case READ:
                List<Voucher> vouchers = voucherService.getVouchers();
                outputService.showVoucherList(vouchers);
                break;
            case UPDATE:
                UUID voucherIdForUpdate = inputService.inputVoucherId();
                outputService.showVoucherType();
                VoucherType voucherTypeForUpdate = inputService.selectVoucherType();
                long amountForUpdate = inputService.inputAmount(voucherTypeForUpdate);

                Voucher updatedVoucher = voucherService.updateVoucher(voucherIdForUpdate, new VoucherReqDto(voucherTypeForUpdate, amountForUpdate));
                outputService.showVoucher(updatedVoucher, CommandAboutVoucher.UPDATE);
                break;
            case DELETE:
                UUID voucherIdForDelete = inputService.inputVoucherId();
                voucherService.deleteVoucher(voucherIdForDelete);
                break;
            default:
                throw new WrongInputException();
        }
    }

    /**
     * 고객에서 선택된 추가 명령에 따라 필요한 메서드 실행을 담당하는 메서드입니다.
     **/
    private void doCommandAboutCustomer(CommandAboutCustomer commandAboutCustomer) throws IOException{
        switch (commandAboutCustomer) {
            case CREATE:
                String nameForCreate = inputService.inputName();
                String emailForCreate = inputService.inputEmail();

                Customer createdCustomer = customerService.createCustomer(new CustomerCreateReqDto(nameForCreate, emailForCreate));
                outputService.showCustomer(createdCustomer, CREATE);
                break;
            case READ:
                List<Customer> customers = customerService.getCustomers();
                outputService.showCustomerList(customers);
                break;
            case UPDATE:
                UUID customerIdForUpdate = inputService.inputCustomerId();
                String nameForUpdate = inputService.inputName();

                Customer updatedCustomer = customerService.updateCustomer(customerIdForUpdate, new CustomerUpdateReqDto(nameForUpdate));
                outputService.showCustomer(updatedCustomer, UPDATE);
                break;
            case DELETE:
                UUID customerIdForDelete = inputService.inputCustomerId();
                customerService.deleteCustomer(customerIdForDelete);
                break;
            default:
                throw new WrongInputException();
        }
    }

    /**
     * 바우처 지갑에서 선택된 추가 명령에 따라 필요한 메서드 실행을 담당하는 메서드입니다.
     **/
    private void doCommandAboutVoucherWallet(CommandAboutVoucherWallet commandAboutVoucherWallet) throws IOException{
        switch (commandAboutVoucherWallet) {
            case ALLOCATE_VOUCHER:
                UUID customerId = inputService.inputCustomerId();
                UUID voucherId = inputService.inputVoucherId();

                VoucherWallet createdVoucherWallet = voucherWalletService.createVoucherWallet(customerId, voucherId);
                outputService.showVoucherWallet(createdVoucherWallet);
                break;
            case FIND_ALLOCATED_VOUCHER:
                UUID customerIdForAllocatedVoucher = inputService.inputCustomerId();
                List<Voucher> vouchers = voucherWalletService.getAllocatedVouchers(customerIdForAllocatedVoucher);
                outputService.showVoucherList(vouchers);
                break;
            case FIND_VOUCHER_OWNER:
                UUID voucherIdForVoucherOwner = inputService.inputVoucherId();
                List<Customer> customers = voucherWalletService.getVoucherOwners(voucherIdForVoucherOwner);
                outputService.showCustomerList(customers);
                break;
            case DELETE_CUSTOMER_VOUCHER:
                UUID customerIdForDelete = inputService.inputCustomerId();
                UUID voucherIdForDelete = inputService.inputVoucherId();
                voucherWalletService.deleteVoucherWallet(customerIdForDelete, voucherIdForDelete);
                break;
            default:
                throw new WrongInputException();
        }
    }

}
