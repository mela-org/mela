dependencies {
  compile(Deps.reactor_core)
  compile(Deps.guava)

  testCompile(Deps.Test.assert4j)
  testCompile(Deps.Test.JUnit.api)
  testRuntimeOnly(Deps.Test.JUnit.engine)
}