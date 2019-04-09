package com.github.stupremee.mela.mongo;

import com.github.stupremee.mela.mongo.beans.MongoCollectionBean;
import com.github.stupremee.mela.mongo.beans.MongoDatabaseBean;
import com.github.stupremee.mela.repository.RepositoryFactory;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import javax.annotation.Nonnull;
import reactor.core.publisher.Flux;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 07.04.2019
 */
@SuppressWarnings("unused")
public interface MongoDB extends RepositoryFactory {

  @Nonnull
  MongoClient getClient();

  @Nonnull
  Flux<MongoDatabaseBean> listDatabases();

  @Nonnull
  Flux<MongoCollectionBean> listCollections(@Nonnull String database);

  @Nonnull
  MongoDatabase getDatabase(@Nonnull String databaseName);

  @Nonnull
  Flux<MongoCollectionBean> getCollections(@Nonnull String database);

  @Nonnull
  <T> MongoCollection<T> getCollection(@Nonnull String database, @Nonnull String collection);

  @Nonnull
  <T> MongoCollection<T> getCollection(@Nonnull String database, @Nonnull String collection,
      @Nonnull Class<T> type);

  void close();
}
