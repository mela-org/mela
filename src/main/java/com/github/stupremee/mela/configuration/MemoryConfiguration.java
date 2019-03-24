package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import io.vavr.control.Try;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
@SuppressWarnings({"unused", "Duplicates"})
final class MemoryConfiguration implements Configuration {

  private final Logger log = Loggers.getLogger(MemoryConfiguration.class);
  private final Map<String, Object> map;
  private final Configuration defaults;

  MemoryConfiguration(@NotNull Configuration defaults) {
    this(new LinkedHashMap<>(), defaults);
  }

  MemoryConfiguration(@NotNull Map<String, Object> map,
      @NotNull Configuration defaults) {
    this.map = map;
    this.defaults = defaults;
  }

  @NotNull
  @Override
  public Configuration section(String path) {
    return Try.ofSupplier(() -> {
      Object def = defaults.object(path);
      Configuration defaults = def instanceof Configuration ? (Configuration) def
          : new MemoryConfiguration(defaultsOrEmpty(path));
      return get(path, defaults).getOrNull();
    }).onFailure(errorHandler("Error while getting section.")).getOrElse(Configuration.empty());
  }

  @Override
  @SuppressWarnings("unchecked")
  public void set(String path, Object value) {
    Object val = value;

    if (val.getClass().isArray()) {
      val = Arrays.asList((Object[]) value);
    }

    if (val instanceof Map) {
      val = new MemoryConfiguration((Map) val, defaultsOrEmpty(path));
    }

    Configuration section = sectionFor(path);
    if (section == this) {
      if (val == null) {
        remove(path);
      } else {
        map.put(path, val);
      }
    } else {
      section.set(child(path), val);
    }
  }

  @NotNull
  @Override
  @SuppressWarnings("unchecked")
  public <T> Option<T> get(String path, T def) {
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
    return res.onFailure(errorHandler("Error while getting object.")).toOption();
  }

  @NotNull
  @Override
  @SuppressWarnings("unchecked")
  public Collection<Object> list(String path) {
    Object value = get(path);
    if (value instanceof Option.Some && ((Option) value).get() instanceof List<?>) {
      return (List<Object>) ((Option) value).get();
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public boolean exists(String path) {
    return !get(path, null).isEmpty();
  }

  @NotNull
  @Override
  public Option<Object> object(String path, Object def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<Object> objects(String path) {
    return list(path).stream().filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Option<String> string(String path, String def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<String> strings(String path) {
    return list(path).stream().filter(Objects::nonNull)
        .filter(o -> o instanceof String)
        .map(o -> (String) o)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Option<Number> number(String path, Number def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<Number> numbers(String path) {
    var res = list(path).stream()
        .filter(Objects::nonNull)
        .filter(o -> o instanceof Number)
        .map(o -> (Number) o)
        .collect(Collectors.toList());
    return res;
  }

  @NotNull
  @Override
  public Option<Boolean> bool(String path, Boolean def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<Boolean> bools(String path) {
    return list(path).stream().filter(Objects::nonNull)
        .filter(o -> o instanceof Boolean)
        .map(o -> (Boolean) o)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Collection<String> keys() {
    return map.keySet();
  }

  @Override
  public void remove(String path) {
    map.remove(path);
  }

  @Override
  public <T> Option<T> defaultValue(String path) {
    return defaults.get(path);
  }

  private String child(String path) {
    int index = path.indexOf(SEPARATOR);
    return (index == -1) ? path : path.substring(index + 1);
  }

  private Configuration defaultsOrEmpty(String path) {
    return (defaults instanceof EmptyConfiguration || defaults == null) ? Configuration.empty()
        : defaults.section(path);
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

    return (Configuration) section;
  }

  private Consumer<Throwable> errorHandler(String msg) {
    return throwable -> log.error(msg, throwable);
  }
}
