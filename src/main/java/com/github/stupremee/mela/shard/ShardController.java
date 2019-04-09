package com.github.stupremee.mela.shard;

import com.github.stupremee.mela.event.EventDispatcher;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.ApplicationInfo;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildEmoji;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.UserEditSpec;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
@SuppressWarnings("unused")
public interface ShardController {

  /**
   * Updates the presence for all shards.
   *
   * @param presence The presence that should be placed
   */
  void updatePresence(@Nonnull Presence presence);

  /**
   * Logs in all shards and set the default presences.
   *
   * @return A {@link Flux} that contains all shards.
   */
  @Nonnull
  Flux<DiscordClient> login();

  /**
   * Tries to find the shard with the given id and starts it.
   *
   * @param shard The shard to start
   */
  void login(int shard);

  /**
   * Logs out all shards.
   *
   * @return A {@link Flux} that contains all logged out shards
   */
  @Nonnull
  Flux<DiscordClient> logout();

  /**
   * Tries to find the shard with the given id and stops it.
   *
   * @param shard The shard to stop
   */
  void logout(@Nonnegative int shard);

  /**
   * Restarts all shards that are connected and starts all shards that are not connected.
   */
  void restart();

  /**
   * Restarts the shard with the given id.
   *
   * @param shard The id of the shard that will be restarted
   */
  void restart(@Nonnegative int shard);

  /**
   * Tries to retrieve a user and returns it in a {@link Mono}.
   *
   * @param id The id of the user
   * @return The {@link Mono} contains the fetched user, that completes when the user is fetched.
   */
  @Nonnull
  Mono<User> getUserById(@Nonnull Snowflake id);

  /**
   * Tries to retrieve a {@link Channel} and returns it in a {@link Mono}.
   *
   * @param id The id of the channel
   * @return The {@link Mono} contains the fetched channel, that completes when the user is fetched.
   */
  @Nonnull
  Mono<Channel> getChannelById(@Nonnull Snowflake id);

  /**
   * Tries to retrieve a {@link Guild} and returns it in a {@link Mono}.
   *
   * @param id The id of the guild
   * @return The {@link Mono} contains the fetched guild, that completes when the user is fetched.
   */
  @Nonnull
  Mono<Guild> getGuildById(@Nonnull Snowflake id);

  /**
   * Tries to retrieve a {@link Member} from his id and his guildId.
   *
   * @param guildId The guild id
   * @param userId The user id
   * @return The {@link Mono} that contains the {@link Member} and that completes when the member is
   *     fetched
   */
  @Nonnull
  Mono<Member> getMemberById(@Nonnull Snowflake guildId, @Nonnull Snowflake userId);

  /**
   * Tries to retrieve a message from the channel id and the message id.
   *
   * @param channelId The channel id
   * @param messageId The message id
   * @return The {@link Mono} that contains the {@link Message} and is completed when the message is
   *     successfully fetched
   */
  @Nonnull
  Mono<Message> getMessageById(@Nonnull Snowflake channelId, @Nonnull Snowflake messageId);

  /**
   * Tries to retrieve a {@link GuildEmoji} by the id.
   *
   * @param id The id of the emote
   * @return A {@link Mono} where, upon successful completion, emits the {@link GuildEmoji *
   *     application info}. If an error is received, it is emitted through the Mono.
   */
  @Nonnull
  Mono<GuildEmoji> getEmoteById(@Nonnull Snowflake id);

  /**
   * Tries to retrieve a {@link Role} by his id.
   *
   * @param id The role id
   * @return A {@link Mono} where, upon successful completion, emits the {@link Role * * application
   *     info}. If an error is received, it is emitted through the Mono.
   */
  @Nonnull
  Mono<Role> getRoleById(@Nonnull Snowflake id);

  /**
   * Requests to edit the current user that is logged in.
   *
   * @param spec A {@link Consumer} that provides a "blank" {@link UserEditSpec} to be operated
   *     on.
   * @return A {@link Mono} where, upon successful completion, emits the edited {@link User}. If an
   *     error is received, * it is emitted through the {@code Mono}.
   */
  @Nonnull
  Mono<User> edit(@Nonnull Consumer<? super UserEditSpec> spec);

  /**
   * Requests to retrieve the application info.
   *
   * @return A {@link Mono} where, upon successful completion, emits the {@link ApplicationInfo
   *     application info}. If an error is received, it is emitted through the Mono.
   */
  @Nonnull
  Mono<ApplicationInfo> getApplicationInfo();

  /**
   * Calculates the average of the amount of time it last took Discord Gateway to respond to a
   * heartbeat with an ack from all shards that are connected.
   *
   * @return The average ping
   */
  double getAveragePing();

  /**
   * Returns the {@link EventDispatcher} that subscribes events to all shards.
   *
   * @return The {@link EventDispatcher}
   */
  @Nonnull
  EventDispatcher getEventDispatcher();

  /**
   * Counts all shards that are currently connected.
   *
   * @return The count of connected shards
   */
  int getConnectedShards();

  /**
   * Returns all guilds of all shards in a {@link Flux}.
   *
   * @return A {@link Flux} that contains all guilds
   */
  @Nonnull
  Flux<Guild> getGuilds();

  /**
   * Returns all users from all shards in a {@link Flux}.
   *
   * @return A {@link Flux} that contains all users
   */
  @Nonnull
  Flux<User> getUsers();

  /**
   * Returns all {@link DiscordClient shards} in a {@link Flux}.
   *
   * @return All {@link DiscordClient shards} in a {@link Flux}
   */
  @Nonnull
  Flux<DiscordClient> getShards();

  /**
   * Returns the {@link DiscordClient shard} in an {@link Optional} with the given id.
   *
   * @param id The id of the shard
   * @return A {@link Optional} that contains the shard or is empty if the shard with this id
   *     doesn't exists
   */
  @Nonnull
  Optional<DiscordClient> getShardById(@Nonnegative int id);
}
