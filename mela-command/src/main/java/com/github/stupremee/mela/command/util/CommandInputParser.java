package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandGroup;

import java.util.function.Predicate;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandInputParser {

  private String currentWord;
  private String remaining;

  private CommandGroup group;
  private CommandCallable callable;
  private String arguments;

  public CommandInputParser(CommandGroup root, String input) {
    this.remaining = input.trim();
    this.currentWord = "";
    this.group = root;
  }

  public CommandInput parse() {
    stripGroup();
    stripCallable();
    return new CommandInput(group, callable, remaining);
  }

  private void stripGroup() {
    while (!remaining.isEmpty()) {
      nextWord();
      CommandGroup child = findIn(group.getChildren(), (group) -> group.getNames().contains(currentWord));
      if (child == null)
        return;
      group = child;
    }
  }

  private void stripCallable() {
    nextWord();
    CommandCallable labelledCallable =
        findIn(group.getCommands(), (command) -> command.getLabels().contains(currentWord));
    callable = labelledCallable == null
        ? findIn(group.getCommands(), (command) -> command.getLabels().isEmpty())
        : labelledCallable;
  }

  private void nextWord() {
    remaining = remaining.substring(currentWord.length()).trim();
    int spaceIndex = remaining.indexOf(' ');
    currentWord = spaceIndex == -1 ? remaining : remaining.substring(0, spaceIndex);
  }

  private <T> T findIn(Iterable<T> iterable, Predicate<T> predicate) {
    for (T t : iterable) {
      if (predicate.test(t)) {
        return t;
      }
    }
    return null;
  }

  public static CommandInput parse(CommandGroup root, String input) {
    return new CommandInputParser(root, input).parse();
  }

}
