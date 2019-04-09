package com.github.stupremee.mela.configuration;

import com.github.stupremee.mela.util.Loggers;
import com.google.common.base.Preconditions;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
@SuppressWarnings({"unused", "Duplicates", "unchecked"})
final class MemoryConfiguration implements Configuration {

  private static final Logger LOGGER = Loggers.getLogger("MemoryConfiguration");
  private final Map<String, Object> map;
  private final Configuration defaults;

  MemoryConfiguration(@Nonnull Configuration defaults) {
    this(new LinkedHashMap<>(), defaults);
  }

  MemoryConfiguration(@Nonnull Map<String, Object> map,
      @Nonnull Configuration defaults) {
    Preconditions.checkNotNull(map, "map can't be null.");
    Preconditions.checkNotNull(defaults, "defaults can't be null.");

    this.map = map;
    this.defaults = defaults;
  }

  @NotNull
  @Nonnull
  @Override
  public Configuration getSection(@NotNull String path) {
    Preconditions.checkNotNull(path, "path can't be null.");

    return Try.ofSupplier(() -> {
      Object def = defaults.getObject(path);
      Configuration defaults = def instanceof Configuration ? (Configuration) def
          : new MemoryConfiguration(defaultsOrEmpty(path));
      return get(path, defaults).getOrNull();
    }).onFailure(errorHandler("Error while getting getSection.")).getOrElse(Configuration.empty());
  }

  @Override
  public void set(@NotNull String path, @NotNull Object value) {
    Preconditions.checkNotNull(path, "path can't be null.");
    Preconditions.checkNotNull(value, "value can't be null.");

    Object val = value;

    if (val.getClass().isArray()) {
      val = Arrays.asList((Object[]) value);
    }

    if (val instanceof Map) {
      val = new MemoryConfiguration((Map) val, defaultsOrEmpty(path));
    }

    Configuration section = sectionFor(path);
    if (section == this) {
      map.put(path, val);
    } else {
      section.set(child(path), val);
    }
  }

  @Nonnull
  @Override
  public <T> Option<T> get(@NotNull String path, @Nullable T def) {
    Configuration section = sectionFor(path);

    Object value;
    if (section == this) {
      value = map.get(path);
    } else {
      value = section.get(child(path), def).getOrNull();
    }

    if (value == null && def instanceof Configuration) {
      map.put(path, def);
    }

    Try<T> res;
    if (def == null && value == null) {
      return Option.none();
    } else if (def == null) {
      res = Try.ofSupplier(() -> (T) value);
    } else {
      res = Try.ofSupplier(() -> value == null ? def : (T) value);
    }
    return res.onFailure(errorHandler("Error while getting getObject.")).toOption();
  }

  @Nonnull
  @Override
  public Collection<Object> getList(@NotNull String path) {
    Object value = get(path);
    if (value instanceof Option.Some && ((Option) value).get() instanceof List<?>) {
      return (List<Object>) ((Option) value).get();
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public boolean exists(@NotNull String path) {
    return !get(path).isEmpty();
  }

  @Nonnull
  @Override
  public Option<Object> getObject(@NotNull String path, @Nullable Object def) {
    return get(path, def);
  }

  @Nonnull
  @Override
  public Collection<Object> getObjects(@NotNull String path) {
    return getList(path).stream().filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Nonnull
  @Override
  public Option<String> getString(@NotNull String path, @Nullable String def) {
    return get(path, def);
  }

  @Nonnull
  @Override
  public Collection<String> getStrings(@NotNull String path) {
    return getList(path).stream().filter(Objects::nonNull)
        .filter(o -> o instanceof String)
        .map(o -> (String) o)
        .collect(Collectors.toList());
  }

  @Nonnull
  @Override
  public Option<Number> getNumber(@NotNull String path, @Nullable Number def) {
    return get(path, def);
  }

  @Nonnull
  @Override
  public Collection<Number> getNumbers(@NotNull String path) {
    return getList(path).stream()
        .filter(Objects::nonNull)
        .filter(o -> o instanceof Number)
        .map(o -> (Number) o)
        .collect(Collectors.toList());
  }

  @Nonnull
  @Override
  public Option<Boolean> getBool(@NotNull String path, @Nullable Boolean def) {
    return get(path, def);
  }

  @NotNull
  @Nonnull
  @Override
  public Collection<Boolean> getBools(@NotNull String path) {
    return getList(path).stream().filter(Objects::nonNull)
        .filter(o -> o instanceof Boolean)
        .map(o -> (Boolean) o)
        .collect(Collectors.toList());
  }

  @Nonnull
  @Override
  public Collection<String> getKeys() {
    return map.keySet();
  }

  @Override
  public void remove(@NotNull String path) {
    map.remove(path);
  }

  @Override
  public <T> Option<T> getDefaultValue(@NotNull String path) {
    return defaults.get(path);
  }

  @Nonnull
  @Override
  public String writeToString(@NotNull ConfigurationParser parser) throws IOException {
    var writer = new StringWriter();
    write(writer, parser);
    return writer.toString();
  }

  @Override
  public void write(@NotNull Writer writer, @NotNull ConfigurationParser parser)
      throws IOException {
    Map<String, Object> mapToParse = new LinkedHashMap<>(map);
    replaceConfiguration(mapToParse);
    parser.serialize(mapToParse, writer);
  }

  private void replaceConfiguration(Map<String, Object> map) {
    map.forEach((key, value) -> {
      if (value instanceof MemoryConfiguration) {
        map.put(key, ((MemoryConfiguration) value).map);
      }
    });
  }

  private String child(String path) {
    int index = path.indexOf(SEPARATOR);
    return (index == -1) ? path : path.substring(index + 1);
  }

  private Configuration defaultsOrEmpty(String path) {
    return (defaults instanceof EmptyConfiguration || defaults == null) ? Configuration.empty()
        : defaults.getSection(path);
  }

  private Configuration sectionFor(String path) {
    int index = path.indexOf(SEPARATOR);
    if (index == -1) {
      return this;
    }

    String root = path.substring(0, index);
    Object section = map.get(root);
    if (section == null) {
      section = new MemoryConfiguration(defaultsOrEmpty(root));
      map.put(root, section);
    }

    if (section instanceof Map) {
      section = new MemoryConfiguration((Map<String, Object>) section,
          EmptyConfiguration.instance());
    }

    return (Configuration) section;
  }

  private Consumer<Throwable> errorHandler(String msg) {
    return throwable -> LOGGER.error(msg, throwable);
  }
}
