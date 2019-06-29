dependencies {
  compile(Deps.guice)
  compile(Deps.guava)

  compile(Deps.Jackson.databind)
  compile(Deps.Jackson.Dataformat.yaml)

  testCompile(Deps.Test.assertj)
  testCompile(Deps.Test.JUnit.api)
  testRuntimeOnly(Deps.Test.JUnit.engine)
}