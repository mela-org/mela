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
// The getList tries to map all objects in an array / list to the given type
config.getList("some.userList", User.class);
```

### Creating multiple configs

#### Create your Binding Annotation

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD, ElementType.PARAMETER)
@BindingAnnotation // This is important for Guice. So don't forget it
@interface MyCustomConfig {
}
```

#### Register the Custom Config

```java
// You need to register your custom config when creating the ConfigModule
Module module = ConfigModule.builder()
        .rootProvider(JsonConfigProvider.of(new File("config.json")))
        .customConfig()
          .annotatedWith(MyCustomConfig.class)
          .providedBy(YamlConfigProvider.of(new File("moneySettings.yml")))
          .register()
        .build();
// You can register as many custom configs as you want
// Now you can continue as in the example above
// ...
```

#### Inject values from a config

You can inject a value from a config by using the `@ConfigValue` annotation.

```java
public class Foo {
  @MyCustomConfig // Use the config which is bind to a custom config
  @ConfigValue("someNumber")
  int numberFromMyCustomConfig;
  
  @ConfigValue("someString")
  String someString;
}

// We will use the injector from the example above
Foo foo = new Foo();
injector.injectMembers(foo);
// The value for "numberFromMyCustomConfig" will be taken from the config which was bind to
// the "MyCustomConfig" annotation
// The value for "someString" will be taken from the root config.
``` 

## Used Dependencies

- [Guice](https://github.com/google/guice)
- [Jackson Databind](https://github.com/FasterXML/jackson-databind)
- [Jackson Dataformat Yaml](https://github.com/FasterXML/jackson-dataformats-text/tree/master/yaml)
- [AssertJ](https://github.com/joel-costigliola/assertj-core)
- [JUnit 5](https://github.com/junit-team/junit5)