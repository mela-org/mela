package com.github.stupremee.mela.command.bind.process;

import com.github.stupremee.mela.command.parse.Arguments;

import javax.annotation.Nonnull;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ArgumentChain {

  private final Arguments arguments;

  private int index;

  public ArgumentChain(@Nonnull Arguments arguments) {
    this.arguments = checkNotNull(arguments);
    this.index = 0;
  }

  public boolean hasNext() {
    return index < arguments.size();
  }

  @Nonnull
  public String consume() {
    if (index >= arguments.size()) {
      throw new NoSuchElementException("No argument present for index " + index);
    }
    return arguments.get(index++);
  }

  public void back() {
    checkState(index > 0, "");
    --index;
  }

  @Nonnull
  public Arguments getArguments() {
    return arguments;
  }
}
