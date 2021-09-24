package org.prgrms.kdt.command;

import org.prgrms.kdt.command.controller.CommandController;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements Runnable {

    private static final String INPUT_PROMPT = "> ";

    private final Input input;
    private final Output output;
    private final CommandController commandController;

    public CommandLineApplication(Console console, CommandController commandController) {
        this.input = console;
        this.output = console;
        this.commandController = commandController;
    }

    @Override
    public void run() {
        while (true) {
            output.printOnStart(); // command 설명
            try {
                if (!execute()) break;
            } catch (Exception e) {
                output.printInputError();
            }
        }
    }

    private boolean execute() {
        String inputCommandType = input.inputString(INPUT_PROMPT);

        return commandController.getCommandService(inputCommandType).execute();
    }
}