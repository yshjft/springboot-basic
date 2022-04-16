package org.prgms.voucheradmin.domain.administrator;

import org.prgms.voucheradmin.domain.console.service.enums.Command;
import org.prgms.voucheradmin.domain.console.service.ConsoleService;
import org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.customer.dto.CustomerDto;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherCreateReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 로직 수행을 위해 필요한 메서드를 호출하는 클래스 입니다.
 */
@Component
public class Administrator {
    private final static Logger logger = LoggerFactory.getLogger(Administrator.class);

    private final ConsoleService consoleService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public Administrator(ConsoleService consoleService, VoucherService voucherService, CustomerService customerService) {
        this.consoleService = consoleService;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    /**
     * 어플리케이션을 시작하는 메서드입니다.
     **/
    public void run() {
        while (true) {
            try {
                consoleService.showCommandList();
                Command command = consoleService.selectCommand();

                switch (command) {
                    case VOUCHER:
                        consoleService.showCommandAboutVoucher();
                        CommandAboutVoucher commandAboutVoucher = consoleService.selectCommandAboutVoucher();
                        doCommandAboutVoucher(commandAboutVoucher);
                        break;
                    case CUSTOMER:
                        break;
                    case BLACKLIST:
                        List<CustomerDto> blackListedCustomers = customerService.getBlackList();
                        consoleService.showBlacklist(blackListedCustomers);
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void doCommandAboutVoucher(CommandAboutVoucher commandAboutVoucher) throws IOException {
        switch (commandAboutVoucher) {
            case CREATE:
                consoleService.showVoucherType();
                VoucherType voucherType = consoleService.selectVoucherType();
                VoucherCreateReqDto voucherCreateReqDto = consoleService.inputAmount(voucherType);
                Voucher createdVoucher = voucherService.createVoucher(voucherCreateReqDto);
                consoleService.showVoucherCreated(createdVoucher);
                break;
            case READ:
                List<Voucher> vouchers = voucherService.getVouchers();
                consoleService.showVoucherList(vouchers);
        }
    }

}
