package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.compile.CommandTree;
import com.google.inject.Inject;

import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultCommandTreeMerger extends LazySingletonProvider<CommandTree> {

  private final Set<CommandTree> trees;

  @Inject
  public DefaultCommandTreeMerger(Set<CommandTree> trees) {
    this.trees = trees;
  }

  @Override
  protected CommandTree createInstance() {
    return trees.stream()
        .reduce(CommandTree::merge)
        .orElse(CommandTree.EMPTY);
  }
}