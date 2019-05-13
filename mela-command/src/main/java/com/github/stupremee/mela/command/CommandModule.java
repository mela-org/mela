package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.annotations.Sender;
import com.github.stupremee.mela.command.providers.TextChannelProvider;
import com.github.stupremee.mela.command.providers.MemberProvider;
import com.github.stupremee.mela.command.providers.NamespaceProvider;
import com.github.stupremee.mela.command.providers.UserProvider;
import com.google.inject.Inject;
import com.sk89q.intake.parametric.AbstractModule;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 13.05.19
 */
public final class CommandModule extends AbstractModule {

  private final DiscordClient client;

  @Inject
  CommandModule(DiscordClient client) {
    this.client = client;
  }

  @Override
  protected void configure() {
    bind(DiscordClient.class)
        .toInstance(client);

    bind(User.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceProvider.create(User.class));

    bind(TextChannel.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceProvider.create(TextChannel.class));

    bind(Guild.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceProvider.create(Guild.class));

    bind(User.class)
        .toProvider(UserProvider.create(client));

    bind(Member.class)
        .toProvider(MemberProvider.create());

    bind(TextChannel.class)
        .toProvider(TextChannelProvider.create());
  }
}