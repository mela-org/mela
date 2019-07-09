package com.github.stupremee.mela.command.bind.internal;

import com.github.stupremee.mela.command.bind.CommandBindingNode;
import com.github.stupremee.mela.command.bind.ExceptionBindingBuilder;
import com.github.stupremee.mela.command.bind.InterceptorBindingBuilder;
import com.github.stupremee.mela.command.bind.ParameterBindingBuilder;
import com.github.stupremee.mela.command.bind.tree.CommandTree;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalCommandBindingNode implements CommandBindingNode {

  private final InternalCommandBindingNode parent;
  private final CommandMultibinder multibinder;
  private final InjectableCommandTree tree;

  InternalCommandBindingNode(CommandMultibinder multibinder) {
    this.parent = null;
    this.multibinder = multibinder;
    this.tree = new InjectableCommandTree();
    Multibinder.newSetBinder(this.multibinder.binder(), CommandTree.class).addBinding().toInstance(tree);
  }

  private InternalCommandBindingNode(InternalCommandBindingNode parent) {
    this.parent = parent;
    this.multibinder = parent.multibinder;
    this.tree = parent.tree;
  }

  @Nonnull
  @Override
  public CommandBindingNode group(@Nonnull String... aliases) {
    checkNotNull(aliases);
    CommandTree.Group group = tree.createChildIfNotExists(Set.of(aliases));
    tree.stepDown(group);
    return new InternalCommandBindingNode(this);
  }

  @Override
  public CommandBindingNode parent() {
    checkState(parent != null, "Cannot go to parent, this is the highest node");
    return parent;
  }

  @Nonnull
  @Override
  public CommandBindingNode add(@Nonnull Class<?> commandClass) {
    checkNotNull(commandClass);
    tree.addCommand(commandClass);
    multibinder.commandObjectBinder().addBinding().to(commandClass);
    return this;
  }

  @Nonnull
  @Override
  public CommandBindingNode add(@Nonnull Object command) {
    checkNotNull(command);
    tree.addCommand(command.getClass());
    multibinder.commandObjectBinder().addBinding().toInstance(command);
    return this;
  }

  @Nonnull
  @Override
  public <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(@Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return new InternalInterceptorBindingBuilder<>(this, tree, multibinder.interceptorBinder(), annotationType);
  }

  @Nonnull
  @Override
  public <T extends Throwable> ExceptionBindingBuilder<T> handle(@Nonnull Class<T> exceptionType) {
    checkNotNull(exceptionType);
    return new InternalExceptionBindingBuilder<>(this, tree, multibinder.handlerBinder(), exceptionType);
  }

  @Nonnull
  @Override
  public <T> ParameterBindingBuilder<T> bindParameter(@Nonnull Class<T> parameterType) {
    return bindParameter(TypeLiteral.get(parameterType));
  }

  @Nonnull
  @Override
  public <T> ParameterBindingBuilder<T> bindParameter(@Nonnull TypeLiteral<T> literal) {
    checkNotNull(literal);
    return new InternalParameterBindingBuilder<>(this, tree, multibinder.mapperBinder(), literal);
  }

}