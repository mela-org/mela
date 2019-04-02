package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.stupremee.mela.util.Loggers;
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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
@SuppressWarnings({"unused", "Duplicates"})
final class MemoryConfiguration implements Configuration {

  private static final Logger log = Loggers.getLogger("MemoryConfiguration");
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
  public Configuration getSection(String path) {
    return Try.ofSupplier(() -> {
      Object def = defaults.getObject(path);
      Configuration defaults = def instanceof Configuration ? (Configuration) def
          : new MemoryConfiguration(defaultsOrEmpty(path));
      return get(path, defaults).getOrNull();
    }).onFailure(errorHandler("Error while getting getSection.")).getOrElse(Configuration.empty());
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
      map.put(path, val);
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
    return res.onFailure(errorHandler("Error while getting getObject.")).toOption();
  }

  @NotNull
  @Override
  @SuppressWarnings("unchecked")
  public Collection<Object> getList(String path) {
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
  public Option<Object> getObject(String path, Object def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<Object> getObjects(String path) {
    return getList(path).stream().filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Option<String> getString(String path, String def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<String> getStrings(String path) {
    return getList(path).stream().filter(Objects::nonNull)
        .filter(o -> o instanceof String)
        .map(o -> (String) o)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Option<Number> getNumber(String path, Number def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<Number> getNumbers(String path) {
    return getList(path).stream()
        .filter(Objects::nonNull)
        .filter(o -> o instanceof Number)
        .map(o -> (Number) o)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Option<Boolean> getBool(String path, Boolean def) {
    return get(path, def);
  }

  @NotNull
  @Override
  public Collection<Boolean> getBools(String path) {
    return getList(path).stream().filter(Objects::nonNull)
        .filter(o -> o instanceof Boolean)
        .map(o -> (Boolean) o)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Collection<String> getKeys() {
    return map.keySet();
  }

  @Override
  public void remove(String path) {
    map.remove(path);
  }

  @Override
  public <T> Option<T> getDefaultValue(String path) {
    return defaults.get(path);
  }

  @NotNull
  @Override
  public String writeToString(ConfigurationParser parser) throws IOException {
    var writer = new StringWriter();
    write(writer, parser);
    return writer.toString();
  }

  @Override
  public void write(Writer writer, ConfigurationParser parser) throws IOException {
    parser.serialize(map, writer);
  }

  private String child(String path) {
    int index = path.indexOf(SEPARATOR);
    return (index == -1) ? path : path.substring(index + 1);
  }

  private Configuration defaultsOrEmpty(String path) {
    return (defaults instanceof EmptyConfiguration || defaults == null) ? Configuration.empty()
        : defaults.getSection(path);
  }

  @SuppressWarnings("unchecked")
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

  static StdSerializer<MemoryConfiguration> getSerializer() {
    return new ConfigurationSerializer();
  }

  private Consumer<Throwable> errorHandler(String msg) {
    return throwable -> log.error(msg, throwable);
  }

  private static class ConfigurationSerializer extends StdSerializer<MemoryConfiguration> {

    private ConfigurationSerializer() {
      super(MemoryConfiguration.class);
    }

    @Override
    public void serialize(MemoryConfiguration value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
      gen.writeObject(value.map);
    }
  }
}
