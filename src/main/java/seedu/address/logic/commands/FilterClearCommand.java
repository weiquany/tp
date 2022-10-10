package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isAnyNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;

/**
 * Removes filter applied to address book based on arguments provided.
 */
public class FilterClearCommand extends FilterCommand {

    public static final String COMMAND_SPECIFIER = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_SPECIFIER
            + ": Removes filter of the specified name"
            + " and tags (case-insensitive) and displays the contacts without the filter applied \n"
            + "Parameters: " + PREFIX_NAME + "NAME ...\n" + "Example: " + COMMAND_WORD + " " + COMMAND_SPECIFIER + " "
            + PREFIX_NAME
            + "alice";

    public FilterClearCommand(FilterCommandPredicate predicate) {
        super(predicate);
    }

    public FilterClearCommand() {
        super();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        clearSpecifiedFilters(model);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Clear filters in {@code model}. Will clear all filters if {@code predicate} is {@code null}.
     *
     * @params model Model which the filter will be updated.
     */
    private void clearSpecifiedFilters(Model model) {
        if (predicate == null) {
            model.clearFiltersInFilteredPersonList();
            return;
        }
        assert isAnyNonNull(predicate.getNamePredicate(), predicate.getTagsPredicate());
        if (predicate.getNamePredicate() != null) {
            model.removeFilterFromFilteredPersonList(predicate.getNamePredicate());
        }
        if (predicate.getTagsPredicate() != null) {
            model.removeFilterFromFilteredPersonList(predicate.getTagsPredicate());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterClearCommand // instanceof handles nulls
                && predicate.equals(((FilterClearCommand) other).predicate)); // state check
    }
}