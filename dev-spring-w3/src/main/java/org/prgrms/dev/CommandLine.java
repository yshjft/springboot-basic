package org.prgrms.dev;

import org.prgrms.dev.command.CommandType;
import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.InputConsole;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.io.OutputConsole;
import org.prgrms.dev.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

public class CommandLine implements Runnable {
    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;

    public CommandLine(InputConsole input, OutputConsole output, ApplicationContext applicationContext) {
        this.input = input;
        this.output = output;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        while (execute(voucherService)) ;
    }

    private boolean execute(VoucherService voucherService) {
        boolean flag = true;

        try {
            output.init();
            String inputCommandType = input.inputCommandType("> ");
            flag = CommandType.execute(inputCommandType, input, output, voucherService);
        } catch (IllegalArgumentException e) {
            output.invalidCommandTypeInput();
        }

        return flag;
    }
}