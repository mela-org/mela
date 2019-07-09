# Config

This module is for creating config files which can be used to get input from a user.

## Installation

Coming soon...

## Usage

We use [Guice](https://github.com/google/guice) for Dependency Injection.
So you need to know the basics of Guice to use this module efficiently.

### Creating a config

```java
// Create a json config provider from a given file
ConfigProvider jsonConfigProvider = JsonConfigProvider.of(new File("config.json"));
// There is also a YamlConfigProvider that will parse the config as yaml
ConfigProvider yamlConfigProvider = YamlConfigProvider.of(new File("config.yaml"));
// You can also create a config provider directly from the content
jsonConfigProvider = JsonConfigProvider.of("Your json content goes here");
// Create a config module that will be installed into the Injector
Module configModule = ConfigModule.of(jsonConfigProvider);
// Create a Injector
Injector injector = Guice.createInjector(configModule);
// Now you can just create a config via the injector
Config config = injector.getInstance(Config.class);
```

### Accessing values

```java
// You can access values in a bukkit like style
// The get* methods will return an empty Optional if the path does not exists
// or the value at the given path can't be converted to the wanted type
config.getString("some.path");
config.getNumber("some.number");
// The getAs method uses Jackson to map the object at the given path
// to the given type
config.getAs("some.user", User.class);
```

### Creating multiple configs

To create multiple configs you need to bind everything yourself in a custom Guice module.

```java
public final class MyCustomConfigModule extends AbstractModule {
  
  @Override
  public void configure() {
    bind(Config.class)
      .annotatedWith(MyCustomConfig.class)
      .toProvider(JacksonConfigAssembler.create(yourObjectMapper, yourConfig));
    // The JacksonConfigAssembler will try to create a config with the given ObjectMapper
    // by reading the content of the InputStream.
    // The config you provide in the create method needs to be a InputStream.
  }
}
```

## Used Dependencies

- [Guice](https://github.com/google/guice)
- [Jackson Databind](https://github.com/FasterXML/jackson-databind)
- [Jackson Dataformat Yaml](https://github.com/FasterXML/jackson-dataformats-text/tree/master/yaml)
- [AssertJ](https://github.com/joel-costigliola/assertj-core)
- [JUnit 5](https://github.com/junit-team/junit5)